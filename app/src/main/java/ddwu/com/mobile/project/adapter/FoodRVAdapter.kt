package ddwu.com.mobile.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.project.data.FoodItem
import ddwu.com.mobile.project.databinding.ItemFoodBinding

class FoodRVAdapter : RecyclerView.Adapter<FoodRVAdapter.FoodHolder>() {
	var food: List<FoodItem>? = null
	override fun getItemCount(): Int {
		return food?.size ?: 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
		val itemBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FoodHolder(itemBinding)
	}

	override fun onBindViewHolder(holder: FoodHolder, position: Int) {
		holder.itemBinding.tvFoodName.text = food?.get(position).toString()
		holder.itemBinding.tvFoodCalories.text = food?.get(position).toString()
	}

	class FoodHolder(val itemBinding: ItemFoodBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
