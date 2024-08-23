package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.Routes
import com.jobseeker.cluedetectivenotes.ui.viewModel.ControlBarViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel

@Composable
fun ControlBar(
    controlBarViewModel:ControlBarViewModel,
    isDisplayControlBar:Boolean,
    navController: NavHostController,
    gameSettingViewModel: GameSettingViewModel = viewModel(),
    optionViewModel : OptionViewModel = viewModel()
){
    val uiState = controlBarViewModel.store.uiState.collectAsState()
    val isSubMarkerBarOpen = uiState.value.isSubMarkerBarOpen

    var expandedDefault by remember { mutableStateOf(true) }
    val rotateStateDefault = animateFloatAsState( targetValue = if (expandedDefault) 90F else 270F, label = "", )
    var expandedLast by remember { mutableStateOf(false) }
    val rotateStateLast = animateFloatAsState( targetValue = if (expandedLast) 90F else 270F, label = "", )

    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang

    ConstraintLayout() {
        val (controlBar, markerControlBar, subMarkerControlBar, menu) = createRefs()

        if(isDisplayControlBar){
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .constrainAs(markerControlBar) {
                    bottom.linkTo(if (isSubMarkerBarOpen) subMarkerControlBar.top else controlBar.top)
                }
                .clickable(enabled = false) {})
            {
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .weight(5F)){
                    Row (horizontalArrangement = Arrangement.Start){
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickCheckMaker() }
                        ) {
                            Text(text = "O", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickCrossMaker() }
                        ) {
                            Text(text = "X", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickSlashMaker() }
                        ) {
                            Text(text = "/", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickQuestionMaker() }
                        ) {
                            Text(text = "?", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickExclamationMaker() }
                        ) {
                            Text(text = "!", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            modifier = Modifier.weight(1F),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if(isSubMarkerBarOpen) Color.Gray else Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.onClickSubMaker(!isSubMarkerBarOpen) }
                        ) {
                            Text(text = "0~9", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)){
                    Button(
                        modifier = Modifier.width(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        ),
                        onClick = { controlBarViewModel.intent.cancelClickedCells() },
                    ) {
                        Image(
                            painterResource(R.drawable.ic_conrolbar_shutdown_markerbar_button),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }//Row End - 메인 마커 컨트롤 바
            if(isSubMarkerBarOpen){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray)
                    .constrainAs(subMarkerControlBar) {
                        bottom.linkTo(controlBar.top)
                    }
                    .clickable(enabled = false) {})
                {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .weight(5F)){
                        Row (
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                        ){
                            Button(
                                modifier = Modifier.width(30.dp),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = { expandedDefault = !expandedDefault }
                            ) {
                                Icon(
                                    Icons.Default.ArrowDropDown, "",
                                    modifier = Modifier.rotate(rotateStateDefault.value)
                                )
                            }
                            AnimatedVisibility(
                                visible = expandedDefault,
                            ) {
                                Row (modifier = Modifier.fillMaxWidth()){
                                    val subMarkerItems = uiState.value.subMarkerItems

                                    for (idx in 0 until 4) {
                                        val subMarkerItem = subMarkerItems[idx]
                                        Button(
                                            modifier = Modifier.width(30.dp),
                                            contentPadding = PaddingValues(10.dp),
                                            shape = RoundedCornerShape(0.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.DarkGray,
                                                contentColor = Color.White
                                            ),
                                            onClick = {
                                                controlBarViewModel.intent.onClickSubMarkerItem(
                                                    subMarkerItem
                                                )
                                            }
                                        ) {
                                            Text(text = subMarkerItem)
                                        }
                                    }
                                    AnimatedVisibility(
                                        visible = expandedLast,
                                    ){
                                        Row (modifier = Modifier.fillMaxWidth()){
                                            for (idx in 4 until subMarkerItems.size) {
                                                val subMarkerItem = subMarkerItems[idx]
                                                Button(
                                                    modifier = Modifier.width(30.dp),
                                                    contentPadding = PaddingValues(10.dp),
                                                    shape = RoundedCornerShape(0.dp),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = Color.DarkGray,
                                                        contentColor = Color.White
                                                    ),
                                                    onClick = {
                                                        controlBarViewModel.intent.onClickSubMarkerItem(
                                                            subMarkerItem
                                                        )
                                                    }
                                                ) {
                                                    Text(text = subMarkerItem)
                                                }
                                            }
                                        }
                                    }
                                    Button(
                                        modifier = Modifier.width(30.dp),
                                        contentPadding = PaddingValues(0.dp),
                                        shape = RoundedCornerShape(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.DarkGray,
                                            contentColor = Color.White
                                        ),
                                        onClick = { expandedLast = !expandedLast }
                                    ) {
                                        Icon(
                                            Icons.Default.ArrowDropDown, "",
                                            modifier = Modifier.rotate(rotateStateLast.value)
                                        )
                                    }
                                }
                            }

                            val addedSubMarkerItems = uiState.value.addedSubMarkerItems
                            if(addedSubMarkerItems.isNotEmpty()){
                                for (subMarkerItem in addedSubMarkerItems) {
                                    Button(
                                        contentPadding = PaddingValues(10.dp),
                                        shape = RoundedCornerShape(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.DarkGray,
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            controlBarViewModel.intent.onClickSubMarkerItem(
                                                subMarkerItem
                                            )
                                        }
                                    ) {
                                        Text(text = subMarkerItem)
                                    }
                                }
                            }
                        }
                    }
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)){
                        Button(
                            modifier = Modifier.width(50.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = { controlBarViewModel.intent.openAddSubMarkerDialog() },
                        ) {
                            Image(
                                painterResource(R.drawable.ic_controlbar_add_submarker),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }//Row End - 서브 마커 컨트롤 바
        Row (modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color.DarkGray)
            .constrainAs(controlBar) {
                bottom.linkTo(parent.bottom)
            }
            .clickable(enabled = false) {})
        {
            Column (modifier = Modifier
                .fillMaxWidth()
                .weight(5F)){
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

                }
            }
            Column (modifier = Modifier
                .fillMaxWidth()
                .weight(1F)){
                Button(
                    modifier = Modifier.width(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    ),
                    onClick = { isDropDownMenuExpanded = true },
                ) {
                    Image(
                        painterResource(R.drawable.baseline_more_horiz_24),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }//Row End - 컨트롤 바

        Row(modifier = Modifier
            .constrainAs(menu) {
                bottom.linkTo(controlBar.top)
            }){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ){
                Row{
                    DropdownMenu(
                        expanded = isDropDownMenuExpanded,
                        onDismissRequest = { isDropDownMenuExpanded = false },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                    ){
                        DropdownMenuItem(
                            text = { Text(text = multiLang["BTN.HOME"]!!) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "",
                                )
                            },
                            onClick = {
                                //전체 초기화
                                gameSettingViewModel.intent.initAll()
                                navController.navigate(Routes.Home.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = multiLang["BTN.SETTING"]!!) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "",
                                )
                            },
                            onClick = {
                                //게임 초기화
                                gameSettingViewModel.intent.initGame()
                                navController.navigate(Routes.PlayerSetting.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = multiLang["BTN.OPTION"]!!) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "",
                                )
                            },
                            onClick = { navController.navigate(Routes.Option.route) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CustomTextFieldDialog(
    initialText: String?,
    onClickCancel: () -> Unit,
    onClickConfirm: (text: String) -> Unit,
    optionViewModel: OptionViewModel = viewModel()
) {
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang

    val text = remember { mutableStateOf(initialText ?: "") }

    Dialog(
        onDismissRequest = { onClickCancel() },
    ) {
        Card(
            shape = RoundedCornerShape(8.dp), // Card의 모든 꼭지점에 8.dp의 둥근 모서리 적용
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(text = multiLang["MSG.SHT_CB_ADSBMK_DESC"]!!)

                Spacer(modifier = Modifier.height(15.dp))

                // TextField
                BasicTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFBAE5F5),
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(all = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "",
                                tint = Color.DarkGray,
                            )
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            innerTextField()
                        }
                    },
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = {
                        onClickCancel()
                    }) {
                        Text(text = multiLang["BTN.CANCEL"]!!)
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    TextButton(onClick = {
                        onClickConfirm(text.value)
                    }) {
                        Text(text = multiLang["BTN.CONFIRM"]!!)
                    }
                }
            }
        }
    }
}