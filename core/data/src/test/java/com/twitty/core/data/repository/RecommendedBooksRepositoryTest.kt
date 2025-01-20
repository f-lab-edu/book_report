package com.twitty.core.data.repository

import com.twitty.core.data.AssetLoader
import com.twitty.core.data.testdoubles.FakeAssetLoader
import com.twitty.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RecommendedBooksRepositoryTest {

    lateinit var subject: RecommendedBooksRepository
    lateinit var assetLoader: AssetLoader
    lateinit var ioDispatcher: CoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        assetLoader = FakeAssetLoader()
        ioDispatcher = UnconfinedTestDispatcher()
        subject = RecommendedBooksRepository(assetLoader, ioDispatcher)
    }

    @Test
    fun `fetchRecommendedBooks json 파일에 있는 모든 책들 반환`() = runTest {
        val testBook1 = Book(
            id = 1,
            title = "Android",
            link = "https://search.shopping.naver.com/book/catalog/32451971823",
            image = "https://shopping-phinf.pstatic.net/main_3245197/32451971823.20220519081242.jpg",
            author = "",
            discount = "109690",
            publisher = "Edizioni Accademiche Italiane",
            pubdate = "20160114",
            isbn = "9783639777833",
            description = "Un metodo semplice per consentire a tutti gli appassionati di conoscere il mondo Android e sviluppare applicazioni per smartphone e tablet con semplicita. Riassunto in poche pagine tutto quello che c'e da sapere sul funzionamento delle App basate sul sistema operativo di casa Google. Seguendo questa guida, con semplici passaggi, potete creare tutte le applicazioni su misura per voi. All'interno troverete un'applicazione completa e funzionante d'esempio."
        )

        val testBook2 = Book(
            id = 7,
            title = "Android Studio를 활용한 안드로이드 프로그래밍 (Android 14.0(U) 지원, 9판)",
            link = "https://search.shopping.naver.com/book/catalog/45326523619",
            image = "https://shopping-phinf.pstatic.net/main_4532652/45326523619.20241230071341.jpg",
            author = "우재남^박길식",
            discount = "32300",
            publisher = "한빛아카데미",
            pubdate = "20240119",
            isbn = "9791156640219",
            description = "프로그래밍 초보자도 할 수 있는 Java 기반 안드로이드 프로그래밍 교재\n\n이 책은 대학교나 IT 전문학원의 안드로이드 프로그래밍 과목 수강생을 대상으로 합니다. 기본적인 프로그래밍을 접해본 독자라면 Java 기초부터 시작하여 안드로이드 앱 개발까지 한번에 학습할 수 있도록 구성되어 있습니다. 특별히 이번 9판에서는 버전업을 적용하여 JDK 17, Android 14.0(U), Android Studio Hedgehog에서 실습할 수 있습니다. 또한 연습문제 일부를 변경하였으며, 13장에 ‘경기도 맛집 찾기 앱 만들기’ 프로젝트를 추가하였습니다. 마지막으로 안드로이드 프로그래밍을 하면서 접할 수 있는 다양한 오류나 실수까지 친절하게 안내하여 시행착오 없이 안드로이드를 빠르게 정복할 수 있을 것입니다.\n\n※ 본 도서는 대학 강의용 교재로 개발되었으므로 연습문제 해답은 제공하지 않습니다."
        )

        val books = subject.fetchRecommendedBooks().first()

        Assert.assertTrue(books.isNotEmpty())
        Assert.assertTrue(books.size == 10)
        Assert.assertTrue(books.contains(testBook1))
        Assert.assertTrue(books.contains(testBook2))

    }

}