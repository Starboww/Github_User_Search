package `in`.starbow.networking

import `in`.starbow.networking.User

data class UserResponse(
	val totalCount: Int? = null,
	val incompleteResults: Boolean? = null,
	val items: List<User>? = null
)

