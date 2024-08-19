package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel

@Composable
fun ControlBar(controlBarViewModel:ControlBarViewModel,isDisplayControlBar:Boolean,navController: NavHostController){
    val uiState = controlBarViewModel.store.uiState.collectAsState()
    val isSubMarkerBarOpen = uiState.value.isSubMarkerBarOpen

    ConstraintLayout() {
        val (controlBar, markerControlBar, subMarkerControlBar) = createRefs()

        if(isDisplayControlBar){
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .constrainAs(markerControlBar) {
                    bottom.linkTo(if(isSubMarkerBarOpen) subMarkerControlBar.top else controlBar.top)
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
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickSubMaker(!isSubMarkerBarOpen) }
                        ) {
                            Text(text = "0~9", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            if(isSubMarkerBarOpen){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray)
                    .constrainAs(subMarkerControlBar) {
                        bottom.linkTo(controlBar.top)
                    }
                    .clickable(enabled = false) {})
                {
                    Column {
                        Row (
                            modifier = Modifier.horizontalScroll(rememberScrollState()).padding(start = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                        ){
                            val subMarkerItems = uiState.value.subMarkerItems

                            for(subMarkerItem in subMarkerItems){
                                Button(
                                    modifier = Modifier.width(30.dp),
                                    contentPadding = PaddingValues(10.dp),
                                    shape = RoundedCornerShape(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.DarkGray,
                                        contentColor = Color.White
                                    ),
                                    onClick = { controlBarViewModel.intent.onClickSubMarkerItem(subMarkerItem) }
                                ) {
                                    Text(text = subMarkerItem)
                                }
                            }
                        }
                    }
                }
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.DarkGray)
            .constrainAs(controlBar) {
                bottom.linkTo(parent.bottom)
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
                        Image(
                            painterResource(R.drawable.ic_controlbar_undo_button),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.redo() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Image(
                            painterResource(R.drawable.ic_controlbar_redo_button),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                            disabledContainerColor = Color.DarkGray
                        ),
                        onClick = { controlBarViewModel.intent.clearClickedCells() },
                        modifier = Modifier.weight(1F),
                        enabled = isDisplayControlBar
                    ) {
                        val id = if(isDisplayControlBar) R.drawable.ic_controlbar_clear_button else R.drawable.ic_controlbar_clear_button_off
                        Image(
                            painterResource(id),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.cancelClickedCells() },
                        modifier = Modifier.weight(1F)
                    ) {
                        Image(
                            painterResource(R.drawable.ic_controlbar_close_button),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { navController.navigate(Routes.Option.route) },
                        modifier = Modifier.weight(1F)
                    ) {
                        Image(
                            painterResource(R.drawable.baseline_more_vert_24),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }//Row End
    }
}