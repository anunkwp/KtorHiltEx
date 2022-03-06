package com.nunkung.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nunkung.myapplication.databinding.ActivityMainBinding
import com.nunkung.myapplication.network.KtorHttpClient
import com.nunkung.myapplication.network.UserApi
import kotlinx.coroutines.runBlocking

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by  lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val requestClient = KtorHttpClient.ktorHttpClient

        runBlocking {
            val callUser = UserApi(requestClient).getUserKtor()
            Log.d("USER-SIZE", callUser.size.toString())
            binding.apply {
                callUser[0].let { data ->
                    tvUserId.text = "UserId : ${data.userId}"
                    tvId.text = "id : ${data.id}"
                    tvTitle.text = "Title : ${data.title}"
                    tvBody.text = "Body : ${data.body}"
                }
            }
        }
    }
}