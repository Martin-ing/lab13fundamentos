package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                BMICalc()
            }
        }
    }
}

@Composable
fun BMICalc() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Float?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BMICalcForm(
            weight = weight,
            height = height,
            onWeightChange = { weight = it },
            onHeightChange = { height = it },
            onCalculateClick = {
                val newWeight = weight.toFloatOrNull() ?: 0f
                val newHeight = height.toFloatOrNull() ?: 0f
                if (newHeight > 0) {
                    bmiResult = newWeight / (newHeight * newHeight)
                }
            }
        )
    }
}

@Composable
fun BMICalcForm(weight: String, height: String, onWeightChange: (String) -> Unit, onHeightChange: (String) -> Unit, onCalculateClick: () -> Unit) {

}

@Composable
fun BMICalcResult() {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        BMICalc()
    }
}