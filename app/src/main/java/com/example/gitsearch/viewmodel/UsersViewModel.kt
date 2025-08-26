package com.example.gitsearch.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitsearch.model.PersonalInfo
import com.example.gitsearch.model.RetrofitInstance
import com.example.gitsearch.model.UserData
import com.example.gitsearch.model.UserResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsersViewModel () : ViewModel() {

    private val _users = MutableStateFlow<List<UserData>>(emptyList())
    val users: StateFlow<List<UserData>> = _users

    private val _userInfo = MutableStateFlow<PersonalInfo?>(null)
    val userInfo: StateFlow<PersonalInfo?> = _userInfo

    private val _noUsers = MutableStateFlow(false)
    val noUsers: StateFlow<Boolean> = _noUsers

    private var currentPage = 1
    private var isLoading = false
    var currentQuery: String = ""

    private fun loadUsers() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.usersapi.searchUsers(currentQuery, page = currentPage)

                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        _users.value += userResponse.items
                        currentPage++
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadUserInfo(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.usersapi.getUserInfo(username)
                if (response.isSuccessful) {
                    _userInfo.value = response.body()
                } else {
                    println("Error fetching user info: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun searchUsers(query: String, reset: Boolean = true) {
        if (reset) {
            currentQuery = query
            currentPage = 1
            _users.value = emptyList()
        }
        loadUsers()
    }


}

