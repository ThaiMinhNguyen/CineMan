package com.nemo.cineman.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nemo.cineman.api.UserRepository
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.TMDBSortOptions
import com.nemo.cineman.entity.UserMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _listId = MutableLiveData<Int>()
    val listId: LiveData<Int> get() = _listId

    private val _movies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>> get() = _movies

    private val _sortOption = MutableLiveData(TMDBSortOptions.POPULARITY_DESC)
    val sortOption: LiveData<TMDBSortOptions.SortOption> get() = _sortOption

    private val _sortUpdateTrigger = MutableLiveData(0)
    val sortUpdateTrigger: LiveData<Int> get() = _sortUpdateTrigger

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _listName = MutableLiveData<String>()
    val listName: LiveData<String> get() = _listName

    private val _message = MutableLiveData<String?>(null)
    val message: LiveData<String?> get() = _message

    fun onMessageHandled() {
        _message.value = null
    }

    fun setListId(listId: Int) {
        _listId.value = listId
        Log.d("MyLog", "List ID: $listId")
    }

    fun setSortOption(sortOption: TMDBSortOptions.SortOption) {
        Log.d("MyLog", "Setting sort option: ${TMDBSortOptions.getDisplayName(sortOption)}")
        _sortOption.value = sortOption
        _sortUpdateTrigger.value = _sortUpdateTrigger.value?.plus(1)
    }

    private fun refreshMovies() {
        _sortUpdateTrigger.value = _sortUpdateTrigger.value?.plus(1)
    }

    fun getListName() {
        viewModelScope.launch {
            val listId = _listId.value ?: 0
            val result = userRepository.getListName(listId)
            result.onSuccess {
                _listName.value = it
            }.onFailure {
                _listName.value = "All Movies"
            }
        }
    }

    fun getMovies(listId: Int): Flow<PagingData<Movie>> {
        Log.d("MyLog", "List ID on getMovies: $listId")
        val sortBy = _sortOption.value?.toParameterString() ?: TMDBSortOptions.POPULARITY_DESC.toParameterString()

        return userRepository.getMoviesFromList(
            listId = listId,
            sortBy = sortBy
        ).cachedIn(viewModelScope)
    }

    fun clearList(listId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.clearListItems(listId)
            result.onSuccess {
                _message.value = it.statusMessage
                Log.d("MyLog", "Clearing list: $listId")
            }.onFailure {
                _message.value = it.message
                Log.d("MyLog", "Error clearing list: ${it.message}")
            }
            _isLoading.value = false
        }
    }

    fun deleteList(listId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.deleteList(listId)
            result.onSuccess {
                _message.value = it.statusMessage
                Log.d("MyLog", "Deleting list: $listId")
            }.onFailure {
                _message.value = it.message
                Log.d("MyLog", "Error deleting list: ${it.message}")
            }
            _isLoading.value = false
        }
    }

    fun editList(listId: Int, name: String, description: String, language: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val userMovieList = UserMovieList(name, description, language)
            val result = userRepository.updateList(listId, userMovieList)
            result.onSuccess {
                _message.value = it.statusMessage
            }.onFailure {
                _message.value = it.message
                Log.d("MyLog", "Error editing list: ${it.message}")
            }
            _isLoading.value = false
        }
    }

    fun createUserList(name: String, description: String, language: String){
        val userMovieList = UserMovieList(name, description, language)
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.createUserList(userMovieList)
            result.onSuccess { response ->
                _message.value = response.status_message
                Log.e("MyLog", "Create new list: ${response.list_id}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to create new list: ${exception.message}")
            }
            _isLoading.value = false
        }
    }
    

}