package com.example.graphqldemo.utils

import io.restassured.module.webtestclient.response.ValidatableWebTestClientResponse
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.not

object RestAssuredWebTestClientUtils {
    private const val BASE_JSON_ROOT_PATH = "\$"
    private const val DATA_KEY = "data"
    private const val ERRORS_KEY = "erros"

    fun ValidatableWebTestClientResponse.verifyOnlyDataExists(): ValidatableWebTestClientResponse =
        body(
            BASE_JSON_ROOT_PATH,
            hasKey(DATA_KEY),
            BASE_JSON_ROOT_PATH,
            not(
                hasKey(
                    ERRORS_KEY
                )
            )
        )
}
