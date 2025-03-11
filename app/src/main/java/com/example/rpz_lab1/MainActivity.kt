package com.example.rpz_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.rpz_lab1.ui.theme.RPZ_lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPZ_lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicCalculator()
                }
            }
        }
    }
}

@Composable
fun BasicCalculator(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top) {
        var num1 by remember { mutableStateOf("") }
        var num2 by remember { mutableStateOf("") }
        var result by remember { mutableStateOf<String?>(null) }
        val radioOptions = listOf("+", "-", "x", "/")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


        //Перше текстове поле
        OutlinedTextField(
            value = num1,
            onValueChange = {if (it.all { char -> char.isDigit() || char.equals('.')}) num1 = it},
            label = { Text("Введіть число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )

        //радіо-батони
        Row(modifier = Modifier.selectableGroup()){
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            Color.Magenta,
                            Color.DarkGray
                        )
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        //друге текстове поле
        OutlinedTextField(
            value = num2,
            onValueChange = {if (it.all { char -> char.isDigit() || char.equals('.')}) num2 = it},
            label = { Text("Введіть число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )

        //кнопка обчислення результату вибраної попередньо операції
        Button(
            onClick = {
                val number1 = num1.toDoubleOrNull();
                val number2 = num2.toDoubleOrNull();
                if (number1 == null || number2 == null){
                    result = "Неправильний ввід даних"
                } else {
                    result = when (selectedOption){
                        "+" -> (number1 + number2).toString()
                        "-" -> (number1 - number2).toString()
                        "x" -> (number1 * number2).toString()
                        "/" -> if (number2 == 0.0) "Ділення на 0 неможливе" else (number1 / number2).toString()
                        else -> "Невідома помилка"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xff0e8da4)),
            modifier = Modifier
                .padding(20.dp)
                .width(100.dp)
        ) { Text("=") }

        result?.let{
            Text(
                text="Результат: $it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(20.dp))
        }
    }
}