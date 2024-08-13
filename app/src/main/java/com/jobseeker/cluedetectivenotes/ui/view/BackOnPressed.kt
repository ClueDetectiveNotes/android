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
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel
import kotlin.system.exitProcess

@Composable
fun BackOnPressed(optionViewModel: OptionViewModel = viewModel()) {
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