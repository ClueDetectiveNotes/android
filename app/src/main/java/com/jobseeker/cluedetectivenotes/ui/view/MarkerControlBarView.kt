package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.CellViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.MarkerControlBarViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.SheetViewModel
import java.util.UUID

@Composable
fun MarkerControlBarView(markerControlBarViewModel: MarkerControlBarViewModel = viewModel()){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    ConstraintLayout() {
        val (box) = createRefs()

        Row (modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .constrainAs(box) {
                bottom.linkTo(parent.bottom)
            }
            .clickable(enabled = false) {}){
            Column (modifier = Modifier.width(screenWidth-80.dp)){
                Row (horizontalArrangement = Arrangement.Start){

                    CrossButton(markerControlBarViewModel = markerControlBarViewModel)
                    QuestionButton(markerControlBarViewModel = markerControlBarViewModel)
                }
            }
            Column {
                Row (horizontalArrangement = Arrangement.End){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { markerControlBarViewModel.cancelClickedCells() }
                    ) {
                        Text(text = "취소", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }//Row End
    }
}

@Composable
fun CrossButton(markerControlBarViewModel: MarkerControlBarViewModel){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        ),
        onClick = {
            markerControlBarViewModel.onClickCrossMarker()
        }
    ) {
        Text(text = "X", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun QuestionButton(markerControlBarViewModel: MarkerControlBarViewModel){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        ),
        onClick = {
            markerControlBarViewModel.onClickQuestionMaker()
        }
    ) {
        Text(text = "?", fontWeight = FontWeight.Bold)
    }
}