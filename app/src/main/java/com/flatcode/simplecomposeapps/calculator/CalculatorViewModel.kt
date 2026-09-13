package com.flatcode.simplecomposeapps.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.calculator.data.CalculatorEntity
import com.flatcode.simplecomposeapps.calculator.data.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val repository: CalculatorRepository) :
    ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    val historyList: StateFlow<List<CalculatorEntity>> = repository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun appendValue(value: String) {
        Timber.d("Appending value: %s", value)
        _expression.value += value
    }

    fun clearAll() {
        Timber.d("Clearing all")
        _expression.value = ""
        _result.value = ""
    }

    fun deleteLast() {
        val currentExp = _expression.value
        if (currentExp.isNotEmpty()) {
            _expression.value = currentExp.dropLast(1)
            Timber.d("Deleted last character. New expression: %s", _expression.value)
        }
    }

    fun setResultValue(evaluatedResult: String) {
        _result.value = evaluatedResult
    }

    fun evaluateExpression() {
        val currentExpression = _expression.value
        Timber.d("Evaluating expression: %s", currentExpression)
        if (currentExpression.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val finalResult =
                        withContext(Dispatchers.Default) {
                            val expressionBuilder =
                                ExpressionBuilder(currentExpression).build()
                            val resultVal = expressionBuilder.evaluate()
                            val longResult = resultVal.toLong()
                            if (resultVal == longResult.toDouble()) {
                                "= $longResult"
                            } else {
                                "= $resultVal"
                            }
                        }
                    setResultValue(finalResult)
                    saveToHistory(currentExpression, finalResult)
                    Timber.d("Evaluation successful: %s", finalResult)
                } catch (e: Exception) {
                    _result.value = "Error"
                    Timber.e(e, "Evaluation failed")
                }
            }
        }
    }

    fun saveToHistory(exp: String, res: String) {
        viewModelScope.launch {
            if (exp.isNotEmpty() && res.isNotEmpty()) {
                repository.insertHistory(
                    CalculatorEntity(expression = exp, result = res)
                )
                Timber.d("Saved to history: %s %s", exp, res)
            }
        }
    }

    fun clearHistory() {
        Timber.d("Clearing history")
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
