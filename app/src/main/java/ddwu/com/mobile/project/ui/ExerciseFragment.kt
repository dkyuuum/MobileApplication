package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.project.adapter.DiaryRVAdapter
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {
	private lateinit var binding: FragmentExerciseBinding

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentExerciseBinding.inflate(layoutInflater)

		val itemList = ArrayList<Exercise>()

		itemList.add(Exercise("2023/12/22", "헬스", "스쿼트 50kg * 10, 10세트", "01:20:00"))
		itemList.add(Exercise("2023/12/20", "주짓수", "라쏘 가드", "01:00:00"))
		itemList.add(Exercise("2023/12/19", "수영", "수영 1000m", "00:30:00"))

		val exerciseRVAdapter = DiaryRVAdapter(itemList)

		binding.rvDiary.adapter = exerciseRVAdapter
		binding.rvDiary.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

		return binding.root
	}
}