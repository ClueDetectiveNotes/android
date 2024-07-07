package com.jobseeker.cluedetectivenotes.ui.view

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes

@Composable
fun PlayerSettingView(navController: NavHostController){

    Surface {
        Column {
            Row {
                Text(text = "게임에 참여하는 인원 수와 이름을 설정해주세요.")
            }
            Row {
                Stepper()
            }
            Row {
                PlayerList()
            }
            Row {
                NextButton(navController)
            }
        }
    }
}

@Composable
fun Stepper(){
    val playerCount by remember { mutableStateOf("3") }

    Column {
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "-")
        }
    }
    Column {
        Box(modifier = Modifier.width(50.dp).height(50.dp)){
            Text(text = playerCount, modifier = Modifier.align(Alignment.Center))
        }
    }
    Column {
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun PlayerList(){
    val data = remember { mutableStateOf(List(3) { "Item $it" }) }

    LazyColumn {
        items(items = data.value){
            PlayerListItem()
        }
    }
}

@Composable
fun PlayerListItem(){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(modifier = Modifier.padding(4.dp,2.dp,4.dp,2.dp)){
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Player") }
        )
    }
}

@Composable
fun NextButton(navController: NavHostController){
    Button(onClick = { navController.navigate(Routes.PlayerDetailSetting.route) }) {
        Text(text = "다음")
    }
}