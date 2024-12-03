package com.towitty.bookreport.data.network

import com.towitty.bookreport.BuildConfig
import com.towitty.bookreport.data.network.model.NetworkSearchBook
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    /**
     * @query 검색어. UTF-8로 인코딩 되어야 합니다.
     * @display 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
     * @start 검색 시작 위치(기본값: 1, 최댓값: 100)
     * @sort 검색 결과 정렬 방법, [sim] 정확도 순으로 내림차순 정렬(기본값), [date] 출간일 순으로 내림차순 정렬
     * @d_titl 도서명 검색, 검색할 ISBN. d_titl 과 d_isbn 중 1개 이상의 파라미터를 사용해야 합니다.
     * @d_isbn ISBN 검색
     */
    @GET("book.json")
    suspend fun getSearchBook(
        @Query("query") query: String,
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.NAVER_API_CLIENT,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.NAVER_API_SECRET,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "sim",
    ): Response<NetworkSearchBook>

    @GET("book.json")
    suspend fun getSearchBookDetail(
        @Header("X-Naver-Client-Id") clientId: String = BuildConfig.NAVER_API_CLIENT,
        @Header("X-Naver-Client-Secret") clientSecret: String = BuildConfig.NAVER_API_SECRET,
        @Query("query") query: String,
        @Query("display") display: Int = 10,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "sim",
        @Query("d_titl") title: String = "",
        @Query("d_isbn") isbn: String,
    ): Response<NetworkSearchBook>
}