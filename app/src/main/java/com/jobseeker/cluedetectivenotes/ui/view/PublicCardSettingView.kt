package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel

@Composable
fun PublicCardSettingView(navController: NavHostController, gameSettingViewModel: GameSettingViewModel = viewModel()){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    val context = LocalContext.current

    Surface(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.fillMaxHeight()) {
            ConstraintLayout(modifier = Modifier.fillMaxHeight()){
                val (desc, publicCards, button) = createRefs()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(desc) {
                        top.linkTo(parent.top)
                    }){
                    Column {
                        Row {
                            Text(text = "공용카드 설정", fontSize = 28.sp)
                        }
                        Row {
                            Text(text = "공용카드를 설정해주세요.")
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(publicCards) {
                        top.linkTo(desc.bottom)
                    })
                {
                    Box{
                        Text(text = uiState.value.numOfPublicCards.toString())
                    }
                    Box{
                        Text(text = uiState.value.numOfHands.toString())
                    }
                    Box (modifier = Modifier
                        .width(50.dp)
                        .fillMaxHeight()){

                        Image(
                            painterResource(id = context.resources.getIdentifier("img_suspect_green","drawable",context.packageName)),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }//Drag Handle Box End
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }){
                    NextToPlayerCardSettingButton(navController, gameSettingViewModel)
                }
            }
        }
    }
}

@Composable
fun NextToPlayerCardSettingButton(navController: NavHostController, gameSettingViewModel:GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.Sheet.route) },
            enabled = uiState.value.playerOrderSettingNextButtonEnabled
        ) {
            Text(text = "플레이")
        }
    }
}