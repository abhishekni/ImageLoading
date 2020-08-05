package com.abhishekn.techm

import com.abhishekn.techm.network.ServiceApi
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8080

    lateinit var serviceApi: ServiceApi

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)

        serviceApi = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ServiceApi::class.java)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun parseImageAPI() {
        server.apply {
            enqueue(
                response = MockResponse()
                    .setResponseCode(HttpsURLConnection.HTTP_OK)
//                    .setBody(FileReader.readStringFromFile("MockImageResponse.json"))
                    .setBody(MockResponseReader("MockImageResponse.json").fileContent)
            )
        }
//        serviceApi.getImages().
    }

}