package com.jobseeker.cluedetectivenotes.ui

sealed class Routes (val route: String){
    object Home : Routes("HomeView")
    object Sheet : Routes("SheetView")
}