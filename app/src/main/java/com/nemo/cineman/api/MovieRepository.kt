package com.nemo.cineman.api

import javax.inject.Inject

class MovieRepository @Inject constructor(
    val service: MovieService
)