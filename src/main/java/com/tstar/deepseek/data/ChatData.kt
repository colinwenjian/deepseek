package com.tstar.deepseek.data

// DeepSeek API 请求和响应数据类
data class ChatRequest(
    val model: String,
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class ChatSetting(
    val model: String,
    val apiKey: String
)