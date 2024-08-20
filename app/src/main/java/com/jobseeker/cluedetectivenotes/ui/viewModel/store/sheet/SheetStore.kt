package com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.SheetUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class SheetStore(
) : SheetActionStore, SheetStateStore {
    override val _uiState: MutableStateFlow<SheetUiState> = MutableStateFlow(SheetUiState())
    override val uiState: StateFlow<SheetUiState> = _uiState.asStateFlow()

    override fun parseSelectedIds(selectedIds: List<UUID>) {
        _uiState.update { it.copy(selectedIds = selectedIds) }
    }
    override fun parseIsMultiMode(isMultiMode: Boolean) {
        _uiState.update { it.copy( isMultiMode = isMultiMode ) }
    }

    override fun parseSelectedRownameCellIds(selectedRownameCellsIdList: List<UUID>) {
        _uiState.update { it.copy( selectedRownameIds = selectedRownameCellsIdList ) }
    }

    override fun parseSelectedColnameCellIds(selectedColnameCellsIdList: List<UUID>) {
        _uiState.update { it.copy( selectedColnameIds = selectedColnameCellsIdList ) }
    }

    override fun parseOpenConfirmToDefaultModeDialog(openConfirmToDefaultModeDialog: Boolean) {
        _uiState.update { it.copy( openConfirmToDefaultModeDialog = openConfirmToDefaultModeDialog ) }
    }

    override fun parseOpenAddSubMarkerDialog(openAddSubMarkerDialog: Boolean) {
        _uiState.update { it.copy( openAddSubMarkerDialog = openAddSubMarkerDialog ) }
    }
}