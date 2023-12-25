package ddwu.com.mobile.project.network

import ddwu.com.mobile.project.data.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MapAPIService {

	@GET("v1/search/book.json")
	fun getBooksByKeyword (
		@Header("X-Naver-Client-Id") clientId: String,
		@Header("X-Naver-Client-Secret") clientSecret: String,
		@Query("query") keyword: String,
	)  : Call<Search>

}