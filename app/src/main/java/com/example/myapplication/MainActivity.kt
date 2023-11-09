package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var jsonPlaceholderApi: JsonPlaceholderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)

        val call = jsonPlaceholderApi.getPosts()
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts: List<Post>? = response.body()
                    if (posts != null) {
                        for (post in posts) {
                            val title = post.title
                            println("Título: $title")
                        }
                    }
                } else {
                    // Manejar el error de respuesta
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Manejar el error de la solicitud
            }
        })

    }
}
