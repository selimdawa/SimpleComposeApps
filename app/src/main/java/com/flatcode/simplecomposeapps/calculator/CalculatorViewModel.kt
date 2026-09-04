package com.flatcode.simplecomposeapps.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.calculator.data.CalculatorDao
import com.flatcode.simplecomposeapps.calculator.data.CalculatorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val calculatorDao: CalculatorDao) :
    ViewModel() {

    private val _expression = MutableLiveData("")
    val expression: LiveData<String> get() = _expression

    private val _result = MutableLiveData("")
    val result: LiveData<String> get() = _result

    val historyList: LiveData<List<CalculatorEntity>> = calculatorDao.getAllHistory().asLiveData()

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
                        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
                            val expression =
                                net.objecthunter.exp4j.ExpressionBuilder(currentExpression).build()
                            val result = expression.evaluate()
                            val longResult = result.toLong()
                            if (result == longResult.toDouble()) {
                                "= $longResult"
                            } else {
                                "= $result"
                            }
                        }
                    setResultValue(finalResult)
                    saveToHistory(currentExpression, finalResult)
                } catch (e: Exception) {
                    _result.value = "Error"
                }
            }
        }
    }

    fun saveToHistory(exp: String, res: String) {
        viewModelScope.launch {
            if (exp.isNotEmpty() && res.isNotEmpty()) {
                calculatorDao.insertHistory(
                    CalculatorEntity(expression = exp, result = res)
                )
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            calculatorDao.clearHistory()
        }
    }
}