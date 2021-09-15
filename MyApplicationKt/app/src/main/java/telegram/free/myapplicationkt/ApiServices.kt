package telegram.free.myapplicationkt

import retrofit2.Call
import retrofit2.http.GET
import telegram.free.myapplicationkt.Data.Response

interface ApiServices {
    @GET("/posts")
    fun getPosts() : Call<List<Response>>

}