package com.syoon.codelab_android_compose

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun MyApp(
    modifier: Modifier = Modifier, // 4. 기본 수정자가 있는 매개변수를 포함하는 것이 좋다
) {
    // 6. 상태 호이스팅
    var shouldShowOnBoarding by remember { mutableStateOf(true) } // by로 get,set 위임

    Surface(modifier = modifier) {
        if (shouldShowOnBoarding) {
            OnboardingScreen(
                // shouldShowOnBoarding 상태가 아닌 함수 콜백 전달
                onContinueClicked = { shouldShowOnBoarding = false }
            )
        } else {
            Greetings()
        }

    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    /**
     * 5. 항목의 상태를 저장하는 값 선언
     * 5-1. var expanded = false -> 컴포즈에서 상태변경을 감지하지 않음
     * 5-2. val expanded = mutableStateOf(false) -> 상태 변경을 감지하지만 이전 상태를 기억하지 못함
     */
    var expanded by remember { mutableStateOf(false) }
    val extraPadding = if (expanded) 48.dp else 0.dp
    // Surface 내부에 중첩된 컴포넌트는 배경 색상 위에 그려짐
    // 1. androidx.compose.material3.Surface의 경우 Material에 따른 적절한 기본값과 패턴을 적용 (배경색: primary)
    // 2. Text 컬러를 따로 지정하지 않아도 자동으로 흰색 설정됨 (텍스트: 테마에 정의된 onPrimary)
    // 3. 수정자를 통해 상위 요소 레이아웃 내에서 UI요소가 배치, 표시, 동작하는 방식을 알림
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(Color.Blue)
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
                    .background(Color.Gray)
            ) {
                Text(text = "Hello")
                Text(text = name)
            }
            ElevatedButton(onClick = { expanded = !expanded }) {
                Text(text = if (expanded) "Show more" else "Show less")
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit = {}, modifier: Modifier = Modifier) {
    // 6. 상태값을 상위 요소로 끌어올림 -> MyApp()
    // var shouldShowOnBoarding by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodelabandroidcomposeTheme {
        MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    CodelabandroidcomposeTheme {
        OnboardingScreen()
    }
}