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


//补全
data class FimRequest(
    val model: String,
    val prompt: String, //前缀
    val suffix: String,  //后缀
    val max_tokens: Int
)

data class FimResponse(
    val choices: List<Fim>
)

data class Fim(
    val text: String
)