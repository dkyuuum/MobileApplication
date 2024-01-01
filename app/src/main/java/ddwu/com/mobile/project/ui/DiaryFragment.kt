package ddwu.com.mobile.project.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ddwu.com.mobile.project.R
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
		initRecordButton()

		return binding.root
	}

	// 기록하기 Button 설정
	private fun initRecordButton() {
		binding.btnRecord.setOnClickListener {
			val transaction = parentFragmentManager.beginTransaction()
			transaction
				.replace(R.id.fl_container, RecordFragment())
				.addToBackStack(null)
				.commit()
		}
	}

	private fun initViewPager() {
		val albumAdapter = DiaryVPAdapter(this)

		binding.viewPager.adapter = albumAdapter
		TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
			tab.text = information[position]
		}.attach()

		// ExerciseFragment에서만 버튼 띄움
		binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				super.onPageSelected(position)
				if (position == 1) {
					binding.btnRecord.visibility = View.GONE
				} else {
					binding.btnRecord.visibility = View.VISIBLE
				}
			}
		})
	}
}