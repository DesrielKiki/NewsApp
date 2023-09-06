package desriel.kiki.newsapp.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("data")
	val data: DetailData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("deletedAt")
	val deletedAt: Any? = null,

	@field:SerializedName("category_id")
	val categoryId: String? = null,

	@field:SerializedName("thumb")
	val thumb: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("news_category")
	val newsCategory: NewsCategory? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("place_id")
	val placeId: Any? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DetailCategory(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("deletedAt")
	val deletedAt: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
