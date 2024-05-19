package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.UseCase
import com.jobseeker.cluedetectivenotes.application.useCase.cell.ChooseCrossMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.cell.ChooseQuestionMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.cell.LoadCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.CancelClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.snapshot.SnapshotDecorator
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarActionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetActionStore
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class ControlBarIntent (private val store: ControlBarActionStore, private val sheetStore: SheetActionStore) {
    private val loadCellUseCase : LoadCellUseCase = LoadCellUseCase()
    private val chooseCrossMarkerUseCase : UseCase<JSONArray> = SnapshotDecorator(ChooseCrossMarkerUseCase())
    private val chooseQuestionMarkerUseCase : UseCase<JSONArray> = SnapshotDecorator(ChooseQuestionMarkerUseCase())
    private val cancelClickedCellUseCase : UseCase<JSONObject> = SnapshotDecorator(CancelClickedCellUseCase())

    fun initCells(){
        store.initCells(loadCellUseCase.execute())
    }

    fun onClickCrossMaker(){
        store.parseCells(chooseCrossMarkerUseCase.execute())
    }

    fun onClickQuestionMaker(){
        store.parseCells(chooseQuestionMarkerUseCase.execute())
    }

    fun cancelClickedCells(){
        val sheetState : JSONObject = cancelClickedCellUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        sheetStore.parseSelectedIds(selectedCellsIdList)
        sheetStore.parseIsMultiMode(isMultiSelectionMode)
    }
}