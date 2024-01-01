package ddwu.com.mobile.project.network

import ddwu.com.mobile.project.data.FoodInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodAPIService {
	@GET("api/{keyId}/{serviceId}/{dataType}/1/100")
	fun searchFoodCalorie(
		@Path("keyId") keyId: String,
		@Path("serviceId") serviceId: String,
		@Path("dataType") dataType: String,
		@Query("DESC_KOR") foodName: String
	): Call<FoodInfo>
}