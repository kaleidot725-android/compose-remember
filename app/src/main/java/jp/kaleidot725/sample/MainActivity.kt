package jp.kaleidot725.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jp.kaleidot725.sample.ui.theme.SampleTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    val mainState = MainState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val count by mainState.count.collectAsState()

            SampleTheme {
                if (10 < count) {
                    CounterOver10(count, { mainState.plus() }, { mainState.minus() })
                } else {
                    Counter(count, { mainState.plus() }, { mainState.minus() })
                }
            }
        }
    }
}

@Composable
private fun CounterOver10(count: Int, onPlus: () -> Unit, onMinus: () -> Unit) {
    var plusCount by remember { mutableStateOf(0) }
    var minusCount by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Column {
            Text("Count $count PlusCount $plusCount MinusCount $minusCount")
            Button(
                onClick = {
                    plusCount++
                    onPlus.invoke()
                }
            ) {
                Text(text = "Plus")
            }
            Button(
                onClick = {
                    minusCount++
                    onMinus.invoke()
                }
            ) {
                Text(text = "Minus")
            }
        }
    }
}

@Composable
private fun Counter(count: Int, onPlus: () -> Unit, onMinus: () -> Unit) {
    var plusCount by remember { mutableStateOf(0) }
    var minusCount by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Column {
            Text("Count $count PlusCount $plusCount MinusCount $minusCount")
            Button(
                onClick = {
                    plusCount++
                    onPlus.invoke()
                }
            ) {
                Text(text = "Plus")
            }
            Button(
                onClick = {
                    minusCount++
                    onMinus.invoke()
                }
            ) {
                Text(text = "Minus")
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