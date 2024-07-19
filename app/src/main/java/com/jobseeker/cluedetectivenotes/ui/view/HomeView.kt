package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes

@Composable
fun HomeView(navController: NavHostController) {
    Box(modifier = Modifier.size(50F.dp), contentAlignment = Alignment.Center){
        Button(content = { Text(text = "시작") },
                onClick = {
                    navController.navigate(Routes.PlayerSetting.route)
                })
    }
    BackOnPressed()
}