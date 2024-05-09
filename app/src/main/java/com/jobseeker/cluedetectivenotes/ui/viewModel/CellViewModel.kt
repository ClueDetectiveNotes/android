package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.application.useCase.cell.ChooseCrossMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.cell.ChooseQuestionMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.cell.LoadCellUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import java.util.UUID

data class CellUiState(
    var mainMarker : String = ""
)

class CellViewModel() : ViewModel() {
    private val _cells : HashMap<UUID, MutableStateFlow<CellUiState>> = HashMap()
    val cells : HashMap<UUID, StateFlow<CellUiState>> = HashMap()

    private val loadCellUseCase : LoadCellUseCase = LoadCellUseCase()
    private val chooseCrossMarkerUseCase : ChooseCrossMarkerUseCase = ChooseCrossMarkerUseCase()
    private val chooseQuestionMarkerUseCase : ChooseQuestionMarkerUseCase = ChooseQuestionMarkerUseCase()

    init {
        val cellsArr = loadCellUseCase.execute()
        for(idx in 0 until cellsArr.length()){
            val cellObj = cellsArr.getJSONObject(idx)
            _cells[cellObj.get("id") as UUID] = MutableStateFlow(CellUiState(mainMarker = cellObj.get("mainMarker") as String))
            cells[cellObj.get("id") as UUID] = _cells[cellObj.get("id") as UUID]!!.asStateFlow()
        }
    }

    fun onClickCrossMaker() {
        val cellsArr: JSONArray = chooseCrossMarkerUseCase.execute()
        for (idx in 0 until cellsArr.length()){
            val cellObj = cellsArr.getJSONObject(idx)
            _cells[cellObj.get("id") as UUID]!!.update { it.copy(mainMarker = cellObj.get("mainMarker") as String) }
        }
    }
    fun onClickQuestionMaker() {
        val cellsArr: JSONArray = chooseQuestionMarkerUseCase.execute()
        for (idx in 0 until cellsArr.length()){
            val cellObj = cellsArr.getJSONObject(idx)
            _cells[cellObj.get("id") as UUID]!!.update { it.copy(mainMarker = cellObj.get("mainMarker") as String) }
        }
    }
}
