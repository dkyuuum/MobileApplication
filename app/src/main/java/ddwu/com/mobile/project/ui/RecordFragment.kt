package ddwu.com.mobile.project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ddwu.com.mobile.project.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {
	private lateinit var binding: FragmentRecordBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentRecordBinding.inflate(layoutInflater)

		return binding.root
	}
}