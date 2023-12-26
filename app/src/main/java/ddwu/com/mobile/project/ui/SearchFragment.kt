package ddwu.com.mobile.project.ui

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.adapter.SearchRVAdapter
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class SearchFragment : Fragment() {
	private lateinit var binding: FragmentSearchBinding
	private val TAG = "SearchFragment"

	private lateinit var fusedLocationClient : FusedLocationProviderClient
	private lateinit var geocoder : Geocoder
	private lateinit var currentLoc : Location

	private lateinit var googleMap : GoogleMap
	private var centerMarker : Marker? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(layoutInflater)
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
		geocoder = Geocoder(requireContext(), Locale.getDefault())

		val mapFragment: SupportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
		mapFragment.getMapAsync (mapReadyCallback) // map 정보 가져오기 (Callback 호출)

		initRecyclerView()

		return binding.root
	}

	private fun initRecyclerView() {
		val itemList = ArrayList<Search>()

		itemList.add(Search("워너비짐", "서울시 성북구 월곡동"))
		itemList.add(Search("백주년 헬스장", "서울시 성북구 하월곡동"))
		itemList.add(Search("스포애니", "서울시 금천구 시흥동"))

		val searchRVAdapter = SearchRVAdapter()

		binding.rvSearch.adapter = searchRVAdapter
		binding.rvSearch.layoutManager =
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
	}

	private val mapReadyCallback = OnMapReadyCallback { map ->
		googleMap = map
		Log.d(TAG, "GoogleMap is ready")

		googleMap.setOnMapClickListener { latLng: LatLng ->
			Toast.makeText(context, latLng.toString(), Toast.LENGTH_SHORT).show()
		}

		googleMap.setOnMapLongClickListener { latLng: LatLng ->
			Toast.makeText(context, latLng.toString(), Toast.LENGTH_SHORT).show()
		}
	}
}