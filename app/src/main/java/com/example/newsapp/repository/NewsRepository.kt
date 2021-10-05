package com.example.newsapp.repository

import com.example.newsapp.model.News
import com.example.newsapp.provider.NewsProvider
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(country: String): List<News>
    fun getNew(tittle: String):News
}

class NewsRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider
) : NewsRepository{

    private var news: List<News> = emptyList()

    override suspend fun getNews(country: String): List<News> {
        val apiResponse = newsProvider.topHeadLines(country).body()
        if (apiResponse?.status == "error")
            when (apiResponse.code){
                "apiKeyMissing" -> throw  MissingApiKeyException()
                "apiKeyInvalid" -> throw  ApiKeyInvalidException()
                else -> throw  Exception()
            }
    }

    override fun getNew(tittle: String): News {
        TODO("Not yet implemented")
    }
}
class MissingApiKeyException : java.lang.Exception()
class ApiKeyInvalidException : java.lang.Exception()