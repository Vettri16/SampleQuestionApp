package com.demo.samplequestionapp.rest

data class RestClientEntity<T>(val success: Boolean, val code: Int, val message: String, val data:T?, val apiError: APIError?)