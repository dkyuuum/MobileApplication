package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ddwu.com.mobile.project.adapter.DiaryVPAdapter
import ddwu.com.mobile.project.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {
	private lateinit var binding: FragmentDiaryBinding
	private val information = arrayListOf("운동", "음식")

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentDiaryBinding.inflate(layoutInflater)
		initViewPager()

		return binding.root
	}

	private fun initViewPager() {
		val albumAdapter = DiaryVPAdapter(this)

		binding.viewPager.adapter = albumAdapter
		TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
			tab.text = information[position]
		}.attach()
	}
}