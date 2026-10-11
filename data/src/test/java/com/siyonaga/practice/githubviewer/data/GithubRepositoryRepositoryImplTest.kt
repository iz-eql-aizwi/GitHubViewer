package com.siyonaga.practice.githubviewer.data

import com.siyonaga.practice.githubviewer.model.GithubRepositoryDetail
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class GithubRepositoryRepositoryImplTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val retrofitJson: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    private val mockGithubRepositoryDetailBody = """
        {
            "content": "44GC44GE44GG44GI44GK"
        }
    """.trimIndent()
    private lateinit var githubRepositoryRepositoryImpl: GithubRepositoryRepositoryImpl
    private lateinit var githubRepositoryApiService: GithubRepositoryApiService

    @Before
    fun setUp() {
        mockWebServer.start()

        githubRepositoryApiService = Retrofit
            .Builder()
            .addConverterFactory(retrofitJson.asConverterFactory("application/json".toMediaType()))
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(GithubRepositoryApiService::class.java)

        githubRepositoryRepositoryImpl = GithubRepositoryRepositoryImpl(
            githubRepositoryApiService = githubRepositoryApiService,
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getGithubRepositoryDetail  が成功した場合には GithubRepositoryDetail を返す`() {
        runTest {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(mockGithubRepositoryDetailBody),
            )

            val ownerName = "developer001"
            val repoName = "android001"
            val readmeText = "あいうえお"
            val actualGithubRepositoryDetail = githubRepositoryRepositoryImpl
                .getGithubRepositoryDetail(
                    ownerName = ownerName,
                    repoName = repoName,
                )
            val expectedGithubRepositoryDetail = GithubRepositoryDetail(
                readmeText = readmeText,
            )

            assertEquals(
                expectedGithubRepositoryDetail,
                actualGithubRepositoryDetail,
            )
        }
    }
}
