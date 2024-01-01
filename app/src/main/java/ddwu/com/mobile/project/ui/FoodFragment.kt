package ddwu.com.mobile.project.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.adapter.FoodRVAdapter
import ddwu.com.mobile.project.data.FoodInfo
import ddwu.com.mobile.project.data.FoodItem
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
	private lateinit var loadingLayout: View
	private var result = 0

	private fun showLoading() {
		loadingLayout.visibility = View.VISIBLE
	}

	private fun hideLoading() {
		loadingLayout.visibility = View.GONE
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFoodBinding.inflate(layoutInflater)

		initRecyclerView()
		settingRetrofit()

		loadingLayout = binding.loadingLayout

		hideLoading()

		return binding.root
	}

	private fun initRecyclerView() {
		adapter = FoodRVAdapter()
		binding.rvFood.adapter = adapter
		binding.rvFood.layoutManager = LinearLayoutManager(context)
	}
	private fun settingRetrofit() {
		val okHttpClient= OkHttpClient.Builder()
			.connectTimeout(999999, TimeUnit.SECONDS)
			.readTimeout(999999, TimeUnit.SECONDS)
			.writeTimeout(999999, TimeUnit.SECONDS)
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

			// 로딩 화면
			showLoading()

			// 키보드 내리기
			val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

			val apiCallback = object : Callback<FoodInfo> {
				override fun onResponse(call: Call<FoodInfo>, response: Response<FoodInfo>) {
					if (response.isSuccessful) {
						// 로딩 화면 없앰
						hideLoading()

						val root: FoodInfo? = response.body()

						root?.list?.food?.let { foodList ->
							val filteredFoodList = foodList.filter { it.foodName.contains(keyword) }
							adapter.food = filteredFoodList
							adapter.notifyDataSetChanged()
						}
						adapter.itemClickListener = object : FoodRVAdapter.OnItemClickListener {
							override fun onItemClick(foodItem: FoodItem, position: Int) {
								var selectedCalories = foodItem.kcal.toInt()
								result += selectedCalories

								Toast.makeText(context, "총 칼로리: $result", Toast.LENGTH_SHORT).show()
							}
						}

					} else {
						Log.d(TAG, "검색 결과가 없습니다!")
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
				keyword
			)
			apiCall.enqueue(apiCallback)
		}
	}
}
