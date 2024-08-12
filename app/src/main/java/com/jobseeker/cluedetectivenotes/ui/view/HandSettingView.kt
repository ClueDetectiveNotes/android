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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    val numOfPublicCards = uiState.value.numOfPublicCards
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
                    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        //선택한 카드
                        Row{
                            Column {
                                Spacer(modifier = Modifier.height(50.dp))
                                Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
                                    for(idx in 0 until numOfHands){

                                        val hand =
                                            try { handList[idx] }
                                            catch (_:Exception) { null }

                                        val cardType =
                                            if(suspectCardList.contains(hand)){ "suspect" }
                                            else if(weaponCardList.contains(hand)){ "weapon" }
                                            else if(crimeSceneCardList.contains(hand)){ "crimeScene" }
                                            else { null }

                                        if(hand != null && cardType != null){
                                            GameCardView(
                                                context = context,
                                                type = cardType,
                                                cardName = hand,
                                                clickAction = { gameSettingViewModel.intent.selectHand(hand) },
                                                colorFilter = null
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
                                    CardList(
                                        gameSettingViewModel = gameSettingViewModel,
                                        context = context,
                                        handList = handList,
                                        cardList = suspectCardList,
                                        cardType = "suspect",
                                        listName = "용의자"
                                    )
                                    CardList(
                                        gameSettingViewModel = gameSettingViewModel,
                                        context = context,
                                        handList = handList,
                                        cardList = weaponCardList,
                                        cardType = "weapon",
                                        listName = "흉기"
                                    )
                                    CardList(
                                        gameSettingViewModel = gameSettingViewModel,
                                        context = context,
                                        handList = handList,
                                        cardList = crimeSceneCardList,
                                        cardType = "crime_scene",
                                        listName = "범행장소"
                                    )
                                }
                            }
                        }
                    }
                }
                if(numOfHands == handList.size){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(button) {
                            bottom.linkTo(parent.bottom)
                        }
                    ){
                        if(numOfPublicCards > 0){
                            NextToPublicCardSettingButton(navController = navController)
                        }else{
                            PlayGameButton(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardList(gameSettingViewModel:GameSettingViewModel, context: Context, handList:List<String>, cardList:List<String>, cardType:String, listName:String){
    Row {
        Column {
            Row {
                Text(text = listName)
            }
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                for (card in cardList) {

                    val colorFilter = if (handList.contains(card)) ColorFilter.tint(
                        color = Color.Gray,
                        blendMode = BlendMode.Darken
                    ) else null

                    GameCardView(
                        context = context,
                        type = cardType,
                        cardName = card,
                        clickAction = { gameSettingViewModel.intent.selectHand(card) },
                        colorFilter = colorFilter
                    )
                }
            }
        }
    }
}