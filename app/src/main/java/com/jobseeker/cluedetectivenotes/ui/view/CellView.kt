package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 메인마커를 정중앙에 표시
        Text(
            text = mainMarker,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
        
        // init 또는 lock 상태를 좌측 상단에 표시 (아이콘 대신 텍스트로 임시 표시)
        if(isInit || isLock){
            Text(
                text = if(isInit){"init"}else{"lock"},
                fontSize = 8.sp,
                modifier = Modifier
                    .align(Alignment.TopStart),
                textAlign = TextAlign.Start,
            )
        }
        
        // 서브마커들을 하단에 표시
        if(subMarkerItems.isNotEmpty()){
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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