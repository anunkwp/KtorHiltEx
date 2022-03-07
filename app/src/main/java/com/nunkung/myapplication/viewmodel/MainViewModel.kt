package com.nunkung.myapplication.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nunkung.myapplication.model.UserPostData
import com.nunkung.myapplication.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by 「 Nun Kung 」  on 07-Mar-22 :)
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    val onObserverUserPost = MutableLiveData<MutableList<UserPostData>>()

    fun requestUserPost() = viewModelScope.launch(Dispatchers.Main) {
        val result  = withContext(Dispatchers.IO){
                repo.getUserKtor()
        }
        onObserverUserPost.value = result
    }
}