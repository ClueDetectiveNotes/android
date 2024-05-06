package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
import com.jobseeker.cluedetectivenotes.application.useCase.cell.LoadCellUseCase
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.observer.ICellObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class CellUiState(
    val id : UUID,
    var marker : String
)

//class CellViewModelFactory(private val id: UUID) : ViewModelProvider.NewInstanceFactory(){
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = CellViewModel(id) as T
//}

class CellViewModel(id: UUID) : ViewModel(), ICellObserver {
    private val _uiState : MutableStateFlow<CellUiState>
    val uiState : StateFlow<CellUiState>

    private val loadCellUseCase : LoadCellUseCase = LoadCellUseCase(id, this)

    override fun onCleared() {
        super.onCleared()
        loadCellUseCase.removeObserver(this);
    }

    init {
        val cell = loadCellUseCase.execute()
        _uiState = MutableStateFlow(CellUiState(id, cell.get("marker") as String))
        uiState = _uiState.asStateFlow()
    }

    override fun update() {
        val cell = loadCellUseCase.execute()
        _uiState.update { it.copy(marker = cell.get("marker") as String) }
    }
}
