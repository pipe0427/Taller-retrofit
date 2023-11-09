package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                        val myAdapter = MyAdapter(posts)
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        recyclerView.adapter = myAdapter
                        myAdapter.notifyDataSetChanged()
                    }
                } else {
                    println("Error de respuesta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Manejar el error de la solicitud
                println("Error de solicitud: ${t.message}")
            }
        })

    }
}
