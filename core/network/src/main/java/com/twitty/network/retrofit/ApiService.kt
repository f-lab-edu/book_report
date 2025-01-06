package com.twitty.network.retrofit

import com.twitty.network.BuildConfig
import com.twitty.network.model.NetworkBookContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    /**
     * @query 검색어. UTF-8로 인코딩 되어야 합니다. isbn 으로 검색 가능
     * @display 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
     * @start 검색 시작 위치(기본값: 1, 최댓값: 100)
     * @sort 검색 결과 정렬 방법, [sim] 정확도 순으로 내림차순 정렬(기본값), [date] 출간일 순으로 내림차순 정렬
     */
    @GET("book.json")
    suspend fun fetchSearchBook(
        @Query("query") query: String,
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.NAVER_API_CLIENT,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.NAVER_API_SECRET,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "sim",
    ): Response<NetworkBookContainer>

    @GET("book_adv.json")
    suspend fun fetchDetailSearchBookByIsbn(
        @Query("query") query: String,
        @Query("d_isbn") isbn: String = query,
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.NAVER_API_CLIENT,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.NAVER_API_SECRET,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
    ): Response<NetworkBookContainer>
}