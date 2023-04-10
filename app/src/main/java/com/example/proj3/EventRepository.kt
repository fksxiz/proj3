package com.example.proj3

import android.util.Log
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class EventRepository {

    fun getEvents(limit: String, offset: String, callback: (List<Event>) -> Unit){

        val request = Request.Builder().get()
            .url(
                HttpUrl.Builder()
                    .scheme("http")
                    .host("rpcollege.ru")
                    .addPathSegments("event/list/json")
                    .addQueryParameter("limit", if(limit.isNotEmpty()) limit else "5")
                    .addQueryParameter("offset", if(offset.isNotEmpty()) offset else "0")
                    .build()
            )
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e(SERVER_TAG, "Response error.\n${e.message}")
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                if(!response.isSuccessful){
                    Log.e(SERVER_TAG, "response isn`t successful code is \n${response.code}")
                    return callback(emptyList())
                }

                val responseBody = response.body?.string() as String

                val parsedEvents = parseEventResponse(responseBody)
                callback(parsedEvents)
            }

        })
    }

    private fun parseEventResponse(body: String): List<Event>{
        return try {
            val events = JSONArray(body)

            val list = mutableListOf<Event>()

            (0 until events.length())
                .map { index -> events.getJSONObject(index) }
                .map { eventJSONObject ->
                    val id= eventJSONObject.getLong("eventId")
                    val title =eventJSONObject.getString("eventName")
                    val createDate =eventJSONObject.getString("eventCreateDate")
                    val annotation = eventJSONObject.getString("eventAnnounce")
                    val message = eventJSONObject.getString("eventText")
                    val imageUrl = eventJSONObject.getString("eventImagePath")

                    list.add(Event(id,title,createDate, annotation, message, imageUrl))
                }

            list
        }catch (e:JSONException){
            Log.e("SERVER_TAG", "parse response error = ${e.message}")
            mutableListOf()
        }
    }

    companion object{
        const val SERVER_TAG ="SERVER_TAG"
    }

}