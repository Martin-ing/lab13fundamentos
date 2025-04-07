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
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFC4E8F2)
                ){
                Column {
                    //Encabezado
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1AA0F1))
                            .padding(12.dp)
                            .height(30.dp)
                    ) {
                        Text(
                            text = "BMI Calculator",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    //ejecucion de toda la app
                    BMICalc()
                }
                }
            }
        }
    }
}

@Composable
fun BMICalc() {
    //variables de peso, altura, resultado y las verificaciones para mostrar el contenido
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Float>(0f) }
    var showBmiResult by remember { mutableStateOf(false) }
    var primeraVez by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //manda a llamar al form donde se ingresan los datos
        BMICalcForm(
            sbmi = showBmiResult,
            weight = weight,
            height = height,
            //funciones que manejan los cambios de valores
            onWeightChange = { weight = it },
            onHeightChange = { height = it },
            onCalculateClick = {
                val newWeight = weight.toFloatOrNull() ?: 0f
                val newHeight = height.toFloatOrNull() ?: 0f
                primeraVez = true
                //Si alguno de los dos valores son falsos se toma el inpunt como invalido
                if(newWeight == 0f || newHeight == 0f) {
                    showBmiResult = false
                }
                else{
                    showBmiResult = true
                }
                if (newHeight > 0) {
                    bmiResult = (newWeight * 0.453592f) / (newHeight * newHeight * 0.0001f)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
        //manda a llamar a la funcion que muestra el resulado, solo si ya no es la primera vez que se preciona el boton
        if(primeraVez) {
            BMICalcResult(sbmi = showBmiResult, bmi = bmiResult)
        }

    }
}

//En esta funcion esta el form para ingresar los datos, con los campos de texto y el boton de submit
@Composable
fun BMICalcForm(sbmi: Boolean,weight: String, height: String, onWeightChange: (String) -> Unit, onHeightChange: (String) -> Unit, onCalculateClick: () -> Unit) {
    Column {

        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { Text("Peso (lbs)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = height,
            onValueChange = onHeightChange,
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCalculateClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular IMC")
        }
    }
}

//Aqui se despliegan los resultados
@Composable
fun BMICalcResult(sbmi:Boolean, bmi: Float) {
    var category = ""
    var circleColor = Color.Gray

    //dependiendo del rango va a mostrar la condicion del usuario con un color para representar la situacion
    if (bmi < 18.5f) {
        category = "Bajo peso"
        circleColor = Color(0xFF2196F3)
    } else if (bmi < 25f) {
        category = "Normal"
        circleColor = Color(0xFF4CAF50)
    } else if (bmi < 30f) {
        category = "Sobre peso"
        circleColor = Color(0xFFFFC107)
    } else {
        category = "Obeso"
        circleColor = Color(0xFFF44336)
    }

    //card donde se muestran los datos
    //dos columnas, una con el resultado numerico y otra con el texto de obesidad, normal, etc...
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        //Solo si los inputs son validos va a mostrar la informacion
        if(sbmi) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tu IMC es:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "%.2f".format(bmi),
                        style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF9C27B0)) // Morado
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(circleColor, shape = CircleShape)
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        //De lo contrario se muestra un mensaje de error
        else{
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Datos invalidos",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        BMICalc()
    }
}