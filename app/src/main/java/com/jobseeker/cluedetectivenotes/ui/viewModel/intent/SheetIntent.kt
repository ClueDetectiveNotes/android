package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ChangeDefaultModeUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ClickColnameUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ClickRownameUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.InitSheetStructUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LoadCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LoadSheetUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LongClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.SnapshotDecorator
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.CanNotSelectAlreadySelectedCellException
import com.jobseeker.cluedetectivenotes.domain.model.sheet.exceptions.InferenceModeException
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetActionStore
import org.json.JSONObject
import java.util.UUID

class SheetIntent(private val store: SheetActionStore, private val cellStore: CellActionStore) {

    private val initSheetUseCase : InitSheetStructUseCase = InitSheetStructUseCase()
    private val loadSheetUseCase : LoadSheetUseCase = LoadSheetUseCase()
    private val loadCellUseCase : LoadCellUseCase = LoadCellUseCase()
    private val clickCellUseCase : UseCase<JSONObject> = SnapshotDecorator(ClickCellUseCase())
    private val longClickCellUseCase : UseCase<JSONObject> = SnapshotDecorator(LongClickCellUseCase())
    private val clickRownameUseCase : UseCase<JSONObject> = SnapshotDecorator(ClickRownameUseCase())
    private val clickColnameUseCase : UseCase<JSONObject> = SnapshotDecorator(ClickColnameUseCase())
    private val changeDefaultModeUseCase : UseCase<JSONObject> = SnapshotDecorator(ChangeDefaultModeUseCase())

    fun onInitSheet() : JSONObject{
        return initSheetUseCase.execute()
    }

    fun initCells(){
        cellStore.initCells(loadCellUseCase.execute())
    }

    fun loadSheetState(){
        val sheetState : JSONObject = loadSheetUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val isInferenceMode : Boolean = sheetState.get("isInferenceMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
        store.parseIsInferenceMode(isInferenceMode)
    }

    fun onClickCell(cellId: UUID){
        try{
            val sheetState : JSONObject = clickCellUseCase.execute(cellId)
            val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

            store.parseSelectedIds(selectedCellsIdList)
        }catch (e : InferenceModeException){
            store.parseOpenConfirmToDefaultModeDialog(true);
        }

    }

    fun onLongClickCell(cellId: UUID) {
        try{
            val sheetState: JSONObject = longClickCellUseCase.execute(cellId)
            val isMultiSelectionMode: Boolean = sheetState.get("isMultiSelectionMode") as Boolean
            val selectedCellsIdList: List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

            store.parseSelectedIds(selectedCellsIdList)
            store.parseIsMultiMode(isMultiSelectionMode)
        }catch (e : InferenceModeException){
            store.parseOpenConfirmToDefaultModeDialog(true);
        }catch (_: CanNotSelectAlreadySelectedCellException){}
    }

    fun onClickRowname(rowname: String){
        val sheetState: JSONObject = clickRownameUseCase.execute(rowname)
        val isMultiSelectionMode: Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val isInferenceMode : Boolean = sheetState.get("isInferenceMode") as Boolean
        val selectedCellsIdList: List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
        store.parseIsInferenceMode(isInferenceMode)
    }

    fun onClickColname(colname: String){
        val sheetState: JSONObject = clickColnameUseCase.execute(colname)
        val isMultiSelectionMode: Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val isInferenceMode : Boolean = sheetState.get("isInferenceMode") as Boolean
        val selectedCellsIdList: List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
        store.parseIsInferenceMode(isInferenceMode)
    }

    fun closeConfirmToDefaultModeDialog(){
        store.parseOpenConfirmToDefaultModeDialog(false)
    }

    fun changeDefaultMode() {
        val sheetState: JSONObject = changeDefaultModeUseCase.execute()
        val isMultiSelectionMode: Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val isInferenceMode : Boolean = sheetState.get("isInferenceMode") as Boolean
        val selectedCellsIdList: List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>

        store.parseSelectedIds(selectedCellsIdList)
        store.parseIsMultiMode(isMultiSelectionMode)
        store.parseIsInferenceMode(isInferenceMode)
        store.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        store.parseSelectedColnameCellIds(selectedColnameCellsIdList)
    }
}