package com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.SheetUiState
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

interface SheetActionStore {
    val _uiState : MutableStateFlow<SheetUiState>

    fun parseSelectedIds(selectedIds : List<UUID>)
    fun parseIsMultiMode(isMultiMode : Boolean)
    fun parseSelectedRownameCellIds(selectedRownameCellsIdList: List<UUID>)
    fun parseSelectedColnameCellIds(selectedRownameCellsIdList: List<UUID>)
    fun parseOpenConfirmToDefaultModeDialog(openConfirmToDefaultModeDialog: Boolean)
}