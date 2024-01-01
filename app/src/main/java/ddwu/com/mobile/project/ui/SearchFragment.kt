import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobile.project.adapter.DiaryRVAdapter
import ddwu.com.mobile.project.adapter.SearchRVAdapter
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), OnMapReadyCallback {
	private lateinit var binding: FragmentSearchBinding

	private lateinit var mapView: MapView

	private lateinit var fusedLocationClient : FusedLocationProviderClient
	private lateinit var geocoder : Geocoder
	private lateinit var currentLoc : Location

	private lateinit var googleMap : GoogleMap
	private var centerMarker : Marker? = null

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(layoutInflater)
		initRecyclerView()
		// startLocUpdates()
		return binding.root
	}

	override fun onMapReady(map: GoogleMap) {
		if (map != null) {
			googleMap = map
			val location = LatLng(37.7749, 127.04153) // 좌표 설정 (위도, 경도)
			val markerOptions = MarkerOptions().position(location).title("Marker Title")
			googleMap.addMarker(markerOptions)
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
		}
	}
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mapView = binding.mapView
		mapView.onCreate(savedInstanceState)
		mapView.getMapAsync(this)
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

	private fun initRecyclerView() {
		val itemList = ArrayList<Search>()

		itemList.add(Search("워너비짐", "서울시 성북구 월곡동"))
		itemList.add(Search("백주년 헬스장", "서울시 성북구 하월곡동"))
		itemList.add(Search( "스포애니", "서울시 금천구 시흥동"))

		val searchRVAdapter = SearchRVAdapter()

		binding.rvSearch.adapter = searchRVAdapter
		binding.rvSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

	}

	/*마커 추가*/
	fun addMarker(targetLoc: LatLng) {  // LatLng(37.606320, 127.041808)
		val markerOptions: MarkerOptions = MarkerOptions() // 마커를 표현하는 Option 생성
		markerOptions.position(targetLoc)
			.title("마커 제목")
			.snippet("말풍선")
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

		centerMarker = googleMap.addMarker(markerOptions) // 지도에 마커 추가, 추가마커 반환
		centerMarker?.showInfoWindow() // 마커 터치 시 InfoWindow 표시
		centerMarker?.tag = "database_id" // 마커에 관련 정보(object) 저장

		googleMap.setOnMarkerClickListener { marker ->
			Toast.makeText(context, marker.tag.toString(), Toast.LENGTH_SHORT).show()
			false // true일 경우 이벤트 처리 종료이므로 info window 미출력
		}
		googleMap.setOnInfoWindowClickListener { marker ->
			Toast.makeText(context, marker.title, Toast.LENGTH_SHORT).show()
		}
	}

	/*위치 정보 수신 시 수행할 동작을 정의하는 Callback*/
	private val locCallback : LocationCallback = object : LocationCallback() {
		override fun onLocationResult(locResult: LocationResult) {
			currentLoc = locResult.locations.get(0)

//			geocoder.getFromLocation(currentLoc.latitude, currentLoc.longitude, 5) { addresses ->
//				CoroutineScope(Dispatchers.Main).launch {
//					Toast.makeText(context, "위도: ${currentLoc.latitude}, 경도: ${currentLoc.longitude}", Toast.LENGTH_LONG).show()
//					Toast.makeText(context, addresses?.get(0)?.getAddressLine(0).toString(), Toast.LENGTH_LONG).show()
//				}
//			}
			CoroutineScope(Dispatchers.Main).launch {
                val addresses = geocoder.getFromLocation(currentLoc.latitude, currentLoc.longitude, 5)
				Toast.makeText(context, "위도: ${currentLoc.latitude}, 경도: ${currentLoc.longitude}", Toast.LENGTH_LONG).show()
				Toast.makeText(context, addresses?.get(0)?.getAddressLine(0).toString(), Toast.LENGTH_LONG).show()
            }

			val targetLoc: LatLng = LatLng(currentLoc.latitude, currentLoc.longitude)
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
		}
	}

	private val locRequest = LocationRequest.Builder(5000)
		.setMinUpdateIntervalMillis(3000)
		.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
		.build()

	/*위치 정보 수신 시작*/
	@SuppressLint("MissingPermission")
	private fun startLocUpdates() {
		fusedLocationClient.requestLocationUpdates(
			locRequest,     // LocationRequest 객체
			locCallback,    // LocationCallback 객체
			Looper.getMainLooper()  // System 메시지 수신 Looper
		)
	}
}