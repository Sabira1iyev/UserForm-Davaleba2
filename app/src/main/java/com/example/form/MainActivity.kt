package com.example.form

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.form.ui.theme.AccentCyan
import com.example.form.ui.theme.AccentCyanDim
import com.example.form.ui.theme.BackgroundDark
import com.example.form.ui.theme.CardDark
import com.example.form.ui.theme.ErrorRed
import com.example.form.ui.theme.FormTheme
import com.example.form.ui.theme.SurfaceDark
import com.example.form.ui.theme.TextPrimary
import com.example.form.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = AccentCyan,
                    onPrimary = BackgroundDark,
                    background = BackgroundDark,
                    onBackground = TextPrimary,
                    surface = SurfaceDark,
                    onSurface = TextPrimary,
                    surfaceVariant = CardDark,
                    onSurfaceVariant = TextSecondary,
                    outline = AccentCyanDim,
                    error = ErrorRed
                )
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundDark
                ) { innerPadding ->
                    FormScreen(innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(innerPadding: PaddingValues = PaddingValues()) {
    val context = LocalContext.current

    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val options = listOf("Android", "iOS", "Web")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Student Form",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )

        OutlinedTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextSecondary,
                cursorColor = AccentCyan
            )
        )

        OutlinedTextField(
            value = surnameState,
            onValueChange = { surnameState = it },
            label = { Text("Surname") },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextSecondary,
                cursorColor = AccentCyan
            )
        )

        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextSecondary,
                cursorColor = AccentCyan
            )
        )

        Text(
            text = "Your Favorite Aspect",
            color = TextPrimary
        )

        options.forEach { option ->
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { selectedOption = option }
                )
                Text(text = option, color = TextPrimary)
            }
        }

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "I agree to the terms and conditions.",
                color = TextPrimary
            )
            Switch(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it }
            )
        }

        OutlinedTextField(
            value = dateState,
            onValueChange = {},
            label = { Text("Date") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = TextSecondary,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextSecondary,
                cursorColor = AccentCyan
            ),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.CalendarMonth,
                        contentDescription = "Choose date",
                        tint = TextPrimary
                    )
                }
            }
        )

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = java.util.Calendar.getInstance()
                            calendar.timeInMillis = millis
                            val day = String.format("%02d", calendar.get(java.util.Calendar.DAY_OF_MONTH))
                            val month = String.format("%02d", calendar.get(java.util.Calendar.MONTH) + 1)
                            val year = calendar.get(java.util.Calendar.YEAR)
                            dateState = "$day/$month/$year"
                        }
                        showDatePicker = false
                    }) { Text("Ok") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(
            onClick = {
                if (nameState.isBlank() || surnameState.isBlank() || emailState.isBlank() || dateState.isBlank() || selectedOption.isBlank() || !isAgreed) {
                    Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}