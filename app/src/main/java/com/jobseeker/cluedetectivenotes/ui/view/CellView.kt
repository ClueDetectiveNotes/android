package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobseeker.cluedetectivenotes.ui.viewModel.CellViewModel
import java.util.UUID

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CellView(cellViewModel:CellViewModel){
    val uiState = cellViewModel.uiState.collectAsState()
    Text(text = uiState.value.marker,
            modifier = Modifier.fillMaxWidth()
                    .height(40.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center)
}