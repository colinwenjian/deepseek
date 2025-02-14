package com.tstar.deepseek

import com.tstar.deepseek.data.ChatRequest
import com.tstar.deepseek.data.ChatResponse
import com.tstar.deepseek.data.FimRequest
import com.tstar.deepseek.data.FimResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// DeepSeek API 服务接口
interface DeepSeekApiService {
    @POST("v1/chat/completions")
    fun getChatResponse(
        @Header("Authorization") authHeader: String,
        @Body request: ChatRequest
    ): Call<ChatResponse>
}

interface DeepSeekFimService {
    @POST("completions")
    fun getFimResponse(
        @Header("Authorization") authHeader: String,
        @Body request: FimRequest
    ): Call<FimResponse>
}