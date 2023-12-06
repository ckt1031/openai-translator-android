package com.ckt1031.openai.translator.client

import com.ckt1031.openai.translator.model.ChatCompletionResponse
import com.ckt1031.openai.translator.model.ChatRequestBody
import com.ckt1031.openai.translator.model.StreamChatCompletionResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

public interface APIService {
    @POST("v1/chat/completions")
    fun postChatCompletions(@Body requestBody: ChatRequestBody): Call<ChatCompletionResponse>

    @POST("v1/chat/completions")
    @Streaming
    fun postStreamChatCompletions(@Body requestBody: ChatRequestBody): Call<StreamChatCompletionResponse>
}
