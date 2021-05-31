package `in`.starbow.networking

import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query

interface Githubinterface {
    @GET("users")
    suspend fun getUser():Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserbyId(@Path("id")id:String="Starboww"):Response<User>

    @GET("search/users")
   suspend fun searchUsers(@Query("q")query:String):Response<UserResponse>

}