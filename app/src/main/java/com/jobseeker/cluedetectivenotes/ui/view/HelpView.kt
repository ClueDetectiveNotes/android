package com.jobseeker.cluedetectivenotes.ui.view

import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpView(navController: NavHostController) {
    val context = LocalContext.current
    var useExternalBrowser by remember { mutableStateOf(false) }

    // Chrome Custom Tabs로 URL 열기
    LaunchedEffect(Unit) {
        if (!useExternalBrowser) {
            try {
                val customTabsIntent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setUrlBarHidingEnabled(false)
                    .build()
                
                // 사용설명 URL
                val url = "https://cyan-satin-9e6.notion.site/181cb7d0177180358bfeca9764fb2e42?pvs=4"
                customTabsIntent.launchUrl(context, android.net.Uri.parse(url))
                
                // Chrome Custom Tabs가 열린 후 이전 화면으로 돌아가기
                navController.navigateUp()
            } catch (e: Exception) {
                // Chrome Custom Tabs를 사용할 수 없는 경우 기본 브라우저로 열기
                try {
                    val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://cyan-satin-9e6.notion.site/181cb7d0177180358bfeca9764fb2e42?pvs=4"))
                    context.startActivity(intent)
                    navController.navigateUp()
                } catch (e2: Exception) {
                    // 모든 방법이 실패한 경우 내장 HTML 사용
                    useExternalBrowser = true
                }
            }
        }
    }
    
    if (useExternalBrowser) {
        // 내장 HTML 사용 (CSP 문제 완전 회피)
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text("사용설명") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                        }
                    }
                )
                
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            webViewClient = WebViewClient()
                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                loadWithOverviewMode = true
                                useWideViewPort = true
                                setSupportZoom(true)
                                builtInZoomControls = true
                                displayZoomControls = false
                                allowFileAccess = false
                                allowContentAccess = false
                                textZoom = 100
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    update = { webView ->
                        // TODO: DB에서 다국어 HTML 콘텐츠 조회
                        // val htmlContent = getHelpContentFromDB(language)
                        
                        // 임시 HTML 템플릿 (나중에 DB에서 가져올 예정)
                        val htmlContent = getHelpHtmlTemplate()
                        
                        webView.loadData(htmlContent, "text/html", "UTF-8")
                    }
                )
            }
        }
    } else {
        // 로딩 화면 표시
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("사용설명을 여는 중...")
                    Text("잠시만 기다려주세요.")
                }
            }
        }
    }
}

// TODO: DB에서 다국어 HTML 콘텐츠를 가져오는 함수
private fun getHelpContentFromDB(language: String): String {
    // 예시: SQLiteHelper에서 HTML 콘텐츠 조회
    // return SQLiteHelper.getHelpContent(language)
    return getHelpHtmlTemplate()
}

// HTML 템플릿 (나중에 DB에서 동적으로 가져올 예정)
private fun getHelpHtmlTemplate(): String {
    return """
    <!DOCTYPE html>
    <html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Clue Detective Notes 사용설명</title>
        <style>
            body { 
                font-family: Arial, sans-serif; 
                margin: 0;
                padding: 0;
                line-height: 1.6;
                background-color: rgb(245, 245, 245);
                color: rgb(51, 51, 51);
            }
            .container {
                max-width: 800px;
                margin: 0 auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                margin-top: 20px;
                margin-bottom: 20px;
            }
            h1 { 
                color: rgb(51, 51, 51); 
                border-bottom: 2px solid rgb(0, 123, 255);
                padding-bottom: 10px;
                margin-top: 0;
            }
            h2 { 
                color: rgb(85, 85, 85); 
                margin-top: 30px;
                margin-bottom: 15px;
            }
            .step { 
                background: rgb(248, 249, 250); 
                padding: 15px; 
                margin: 15px 0; 
                border-radius: 8px;
                border-left: 4px solid rgb(0, 123, 255);
            }
            .tip {
                background: rgb(255, 243, 205);
                padding: 15px;
                margin: 15px 0;
                border-radius: 8px;
                border-left: 4px solid rgb(255, 193, 7);
            }
            .warning {
                background: rgb(248, 215, 218);
                padding: 15px;
                margin: 15px 0;
                border-radius: 8px;
                border-left: 4px solid rgb(220, 53, 69);
            }
            ul {
                margin: 10px 0;
                padding-left: 20px;
            }
            li {
                margin: 5px 0;
            }
            .highlight {
                background-color: rgb(255, 243, 205);
                padding: 2px 4px;
                border-radius: 3px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Clue Detective Notes 사용설명</h1>
            
            <h2>게임 시작하기</h2>
            <div class="step">
                <strong>1단계:</strong> 홈 화면에서 <span class="highlight">"게임 시작"</span> 버튼을 클릭합니다.
            </div>
            <div class="step">
                <strong>2단계:</strong> 플레이어 설정에서 참가자 수와 이름을 입력합니다.
            </div>
            <div class="step">
                <strong>3단계:</strong> 각 플레이어의 카드를 설정합니다.
            </div>
            
            <h2>게임 플레이</h2>
            <div class="step">
                <strong>시트 사용법:</strong> 그리드에서 각 셀을 클릭하여 마커를 설정할 수 있습니다.
            </div>
            <div class="step">
                <strong>마커 종류:</strong> 
                <ul>
                    <li><strong>✓ (체크):</strong> 확실히 가지고 있음</li>
                    <li><strong>✗ (엑스):</strong> 확실히 가지고 있지 않음</li>
                    <li><strong>? (물음표):</strong> 불확실함</li>
                </ul>
            </div>
            
            <div class="tip">
                <strong>팁:</strong> 롱클릭으로 서브마커를 추가할 수 있습니다.
            </div>
            
            <h2>설정</h2>
            <div class="step">
                <strong>언어 변경:</strong> 옵션에서 한국어/영어를 선택할 수 있습니다.
            </div>
            <div class="step">
                <strong>게임 저장:</strong> 게임 진행 상황은 자동으로 저장됩니다.
            </div>
            
            <div class="warning">
                <strong>참고:</strong> 외부 링크가 차단되어 내장 설명을 표시합니다.
            </div>
        </div>
    </body>
    </html>
    """.trimIndent()
}