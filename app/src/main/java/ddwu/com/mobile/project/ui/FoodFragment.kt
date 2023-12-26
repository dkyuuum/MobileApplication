package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.adapter.FoodRVAdapter
import ddwu.com.mobile.project.data.FoodInfo
import ddwu.com.mobile.project.databinding.FragmentFoodBinding
import ddwu.com.mobile.project.network.FoodAPIService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FoodFragment : Fragment() {
	private lateinit var binding: FragmentFoodBinding
	private val TAG = "FoodFragment"
	private lateinit var adapter: FoodRVAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFoodBinding.inflate(layoutInflater)

		adapter = FoodRVAdapter()
		binding.rvFood.adapter = adapter
		binding.rvFood.layoutManager = LinearLayoutManager(context)

		val okHttpClient = OkHttpClient.Builder()
			.connectTimeout(180, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(180, TimeUnit.SECONDS)
			.build()

		var gson = GsonBuilder().setLenient().create()

		val retrofit = Retrofit.Builder()
			.baseUrl(resources.getString(R.string.food_api_url))
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(okHttpClient)
			.build()

		val service = retrofit.create(FoodAPIService::class.java)

		binding.btnSearch.setOnClickListener {
			val keyword = binding.etSearch.text.toString()
			Log.d("FoodFragment", "keyword: $keyword")

			val apiCallback = object : Callback<FoodInfo> {
				override fun onResponse(call: Call<FoodInfo>, response: Response<FoodInfo>) {
					if (response.isSuccessful) {
						val root: FoodInfo? = response.body()
						adapter.food = root?.list?.food
						adapter.notifyDataSetChanged()

					} else {
						Log.d(TAG, "Unsuccessful Response")
						Log.d(TAG, response.errorBody()!!.string())
					}
				}

				override fun onFailure(call: Call<FoodInfo>, t: Throwable) {
					Log.d(TAG, "OpenAPI Call Failure ${t.message}")
				}
			}

			val apiCall: Call<FoodInfo> = service.searchFoodCalorie(
				resources.getString(R.string.client_id),
				"I2790",
				"json",
				"1",
				"10",
				keyword
			)
			apiCall.enqueue(apiCallback)
		}

		return binding.root
	}
}
