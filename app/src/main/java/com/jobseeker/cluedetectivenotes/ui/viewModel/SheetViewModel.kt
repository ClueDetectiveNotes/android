package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.ClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LongClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.sheet.LoadSheetUseCase
import com.jobseeker.cluedetectivenotes.domain.model.sheet.observer.ISheetObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.util.UUID

data class SheetUiState(
    val selectedIds:List<UUID> = emptyList(),
    var isMultiMode: Boolean = false
)

class SheetViewModel: ViewModel(), ISheetObserver{
    private val _uiState : MutableStateFlow<SheetUiState> = MutableStateFlow(SheetUiState())
    val uiState : StateFlow<SheetUiState> = _uiState.asStateFlow()

    private val loadSheetUseCase : LoadSheetUseCase = LoadSheetUseCase(this)
    private val clickCellUseCase : ClickCellUseCase = ClickCellUseCase()
    private val longClickCellUseCase : LongClickCellUseCase = LongClickCellUseCase()

    override fun onCleared(){
        super.onCleared()
        loadSheetUseCase.removeObserver(this)
    }

    fun onLoadSheet() : JSONObject{
        val sheetState = loadSheetUseCase.execute()
        _uiState.update { it.copy(selectedIds = sheetState.get("selectedCells") as List<UUID>) }
        _uiState.update { it.copy(isMultiMode = sheetState.get("isMultiSelectionMode") as Boolean) }
        return sheetState
    }

    fun onClickCell(cellId:UUID){
        val sheetState : JSONObject = clickCellUseCase.execute(cellId)
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        _uiState.update { it.copy(selectedIds = selectedCellsIdList) }
    }

    fun onLongClickCell(cellId: UUID) {
        val sheetState : JSONObject = longClickCellUseCase.execute(cellId);
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        _uiState.update { it.copy( isMultiMode = isMultiSelectionMode ) }
        _uiState.update { it.copy( selectedIds = selectedCellsIdList) }
    }

    override fun update() {
        val sheetState = loadSheetUseCase.execute()
        _uiState.update { it.copy(selectedIds = sheetState.get("selectedCells") as List<UUID>) }
        _uiState.update { it.copy(isMultiMode = sheetState.get("isMultiSelectionMode") as Boolean) }
    }
}