package com.flatcode.simplecomposeapps.candycrushgame

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushDao
import com.flatcode.simplecomposeapps.candycrushgame.data.CandyCrushEntity
import com.flatcode.simplecomposeapps.ui.AppIcons
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

    val board = mutableStateListOf<Int>()
    val score = mutableIntStateOf(0)
    val highScore = mutableIntStateOf(0)

    init {
        viewModelScope.launch {
            val data = candyCrushDao.getCandyCrushData().first()
            if (data != null && data.boardState.isNotEmpty()) {
                highScore.intValue = data.highScore
                score.intValue = data.score
                board.clear()
                board.addAll(data.boardState.split(",").map { it.toInt() })
            } else {
                createBoard()
            }
            startGameLoop()
        }
    }

    private fun createBoard() {
        board.clear()
        repeat(noOfBlocks * noOfBlocks) { index ->
            var randomCandy: Int
            do {
                randomCandy = candies[floor(Math.random() * candies.size).toInt()]
            } while (wouldCreateMatch(index, randomCandy))
            board.add(randomCandy)
        }
    }

    private fun wouldCreateMatch(index: Int, candy: Int): Boolean {
        val row = index / noOfBlocks
        val col = index % noOfBlocks

        // Check left
        if (col >= 2 && board[index - 1] == candy && board[index - 2] == candy) return true
        // Check up
        if (row >= 2 && board[index - noOfBlocks] == candy && board[index - 2 * noOfBlocks] == candy) return true

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
        if (score.intValue > highScore.intValue) {
            highScore.intValue = score.intValue
        }
    }

    private suspend fun saveGameData() {
        val boardState = board.joinToString(",")
        candyCrushDao.saveCandyCrushData(
            CandyCrushEntity(
                highScore = highScore.intValue,
                score = score.intValue,
                boardState = boardState
            )
        )
    }

    fun swapCandies(draggedIndex: Int, replacedIndex: Int) {
        if (replacedIndex in board.indices) {
            val temp = board[draggedIndex]
            board[draggedIndex] = board[replacedIndex]
            board[replacedIndex] = temp
        }
    }

    private fun checkRowForThree() {
        for (i in 0..61) {
            val chosenCandy = board[i]
            val isBlank = board[i] == notCandy
            val notValid = arrayOf(6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55)
            if (i !in notValid) {
                if (board[i] == chosenCandy && !isBlank && board[i + 1] == chosenCandy && board[i + 2] == chosenCandy) {
                    score.intValue += 3
                    board[i] = notCandy
                    board[i + 1] = notCandy
                    board[i + 2] = notCandy
                }
            }
        }
    }

    private fun checkColumnForThree() {
        for (i in 0..46) {
            val chosenCandy = board[i]
            val isBlank = board[i] == notCandy
            if (board[i] == chosenCandy && !isBlank && board[i + noOfBlocks] == chosenCandy && board[i + 2 * noOfBlocks] == chosenCandy) {
                score.intValue += 3
                board[i] = notCandy
                board[i + noOfBlocks] = notCandy
                board[i + 2 * noOfBlocks] = notCandy
            }
        }
    }

    private fun moveDownCandies() {
        val firstRow = arrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        for (i in 55 downTo 0) {
            if (board[i + noOfBlocks] == notCandy) {
                board[i + noOfBlocks] = board[i]
                board[i] = notCandy
                if (i in firstRow && board[i] == notCandy) {
                    val randomColor = floor(Math.random() * candies.size).toInt()
                    board[i] = candies[randomColor]
                }
            }
        }
        for (i in 0 until noOfBlocks) {
            if (board[i] == notCandy) {
                val randomColor = floor(Math.random() * candies.size).toInt()
                board[i] = candies[randomColor]
            }
        }
    }
}