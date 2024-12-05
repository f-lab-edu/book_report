package com.towitty.bookreport.presentation.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.towitty.bookreport.R
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.presentation.ui.HomeViewModel
import com.towitty.bookreport.presentation.ui.common.BookReportIcons
import com.towitty.bookreport.presentation.ui.common.FavoriteIconState
import com.towitty.bookreport.presentation.ui.common.IconState
import com.towitty.bookreport.presentation.ui.components.BookCard
import com.towitty.bookreport.presentation.ui.components.BookReportCard
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    bookReportListState: State<List<BookReportEntity>>,
    onNavigateBookDetail: (bookIsbn: String) -> Unit,
    onNavigateBookReport: (bookReportId: Int) -> Unit,
    onMoveSettings: (context: Context) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val favoriteBooksState by homeViewModel.favoriteBooks.collectAsState()
    val favoriteBookReportsState by homeViewModel.favoriteBookReports.collectAsState()

    Surface(Modifier.padding(16.dp)) {
        val context = LocalContext.current
        Column {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = { onMoveSettings(context) },
                        content = { Icon(BookReportIcons.Settings, contentDescription = null) }
                    )
                }
            )

            HomeTitleSlot(
                text = stringResource(R.string.title_recommended_book),
            ) {
                MonthRecommendedBook()
            }
            HomeTitleSlot(
                text = stringResource(R.string.title_favorite_list),
                isVisibleIcon = true,
                onChangeCard = {},
                onChangeList = {}
            ) {
                FavoriteList(
                    favoriteBooks = favoriteBooksState,
                    favoriteBookReports = favoriteBookReportsState,
                    onNavigateBookDetail = onNavigateBookDetail,
                    onNavigateBookReport = onNavigateBookReport
                )
            }
            HomeTitleSlot(
                text = stringResource(R.string.title_bookreport_list),
                isVisibleIcon = true,
                onChangeCard = {},
                onChangeList = {}
            ) {
                BookReportList(
                    bookReportListState,
                    onMoveBookReport = onNavigateBookReport
                )
            }
        }
    }
}

@Composable
fun MonthRecommendedBook() {
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { 3 }
    )
    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 64.dp),
            pageSpacing = 16.dp,
            modifier = Modifier.wrapContentSize()
        ) { page ->
            BookCard(
                book = emptyBook,
                modifier = Modifier
                    .graphicsLayer {
                        // 페이지 전환 애니메이션 계산
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        // 크기 조절
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        // 투명도 조절
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                onClick = {},
            )
        }

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun FavoriteList(
    favoriteBooks: List<Book>,
    favoriteBookReports: List<BookReport>,
    onNavigateBookDetail: (bookId: String) -> Unit = {},
    onNavigateBookReport: (bookReportId: Int) -> Unit,
) {
    LazyRow(modifier = Modifier.wrapContentSize()) {
        items(
            items = favoriteBooks,
            key = { it.id }
        ) { favoriteBook ->
            BookCard(book = favoriteBook) {
                onNavigateBookDetail(favoriteBook.isbn)
            }
        }
        items(
            items = favoriteBookReports,
            key = { it.id }
        ) { favoriteBookReport ->
            BookReportCard(bookReport = favoriteBookReport) {
                onNavigateBookReport(favoriteBookReport.id)
            }
        }
    }
}

@Composable
fun BookReportList(
    bookReportListState: State<List<BookReportEntity>>,
    onMoveBookReport: (bookReportId: Int) -> Unit
) {
    val bookReportList by bookReportListState
    LazyColumn {
        items(items = bookReportList, key = { it.id }) { bookReport ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        onMoveBookReport(bookReport.id)
                    }
            ) {
                val (title, date) = createRefs()
                Text(
                    text = bookReport.title,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = bookReport.date,
                    modifier = Modifier.constrainAs(date) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

@Composable
fun HomeTitleSlot(
    text: String,
    isVisibleIcon: Boolean = false,
    iconState: IconState = FavoriteIconState(),
    onChangeList: () -> Unit = {},
    onChangeCard: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(8.dp))

        val (title, cardIcon, listIcon) = createRefs()
        Text(
            text,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(200.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(8.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        if (isVisibleIcon) {
            IconButton(
                onClick = onChangeList,
                modifier = Modifier
                    .constrainAs(listIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(cardIcon.start)
                    }
            ) {
                Icon(
                    imageVector = BookReportIcons.DensityMedium,
                    contentDescription = stringResource(id = R.string.description_card_btn),
                    tint = if (iconState.isList) Color.Black else Color.Gray,
                )
            }
            IconButton(
                onClick = onChangeCard,
                modifier = Modifier
                    .constrainAs(cardIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(
                    imageVector = BookReportIcons.GridView,
                    contentDescription = stringResource(id = R.string.description_list_btn),
                    tint = if (iconState.isCard) Color.Black else Color.Gray,
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    content()

    Spacer(modifier = Modifier.height(8.dp))

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = Color.Gray)
    )


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun MonthRecommendedBookPreview(modifier: Modifier = Modifier) {
    MonthRecommendedBook()
}

@Preview(showBackground = true)
@Composable
fun TitleSlotPreview(modifier: Modifier = Modifier) {
    Column {
        HomeTitleSlot("이달의 추천 Book") { Text("aslfkmaasfasfsafafasfafs\naslfkmlasfm") }
        HomeTitleSlot("즐겨 찾기", true) { Text("aslfkmaasfasfsafafasfafs\naslfkmlasfm") }
        HomeTitleSlot("독후감 목록", true) { Text("aslfkmaasfasfsafafasfafs\naslfkmlasfm") }
    }

}