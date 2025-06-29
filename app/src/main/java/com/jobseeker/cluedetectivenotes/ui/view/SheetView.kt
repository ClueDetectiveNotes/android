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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.jobseeker.cluedetectivenotes.ui.theme.LocalCustomColorsPalette
import com.jobseeker.cluedetectivenotes.ui.viewModel.CellViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel
import java.util.UUID

@SuppressLint("UnrememberedMutableState")
@Composable
fun SheetView(
    sheetViewModel:SheetViewModel = viewModel(),
    cellViewModel: CellViewModel = viewModel(),
    controlBarViewModel: ControlBarViewModel = viewModel(),
    optionViewModel: OptionViewModel = viewModel(),
    navController: NavHostController,
) {
    val uiState = sheetViewModel.store.uiState.collectAsState()
    val isDisplayControlBar by mutableStateOf(uiState.value.selectedIds.isNotEmpty())
    val isSubMarkerBarOpen = controlBarViewModel.store.uiState.collectAsState().value.isSubMarkerBarOpen

    val sheet = sheetViewModel.intent.onInitSheet()
    val cells = sheet.get("cells") as Map<String, Map<String, UUID>>
    val rownames = sheet.get("rownames") as List<Map<String,String>>
    val colnames = sheet.get("colnames") as List<Map<UUID,String>>
    val userId = sheet.get("userId") as UUID

    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang

    // 상수 정의로 높이 값 최적화
    val cellHeight = 50.dp
    val cellPadding = 2.dp
    val topSpacerHeight = 50.dp
    val bottomSpacerHeight = 60.dp
    val controlBarSpacerHeight = if(isSubMarkerBarOpen) 100.dp else 50.dp

    when{
        uiState.value.openConfirmToDefaultModeDialog -> {
            ConfirmToDefaultModeDialog(
                onDismissRequest = { sheetViewModel.intent.closeConfirmToDefaultModeDialog() },
                onConfirmation = {
                    sheetViewModel.intent.closeConfirmToDefaultModeDialog()
                    sheetViewModel.intent.changeDefaultMode();
                },
                dialogTitle = multiLang.getString("MSG.SHT_IF_DL_TITLE"),
                dialogText = multiLang.getString("MSG.SHT_IF_DL_DESC")
            )
        }

        uiState.value.openAddSubMarkerDialog -> {
            CustomTextFieldDialog(
                initialText = "",
                onClickCancel = { controlBarViewModel.intent.closeAddSubMarkerDialog() },
                onClickConfirm = {text: String ->
                    controlBarViewModel.intent.closeAddSubMarkerDialog()
                    controlBarViewModel.intent.addSubMarker(text)
                }
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
                        Spacer(modifier = Modifier.height(cellHeight))
                    }
                }
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)){
                    VerticalGrid(columns = SimpleGridCells.Fixed((colnames.size))){
                        for(column in colnames){
                            ColnameCell(column, userId) {
                                sheetViewModel.intent.onClickColname(column[column.keys.last()]!!)
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
            Spacer(modifier = Modifier.height(topSpacerHeight))
            Row{
                Column(modifier = Modifier.width(110.dp)) {
                    VerticalGrid(columns = SimpleGridCells.Fixed(1)){

                        var rowType: String? = null
                        for(row in rownames) {
                            if (rowType != row["type"]) {
                                rowType = row["type"]!!
                                RowTypeCell(text = multiLang.getString("CRD_TP.$rowType"), modifier = Modifier)
                            }
                            RownameCell(text = multiLang.getString("CRD."+row["name"])) {
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
                                    Spacer(modifier = Modifier.height(cellHeight))
                                }
                            }

                            val rowCell = cells[row["name"]]
                            for(col in colnames){
                                val cellId = rowCell?.get(col[col.keys.last()])!!

                                GridCell(
                                    uiState = cellViewModel.store.cells[cellId]!!.collectAsState(),
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
            if(isDisplayControlBar){
                Spacer(modifier = Modifier.height(controlBarSpacerHeight))
            }
            Spacer(modifier = Modifier.height(bottomSpacerHeight))
        }//Column End
        ControlBar(controlBarViewModel, isDisplayControlBar, navController)
    }
    BackOnPressedShuttingDown()
}

@Composable
fun RowTypeCell(text: String, modifier: Modifier){
    Surface(modifier = modifier
        .height(50.dp)
        .padding(2.dp)) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColnameCell(column: Map<UUID,String>, userId: UUID, clickAction: () -> Unit){
    Surface(modifier = Modifier
        .height(50.dp)
        .padding(2.dp)
        .combinedClickable(onClick = { clickAction() }),
        color = if(userId == column.keys.last()) Color(android.graphics.Color.parseColor("#e8a809"))
                else Color(MaterialTheme.colorScheme.surface.value)
    ) {
        Text(
            text = column[column.keys.last()]!!,
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
        .height(50.dp)
        .padding(2.dp)
        .combinedClickable(onClick = { clickAction() })
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridCell(uiState: State<CellUiState>, selected: Boolean, rowOrColSelected: Boolean, clickAction: () -> Unit, longClickAction: () -> Unit){
    Surface(
        modifier = Modifier
            .height(50.dp)
            .padding(2.dp)
            .combinedClickable(onClick = { clickAction() }, onLongClick = { longClickAction() }),
        color = if(selected) LocalCustomColorsPalette.current.selectedCell
                else if(rowOrColSelected) LocalCustomColorsPalette.current.selectedRowAndColname
                else Color(MaterialTheme.colorScheme.surface.value)
    ){
        CellView(uiState = uiState)
    }
}

@Composable
fun ConfirmToDefaultModeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    optionViewModel: OptionViewModel = viewModel()
){
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = multiLang.getString("BTN.CONFIRM"))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = multiLang.getString("BTN.CANCEL"))
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