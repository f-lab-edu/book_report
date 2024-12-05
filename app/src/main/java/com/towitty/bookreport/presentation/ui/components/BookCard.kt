package com.towitty.bookreport.presentation.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.towitty.bookreport.R
import com.towitty.bookreport.data.network.model.NetworkBook
import com.towitty.bookreport.data.network.model.emptyNetworkBook


@Composable
fun BookCard(networkBook: NetworkBook, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }

    Glide.with(LocalContext.current).asBitmap().load(networkBook.image).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmap.value = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            bitmap.value = null
        }
    })

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(166.dp)
    ) {
        Row {
            bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
                Image(
                    bitmap = fetchedBitmap,
                    contentDescription = null,
                    modifier = Modifier.size(166.dp)
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(166.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text(networkBook.title)
                Text(networkBook.author)
                Text(networkBook.price)
                Text(networkBook.publisher)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookCardPreview(modifier: Modifier = Modifier) {
    BookCard(
        networkBook = emptyNetworkBook,
        modifier = modifier,
        onClick = {}
    )
}