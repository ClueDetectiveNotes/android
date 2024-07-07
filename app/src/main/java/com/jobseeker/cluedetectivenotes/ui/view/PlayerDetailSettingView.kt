package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.ui.Routes
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.ReorderableItem
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterLongPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.rememberReorderableLazyListState
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.reorderable

@Composable
fun PlayerDetailSettingView(navController: NavHostController){

    Surface {
        Column {
            Row {
                VerticalReorderList()
            }
            Row (modifier = Modifier.height(50F.dp)){
                Button(onClick = { navController.navigate(Routes.Sheet.route) }) {
                    Text(text = "플레이")
                }
            }
        }
    }
}

@Composable
fun VerticalReorderList() {
    val data = remember { mutableStateOf(List(10) { "Item $it" }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    val radioOptions = data.value
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        Modifier
                            .height(50.dp)
                            .width(400.dp)
                            .background(Color.Yellow)){
                        RadioButton(selected = (selectedOption == item), onClick = { selectedOption = item })
                        Text(item)
                    }
                }
            }
        }
    }
}