package com.jobseeker.cluedetectivenotes.ui.view

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel
import kotlin.system.exitProcess

@Composable
fun BackOnPressedShuttingDown(optionViewModel: OptionViewModel = viewModel()) {
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang

    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if(System.currentTimeMillis() - backPressedTime <= 800L) {
            (context as Activity).finishAffinity()
            System.runFinalization();
            exitProcess(0);
        } else {
            backPressedState = true
            Toast.makeText(context, multiLang["MSG.CM_BOP"]!!, Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
@Composable
fun BackOnPressedBackToPlayerSetting(navController: NavController, gameSettingViewModel: GameSettingViewModel = viewModel()) {
    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        gameSettingViewModel.intent.initPlayerType()
        navController.popBackStack()
    }
}
@Composable
fun BackOnPressedBackToPlayerSettingDetail(navController: NavController, gameSettingViewModel: GameSettingViewModel = viewModel()) {
    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        gameSettingViewModel.intent.initGame()
        navController.popBackStack()
    }
}
@Composable
fun BackOnPressedBackToHandSetting(navController: NavController, gameSettingViewModel: GameSettingViewModel = viewModel()) {
    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        gameSettingViewModel.intent.initPublicCards()
        navController.popBackStack()
    }
}
@Composable
fun BackOnPressedBackToHome(navController: NavController, gameSettingViewModel: GameSettingViewModel = viewModel()) {
    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        gameSettingViewModel.intent.initAll()
        navController.navigate(Routes.Home.route){
            popUpTo(0)
        }
    }
}