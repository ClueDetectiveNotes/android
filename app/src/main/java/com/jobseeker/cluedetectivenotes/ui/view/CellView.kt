package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CellView(uiState: State<CellUiState>){
    val mainMarker = uiState.value.mainMarker
    val subMarkerItems = uiState.value.subMarkerItems
    val isInit = uiState.value.isInit
    val isLock = uiState.value.isLock

    Column {
        if(isInit || isLock){
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = if(isInit){"init"}else{"lock"},
                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(text = mainMarker,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        if(subMarkerItems.isNotEmpty()){
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ){
                for(subMarkerItem in subMarkerItems){
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ){
                        Text(
                            text = subMarkerItem,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}