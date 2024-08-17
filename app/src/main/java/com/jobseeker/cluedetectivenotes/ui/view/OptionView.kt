package com.jobseeker.cluedetectivenotes.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel


@Composable
fun OptionView(
    optionViewModel: OptionViewModel = viewModel()
) {
    val uiState = optionViewModel.store.uiState.collectAsState()
    val multiLang = uiState.value.multiLang
    val commonCode = uiState.value.commonCode


    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    Surface (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(20.dp)){
        //언어 설정
        Row (modifier = Modifier.fillMaxWidth().fillMaxHeight()){
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1f)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ){
                Text(text = multiLang["OPT.LANGUAGE"]!!)
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth().weight(1f)
            ){
                Row {
                    val language = uiState.value.language
                    Button(
                        onClick = { isDropDownMenuExpanded = true },
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(MaterialTheme.colorScheme.background.value),
                            contentColor = Color(MaterialTheme.colorScheme.primary.value)
                        ),
                        border = BorderStroke(1.dp, Color(MaterialTheme.colorScheme.primary.value))
                    ) {
                        Text(text = multiLang["CM_CD.$language"]!!)
                    }
                }
                Row {
                    val languageSelectBoxList = commonCode["LANGUAGE.SELECT_BOX"]
                    DropdownMenu(expanded = isDropDownMenuExpanded, onDismissRequest = { isDropDownMenuExpanded = false }) {
                        if (languageSelectBoxList != null) {
                            for(languageSelectBoxItem in languageSelectBoxList){
                                val code = languageSelectBoxItem["CODE"];
                                DropdownMenuItem(
                                    text = { Text(text=multiLang["CM_CD.$code"]!!) },
                                    onClick = {
                                        optionViewModel.intent.setLanguage(code!!)
                                        isDropDownMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}