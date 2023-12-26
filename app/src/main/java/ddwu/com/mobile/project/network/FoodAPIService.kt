package ddwu.com.mobile.project.network

import ddwu.com.mobile.project.data.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodAPIService {
	@GET("{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}")
	fun searchFoodCalorie(
		@Path("keyId") keyId: String,
		@Path("serviceId") serviceId: String,
		@Path("dataType") dataType: String,
		@Path("startIdx") startIdx: Int,
		@Path("endIdx") endIdx: Int,
		@Query("foodName") foodName: String
	): Call<Food>
}