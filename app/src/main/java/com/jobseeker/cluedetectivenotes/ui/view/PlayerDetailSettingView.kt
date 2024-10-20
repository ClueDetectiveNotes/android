package com.jobseeker.cluedetectivenotes.ui.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.ReorderableItem
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterLongPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.rememberReorderableLazyListState
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.reorderable
import com.jobseeker.cluedetectivenotes.ui.viewModel.GameSettingViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel

@Composable
fun PlayerDetailSettingView(
    navController: NavHostController,
    gameSettingViewModel: GameSettingViewModel = viewModel(),
    optionViewModel: OptionViewModel = viewModel()
){
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang
    Surface(modifier = Modifier.padding(10.dp)){
        Column(modifier = Modifier.fillMaxHeight()){
            ConstraintLayout(modifier = Modifier.fillMaxHeight()){
                val (desc, playerList, button) = createRefs()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(desc) {
                        top.linkTo(parent.top)
                    }){
                    Column {
                        Row {
                            Text(text = multiLang.getString("MSG.PDS_TITLE"), fontSize = 28.sp)
                        }
                        Row {
                            Text(text = multiLang.getString("MSG.PDS_DESC"))
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(playerList) {
                        top.linkTo(desc.bottom)
                    }){
                    Column (modifier = Modifier.fillMaxHeight()) {
                        Row (modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                        Row (modifier = Modifier.fillMaxWidth()){
                            VerticalReorderList(gameSettingViewModel)
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }){
                    NextToCardSettingButton(navController, gameSettingViewModel)
                }
            }
        }
    }
    BackOnPressedBackToPlayerSetting(navController = navController)
}

@Composable
fun VerticalReorderList(gameSettingViewModel: GameSettingViewModel) {
    val uiState = gameSettingViewModel.store.uiState.collectAsState()
    val data = uiState.value.playerIdList
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        gameSettingViewModel.intent.reorderPlayer(from.index,to.index)
    })

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxHeight()
                .reorderable(state)
                .detectReorderAfterLongPress(state)
        ) {
            items(data, { it }) { item ->
                ReorderableItem(state, key = item) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")
                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            Modifier
                                .height(60.dp)
                                .width(320.dp)
                                .padding(4.dp, 4.dp, 4.dp, 4.dp),
                        )
                        {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                RadioButton(
                                    selected = (uiState.value.selectedOption == item),
                                    onClick = {
                                        gameSettingViewModel.intent.selectPlayer(item)
                                    }
                                )
                            }//RadioButton Box End
                            Box (modifier = Modifier
                                /*.fillMaxWidth()*/
                                .width(220.dp)
                                .fillMaxHeight()
                                .bottomBorder(1.dp, Color.LightGray)) {
                                Text(
                                    text = uiState.value.playerNameMap[item]!!,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }//Player Name Box End
                            Box (modifier = Modifier
                                .width(50.dp)
                                .fillMaxHeight()
                                .bottomBorder(1.dp, Color.LightGray)
                                .detectReorderAfterPress(state)){
                                Image(
                                    painterResource(R.drawable.ic_setting_reorder_hotspot),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }//Drag Handle Box End
                        }//Row End
                    }//Column End
                }//ReorderableItem End
            }//items End
        }//LazyColumn End
    }//Box End
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)