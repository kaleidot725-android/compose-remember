package jp.kaleidot725.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import jp.kaleidot725.sample.ui.theme.SampleTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    private val mainState = MainState()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val count by mainState.count.collectAsState()
            SampleTheme {
                if (10 < count) {
                    CounterOver10(count, { mainState.plus() }, { mainState.minus() })
                } else {
                    CounterLessThan10(count, { mainState.plus() }, { mainState.minus() })
                }
            }
        }
    }
}

class MainState {
    private val _count: MutableStateFlow<Int> = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun plus() {
        _count.value = _count.value + 1
    }

    fun minus() {
        _count.value = _count.value - 1
    }
}

@Composable
private fun CounterOver10(count: Int, onPlus: () -> Unit, onMinus: () -> Unit) {
    val plusCount = remember { mutableStateOf(0) }
    val minusCount = remember { mutableStateOf(0) }
    Counter(count, plusCount, minusCount, onPlus, onMinus)
}

@Composable
private fun CounterLessThan10(count: Int, onPlus: () -> Unit, onMinus: () -> Unit) {
    val plusCount = remember { mutableStateOf(0) }
    val minusCount = remember { mutableStateOf(0) }
    Counter(count, plusCount, minusCount, onPlus, onMinus)
}

@Composable
private fun Counter(
    count: Int,
    plusCount: MutableState<Int>,
    minusCount:  MutableState<Int>,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Text(text = "Counter", fontSize = 20.sp)
            Text(text = "Count $count PlusCount $plusCount MinusCount $minusCount")
            Button(
                onClick = {
                    plusCount.value = plusCount.value++
                    onPlus.invoke()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Plus")
            }
            Button(
                onClick = {
                    minusCount.value = plusCount.value++
                    onMinus.invoke()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Minus")
            }
        }
    }
}
