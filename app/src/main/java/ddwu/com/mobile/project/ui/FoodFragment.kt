package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ddwu.com.mobile.project.databinding.FragmentFoodBinding

class FoodFragment : Fragment() {
	private lateinit var binding: FragmentFoodBinding

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFoodBinding.inflate(layoutInflater)

		return binding.root
	}
}