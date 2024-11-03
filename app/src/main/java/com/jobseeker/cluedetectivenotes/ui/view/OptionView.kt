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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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



    var isDropDownMenuExpandedLanguage by remember { mutableStateOf(false) }
    var isDropDownMenuExpandedDarkThemeType by remember { mutableStateOf(false) }
    Surface {
        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)){
            //언어 설정
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .weight(1f)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                ){
                    Text(text = multiLang.getString("OPT.LANGUAGE"))
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Row {
                        val language = uiState.value.language
                        Button(
                            onClick = { isDropDownMenuExpandedLanguage = true },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(MaterialTheme.colorScheme.background.value),
                                contentColor = Color(MaterialTheme.colorScheme.primary.value)
                            ),
                            border = BorderStroke(1.dp, Color(MaterialTheme.colorScheme.primary.value))
                        ) {
                            Text(text = multiLang.getString("CM_CD.$language"))
                        }
                    }
                    Row {
                        val languageSelectBoxList = commonCode["LANGUAGE.SELECT_BOX"]
                        DropdownMenu(expanded = isDropDownMenuExpandedLanguage, onDismissRequest = { isDropDownMenuExpandedLanguage = false }) {
                            if (languageSelectBoxList != null) {
                                for(languageSelectBoxItem in languageSelectBoxList){
                                    val code = languageSelectBoxItem["CODE"];
                                    DropdownMenuItem(
                                        text = { Text(text=multiLang.getString("CM_CD.$code")) },
                                        onClick = {
                                            optionViewModel.intent.setLanguage(code!!)
                                            isDropDownMenuExpandedLanguage = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }//언어 설정

            //다크 테마 설정
            val isUseDarkTheme = uiState.value.isUseDarkTheme
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .weight(1f)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                ){
                    Text(text = multiLang.getString("OPT.IS_USE_DARK_THEME"))
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Row {
                        Switch(checked = isUseDarkTheme, onCheckedChange = {
                            optionViewModel.intent.setUseDarkTheme(it)
                        })
                    }
                }
            }//다크 테마 사용 유무 설정

            //다크 테마 설정
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .weight(1f)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                ){
                    Text(text = multiLang.getString("OPT.DARK_THEME_TYPE"))
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Row {
                        val darkThemeType = uiState.value.darkThemeType
                        Button(
                            enabled = isUseDarkTheme,
                            onClick = { isDropDownMenuExpandedDarkThemeType = true },
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(MaterialTheme.colorScheme.background.value),
                                contentColor = Color(MaterialTheme.colorScheme.primary.value)
                            ),
                            border = BorderStroke(1.dp, Color(MaterialTheme.colorScheme.primary.value))
                        ) {
                            Text(text = multiLang.getString("CM_CD.$darkThemeType"))
                        }
                    }
                    Row {
                        val darkThemeTypeSelectBoxList = commonCode["DARK_THEME_TYPE.SELECT_BOX"]
                        DropdownMenu(expanded = isDropDownMenuExpandedDarkThemeType, onDismissRequest = { isDropDownMenuExpandedDarkThemeType = false }) {
                            if (darkThemeTypeSelectBoxList != null) {
                                for(darkThemeTypeSelectBoxItem in darkThemeTypeSelectBoxList){
                                    val code = darkThemeTypeSelectBoxItem["CODE"];
                                    DropdownMenuItem(
                                        text = { Text(text=multiLang.getString("CM_CD.$code")) },
                                        onClick = {
                                            optionViewModel.intent.setDarkThemeType(code!!)
                                            isDropDownMenuExpandedDarkThemeType = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }//다크 테마 설정

            //블라인드 투명도 설정
            Row (modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)){
                Column {
                    Text(text = multiLang.getString("OPT.BLIND_TRANSPARENCY"))
                    Row(){
                        var sliderPosition = uiState.value.blindTransparency
                        Column {
                            Slider(
                                value = sliderPosition,
                                onValueChange = {
                                    sliderPosition = it
                                    optionViewModel.intent.setBlindTransparency(sliderPosition)
                                },
                                steps = 2,
                                valueRange = 40f..100f
                            )
                            Text(text = sliderPosition.toString())
                        }
                    }
                }
            }//블라인드 투명도 설정

        }
    }
}