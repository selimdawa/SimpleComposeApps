package com.flatcode.simplecomposeapps.candycrushgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushDao
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushEntity
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CandyCrushViewModel @Inject constructor(
    private val candyCrushDao: CandyCrushDao
) : ViewModel() {

    val noOfBlocks = 8
    val candies = intArrayOf(
        AppIcons.BlueCandy, AppIcons.GreenCandy, AppIcons.RedCandy,
        AppIcons.OrangeCandy, AppIcons.YellowCandy, AppIcons.PurpleCandy
    )
    val notCandy = -1

    private val _board = MutableLiveData<List<Int>>(emptyList())
    val board: LiveData<List<Int>> = _board

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _highScore = MutableLiveData(0)

    init {
        viewModelScope.launch {
            val data = candyCrushDao.getCandyCrushData().first()
            if (data != null && data.boardState.isNotEmpty()) {
                _highScore.value = data.highScore
                _score.value = data.score
                _board.value = data.boardState.split(",").map { it.toInt() }
            } else {
                createBoard()
            }
            startGameLoop()
        }
    }

    private fun createBoard() {
        val newBoard = mutableListOf<Int>()
        repeat(noOfBlocks * noOfBlocks) { index ->
            var randomCandy: Int
            do {
                randomCandy = candies[floor(Math.random() * candies.size).toInt()]
            } while (wouldCreateMatch(newBoard, index, randomCandy))
            newBoard.add(randomCandy)
        }
        _board.value = newBoard
    }

    private fun wouldCreateMatch(currentBoard: List<Int>, index: Int, candy: Int): Boolean {
        val row = index / noOfBlocks
        val col = index % noOfBlocks

        // Check left
        if (col >= 2 && currentBoard[index - 1] == candy && currentBoard[index - 2] == candy) return true
        // Check up
        if (row >= 2 && currentBoard[index - noOfBlocks] == candy && currentBoard[index - 2 * noOfBlocks] == candy) return true

        return false
    }

    private fun startGameLoop() {
        viewModelScope.launch {
            while (true) {
                checkRowForThree()
                checkColumnForThree()
                moveDownCandies()
                updateHighScore()
                saveGameData()
                delay(100.milliseconds)
            }
        }
    }

    private fun updateHighScore() {
        val currentScore = _score.value ?: 0
        val currentHighScore = _highScore.value ?: 0
        if (currentScore > currentHighScore) {
            _highScore.value = currentScore
        }
    }

    private suspend fun saveGameData() {
        val currentBoard = _board.value ?: emptyList()
        val boardState = currentBoard.joinToString(",")
        candyCrushDao.saveCandyCrushData(
            CandyCrushEntity(
                highScore = _highScore.value ?: 0,
                score = _score.value ?: 0,
                boardState = boardState
            )
        )
    }

    fun swapCandies(draggedIndex: Int, replacedIndex: Int) {
        val currentBoard = (_board.value ?: emptyList()).toMutableList()
        if (replacedIndex in currentBoard.indices) {
            val temp = currentBoard[draggedIndex]
            currentBoard[draggedIndex] = currentBoard[replacedIndex]
            currentBoard[replacedIndex] = temp
            _board.value = currentBoard
        }
    }

    private fun checkRowForThree() {
        val currentBoard = (_board.value ?: emptyList()).toMutableList()
        if (currentBoard.isEmpty()) return
        var changed = false
        for (i in 0..61) {
            val chosenCandy = currentBoard[i]
            val isBlank = currentBoard[i] == notCandy
            val notValid = arrayOf(6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55)
            if (i !in notValid) {
                if (currentBoard[i] == chosenCandy && !isBlank && currentBoard[i + 1] == chosenCandy && currentBoard[i + 2] == chosenCandy) {
                    _score.postValue((_score.value ?: 0) + 3)
                    currentBoard[i] = notCandy
                    currentBoard[i + 1] = notCandy
                    currentBoard[i + 2] = notCandy
                    changed = true
                }
            }
        }
        if (changed) _board.postValue(currentBoard)
    }

    private fun checkColumnForThree() {
        val currentBoard = (_board.value ?: emptyList()).toMutableList()
        if (currentBoard.isEmpty()) return
        var changed = false
        for (i in 0..46) {
            val chosenCandy = currentBoard[i]
            val isBlank = currentBoard[i] == notCandy
            if (currentBoard[i] == chosenCandy && !isBlank && currentBoard[i + noOfBlocks] == chosenCandy && currentBoard[i + 2 * noOfBlocks] == chosenCandy) {
                _score.postValue((_score.value ?: 0) + 3)
                currentBoard[i] = notCandy
                currentBoard[i + noOfBlocks] = notCandy
                currentBoard[i + 2 * noOfBlocks] = notCandy
                changed = true
            }
        }
        if (changed) _board.postValue(currentBoard)
    }

    private fun moveDownCandies() {
        val currentBoard = (_board.value ?: emptyList()).toMutableList()
        if (currentBoard.isEmpty()) return
        var changed = false
        val firstRow = arrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        for (i in 55 downTo 0) {
            if (currentBoard[i + noOfBlocks] == notCandy) {
                currentBoard[i + noOfBlocks] = currentBoard[i]
                currentBoard[i] = notCandy
                changed = true
                if (i in firstRow && currentBoard[i] == notCandy) {
                    val randomColor = floor(Math.random() * candies.size).toInt()
                    currentBoard[i] = candies[randomColor]
                }
            }
        }
        for (i in 0 until noOfBlocks) {
            if (currentBoard[i] == notCandy) {
                val randomColor = floor(Math.random() * candies.size).toInt()
                currentBoard[i] = candies[randomColor]
                changed = true
            }
        }
        if (changed) _board.postValue(currentBoard)
    }
}