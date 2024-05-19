package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import androidx.compose.runtime.MutableState
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONArray
import java.util.UUID

interface ControlBarActionStore {
    val _cells : HashMap<UUID, MutableStateFlow<CellUiState>>

    fun initCells(cells : JSONArray)
    fun parseCells(cells : JSONArray)
}