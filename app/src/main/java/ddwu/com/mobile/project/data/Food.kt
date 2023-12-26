package ddwu.com.mobile.project.data

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class FoodInfo(
	@SerializedName("I2790") val list: FoodDto
)

data class FoodDto(
	@SerializedName("row") val food: List<FoodItem>
)

data class FoodItem(
	@SerializedName("DESC_KOR") val foodName: String,
	@SerializedName("NUTR_CONT1") val kcal: String
)