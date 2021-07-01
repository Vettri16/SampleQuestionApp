package com.demo.samplequestionapp.rest

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response

class RestClientError {

    companion object {
        fun parseError(response: Response<*>): APIError? {
            val restClient = RestClient.curInstance
            val converter: Converter<ResponseBody?, APIError> = restClient!!.getClient()
                .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
            var error: APIError? = null
            try {
                if (response.errorBody() != null) {
                    error = converter.convert(response.errorBody())
                }
            } catch (e: Exception) {
                return APIError(1, "Error")
            }
            return error
        }
    }
}