package com.flatcode.simplecomposeapps.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.calculator.data.CalculatorEntity
import com.flatcode.simplecomposeapps.calculator.data.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.objecthunter.exp4j.ExpressionBuilder
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val repository: CalculatorRepository) :
    ViewModel() {

    private val _expression = MutableLiveData("")
    val expression: LiveData<String> = _expression

    private val _result = MutableLiveData("")
    val result: LiveData<String> = _result

    val historyList: LiveData<List<CalculatorEntity>> = repository.getAllHistory().asLiveData()

    fun appendValue(value: String) {
        _expression.value = (_expression.value ?: "") + value
    }

    fun clearAll() {
        _expression.value = ""
        _result.value = ""
    }

    fun deleteLast() {
        val currentExp = _expression.value ?: ""
        if (currentExp.isNotEmpty()) {
            _expression.value = currentExp.dropLast(1)
        }
    }

    fun setResultValue(evaluatedResult: String) {
        _result.value = evaluatedResult
    }

    fun evaluateExpression() {
        val currentExpression = _expression.value ?: ""
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
                } catch (_: Exception) {
                    _result.value = "Error"
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
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}