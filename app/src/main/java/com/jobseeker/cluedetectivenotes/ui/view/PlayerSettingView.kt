package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel
import java.util.UUID

@Composable
fun PlayerSettingView(navController: NavHostController, gameSettingViewModel: GameSettingViewModel = viewModel()){
    val focusManger = LocalFocusManager.current
    val uiState = gameSettingViewModel.store.uiState.collectAsState()

    Surface (modifier = Modifier.addFocusCleaner(focusManger).padding(10.dp)){
        Column (modifier = Modifier.fillMaxHeight()) {
            ConstraintLayout(modifier = Modifier.fillMaxHeight()){
                val (desc, stepper, playerList, button) = createRefs()
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(desc) {
                        top.linkTo(parent.top)
                    }){
                    Column {
                        Row {
                            Text(text = "플레이어 설정", fontSize = 28.sp)
                        }
                        Row {
                            Text(text = "게임에 참여하는 인원 수와 이름을 설정해주세요.")
                        }
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(stepper) {
                        top.linkTo(desc.bottom)
                    }){
                    Column {
                        Row {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                        Row {
                            Stepper(gameSettingViewModel)
                        }
                        Row {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(playerList) {
                        top.linkTo(stepper.bottom)
                    }){
                    PlayerList(gameSettingViewModel)
                }
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }){
                    Column {
                        if(!uiState.value.playerSettingNextButtonEnabled){
                            Row (modifier = Modifier.fillMaxWidth()) {
                                Box (modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "이름이 입력되지 않았거나, 중복된 이름이 있습니다.",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        Row {
                            NextToDetailButton(navController, focusManger, gameSettingViewModel)
                        }
                    }
                }
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
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 2.dp, 4.dp, 2.dp),
        contentAlignment = Alignment.Center
    ){
        Row {
            Column {
                Button(
                    onClick = { gameSettingViewModel.intent.removeLastPlayer() },
                    enabled = uiState.value.minusButtonEnabled
                ) {
                    Image(
                        painterResource(R.drawable.ic_setting_remove_player),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
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
                    onClick = { gameSettingViewModel.intent.addPlayer() },
                    enabled = uiState.value.plusButtonEnabled
                ) {
                    Image(
                        painterResource(R.drawable.ic_setting_add_player),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerList(gameSettingViewModel:GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    val data = uiState.value.playerIdList
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 2.dp, 4.dp, 2.dp),
        contentAlignment = Alignment.Center
    ){
        LazyColumn {
            items(items = data){
                PlayerListItem(gameSettingViewModel, it, uiState.value.playerNameMap[it], data.indexOf(it)+1)
            }
        }
    }
}

@Composable
fun PlayerListItem(gameSettingViewModel:GameSettingViewModel, id:UUID, name: String?, index:Int){
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var text by remember { mutableStateOf(TextFieldValue(name!!)) }

    Box(modifier = Modifier.padding(4.dp,4.dp,4.dp,4.dp)){
        TextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            value = text,
            placeholder = { Text(text = "Player$index Name") },
            onValueChange = {
                                text = it
                                gameSettingViewModel.intent.setPlayerName(id,text.text);
                            },
            modifier = Modifier
                .focusRequester(focusRequester = focusRequester)
                .onFocusChanged {
                    if (!it.isFocused && text.text != "") {
                        gameSettingViewModel.intent.setPlayerName(id, text.text);
                    }
                }
        )
    }
}