package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar.ChooseCrossMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar.ChooseQuestionMarkerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.markerControlBar.CancelClickedCellUseCase

class MarkerControlBarViewModel : ViewModel(){
    private val chooseCrossMarkerUseCase : ChooseCrossMarkerUseCase = ChooseCrossMarkerUseCase()
    private val chooseQuestionMarkerUseCase : ChooseQuestionMarkerUseCase = ChooseQuestionMarkerUseCase()
    private val cancelClickedCellUseCase : CancelClickedCellUseCase = CancelClickedCellUseCase()

    fun onClickCrossMarker(){
        chooseCrossMarkerUseCase.execute()
    }
    fun onClickQuestionMaker(){
        chooseQuestionMarkerUseCase.execute()
    }
    fun cancelClickedCells(){
        cancelClickedCellUseCase.execute()
    }
}