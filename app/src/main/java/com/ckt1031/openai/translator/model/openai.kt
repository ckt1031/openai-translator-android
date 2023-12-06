package com.ckt1031.openai.translator.model

import com.google.gson.annotations.SerializedName

data class OpenAIMessage(
    val role: String,
    val content: String
)

data class ChatRequestBody(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean
)

data class ChatCompletionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("object") val `object`: String,
    @SerializedName("created") val created: Long,
    @SerializedName("model") val model: String,
    @SerializedName("system_fingerprint") val systemFingerprint: String,
    @SerializedName("choices") val choices: List<Choice>,
    @SerializedName("usage") val usage: Usage
)

data class Choice(
    @SerializedName("index") val index: Int,
    @SerializedName("message") val message: Message,
    @SerializedName("finish_reason") val finishReason: String
)

data class Message(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("completion_tokens") val completionTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)

data class StreamChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val system_fingerprint: String,
    val choices: List<Choice>
)

data class StreamChoice(
    val index: Int,
    val delta: StreamDelta,
    val finish_reason: String?
)

data class StreamDelta(
    val role: String?,
    val content: String?
)
