package ddwu.com.mobile.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.project.data.FoodItem
import ddwu.com.mobile.project.databinding.ItemFoodBinding

class FoodRVAdapter : RecyclerView.Adapter<FoodRVAdapter.FoodHolder>() {
	var food: List<FoodItem>? = null
	override fun getItemCount(): Int {
		return food?.size ?: 0
	}

	interface OnItemClickListener {
		fun onItemClick(foodItem: FoodItem, position: Int)
	}

	var itemClickListener: OnItemClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
		val itemBinding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FoodHolder(itemBinding)
	}

	override fun onBindViewHolder(holder: FoodHolder, position: Int) {
//		holder.itemBinding.tvFoodName.text = food?.get(position).toString()
//		holder.itemBinding.tvFoodCalories.text = food?.get(position).toString()
		val currentFood = food?.get(position)
		holder.bind(currentFood)

		holder.itemBinding.btnPick.setOnClickListener {
			itemClickListener?.onItemClick(food?.get(position)!!, position)
		}
	}

	class FoodHolder(val itemBinding: ItemFoodBinding) : RecyclerView.ViewHolder(itemBinding.root) {
		fun bind(foodItem: FoodItem?) {
			val fooKcal = foodItem?.kcal + "kcal"

			itemBinding.tvFoodName.text = foodItem?.foodName
			itemBinding.tvFoodCalories.text = fooKcal

		}
	}
}
