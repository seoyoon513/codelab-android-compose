package com.syoon.codelab_android_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.syoon.codelab_android_compose.ui.theme.CodelabandroidcomposeTheme

class MainActivity : ComponentActivity() { // activity가 앱의 진입점
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // 레이아웃 정의 (기존 뷰 시스템: XML, 컴포즈: Composable 함수)
            CodelabandroidcomposeTheme { // Composable 함수의 스타일 지정
                MyApp(modifier = Modifier.fillMaxSize()) // 4-1. 호출부에서 내부 컴포넌트의 수정자를 조정할 수 있기 때문
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) { // 4. 기본 수정자가 있는 매개변수를 포함하는 것이 좋다
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Greeting(name = "Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Surface 내부에 중첩된 컴포넌트는 배경 색상 위에 그려짐
    // 1. androidx.compose.material3.Surface의 경우 Material에 따른 적절한 기본값과 패턴을 적용 (배경색: primary)
    Surface(color = MaterialTheme.colorScheme.primary) {
        Text( // 2. Text 컬러를 따로 지정하지 않아도 자동으로 흰색 설정됨 (텍스트: 테마에 정의된 onPrimary)
            text = "Hello $name!",
            // 3. 수정자를 통해 상위 요소 레이아웃 내에서 UI요소가 배치, 표시, 동작하는 방식을 알림
            modifier = modifier.padding(24.dp) // 체이닝을 통해 여러 수정자 추가 가능
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodelabandroidcomposeTheme {
        MyApp()
    }
}