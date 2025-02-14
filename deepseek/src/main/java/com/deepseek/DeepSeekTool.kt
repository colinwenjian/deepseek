package com.tstar.deepseek

import android.util.Log
import com.tstar.deepseek.data.ChatRequest
import com.tstar.deepseek.data.FimRequest
import com.tstar.deepseek.data.Message
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

class DeepSeekTool {
    private var mApiKey: String = ""
    private var mModel: String = "deepseek-chat"
    private var deepSeekApiService: DeepSeekApiService
    private var deepSeekFimService: DeepSeekFimService
    private var isMultiRound = false;
    private lateinit var mMessages: ArrayList<Message>

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

        // 初始化 Retrofit 和 DeepSeek API 服务
        val fimRetrofit = Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/beta/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mMessages = arrayListOf<Message>()

        deepSeekApiService = retrofit.create(DeepSeekApiService::class.java)

        deepSeekFimService = fimRetrofit.create(DeepSeekFimService::class.java)
    }

    fun setModel(model: String) {
        mModel = model
    }

    fun setKey(key: String) {
        mApiKey = key
    }

    fun setMultiRound(mr : Boolean) {
        isMultiRound = mr
    }

    fun getAIResponse(userMessage: String) : String {
        var result = "";
        if (mApiKey.isEmpty()) {
            return "Please set your API Key"
        }

        if (!isMultiRound) {
            mMessages.clear()
        }



        mMessages.add(Message(role = "user", content = userMessage))


        val request = ChatRequest(
            model = mModel, // 模型名称
            messages = mMessages
        )

        Log.i("colin", "getAIResponse: $request")

        val call = deepSeekApiService.getChatResponse("Bearer $mApiKey", request)

        val response = call.execute() // 同步执行
        if (response.isSuccessful) {
            val aiMessage = response.body()?.choices?.firstOrNull()?.message?.content
            if (aiMessage != null) {
                if (isMultiRound) {
                    mMessages.add(response.body()!!.choices.firstOrNull()!!.message)
                }
                result = aiMessage
            }
        } else {
            // 处理错误
            val errorMessage = "Error: ${response.code()} - ${response.message()}"
            result = errorMessage
        }
        return result;
    }

    //Fim 补全
    fun getFimResponse(prompt: String, suffix: String, maxTokens: Int) : String {
        var result = "";
        if (mApiKey.isEmpty()) {
            return "Please set your API Key"
        }


        val request = FimRequest(
            model = "deepseek-chat", // 模型名称
            prompt = prompt,
            suffix = suffix,
            max_tokens = maxTokens
        )

        Log.i("colin", "getAIResponse: $request")

        val call = deepSeekFimService.getFimResponse("Bearer $mApiKey", request)

        val response = call.execute() // 同步执行
        if (response.isSuccessful) {
            val aiMessage = response.body()?.choices?.firstOrNull()?.text
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