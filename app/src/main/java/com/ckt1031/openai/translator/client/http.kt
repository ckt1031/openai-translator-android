package com.ckt1031.openai.translator.client

import com.ckt1031.openai.translator.model.ChatCompletionResponse
import com.ckt1031.openai.translator.model.ChatRequestBody
import com.ckt1031.openai.translator.model.StreamChatCompletionResponse
import com.ckt1031.openai.translator.model.TextToSpeechRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Streaming

public interface APIService {
    @POST("v1/chat/completions")
    fun postChatCompletions(
        @Header("Authorization") authorization: String,
        @Body requestBody: ChatRequestBody): Call<ChatCompletionResponse>

    @POST("v1/chat/completions")
    @Streaming
    fun postStreamChatCompletions( @Header("Authorization") authorization: String,
                                   @Body requestBody: ChatRequestBody): Call<StreamChatCompletionResponse>

    @Multipart
    @POST("v1/audio/speech")
    fun postAudioRequest(
        @Header("Authorization") authorization: String,
                          @Body data: TextToSpeechRequest): Call<MultipartBody.Part?>?
}
