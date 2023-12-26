package ddwu.com.mobile.project.ui

import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import ddwu.com.mobile.project.R
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.databinding.FragmentRecordBinding
import java.util.Calendar

class RecordFragment : Fragment() {
	private lateinit var binding: FragmentRecordBinding
	private lateinit var helper: ExerciseDBHelper
	private var pauseTime = 0L

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentRecordBinding.inflate(layoutInflater)
		helper = ExerciseDBHelper(requireContext())

		settingCalendar()
		settingTimer()

		binding.btnSave.setOnClickListener {
			recordInfo()
		}

		binding.btnCancel.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		return binding.root
	}

	private fun settingCalendar() {
		binding.btnCalendar.setOnClickListener {
			val cal = Calendar.getInstance()
			val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
				binding.tvExerciseDate.text = "${year}/${month+1}/${day}"
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

	private fun settingTimer() {
		binding.btnTimeStart.setOnClickListener {
			binding.timeChronometer.base = SystemClock.elapsedRealtime() + pauseTime
			binding.timeChronometer.start()

			binding.btnTimeStart.isEnabled = false
			binding.btnTimeReset.isEnabled = true
			binding.btnTimeStop.isEnabled = true
		}
		binding.btnTimeStop.setOnClickListener {
			pauseTime = binding.timeChronometer.base - SystemClock.elapsedRealtime()
			binding.timeChronometer.stop()
			binding.btnTimeStart.isEnabled = true
			binding.btnTimeReset.isEnabled = true
			binding.btnTimeStop.isEnabled = false
		}

		binding.btnTimeReset.setOnClickListener {
			pauseTime = 0L
			binding.timeChronometer.base = SystemClock.elapsedRealtime()
			binding.timeChronometer.stop()
			binding.btnTimeStart.isEnabled = true
			binding.btnTimeReset.isEnabled = false
			binding.btnTimeStop.isEnabled = false
		}
	}

	private fun recordInfo() {
		val db = helper.writableDatabase
		val newRow = ContentValues()
		val checkedRadioButtonId = binding.etExerciseKindContent.checkedRadioButtonId

		newRow.put(
			ExerciseDBHelper.COL_exercise_date,
			binding.tvExerciseDate.text.toString()
		)

		if (checkedRadioButtonId != -1) { // 라디오 버튼이 선택된 경우
			val selectedRadioButton: RadioButton =
				binding.etExerciseKindContent.findViewById(checkedRadioButtonId)
			val selectedText: String = selectedRadioButton.text.toString()

			newRow.put(
				ExerciseDBHelper.COL_exercise_name,
				selectedText
			)
		} else {
			newRow.put(
				ExerciseDBHelper.COL_exercise_name,
				"선택 안 됨"
			)
		}

		newRow.put(
			ExerciseDBHelper.COL_exercise_content,
			binding.etExerciseContent.text.toString()
		)
		newRow.put(
			ExerciseDBHelper.COL_exercise_time,
			binding.timeChronometer.text.toString()
		)

		db.insert("exercise_table", null, newRow)
		helper.close()

		requireActivity().supportFragmentManager.popBackStack()
	}
}
