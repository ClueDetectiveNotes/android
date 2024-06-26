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
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel

@Composable
fun ControlBar(controlBarViewModel:ControlBarViewModel){
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
            Column (modifier = Modifier.width(screenWidth-240.dp)){
                Row (horizontalArrangement = Arrangement.Start){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.onClickCrossMaker() }
                    ) {
                        Text(text = "X", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.onClickQuestionMaker() }
                    ) {
                        Text(text = "?", fontWeight = FontWeight.Bold)
                    }
                }
            }
            Column {
                Row (horizontalArrangement = Arrangement.End){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.cancelClickedCells() }
                    ) {
                        Text(text = "취소", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.undo() }
                    ) {
                        Text(text = "Undo", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.redo() }
                    ) {
                        Text(text = "Redo", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }//Row End
    }
}