package com.example.graphqldemo.integration

import com.example.graphqldemo.utils.TestConstant
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.junit5.MockKExtension
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import io.restassured.module.webtestclient.response.ValidatableWebTestClientResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@AutoConfigureWebTestClient
@ExtendWith(MockKExtension::class)
@TestInstance(PER_CLASS)
abstract class BaseIntegrationTest {
    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var mapper: ObjectMapper

    protected lateinit var mockWebServer: MockWebServer

    @BeforeAll
    fun beforeAll() {
        RestAssuredWebTestClient.webTestClient(webTestClient)
        mockWebServer = MockWebServer().also { it.start(8082) }
    }

    @AfterAll
    fun afterAll() {
        RestAssuredWebTestClient.reset()
        mockWebServer.close()
    }

    protected fun testQuery(query: String): ValidatableWebTestClientResponse = RestAssuredWebTestClient.given()
        .accept(APPLICATION_JSON)
        .contentType(TestConstant.GraphQL.CONTENT_TYPE)
        .body("query {$query}")
        .`when`()
        .post(TestConstant.GraphQL.BASE_URL)
        .then()
        .log().all()
        .and()
        .assertThat()
        .status(OK)
        .and()

    private fun Any.toJson(): String = mapper.writeValueAsString(this)

    protected fun mockRestClientRequest(
        body: Any,
        contentType: MediaType = APPLICATION_JSON,
        httpStatus: HttpStatus = OK
    ) = mockWebServer.enqueue(
        MockResponse().setHeader(CONTENT_TYPE, contentType.toString()).setBody(body.toJson())
            .setResponseCode(httpStatus.value())
    )
}
