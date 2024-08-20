package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.AddSubMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.CancelClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseCheckMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseCrossMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseExclamationMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseQuestionMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseSlashMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ChooseSubMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.ClearClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.conrtolBar.LoadSubMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.RedoUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.SnapshotDecorator
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.UndoUseCase
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.AlreadyContainsSubMarkerItemException
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetActionStore
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class ControlBarIntent (private val store: ControlBarActionStore, private val sheetStore: SheetActionStore, private val cellStore: CellActionStore) {

    private val loadSubMarkerUseCase : LoadSubMarkerUseCase = LoadSubMarkerUseCase()
    private val chooseCrossMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseCrossMarkerUseCase())
    private val chooseQuestionMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseQuestionMarkerUseCase())
    private val chooseCheckMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseCheckMarkerUseCase())
    private val chooseSlashMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseSlashMarkerUseCase())
    private val chooseExclamationMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseExclamationMarkerUseCase())
    private val cancelClickedCellUseCase : UseCase<JSONObject> = SnapshotDecorator(CancelClickedCellUseCase())
    private val clearClickedCellUseCase : UseCase<JSONObject> = SnapshotDecorator(ClearClickedCellUseCase())
    private val undoUseCase : UndoUseCase = UndoUseCase()
    private val redoUseCase : RedoUseCase = RedoUseCase()
    private val chooseSubMarkerUseCase : UseCase<JSONObject> = SnapshotDecorator(ChooseSubMarkerUseCase())
    private val addSubMarkerUseCase : AddSubMarkerUseCase = AddSubMarkerUseCase()

    init{
        val controlBarState = loadSubMarkerUseCase.execute()
        val subMarkerItems = controlBarState.get("subMarkerItems") as List<String>
        val addedSubMarkerItems = controlBarState.get("addedSubMarkerItems") as List<String>
        store.parseSubMarkerItems(subMarkerItems)
        store.parseAddedSubMarkerItems(addedSubMarkerItems)
    }

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
        store.parseIsSubMarkerBarOpen(false)
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

    fun onClickSubMaker(isSubMarkerBarOpen:Boolean) {
        store.parseIsSubMarkerBarOpen(isSubMarkerBarOpen)
    }

    fun onClickSubMarkerItem(subMarkerItem:String){
        val sheetState : JSONObject = chooseSubMarkerUseCase.execute(subMarkerItem)
        val cells : JSONArray = sheetState.get("cells") as JSONArray
        cellStore.parseCells(cells)
    }

    fun closeAddSubMarkerDialog() {
        sheetStore.parseOpenAddSubMarkerDialog(false)
    }

    fun openAddSubMarkerDialog() {
        sheetStore.parseOpenAddSubMarkerDialog(true)
    }

    fun addSubMarker(text: String) {
        try {
            addSubMarkerUseCase.execute(text)
            val controlBarState = loadSubMarkerUseCase.execute()

            val subMarkerItems = controlBarState.get("subMarkerItems") as List<String>
            val addedSubMarkerItems = controlBarState.get("addedSubMarkerItems") as List<String>

            store.parseSubMarkerItems(subMarkerItems)
            store.parseAddedSubMarkerItems(addedSubMarkerItems)
        }catch (_: AlreadyContainsSubMarkerItemException){
            //알림을 띄우는 것이 좋을 것 같은데
        }
    }
}