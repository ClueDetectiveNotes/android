package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jobseeker.cluedetectivenotes.R
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.ReorderableItem
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterLongPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.detectReorderAfterPress
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.rememberReorderableLazyListState
import com.jobseeker.cluedetectivenotes.ui.utils.reorderable.reorderable
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmarkerSettingsView(
    navController: NavHostController,
    optionViewModel: OptionViewModel = viewModel()
) {
    val multiLang = optionViewModel.store.uiState.collectAsState().value.multiLang
    
    // 더미 서브마커 데이터
    val dummySubmarkers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17")
    
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text(multiLang.getString("SUBMARKER_SETTINGS")) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                        }
                    }
                )
                
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    
                    // 설명 텍스트
                    Text(
                        text = "서브마커를 활성화/비활성화하고 순서를 변경할 수 있습니다.",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    // ReorderableList
                    SubmarkerReorderableList(dummySubmarkers)
                }
            }
            
            // 플로팅 액션 버튼 - 우측 하단에 고정
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = { 
                        // TODO: 새 서브마커 추가 다이얼로그 열기
                        println("새 서브마커 추가 버튼 클릭")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "새 서브마커 추가"
                    )
                }
            }
        }
    }
}

@Composable
fun SubmarkerReorderableList(submarkers: List<String>) {
    // 서브마커 활성화 상태를 관리하는 Map
    var submarkerStates by remember { 
        mutableStateOf(submarkers.associateWith { true }) // 기본값은 모두 활성화
    }
    
    // 화면에서만 작동하는 서브마커 리스트 상태
    var uiSubmarkers by remember { mutableStateOf(submarkers) }
    
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        // 화면에서만 순서 변경
        val fromIndex = from.index
        val toIndex = to.index
        
        uiSubmarkers = uiSubmarkers.toMutableList().apply {
            val item = removeAt(fromIndex)
            add(toIndex, item)
        }
        
        // TODO: 실제 데이터 순서 변경 로직
        // SQLiteHelper.updateSubMarkerOrder(fromIndex, toIndex)
        println("서브마커 순서 변경: $fromIndex → $toIndex")
    })

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .reorderable(state)
                .detectReorderAfterLongPress(state) // 롱클릭으로 드래그 시작
        ) {
            items(uiSubmarkers, { it }) { submarker ->
                ReorderableItem(state, key = submarker) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp, label = "")
                    
                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.White)
                                .border(1.dp, Color.LightGray)
                        ) {
                            // 서브마커 값 - 나머지 공간 모두 차지
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = submarker,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                            
                            // 활성화/비활성화 토글
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Switch(
                                    checked = submarkerStates[submarker] ?: true,
                                    onCheckedChange = { isActive ->
                                        // 토글 상태 업데이트
                                        submarkerStates = submarkerStates.toMutableMap().apply {
                                            put(submarker, isActive)
                                        }
                                        
                                        // TODO: 실제 데이터 업데이트 로직
                                        // SQLiteHelper.toggleSubMarkerUsage(submarkerId, isActive)
                                        println("서브마커 $submarker 활성화 상태: $isActive")
                                    }
                                )
                            }
                            
                            // 드래그 핸들 - 클릭으로도 드래그 시작 가능
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .fillMaxHeight()
                                    .detectReorderAfterPress(state), // 핸들 클릭으로도 드래그 시작
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_setting_reorder_hotspot),
                                    contentDescription = "드래그",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 