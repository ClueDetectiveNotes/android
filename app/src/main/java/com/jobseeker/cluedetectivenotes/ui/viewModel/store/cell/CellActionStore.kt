package com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONArray
import java.util.UUID

interface CellActionStore {
    val _cells : HashMap<UUID, MutableStateFlow<CellUiState>>

    fun initCells(cells : JSONArray)
    fun parseCells(cells : JSONArray)
    fun parseLockedCells(cells: JSONArray)
}