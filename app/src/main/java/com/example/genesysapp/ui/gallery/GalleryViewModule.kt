package com.example.genesysapp.ui.gallery

import androidx.lifecycle.*
import com.example.genesysapp.data.RandomUser
import com.example.genesysapp.di.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModule @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val randomUserLiveData = MutableLiveData<List<RandomUser>>()
    val randomUsers: LiveData<List<RandomUser>> = randomUserLiveData

    private val eventChannel = Channel<UserEvent>()
    val userEvents = eventChannel.receiveAsFlow()

    init {
        onRefresh()
    }

    private lateinit var results: List<RandomUser>

    fun onRefresh() = viewModelScope.launch {
        val randomUserResponse = repository.getRandomUserList()
        results = randomUserResponse.results
        randomUserLiveData.value = results

        val joinToString = results.groupBy { it.nat }
            .map { "${it.key}   ${it.value.size}" }
            .joinToString(separator = "\n")

        eventChannel.send(UserEvent.ShowSummaryMessage(joinToString))
    }

    fun onUserSort() {
        randomUserLiveData.apply {
            value?.sortedBy { it.name.last }
                .also { value = it }
        }
    }


    fun onFilterUser(query: String) {
        randomUserLiveData.value = results
        randomUserLiveData.apply {
            value?.filter {
                it.name.first.toLowerCase().contains(query) or
                        it.name.last.toLowerCase().contains(query)
            }?.also {
                value = it
            }
        }
    }

    sealed class UserEvent {
        data class ShowSummaryMessage(val msg: String) : UserEvent()
    }
}