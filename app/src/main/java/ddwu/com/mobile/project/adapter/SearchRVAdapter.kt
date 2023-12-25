package ddwu.com.mobile.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.project.data.Exercise
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.ItemExerciseBinding
import ddwu.com.mobile.project.databinding.ItemSearchBinding

class SearchRVAdapter(private val searchList: ArrayList<Search>) :
	RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

	interface MyItemClickListener {
		fun onItemClick(album: Search)
		fun onRemoveExercise(position: Int)
	}

	private lateinit var mItemClickListener: MyItemClickListener

	fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
		mItemClickListener = itemClickListener
	}

	fun addItem(search: Search) {
		searchList.add(search)
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		searchList.removeAt(position)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
		val binding: ItemSearchBinding =
			ItemSearchBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(searchList[position])
		holder.itemView.setOnClickListener { mItemClickListener.onItemClick(searchList[position]) }
		holder.binding.tvSearchName.setOnClickListener {
			mItemClickListener.onRemoveExercise(
				position
			)
		}
	}

	override fun getItemCount(): Int = searchList.size

	inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(search: Search) {
			binding.tvSearchName.text = search.name
			binding.tvSearchAddress.text = search.address
		}
	}
}