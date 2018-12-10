package com.vsquad.projects.govorillo.model.api

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type


class ToStringConverterFactory : Converter.Factory() {

    fun fromResponseBody(type: Type, annotations: Array<Annotation>): Converter<ResponseBody, String>? {

        return if (String::class.java == type) {
            Converter<ResponseBody, String> { responseBody -> responseBody.string() }
        } else null

    }

    fun toRequestBody(type: Type, annotations: Array<Annotation>): Converter<*, RequestBody>? {

        return if (String::class.java == type) {
            Converter<String, RequestBody> { value -> RequestBody.create(MediaType.parse("text/plain"), value) }
        } else null

    }
}