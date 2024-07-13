package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel

@Composable
fun ControlBar(controlBarViewModel:ControlBarViewModel,isDisplayControlBar:Boolean){
    ConstraintLayout() {
        val (controlBar, markerControlbar) = createRefs()

        Row (modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray)
            .constrainAs(controlBar) {
                if(isDisplayControlBar){
                    bottom.linkTo(markerControlbar.top)
                }else{
                    bottom.linkTo(parent.bottom)
                }

            }
            .clickable(enabled = false) {})
        {
            Column {
                Row {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.undo() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Undo", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.redo() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Redo", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.clearClickedCells() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Clear", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.cancelClickedCells() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(text = "Cancel", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }//Row End
        if(isDisplayControlBar){
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .constrainAs(markerControlbar) {
                    bottom.linkTo(parent.bottom)
                }
                .clickable(enabled = false) {})
            {
                Column {
                    Row (horizontalArrangement = Arrangement.Start){
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickCheckMaker() }
                        ) {
                            Text(text = "O", fontWeight = FontWeight.Bold)
                        }
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
                            onClick = { controlBarViewModel.intent.onClickSlashMaker() }
                        ) {
                            Text(text = "/", fontWeight = FontWeight.Bold)
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
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickExclamationMaker() }
                        ) {
                            Text(text = "!", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}