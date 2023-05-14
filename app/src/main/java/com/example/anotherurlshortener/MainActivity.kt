package com.example.anotherurlshortener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.anotherurlshortener.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://cleanuri.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ShortenUrlService::class.java)

        binding.shortBtn.setOnClickListener {
            val longUrl = binding.inputURL.text.toString()

            if (longUrl.isNotEmpty()) {
                service.shortenUrl(longUrl).enqueue(object : Callback<ShortenUrlResponse> {
                    override fun onResponse(
                        call: Call<ShortenUrlResponse>,
                        response: Response<ShortenUrlResponse>,
                    ) {
                        if (response.isSuccessful) {
                            binding.shortenedURL.text = response.body()?.result_url
                        } else {
                            binding.shortenedURL.text = "Error: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<ShortenUrlResponse>, t: Throwable) {
                        binding.shortenedURL.text = "Error: ${t.message}"
                    }
                }) // enqueue
            } else {
                binding.shortenedURL.text = "Error: Field is empty..."
            }
        }
    }
}
