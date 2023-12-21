package ddwu.com.mobile.project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddwu.com.mobile.project.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
	private lateinit var binding: FragmentSearchBinding

	override fun onCreateView (
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSearchBinding.inflate(layoutInflater)

		return binding.root
	}
}