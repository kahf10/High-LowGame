package hu.ait.highlowgamecompose.ui.screen

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*


@Composable
fun GameScreen() {
    var generatedNum by rememberSaveable { mutableStateOf(
        Random(System.currentTimeMillis()).nextInt(10)) }

    var textResult by rememberSaveable { mutableStateOf("Good Luck!") }
    var text by rememberSaveable { mutableStateOf("") }

    var inputErrorState by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable { mutableStateOf("Error...") }

    fun validate(text: String) {
        val allDigit = text.all{char -> (char.equals('-') || char.isDigit())}
        errorText = "This field can be number only"
        inputErrorState = !allDigit
    }

    Column(modifier = Modifier.padding(10.dp)) {

        OutlinedTextField(
            value = text,
            label = {("Enter your guess here")},
            onValueChange = {
                text = it
                validate(text)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            OutlinedButton(onClick = {
                try {
                    val myNum = text.toInt()
                    if(myNum == generatedNum){
                        textResult = "Congratulations!! You have won"
                    }
                    else if(myNum < generatedNum)
                    {
                        textResult = "The number is higher"
                    }
                    else
                    {
                        textResult = "The number is lower"
                    }
                } catch (e: Exception) {
                    inputErrorState = true
                    errorText = e.localizedMessage
                }
            }) {
                Text(text = "Guess")
            }

            OutlinedButton(onClick = {
                generatedNum = Random(System.currentTimeMillis()).nextInt(10)
            }) {
                Text(text = "Restart")

            }
        }

        if(inputErrorState) {
            Text(
                text = "$errorText",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Text(text = "$textResult",
//            color = Color.BLUE,
//            fontSize = 28.sp
            )
    }
}