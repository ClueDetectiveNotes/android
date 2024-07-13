package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel
import java.util.UUID

@SuppressLint("UnrememberedMutableState")
@Composable
fun SheetView(
    sheetViewModel:SheetViewModel = viewModel(),
    controlBarViewModel: ControlBarViewModel = viewModel()
) {
    val uiState = sheetViewModel.store.uiState.collectAsState()
    val isDisplayControlBar by mutableStateOf(uiState.value.selectedIds.isNotEmpty())

    val sheet = sheetViewModel.intent.onInitSheet()
    val cells = sheet.get("cells") as Map<String, Map<String, UUID>>
    val rownames = sheet.get("rownames") as List<Map<String,String>>
    val colnames = sheet.get("colnames") as List<String>

    when{
        uiState.value.openConfirmToDefaultModeDialog -> {
            ConfirmToDefaultModeDialog(
                onDismissRequest = { sheetViewModel.intent.closeConfirmToDefaultModeDialog() },
                onConfirmation = {
                    sheetViewModel.intent.closeConfirmToDefaultModeDialog()
                    sheetViewModel.intent.changeDefaultMode();
                },
                dialogTitle = "추리모드를 해제 하시겠습니까?",
                dialogText = "확인 버튼을 누르면 추리모드가 해제되며 기본 선택 모드로 돌아갑니다."
            )
        }
    }

    Surface {
        ConstraintLayout(modifier = Modifier.zIndex(1F)){
            val (players) = createRefs()
            Row (modifier = Modifier
                .constrainAs(players) {
                    top.linkTo(parent.top)
                }
                .background(color = Color(android.graphics.Color.parseColor("#0c3ea1")))
            ){
                Column (modifier = Modifier
                    .width(110.dp)
                    .padding(5.dp)){
                    VerticalGrid(columns = SimpleGridCells.Fixed(1)){
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)){
                    VerticalGrid(columns = SimpleGridCells.Fixed((colnames.size))){
                        for(column in colnames){
                            ColnameCell(column) {
                                sheetViewModel.intent.onClickColname(column)
                            }
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color(android.graphics.Color.parseColor("#0c3ea1")))
            .padding(5.dp)
        ) {
            Row {
                Spacer(modifier = Modifier.height(50.dp))
            }
            Row{
                Column(modifier = Modifier.width(110.dp)) {
                    VerticalGrid(columns = SimpleGridCells.Fixed(1)){

                        var rowType: String? = null
                        for(row in rownames) {
                            if (rowType != row["type"]) {
                                rowType = row["type"]!!
                                RowTypeCell(text = rowType, modifier = Modifier)
                            }
                            RownameCell(text = row["name"]!!) {
                                sheetViewModel.intent.onClickRowname(row["name"]!!)
                            }
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    VerticalGrid(
                        columns = SimpleGridCells.Fixed((colnames.size))){

                        var rowType: String? = null

                        for(row in rownames){
                            if(rowType != row["type"]){
                                rowType = row["type"]
                                for(index in 1..colnames.size){
                                    Spacer(modifier = Modifier.height(40.dp))
                                }
                            }

                            val rowCell = cells[row["name"]]
                            for(col in colnames){
                                val cellId = rowCell?.get(col)!!

                                GridCell(
                                    uiState = controlBarViewModel.store.cells[cellId]!!.collectAsState(),
                                    selected = uiState.value.selectedIds.contains(cellId),
                                    rowOrColSelected = uiState.value.selectedRownameIds.contains(cellId)||uiState.value.selectedColnameIds.contains(cellId),
                                    clickAction = { sheetViewModel.intent.onClickCell(cellId) },
                                    longClickAction = { sheetViewModel.intent.onLongClickCell(cellId) }
                                )
                            }
                        }
                    }//VerticalGrid End
                }
            }
            //}//Row End
            Row {
                Spacer(modifier = Modifier.height(50.dp))
            }
            if(isDisplayControlBar){
                Row {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }//Column End
        ControlBar(controlBarViewModel = controlBarViewModel, isDisplayControlBar)
    }
}

@Composable
fun RowTypeCell(text: String, modifier: Modifier){
    Surface(modifier = modifier
        .height(40.dp)
        .padding(2.dp)) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColnameCell(text: String, clickAction: () -> Unit){
    Surface(modifier = Modifier
        .height(40.dp)
        .padding(2.dp)
        .combinedClickable(onClick = { clickAction() })
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
            )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RownameCell(text: String, clickAction: () -> Unit){
    Surface(modifier = Modifier
        .height(40.dp)
        .padding(2.dp)
        .combinedClickable(onClick = { clickAction() })
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
            )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridCell(uiState: State<CellUiState>, selected: Boolean, rowOrColSelected: Boolean, clickAction: () -> Unit, longClickAction: () -> Unit){
    Surface(
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp)
            .combinedClickable(onClick = { clickAction() }, onLongClick = { longClickAction() })
        , color = if(selected) Color(android.graphics.Color.parseColor("#feffba"))
                    else if(rowOrColSelected) Color(android.graphics.Color.parseColor("#00ffba"))
                    else Color.White
        ){
        CellView(uiState = uiState)
    }
}

@Composable
fun ConfirmToDefaultModeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
){
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "취소")
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        }
    )
}