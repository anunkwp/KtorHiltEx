package com.nunkung.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nunkung.myapplication.databinding.ActivityMainBinding
import com.nunkung.myapplication.network.UserApi
import com.nunkung.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.requestUserPost()
        viewModel.onObserverUserPost.observe(this@MainActivity) { callUser ->
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

