package ddwu.com.mobile.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.databinding.ItemExerciseBinding

class DiaryRVAdapter(private val exerciseList: ArrayList<Exercise>) :
	RecyclerView.Adapter<DiaryRVAdapter.ViewHolder>() {

	interface MyItemClickListener {
		fun onItemClick(exercise: Exercise)
		fun onRemoveExercise(position: Int)
	}

	private lateinit var mItemClickListener: MyItemClickListener

	fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
		mItemClickListener = itemClickListener
	}

	fun addItem(exercise: Exercise) {
		exerciseList.add(exercise)
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		exerciseList.removeAt(position)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
		val binding: ItemExerciseBinding =
			ItemExerciseBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(exerciseList[position])
		holder.itemView.setOnClickListener { mItemClickListener.onItemClick(exerciseList[position]) }
		holder.binding.tvName.setOnClickListener {
			mItemClickListener.onRemoveExercise(
				position
			)
		}
	}

	override fun getItemCount(): Int = exerciseList.size

	inner class ViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(exercise: Exercise) {
			binding.tvContent.text = exercise.content
			binding.tvDate.text = exercise.date
			binding.tvName.text = exercise.name
			binding.tvTime.text = exercise.time

			binding.root.setOnLongClickListener {
				mItemClickListener.onRemoveExercise(adapterPosition)
				true
			}
		}
	}
}