package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.application.useCase.ClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.LongClickCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.CancelClickedCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.LoadSheetUseCase
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

class SheetViewModel: ViewModel(){
    private val _uiState : MutableStateFlow<SheetUiState> = MutableStateFlow(SheetUiState())
    val uiState : StateFlow<SheetUiState> = _uiState.asStateFlow()

    private val loadSheetUseCase : LoadSheetUseCase = LoadSheetUseCase()
    private val clickCellUseCase : ClickCellUseCase = ClickCellUseCase()
    private val longClickCellUseCase : LongClickCellUseCase = LongClickCellUseCase()
    private val cancelClickedCellUseCase : CancelClickedCellUseCase = CancelClickedCellUseCase()

    fun onLoadSheet() : JSONObject{
        return loadSheetUseCase.execute()
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

    fun cancelClickedCells(){
        val sheetState : JSONObject = cancelClickedCellUseCase.execute()
        val isMultiSelectionMode : Boolean = sheetState.get("isMultiSelectionMode") as Boolean
        val selectedCellsIdList : List<UUID> = sheetState.get("selectedCellsIdList") as List<UUID>

        _uiState.update { it.copy( isMultiMode = isMultiSelectionMode ) }
        _uiState.update { it.copy( selectedIds = selectedCellsIdList ) }
    }
}