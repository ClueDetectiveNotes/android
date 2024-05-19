package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.InitSheetStructUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LoadSheetUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LongClickCellUseCase
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetActionStore
import org.json.JSONObject
import java.util.UUID

class SheetIntent(private val store: SheetActionStore) {

    private val initSheetUseCase : InitSheetStructUseCase = InitSheetStructUseCase()
    private val loadSheetUseCase : LoadSheetUseCase = LoadSheetUseCase()
    private val clickCellUseCase : ClickCellUseCase = ClickCellUseCase()
    private val longClickCellUseCase : LongClickCellUseCase = LongClickCellUseCase()

    fun onInitSheet() : JSONObject{
        return initSheetUseCase.execute()
    }

    fun loadSheetState(){
        val sheetState = loadSheetUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
    }

    fun onClickCell(cellId: UUID){
        val sheetState : JSONObject = clickCellUseCase.execute(cellId)
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
    }

    fun onLongClickCell(cellId: UUID) {
        val sheetState: JSONObject = longClickCellUseCase.execute(cellId);
        val isMultiSelectionMode: Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList: List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
    }
}