package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel
import java.util.UUID

@Composable
fun PlayerSettingView(navController: NavHostController, gameSettingViewModel: GameSettingViewModel = viewModel()){
    val focusManger = LocalFocusManager.current

    Surface (modifier = Modifier.addFocusCleaner(focusManger)) {
        Column {
            Row {
                Text(text = "게임에 참여하는 인원 수와 이름을 설정해주세요.")
            }
            Row {
                Stepper(gameSettingViewModel)
            }
            Row {
                PlayerList(gameSettingViewModel)
            }
            Row {
                NextButton(navController, focusManger)
            }
        }
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}) : Modifier{
    return this.pointerInput(Unit) {
        detectTapGestures (onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}

@Composable
fun Stepper(gameSettingViewModel: GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()

    Column {
        Button(
            onClick = { gameSettingViewModel.intent.removeLastPlayer() }
        ) {
            Text(text = "-")
        }
    }
    Column {
        Box(modifier = Modifier
            .width(50.dp)
            .height(50.dp)){
            Text(text = uiState.value.playerNumber.toString(), modifier = Modifier.align(Alignment.Center))
        }
    }
    Column {
        Button(
            onClick = { gameSettingViewModel.intent.addPlayer() }
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun PlayerList(gameSettingViewModel:GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    val data = uiState.value.playerIdList

    LazyColumn {
        items(items = data){
            PlayerListItem(gameSettingViewModel, it, uiState.value.playerNameMap[it])
        }
    }
}

@Composable
fun PlayerListItem(gameSettingViewModel:GameSettingViewModel, id:UUID, name: String?){
    val focusRequester by remember { mutableStateOf(FocusRequester()) }

    var text by remember { mutableStateOf(TextFieldValue(name!!)) }
    Box(modifier = Modifier.padding(4.dp,2.dp,4.dp,2.dp)){
        TextField(
            value = text,
            placeholder = { Text(text = "Player") },
            onValueChange = { text = it },
            modifier = Modifier.focusRequester(focusRequester = focusRequester).onFocusChanged {
                if(!it.isFocused && text.text != "") {
                    gameSettingViewModel.intent.setPlayerName(id,text.text);
                }
            }
        )
    }
}

@Composable
fun NextButton(navController: NavHostController, focusManager: FocusManager){
    Button(onClick = {
        focusManager.clearFocus()
        navController.navigate(Routes.PlayerDetailSetting.route)
    }) {
        Text(text = "다음")
    }
}