package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel

@Composable
fun HomeView(
    navController: NavHostController,
    optionViewModel: OptionViewModel = viewModel()
) {
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang
    Box(modifier = Modifier.size(50F.dp), contentAlignment = Alignment.Center){
        Column {
            Row {
                Button(modifier = Modifier.width(150.dp), content = { Text(text = multiLang.getString("BTN.START")) },
                    onClick = {
                        navController.navigate(Routes.PlayerSetting.route)
                    })
            }
            Row {
                Button(modifier = Modifier.width(150.dp), content = { Text(text = multiLang.getString("BTN.OPTION")) },
                    onClick = {
                        navController.navigate(Routes.Option.route)
                    })
            }
        }
    }
    BackOnPressedShuttingDown()
}