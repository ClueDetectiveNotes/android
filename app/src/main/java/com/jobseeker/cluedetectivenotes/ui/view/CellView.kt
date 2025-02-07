package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
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
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
            ){
                Text(
                    text = if(isInit){"init"}else{"lock"},
                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Row (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = mainMarker,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
        }
        if(subMarkerItems.isNotEmpty()){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
            ){
                for(subMarkerItem in subMarkerItems){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                    ){
                        Text(
                            text = subMarkerItem,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}