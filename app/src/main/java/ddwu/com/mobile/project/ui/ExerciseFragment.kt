package ddwu.com.mobile.project.ui

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
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
	private lateinit var helper : ExerciseDBHelper

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentExerciseBinding.inflate(layoutInflater)
		helper = ExerciseDBHelper(requireContext())
		showFoods()

		return binding.root
	}

	private fun displayExerciseList(exerciseList: ArrayList<Exercise>) {
		val exerciseRVAdapter = DiaryRVAdapter(exerciseList)

		binding.rvDiary.adapter = exerciseRVAdapter
		binding.rvDiary.layoutManager =
			LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
	}

	@SuppressLint("Range")
	fun showFoods() {
		val db = helper.readableDatabase
		val columns = null
		val selection = null
		val selectionArgs = null
		val cursor : Cursor = db.query(
			"exercise_table", columns, selection, selectionArgs,
			null, null, null, null
		)

		val exerciseList = arrayListOf<Exercise>()
		with (cursor) {
			while (moveToNext()) {
				val date = getString( getColumnIndex(ExerciseDBHelper.COL_exercise_date) )
				val name = getString ( getColumnIndex(ExerciseDBHelper.COL_exercise_name) )
				val content = getString ( getColumnIndex(ExerciseDBHelper.COL_exercise_content) )
				val time = getString ( getColumnIndex(ExerciseDBHelper.COL_exercise_time) )

				val dto = Exercise(date, name, content, time)
				exerciseList.add(dto)
			}
		}
		displayExerciseList(exerciseList)

		var result : String = ""

		for (dto in exerciseList) {
			result += dto.toString() + "\n"
		}

		cursor.close()
		helper.close()
	}
}
