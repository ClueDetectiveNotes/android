package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel

@Composable
fun NextToDetailButton(navController: NavHostController, focusManager: FocusManager, gameSettingViewModel: GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            focusManager.clearFocus()
            navController.navigate(Routes.PlayerDetailSetting.route)
        },
            enabled = uiState.value.playerSettingNextButtonEnabled,
        ) {
            Text(text = "다음")
        }
    }
}

@Composable
fun NextToCardSettingButton(navController: NavHostController, gameSettingViewModel:GameSettingViewModel){
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.HandSetting.route) },
            enabled = uiState.value.playerOrderSettingNextButtonEnabled
        ) {
            Text(text = "다음")
        }
    }
}

@Composable
fun NextToPublicCardSettingButton(navController: NavHostController){
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.PublicCardSetting.route) },
        ) {
            Text(text = "다음")
        }
    }
}

@Composable
fun PlayGameButton(navController: NavHostController){
    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { navController.navigate(Routes.Sheet.route) },
        ) {
            Text(text = "플레이")
        }
    }
}