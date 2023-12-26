package ddwu.com.mobile.project.data

import com.google.gson.annotations.SerializedName

data class Food (
	val items: List<Foods>
)

data class Foods (
	@SerializedName("DESC_KOR")	val foodName: String,
	@SerializedName("NUTR_CONT1") val calorie: Int,
)