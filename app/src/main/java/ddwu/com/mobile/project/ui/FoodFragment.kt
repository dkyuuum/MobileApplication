package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.adapter.FoodRVAdapter
import ddwu.com.mobile.project.adapter.SearchRVAdapter
import ddwu.com.mobile.project.data.Food
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.FragmentFoodBinding
import ddwu.com.mobile.project.network.FoodAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodFragment : Fragment() {
	private lateinit var binding: FragmentFoodBinding
	private val TAG = "FoodFragment"
	lateinit var adapter : FoodRVAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFoodBinding.inflate(layoutInflater)

		adapter = FoodRVAdapter()
		binding.rvFood.adapter = adapter
		binding.rvFood.layoutManager = LinearLayoutManager(context)

		val retrofit = Retrofit.Builder()
			.baseUrl(resources.getString(R.string.food_api_url))
			.addConverterFactory(GsonConverterFactory.create())
			.build()

		val service = retrofit.create(FoodAPIService::class.java)
		val adapter = FoodRVAdapter()

		binding.btnSearch.setOnClickListener {
			val keyword = binding.etSearch.text.toString()

			val apiCallback = object : Callback<Food> {
				override fun onResponse(call: Call<Food>, response: Response<Food>) {
					if (response.isSuccessful) {
						val root: Food? = response.body()
						adapter.food = root?.items
						adapter.notifyDataSetChanged()

					} else {
						Log.d(TAG, "Unsuccessful Response")
						Log.d(TAG, response.errorBody()!!.string())     // 응답 오류가 있을 때 상세정보 확인
					}
				}

				override fun onFailure(call: Call<Food>, t: Throwable) {
					Log.d(TAG, "OpenAPI Call Failure ${t.message}")
				}
			}

			val apiCall: Call<Food> = service.searchFoodCalorie(
				resources.getString(R.string.client_id),
				"20210783",
				"xml",
				1,
				10,
				keyword
			)

			apiCall.enqueue(apiCallback)

		}
		return binding.root
	}
}