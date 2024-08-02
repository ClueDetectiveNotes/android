package com.jobseeker.cluedetectivenotes.ui

sealed class Routes (val route: String){
    object Home : Routes("HomeView")
    object PlayerSetting : Routes("PlayerSettingView")
    object PlayerDetailSetting : Routes("PlayerDetailSettingView")
    object PublicCardSetting : Routes("PublicCardSettingView")
    object UserCardSetting : Routes("UserCardSettingView")
    object Sheet : Routes("SheetView")
}