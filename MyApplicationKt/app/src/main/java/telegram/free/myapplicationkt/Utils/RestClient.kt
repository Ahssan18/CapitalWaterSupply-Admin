package telegram.free.myapplicationkt.Utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import telegram.free.myapplicationkt.ApiServices

object RestClient {
    lateinit var retrofit:Retrofit


        val Url="https://jsonplaceholder.typicode.com"
         fun getRetrofit():ApiServices {
             return Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(Url)
                .build().create(ApiServices::class.java)


    }

}