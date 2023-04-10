package com.example.proj3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel(): ViewModel() {
private val repository = EventRepository()

    val events = MutableLiveData<List<Event>>()
    val isLoading =MutableLiveData<Boolean>()

    lateinit var limit: String
    lateinit var offset: String

    fun getEvents(){
        repository.getEvents(limit,offset){
            isLoading.postValue(false)
            events.postValue(it)
        }
    }
}