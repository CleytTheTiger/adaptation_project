package com.example.adaptationproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActiveUser : ViewModel() {
    val _currentUser: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun setCurrentUser(user: String?) {
        _currentUser.value = user
    }
}