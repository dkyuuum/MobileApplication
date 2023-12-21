package ddwu.com.mobile.project.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ddwu.com.mobile.project.ui.ExerciseFragment
import ddwu.com.mobile.project.ui.FoodFragment

class DiaryVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int = 2

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> ExerciseFragment()
			else -> FoodFragment()
		}
	}
}