package ddwu.com.mobile.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ddwu.com.mobile.project.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {
	private lateinit var binding: FragmentDiaryBinding

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentDiaryBinding.inflate(layoutInflater)

		return binding.root
	}
}