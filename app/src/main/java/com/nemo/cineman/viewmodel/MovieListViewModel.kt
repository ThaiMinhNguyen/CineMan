package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nemo.cineman.api.UserRepository
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.TMDBSortOptions
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

    fun getMovies(): Flow<PagingData<Movie>> {
        val listId = _listId.value ?: 0
        Log.d("MyLog", "List ID on getMovies: $listId")
        val sortBy = _sortOption.value?.toParameterString() ?: TMDBSortOptions.POPULARITY_DESC.toParameterString()

        if (listId == 0) {
            return userRepository.getMoviesFromList(
                listId = listId,
                sortBy = sortBy
            ).cachedIn(viewModelScope)
        }

        return userRepository.getMoviesFromList(
            listId = listId,
            sortBy = sortBy
        ).cachedIn(viewModelScope)
    }
}