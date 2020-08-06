package com.abhishekn.techm

import com.abhishekn.techm.model.ImageResponse
import com.abhishekn.techm.network.ServiceApi
import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8080
    private val response = "{\n" +
            "  \"rows\":[\n" +
            "    {\n" +
            "      \"title\":\"Beavers\",\n" +
            "      \"description\":\"Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony\",\n" +
            "      \"imageHref\":\"http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Flag\",\n" +
            "      \"description\":null,\n" +
            "      \"imageHref\":\"http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Transportation\",\n" +
            "      \"description\":\"It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.\",\n" +
            "      \"imageHref\":\"http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Hockey Night in Canada\",\n" +
            "      \"description\":\"These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week.\",\n" +
            "      \"imageHref\":\"http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Eh\",\n" +
            "      \"description\":\"A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation.\",\n" +
            "      \"imageHref\":null\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Housing\",\n" +
            "      \"description\":\"Warmer than you might think.\",\n" +
            "      \"imageHref\":\"http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Public Shame\",\n" +
            "      \"description\":\" Sadly it's true.\",\n" +
            "      \"imageHref\":\"http://static.guim.co.uk/sys-images/Music/Pix/site_furniture/2007/04/19/avril_lavigne.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":null,\n" +
            "      \"description\":null,\n" +
            "      \"imageHref\":null\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Space Program\",\n" +
            "      \"description\":\"Canada hopes to soon launch a man to the moon.\",\n" +
            "      \"imageHref\":\"http://files.turbosquid.com/Preview/Content_2009_07_14__10_25_15/trebucheta.jpgdf3f3bf4-935d-40ff-84b2-6ce718a327a9Larger.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Meese\",\n" +
            "      \"description\":\"A moose is a common sight in Canada. Tall and majestic, they represent many of the values which Canadians imagine that they possess. They grow up to 2.7 metres long and can weigh over 700 kg. They swim at 10 km/h. Moose antlers weigh roughly 20 kg. The plural of moose is actually 'meese', despite what most dictionaries, encyclopedias, and experts will tell you.\",\n" +
            "      \"imageHref\":\"http://caroldeckerwildlifeartstudio.net/wp-content/uploads/2011/04/IMG_2418%20majestic%20moose%201%20copy%20(Small)-96x96.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Geography\",\n" +
            "      \"description\":\"It's really big.\",\n" +
            "      \"imageHref\":null\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Kittens...\",\n" +
            "      \"description\":\"Éare illegal. Cats are fine.\",\n" +
            "      \"imageHref\":\"http://www.donegalhimalayans.com/images/That%20fish%20was%20this%20big.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Mounties\",\n" +
            "      \"description\":\"They are the law. They are also Canada's foreign espionage service. Subtle.\",\n" +
            "      \"imageHref\":\"http://3.bp.blogspot.com/__mokxbTmuJM/RnWuJ6cE9cI/AAAAAAAAATw/6z3m3w9JDiU/s400/019843_31.jpg\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\":\"Language\",\n" +
            "      \"description\":\"Nous parlons tous les langues importants.\",\n" +
            "      \"imageHref\":null\n" +
            "    }\n" +
            "  ]\n" +
            "}"
    lateinit var jsonResponse : String
    lateinit var serviceApi: ServiceApi

    @Before
    fun init() {
        server.start(MOCK_WEBSERVER_PORT)

        serviceApi = Retrofit.Builder()
            .baseUrl(server.url("https://dl.dropboxusercontent.com/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ServiceApi::class.java)
        jsonResponse = response
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun parseImageAPI() {
        val expectedResponse = serviceApi.getImages().execute()
        // verify the response is OK
        val actualResponse = Gson().fromJson(jsonResponse, ImageResponse::class.java)

        assertEquals(expectedResponse.code(), 200)
        assertEquals(expectedResponse.body(), actualResponse)
//        assertEquals(response.body(), json)
    }


}