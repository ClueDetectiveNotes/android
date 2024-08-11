package com.jobseeker.cluedetectivenotes.ui.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel

@Composable
fun HandSettingView(navController: NavHostController, gameSettingViewModel: GameSettingViewModel = viewModel()){
    gameSettingViewModel.intent.loadCardList()

    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    val context = LocalContext.current

    val suspectCardList = uiState.value.suspectCardList
    val weaponCardList = uiState.value.weaponCardList
    val crimeSceneCardList = uiState.value.crimeSceneCardList

    val numOfHands = uiState.value.numOfHands
    val handList = uiState.value.handList

    Surface(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.fillMaxHeight()) {
            ConstraintLayout(modifier = Modifier.fillMaxHeight()){
                val (desc, hands, button) = createRefs()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(desc) {
                        top.linkTo(parent.top)
                    }){
                    Column {
                        Row {
                            Text(text = "개인 카드 설정", fontSize = 28.sp)
                        }
                        Row {
                            Text(text = "개인 카드를 설정해주세요.")
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(hands) {
                        top.linkTo(desc.bottom)
                    })
                {
                    Column {
                        //선택한 카드
                        Row{
                            Column {
                                Spacer(modifier = Modifier.height(50.dp))
                                Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
                                    for(idx in 0 until numOfHands){

                                        val hand =
                                            try { handList[idx] }
                                            catch (_:Exception) { null }

                                        val type =
                                            if(suspectCardList.contains(hand)){ "suspect" }
                                            else if(weaponCardList.contains(hand)){ "weapon" }
                                            else if(crimeSceneCardList.contains(hand)){ "crimeScene" }
                                            else { null }

                                        if(hand != null && type != null){
                                            GameCard(
                                                gameSettingViewModel = gameSettingViewModel,
                                                context = context,
                                                type = type,
                                                cardName = hand
                                            )
                                        }else{
                                            Card (
                                                modifier = Modifier
                                                    .width(120.dp)
                                                    .height(120.dp)
                                                    .padding(5.dp)
                                                    .border(width = 1.dp, color = Color.Black),
                                                shape = RoundedCornerShape(0.dp),
                                                )
                                            {
                                                Text(text = "")
                                            }   
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(50.dp))
                            }
                        }

                        if(numOfHands != handList.size){
                            Row {
                                Column (modifier = Modifier.verticalScroll(rememberScrollState())){
                                    //선택 카드
                                    Row {
                                        Column {
                                            Row {
                                                Text(text = "용의자")
                                            }
                                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                                for (suspectCard in suspectCardList) {
                                                    GameCard(
                                                        gameSettingViewModel = gameSettingViewModel,
                                                        context = context,
                                                        cardList = handList,
                                                        type = "suspect",
                                                        cardName = suspectCard
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Row {
                                        Column {
                                            Row {
                                                Text(text = "흉기")
                                            }
                                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                                for (weaponCard in weaponCardList) {
                                                    GameCard(
                                                        gameSettingViewModel = gameSettingViewModel,
                                                        context = context,
                                                        cardList = handList,
                                                        type = "weapon",
                                                        cardName = weaponCard
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    Row{
                                        Column {
                                            Row {
                                                Text(text = "범행장소")
                                            }
                                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
                                                for (crimeSceneCard in crimeSceneCardList){
                                                    GameCard(
                                                        gameSettingViewModel = gameSettingViewModel,
                                                        context = context,
                                                        cardList = handList,
                                                        type = "crime_scene",
                                                        cardName = crimeSceneCard
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }){
                    if(numOfHands == handList.size){
                        NextToPublicCardSettingButton(navController, gameSettingViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(gameSettingViewModel:GameSettingViewModel, context: Context, cardList:List<String> = emptyList(), type:String, cardName:String){
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(0.dp),
        onClick = {gameSettingViewModel.intent.selectHand(cardName)}
    ) {
        val id = try{
            context.resources.getIdentifier(
                "img_" + type + "_" + cardName.lowercase(),
                "drawable",
                context.packageName)
        } catch (_:Exception){
            0x0
        }
        if(id != 0x0){
            Image(
                painterResource(id = id),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                colorFilter = if(cardList.contains(cardName)) ColorFilter.tint(color = Color.Gray, blendMode = BlendMode.Darken) else null,
                //modifier = Modifier.align(Alignment.Center)
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .border(width = 1.dp, color = Color.Black)
                    .background(color = Color(MaterialTheme.colorScheme.surface.value))
            )
        }else{
            Text(text = cardName)
        }
    }
}

@Composable
fun NextToPublicCardSettingButton(navController: NavHostController, gameSettingViewModel:GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.PublicCardSetting.route) },
            enabled = uiState.value.playerOrderSettingNextButtonEnabled
        ) {
            Text(text = "다음")
        }
    }
}