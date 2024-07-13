package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseCrossMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseQuestionMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.CancelClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseCheckMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseExclamationMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseSlashMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ClearClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.RedoUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.SnapshotDecorator
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.UndoUseCase
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetActionStore
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class ControlBarIntent (private val cellStore: CellActionStore, private val sheetStore: SheetActionStore) {

    private val chooseCrossMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseCrossMarkerUseCase())
    private val chooseQuestionMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseQuestionMarkerUseCase())
    private val chooseCheckMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseCheckMarkerUseCase())
    private val chooseSlashMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseSlashMarkerUseCase())
    private val chooseExclamationMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseExclamationMarkerUseCase())
    private val cancelClickedCellUseCase : UseCase<JSONObject> = SnapshotDecorator(CancelClickedCellUseCase())
    private val clearClickedCellUseCase : UseCase<JSONObject> = SnapshotDecorator(ClearClickedCellUseCase())
    private val undoUseCase : UndoUseCase = UndoUseCase()
    private val redoUseCase : RedoUseCase = RedoUseCase()
    private val selectNextColnameUseCase : SelectNextColnameUseCase<JSONObject> = SelectNextColnameUseCase();

    fun onClickCrossMaker(){
        val sheetState : JSONObject = chooseCrossMarkerUseCase.execute()
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        cellStore.parseCells(cells)
    }

    fun onClickQuestionMaker(){
        val sheetState : JSONObject = chooseQuestionMarkerUseCase.execute()
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        cellStore.parseCells(cells)
    }

    fun onClickCheckMaker() {
        val sheetState : JSONObject = chooseCheckMarkerUseCase.execute()
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        cellStore.parseCells(cells)
    }

    fun onClickSlashMaker() {
        val sheetState : JSONObject = chooseSlashMarkerUseCase.execute()
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        cellStore.parseCells(cells)
    }

    fun onClickExclamationMaker() {
        val sheetState : JSONObject = chooseExclamationMarkerUseCase.execute()
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
        cellStore.parseCells(cells)
    }
    fun cancelClickedCells(){
        val sheetState : JSONObject = cancelClickedCellUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseIsMultiMode(isMultiSelectionMode)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
    }

    fun clearClickedCells() {
        val sheetState : JSONObject = clearClickedCellUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseIsMultiMode(isMultiSelectionMode)
        cellStore.parseCells(cells)
    }

    fun undo(){
        val sheetState : JSONObject = undoUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseIsMultiMode(isMultiSelectionMode)
        cellStore.parseCells(cells)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
    }

    fun redo(){
        val sheetState : JSONObject = redoUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>
        val cells : JSONArray = sheetState.get("cells") as JSONArray
        val selectedRownameCellsIdList: List<UUID> = sheetState.get("selectedRownameCellsIdList") as List<UUID>
        val selectedColnameCellsIdList: List<UUID> = sheetState.get("selectedColnameCellsIdList") as List<UUID>

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseIsMultiMode(isMultiSelectionMode)
        cellStore.parseCells(cells)
        sheetStore.parseSelectedRownameCellIds(selectedRownameCellsIdList)
        sheetStore.parseSelectedColnameCellIds(selectedColnameCellsIdList)
    }
}