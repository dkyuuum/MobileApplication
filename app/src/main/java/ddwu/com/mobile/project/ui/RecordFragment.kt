package ddwu.com.mobile.project.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.databinding.FragmentRecordBinding
import java.util.Calendar
import java.util.Timer

class RecordFragment : Fragment() {
	private lateinit var binding: FragmentRecordBinding
	//  뒤로가기 버튼을 누른 시각을 저장하는 속성
	var initTime = 0L

	// 멈춘 시각을 저장하는 속성
	var pauseTime = 0L

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentRecordBinding.inflate(layoutInflater)
		calendarSetting()
		timerSetting()

		return binding.root
	}

	private fun calendarSetting() {
		binding.btnCalendar.setOnClickListener {
			val cal = Calendar.getInstance()
			val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
				binding.tvExerciseDate.text = "${year}/${month}/${day}"
			}

			DatePickerDialog(
				requireContext(),
				data,
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH)
			).show()
		}
	}

	private fun timerSetting() {

		binding.btnTimeStart.setOnClickListener {
//			binding.btnTimeStart.text = "PAUSE"    // START > PAUSE 변경

			binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
			binding.chronometer.start()

			// 버튼 표시 여부 조정
			binding.btnTimeStart.isEnabled = false
			binding.btnTimeReset.isEnabled = true
			binding.btnTimeStop.isEnabled = true
		}
		binding.btnTimeStop.setOnClickListener {
			pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
			binding.chronometer.stop()
			binding.btnTimeStart.isEnabled = true
			binding.btnTimeReset.isEnabled = true
			binding.btnTimeStop.isEnabled = false
		}

		binding.btnTimeReset.setOnClickListener {
			pauseTime = 0L
			binding.chronometer.base = SystemClock.elapsedRealtime()
			binding.chronometer.stop()
			binding.btnTimeStart.isEnabled = true
			binding.btnTimeReset.isEnabled = false
			binding.btnTimeStop.isEnabled = false
		}

//		// 뒤로가기 버튼 이벤트 핸들러
//		override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//
//			// 뒤로가기 버튼을 눌렀을 때 처리
//			if (keyCode === KeyEvent.KEYCODE_BACK) {
//
//				// 뒤로가기 버튼을 처음 눌렀거나 누른 지 3초가 지났을 때 처리
//				if(System.currentTimeMillis() - initTime > 3000) {
//					Toast.makeText(context, "종료하려면 한 번 더 누르세요!!", Toast.LENGTH_SHORT).show()
//					initTime = System.currentTimeMillis()
//					return true
//				}
//			}
//
//			return super.onKeyDown(keyCode, event)
//		}
	}
}