package com.jobseeker.cluedetectivenotes.viewModel.sheet

import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel
import org.junit.Assert.*;
import org.junit.Before
import org.junit.Test

class SheetViewModelTest {
    private lateinit var sheetViewModel : SheetViewModel;
    @Before
    fun create(){
        sheetViewModel = SheetViewModel();
    }

    @Test
    fun `test test`(){
        assertTrue(sheetViewModel.clickCell()?.get(0) is Cell);
    }
}