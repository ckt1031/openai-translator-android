package com.ckt1031.openai.translator.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ckt1031.openai.translator.items.openaiChatModels
import com.ckt1031.openai.translator.items.openaiVoiceSpeakers
import com.ckt1031.openai.translator.store.APIDataStore
import com.ckt1031.openai.translator.store.APIDataStoreKeys

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(dataStore: DataStore<Preferences>) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(16.dp, 0.dp)
        ) {
//            Row(
//                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 16.dp)
//                    ) {
//                var expanded by remember { mutableStateOf(false) }
//                var selectedIndex by remember { mutableStateOf(0) }
//
//                Box(Modifier.weight(1f)) {
//                    Column {
//                        Text("API Type", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
//                    }
//                }
//
//                Text(
//                    text = apiType[selectedIndex],
//                    modifier = Modifier
//                        .clickable(onClick = { expanded = true })
//                )
//
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    properties = PopupProperties(focusable = true)
//                ) {
//                    apiType.forEachIndexed { index, item ->
//                        DropdownMenuItem(
//                            onClick = {
//                                selectedIndex = index
//                                expanded = false
//                            },
//                            text = {
//                                Text(text = item)
//                            }
//                        )
//                    }
//                }
//            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 16.dp)
            ) {
                var text by remember { mutableStateOf("https://api.openai.com") }

                // Read the Host from DataStore
                val host: String? by APIDataStore(dataStore).readStringPreference(APIDataStoreKeys.OpenAIHost).collectAsState(initial = null)

                LaunchedEffect(host) {
                    if (host != null) {
                        text = host as String
                    }
                }

                LaunchedEffect(text) {
                    APIDataStore(dataStore).saveStringPreference(APIDataStoreKeys.OpenAIHost, text)
                }

                // Use align(Alignment.CenterVertically) to center the Column content vertically
                Box(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text("Host", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                    }
                }

                TextField(
                    value = text,
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterVertically), // Align the TextField vertically
                    onValueChange = { newText ->
                        text = newText
                    },
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 16.dp)
            ) {
                var text by remember { mutableStateOf("") }

                // Use align(Alignment.CenterVertically) to center the Column content vertically
                Box(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text("API Key", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                    }
                }

                // Read the API key from DataStore
                val apiKey: String? by APIDataStore(dataStore).readStringPreference(APIDataStoreKeys.OpenAIKey).collectAsState(initial = null)

                // Initialize the text field with the API key from DataStore
                LaunchedEffect(apiKey) {
                    if (apiKey != null) {
                        text = apiKey as String
                    }
                }

                // Save the API key to DataStore when it changes
                LaunchedEffect(text) {
                    APIDataStore(dataStore).saveStringPreference(APIDataStoreKeys.OpenAIKey, text)
                }

                TextField(
                    value = text,
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterVertically), // Align the TextField vertically
                    onValueChange = { newText ->
                        text = newText
                    },
                    visualTransformation = PasswordVisualTransformation(), // Add this line to make it a password field
                    singleLine = true, // Add this line to ensure the text field is a single line input
                )
            }

            var selectedModelIndex by remember { mutableStateOf(0) }
            var selectedModel = openaiChatModels[selectedModelIndex]

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 16.dp)
            ) {
                var modelExpanded by remember { mutableStateOf(false) }

                val model: String? by APIDataStore(dataStore).readStringPreference(APIDataStoreKeys.OpenAIChatModel).collectAsState(initial = null)

                LaunchedEffect(model) {
                    if (model != null) {
                        selectedModel = model as String
                    }
                }

                LaunchedEffect(selectedModelIndex) {
                    APIDataStore(dataStore).saveStringPreference(APIDataStoreKeys.OpenAIChatModel, openaiChatModels[selectedModelIndex])
                }

                Box(Modifier.weight(1f)) {
                    Column {
                        Text("Chat Model", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                    }
                }

                Text(
                    text = selectedModel,
                    modifier = Modifier
                        .clickable(onClick = { modelExpanded = true })
                )

                DropdownMenu(
                    expanded = modelExpanded,
                    onDismissRequest = { modelExpanded = false },
                    properties = PopupProperties(focusable = true)
                ) {
                    openaiChatModels.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedModelIndex = index
                                modelExpanded = false
                            },
                            text = {
                                Text(text = item)
                            }
                        )
                    }
                }
            }

            if (selectedModel == "custom") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, top = 16.dp)
                ) {
                    var text by remember { mutableStateOf("") }

                    val customModel: String? by APIDataStore(dataStore).readStringPreference(APIDataStoreKeys.OpenAICustomChatModel).collectAsState(initial = null)

                    LaunchedEffect(customModel) {
                        if (customModel != null) {
                            text = customModel as String
                        }
                    }

                    LaunchedEffect(text) {
                        APIDataStore(dataStore).saveStringPreference(APIDataStoreKeys.OpenAICustomChatModel, text)
                    }

                    // Use align(Alignment.CenterVertically) to center the Column content vertically
                    Box(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                        Column(verticalArrangement = Arrangement.Center) {
                            Text("Custom Model", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                        }
                    }

                    TextField(
                        value = text,
                        modifier = Modifier
                            .width(200.dp)
                            .align(Alignment.CenterVertically), // Align the TextField vertically
                        onValueChange = { newText ->
                            text = newText
                        },
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 16.dp)
            ) {
                var status by remember { mutableStateOf(false) }

                // Use align(Alignment.CenterVertically) to center the Column content vertically
                Box(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text("Response in Stream Mode", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                    }
                }

                // Read the API key from DataStore
                val stream: Boolean? by APIDataStore(dataStore).readBoolPreference(APIDataStoreKeys.OpenAIEnableStream).collectAsState(initial = null)

                // Initialize the text field with the API key from DataStore
                LaunchedEffect(stream) {
                    if (stream != null) {
                        status = stream as Boolean
                    }
                }

                // Save the API key to DataStore when it changes
                LaunchedEffect(status) {
                    APIDataStore(dataStore).saveBoolPreference(APIDataStoreKeys.OpenAIEnableStream, status)
                }

                Switch(
                    checked = status,
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    onCheckedChange = {
                        status = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 16.dp)
            ) {
                var speakerIndex  by remember { mutableStateOf(0) }
                var selectedSpeaker = openaiVoiceSpeakers[speakerIndex]
                var modelExpanded by remember { mutableStateOf(false) }

                val speaker: String? by APIDataStore(dataStore).readStringPreference(APIDataStoreKeys.OpenAIVoiceSpeaker).collectAsState(initial = null)

                LaunchedEffect(speaker) {
                    if (speaker != null) {
                        selectedSpeaker = speaker as String
                    }
                }

                LaunchedEffect(speakerIndex) {
                    APIDataStore(dataStore).saveStringPreference(APIDataStoreKeys.OpenAIChatModel, openaiVoiceSpeakers[speakerIndex])
                }

                Box(Modifier.weight(1f)) {
                    Column {
                        Text("TTS Speaker", style = androidx.compose.ui.text.TextStyle(fontSize = 17.sp))
                    }
                }

                Text(
                    text = selectedSpeaker,
                    modifier = Modifier
                        .clickable(onClick = { modelExpanded = true })
                )

                DropdownMenu(
                    expanded = modelExpanded,
                    onDismissRequest = { modelExpanded = false },
                    properties = PopupProperties(focusable = true)
                ) {
                    openaiVoiceSpeakers.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                speakerIndex = index
                                modelExpanded = false
                            },
                            text = {
                                Text(text = item)
                            }
                        )
                    }
                }
            }
        }
    }
}
