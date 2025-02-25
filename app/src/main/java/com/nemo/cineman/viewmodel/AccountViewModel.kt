package com.nemo.cineman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.UserRepository
import com.nemo.cineman.entity.Account
import com.nemo.cineman.entity.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel()  {

    private val _accountDetail = MutableStateFlow<Account?>(null)
    val accountDetail: StateFlow<Account?> = _accountDetail

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onErrorHandled(){
        _error.value = null
    }

    fun fetchAccountDetail() {
        viewModelScope.launch {
            _isLoading.value = true // ⬅️ Hiển thị loading khi bắt đầu
            val sessionId = sharedPreferenceManager.getSessionId()

            if (sessionId != null) {
                val result = userRepository.getAccountDetail(sessionId)
                result.onSuccess { account ->
                    _accountDetail.value = account
                    _error.value = null // Xóa lỗi nếu có
                }.onFailure { e ->
                    _error.value = e.localizedMessage ?: "Unknown error"
                }
            } else {
                _error.value = "Can't get account detail. Please try again later!"
            }
            _isLoading.value = false // ⬅️ Ẩn loading sau khi có kết quả
        }
    }

}