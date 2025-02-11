package com.tstar.deepseek

import com.tstar.deepseek.data.ChatRequest
import com.tstar.deepseek.data.Message
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DeepSeekTool {
    private var mApiKey: String = ""
    private var mModel: String = "deepseek-chat"
    private var deepSeekApiService: DeepSeekApiService

    init {
        // 初始化 Retrofit 和 DeepSeek API 服务
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 连接超时时间
            .readTimeout(30, TimeUnit.SECONDS)    // 读取超时时间
            .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时时间
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // 记录请求和响应的详细信息
            })
            .build()

        // 初始化 Retrofit 和 DeepSeek API 服务
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        deepSeekApiService = retrofit.create(DeepSeekApiService::class.java)
    }

    fun setModel(model: String) {
        mModel = model
    }

    fun setKey(key: String) {
        mApiKey = key
    }

    fun getAIResponse(userMessage: String) : String {
        var result = "";
        if (mApiKey.isEmpty()) {
            return "Please set your API Key"
        }

        val request = ChatRequest(
            model = mModel, // 模型名称
            messages = listOf(Message(role = "user", content = userMessage))
        )

        val call = deepSeekApiService.getChatResponse("Bearer $mApiKey", request)

        val response = call.execute() // 同步执行
        if (response.isSuccessful) {
            val aiMessage = response.body()?.choices?.firstOrNull()?.message?.content
            if (aiMessage != null) {
                result = aiMessage
            }
        } else {
            // 处理错误
            val errorMessage = "Error: ${response.code()} - ${response.message()}"
            result = errorMessage
        }
        return result;
    }
}