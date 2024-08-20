package com.pam.classicspin.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.classicspin.entities.UserRepository
import com.pam.classicspin.presentation.imageResources
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.random.Random

class AppViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val userCoins: StateFlow<Int> = userRepository.currentUserCoins.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    private var _columnStates = MutableStateFlow(List(5) { Random.nextInt(imageResources.size) })
    private var _spinning = MutableStateFlow(false)
    private var _matchFound = MutableStateFlow(false)
    private var _currentJackpot = MutableStateFlow(0)

    val columnStates = _columnStates.asStateFlow()
    val spinning = _spinning.asStateFlow()
    val matchFound = _matchFound.asStateFlow()
    val currentJackpot = _currentJackpot.asStateFlow()

    fun setUserCoinsTo(coins: Int) {
        viewModelScope.launch {
            try {
                userRepository.changeUserCoins(coins)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun onSpinStop(){
        checkForMatch()
        Log.d("","Match found: ${_matchFound.value}")
        if (_matchFound.value){
            val jackpot: Int = when(_columnStates.value[0]){
                0 -> 10 * 5
                1 -> 30 * 5
                2 -> 60 * 5
                3 -> 120 * 5
                4 -> 240 * 5
                5 -> 480 * 5
                6 -> 670 * 5
                7 -> 900 * 5
                else -> {
                    0
                }
            }
            _currentJackpot.emit(jackpot)
            setUserCoinsTo(userCoins.value + jackpot)
        }
        delay(2000)
        _matchFound.emit(false)
        _currentJackpot.emit(0)
    }

    suspend fun onSpinClick() {
        _spinning.emit(true)
        Log.d("", "spinning changed to ${spinning.value}")
        _matchFound.emit(false)

        coroutineScope {
            val jobs = List(5) { i ->
                launch {
                    delay(i * 600L)
                    spinColumn(i)
                }
            }
            jobs.joinAll()
        }

        _spinning.emit(false)
        Log.d("", "spinning changed to ${spinning.value}")
    }

    private suspend fun spinColumn(index: Int) {
        val spinDuration = Random.nextLong(1000, 3000)
        val startTime = System.currentTimeMillis()
        var delay = 50L

        while (System.currentTimeMillis() - startTime < spinDuration) {
            _columnStates.update { currentList ->
                currentList.toMutableList().apply {
                     this[index] = (this[index] + 1) % imageResources.size
//                    this[index] = 1
                }
            }
            delay(delay)

            delay = (delay * 1.05).toLong().coerceAtMost(200)
        }
    }

    private suspend fun checkForMatch(){
        val firstValue = _columnStates.value[0]
        _matchFound.emit(_columnStates.value.all { it == firstValue })
    }
}