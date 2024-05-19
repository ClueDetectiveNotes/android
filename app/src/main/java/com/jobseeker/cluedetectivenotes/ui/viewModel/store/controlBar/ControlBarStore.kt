package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import java.util.UUID

class ControlBarStore : ControlBarActionStore, ControlBarStateStore {
    override val _cells: HashMap<UUID, MutableStateFlow<CellUiState>> = HashMap()
    override val cells: HashMap<UUID, StateFlow<CellUiState>> = HashMap()


    override fun initCells(cells: JSONArray) {
        for(idx in 0 until cells.length()){
            val cellObj = cells.getJSONObject(idx)
            _cells[cellObj.get("id") as UUID] = MutableStateFlow(CellUiState(mainMarker = cellObj.get("mainMarker") as String))
            this.cells[cellObj.get("id") as UUID] = _cells[cellObj.get("id") as UUID]!!.asStateFlow()
        }
    }

    override fun parseCells(cells: JSONArray) {
        for(idx in 0 until cells.length()){
            val cellObj = cells.getJSONObject(idx)
            _cells[cellObj.get("id") as UUID]!!.update { it.copy(mainMarker = cellObj.get("mainMarker") as String) }
        }
    }
}