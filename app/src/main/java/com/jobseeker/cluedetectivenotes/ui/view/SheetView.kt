package com.jobseeker.cluedetectivenotes.ui.view

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cheonjaeung.compose.grid.SimpleGridCells
import com.cheonjaeung.compose.grid.VerticalGrid
import com.jobseeker.cluedetectivenotes.ui.viewModel.CellViewModel
//import com.jobseeker.cluedetectivenotes.ui.viewModel.CellViewModelFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel
import java.util.UUID

@Composable
fun SheetView(
    sheetViewModel:SheetViewModel = viewModel()
) {
    val uiState = sheetViewModel.uiState.collectAsState()

    val sheet = sheetViewModel.onLoadSheet()
    val cells = sheet.get("cells") as Map<String, Map<String, UUID>>
    val rownames = sheet.get("rownames") as List<Map<String,String>>
    val colnames = sheet.get("colnames") as List<String>

    val cellViewModels = HashMap<UUID, CellViewModel>()
    for(row in cells.keys){
        for(col in cells[row]?.keys!!){
            cells[row]?.get(col)?.let {
                cellViewModels.put(it, CellViewModel(it))
            }
        }
    }

    Surface() {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color(android.graphics.Color.parseColor("#0c3ea1")))
        ) {
            Row(modifier = Modifier.padding(5.dp)) {
                Column(modifier = Modifier.width(110.dp)) {
                    VerticalGrid(columns = SimpleGridCells.Fixed(1)){
                        Spacer(modifier = Modifier.height(40.dp))

                        var rowType: String? = null
                        for(row in rownames) {
                            if (rowType != row["type"]) {
                                rowType = row["type"]!!
                                RowTypeCell(text = rowType, modifier = Modifier)
                            }
                            RownameCell(text = row["name"]!!, modifier = Modifier)
                        }
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    VerticalGrid(
                        columns = SimpleGridCells.Fixed((colnames.size))){

                        for(column in colnames){
                            ColnameCell(column)
                        }

                        var rowType: String? = null

                        for(row in rownames){
                            if(rowType != row["type"]){
                                rowType = row["type"]
                                for(index in 1..colnames.size){
                                    Spacer(modifier = Modifier.height(40.dp))
                                }
                            }

                            val rowCell = cells[row["name"]]
                            for(col in rowCell!!.keys){
                                val cellId = rowCell[col]!!

                                GridCell(
                                    cellViewModel = cellViewModels[cellId]!!,
                                    selected = uiState.value.selectedIds.contains(cellId),
                                    clickAction = { sheetViewModel.onClickCell(cellId) },
                                    longClickAction = { sheetViewModel.onLongClickCell(cellId) }
                                )
                            }
                        }
                    }//VerticalGrid End
                }
            }//Row End
            if(uiState.value.selectedIds.isNotEmpty()){
                Row() {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }//Column End
        if(uiState.value.selectedIds.isNotEmpty()){
            ControllBar(sheetViewModel = sheetViewModel, cellViewModels = cellViewModels)
        }
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

@Composable
fun ColnameCell(text: String){
    Surface(modifier = Modifier
        .height(40.dp)
        .padding(2.dp)) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
            )
    }
}

@Composable
fun RownameCell(text: String, modifier: Modifier){
    Surface(modifier = modifier
        .height(40.dp)
        .padding(2.dp)) {
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
fun GridCell(cellViewModel: CellViewModel, selected: Boolean, clickAction: () -> Unit, longClickAction: () -> Unit){
    Surface(
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp)
            .combinedClickable(onClick = { clickAction() }, onLongClick = { longClickAction() })
        , color = if(selected) Color(android.graphics.Color.parseColor("#feffba")) else Color.White
        ){
        CellView(cellViewModel = cellViewModel)
    }
}