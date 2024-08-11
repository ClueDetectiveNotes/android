package com.jobseeker.cluedetectivenotes.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jobseeker.cluedetectivenotes.ui.view.HandSettingView
import com.jobseeker.cluedetectivenotes.ui.view.HomeView
import com.jobseeker.cluedetectivenotes.ui.view.PlayerDetailSettingView
import com.jobseeker.cluedetectivenotes.ui.view.PlayerSettingView
import com.jobseeker.cluedetectivenotes.ui.view.PublicCardSettingView
import com.jobseeker.cluedetectivenotes.ui.view.SheetView

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route){
        composable(Routes.Home.route){
            HomeView(navController = navController)
        }
        composable(Routes.PlayerSetting.route){
            PlayerSettingView(navController = navController)
        }
        composable(Routes.PlayerDetailSetting.route){
            PlayerDetailSettingView(navController = navController)
        }
        composable(Routes.PublicCardSetting.route){
            PublicCardSettingView(navController = navController)
        }
        composable(Routes.HandSetting.route){
            HandSettingView(navController = navController)
        }
        composable(Routes.Sheet.route){
            SheetView()
        }
    }
}