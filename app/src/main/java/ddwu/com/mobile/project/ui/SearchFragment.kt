package ddwu.com.mobile.project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobile.project.adapter.DiaryRVAdapter
import ddwu.com.mobile.project.adapter.SearchRVAdapter
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), OnMapReadyCallback {
	private lateinit var binding: FragmentSearchBinding

	private lateinit var mapView: MapView
	private lateinit var googleMap: GoogleMap

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(layoutInflater)
		initSearchView()
		initRecyclerView()

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mapView = binding.mapView
		mapView.onCreate(savedInstanceState)
		mapView.getMapAsync(this)
	}

	override fun onMapReady(map: GoogleMap?) {
		if (map != null) {
			googleMap = map
			val location = LatLng(37.7749, -122.4194) // 좌표 설정 (위도, 경도)
			val markerOptions = MarkerOptions().position(location).title("Marker Title")
			googleMap.addMarker(markerOptions)
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
		}
	}

	override fun onResume() {
		super.onResume()
		mapView.onResume()
	}

	override fun onPause() {
		super.onPause()
		mapView.onPause()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		mapView.onDestroy()
	}

	override fun onLowMemory() {
		super.onLowMemory()
		mapView.onLowMemory()
	}
	private fun initSearchView() {
		// init SearchView
		binding.search.isSubmitButtonEnabled = true
		binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				// @TODO
				return false
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				// @TODO
				return true
			}
		})
	}

	private fun initRecyclerView() {
		val itemList = ArrayList<Search>()

		itemList.add(Search("워너비짐", "서울시 성북구 월곡동"))
		itemList.add(Search("백주년 헬스장", "서울시 성북구 하월곡동"))
		itemList.add(Search( "스포애니", "서울시 금천구 시흥동"))

		val searchRVAdapter = SearchRVAdapter(itemList)

		binding.rvSearch.adapter = searchRVAdapter
		binding.rvSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

	}
}
