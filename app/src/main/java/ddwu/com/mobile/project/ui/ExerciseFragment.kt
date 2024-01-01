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
	private lateinit var helper: ExerciseDBHelper
	private lateinit var exerciseRVAdapter: DiaryRVAdapter

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
		exerciseRVAdapter = DiaryRVAdapter(exerciseList)
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
		val cursor: Cursor = db.query(
			"exercise_table", columns, selection, selectionArgs,
			null, null, null, null
		)

		val exerciseList = arrayListOf<Exercise>()
		with(cursor) {
			while (moveToNext()) {
				val id = getInt(getColumnIndex(BaseColumns._ID))
				val date = getString(getColumnIndex(ExerciseDBHelper.COL_exercise_date))
				val name = getString(getColumnIndex(ExerciseDBHelper.COL_exercise_name))
				val content = getString(getColumnIndex(ExerciseDBHelper.COL_exercise_content))
				val time = getString(getColumnIndex(ExerciseDBHelper.COL_exercise_time))

				val dto = Exercise(id, date, name, content, time)
				exerciseList.add(dto)
			}
		}
		displayExerciseList(exerciseList)
		clickItem(exerciseList)

		var result: String = ""

		for (dto in exerciseList) {
			result += dto.toString() + "\n"
		}

		cursor.close()
		helper.close()
	}

	private fun clickItem(exerciseList: ArrayList<Exercise>) {
		exerciseRVAdapter.setMyItemClickListener(object : DiaryRVAdapter.MyItemClickListener {
			override fun onItemClick(exercise: Exercise) {

			}

			override fun onRemoveExercise(position: Int) {
				val removeList = exerciseList[position]
				exerciseList.removeAt(position)

				val db = helper.writableDatabase
				val whereClause = "${BaseColumns._ID}=?"
				val whereArgs = arrayOf(removeList.id.toString())

				db.delete("exercise_table", whereClause, whereArgs)

				exerciseRVAdapter.notifyDataSetChanged()
				requireActivity().supportFragmentManager.popBackStack()
			}
		})
	}
}
