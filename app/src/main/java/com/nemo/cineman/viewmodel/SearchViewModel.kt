package com.nemo.cineman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow<String>(value = "")
    val searchText: StateFlow<String> get() = _searchText

    fun onTextChange(text: String) {
        _searchText.value = text
    }

//    fun searchMoviesByTitle() : Flow<PagingData<Movie>> {
//        return movieRepository.searchMoviesByTitle(_searchText.value).cachedIn(viewModelScope)
//    }

    //Debounce technique
    fun searchMoviesByTitle(): Flow<PagingData<Movie>> {
        return searchText
            .debounce(2000) // Đợi 2 giây sau lần nhập cuối cùng
            .distinctUntilChanged() // Chỉ tìm kiếm nếu giá trị thực sự thay đổi
            .flatMapLatest { query ->
                movieRepository.searchMoviesByTitle(query)
            }
            .cachedIn(viewModelScope)
    }
}