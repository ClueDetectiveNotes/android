package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
import com.jobseeker.cluedetectivenotes.application.useCase.LoadCellUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.ChooseCrossMarkerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import java.util.UUID

data class CellUiState(
    val id : UUID,
    var marker : String
)

//class CellViewModelFactory(private val id: UUID) : ViewModelProvider.NewInstanceFactory(){
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = CellViewModel(id) as T
//}

class CellViewModel(id: UUID) : ViewModel() {
    private val _uiState : MutableStateFlow<CellUiState>
    val uiState : StateFlow<CellUiState>

    private val loadCellUseCase : LoadCellUseCase = LoadCellUseCase()
    private val chooseCrossMarkerUseCase : ChooseCrossMarkerUseCase = ChooseCrossMarkerUseCase()

    init {
        val cell = loadCellUseCase.execute(id)
        _uiState = MutableStateFlow(CellUiState(id, cell.get("marker") as String))
        uiState = _uiState.asStateFlow()
    }

    fun onClickCrossMaker() {
        val cellState : JSONObject = chooseCrossMarkerUseCase.execute(_uiState.value.id)
        _uiState.update { it.copy(marker = cellState.get("marker") as String) }
    }
}
