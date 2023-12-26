package ddwu.com.mobile.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.project.data.Search
import ddwu.com.mobile.project.databinding.ItemSearchBinding

class SearchRVAdapter : RecyclerView.Adapter<SearchRVAdapter.SearchHolder>() {
	var search: List<Search>? = null

	override fun getItemCount(): Int {
		return search?.size ?: 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
		val itemBinding =
			ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SearchHolder(itemBinding)
	}

	override fun onBindViewHolder(holder: SearchHolder, position: Int) {
		holder.itemBinding.tvSearchName.text = search?.get(position).toString()
		holder.itemBinding.tvSearchAddress.setOnClickListener {
			clickListener?.onItemClick(it, position)
		}
	}

	class SearchHolder(val itemBinding: ItemSearchBinding) : RecyclerView.ViewHolder(itemBinding.root)

	interface OnItemClickListner {
		fun onItemClick(view: View, position: Int)
	}

	var clickListener: OnItemClickListner? = null

	fun setOnItemClickListener(listener: OnItemClickListner) {
		this.clickListener = listener
	}
	inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(search: Search) {
			binding.tvSearchName.text = search.name
			binding.tvSearchAddress.text = search.address
		}
	}
}