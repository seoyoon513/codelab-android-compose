package com.syoon.codelab_android_compose

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    // 7. rememberSaveable은 구성변경(ex.화면회전) 및 프로세스 중단에도 상태를 저장함
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) } // by로 get,set 위임

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
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name = name)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CardContent(name: String, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color.Blue)
            .animateContentSize( // animateContentSize 수정자를 row에 적용
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .background(Color.Gray)
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.headlineSmall
            ) // 9. CodelabandroidcomposeTheme 내 모든 하위 컴포저블에서 Material테마에 정의된 속성을 가져올 수 있다
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold // 9. copy를 활용하여 미리 정의된 스타일 변경 가능
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(
                        R.string.show_more
                    )
                }
            )
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES) // 9. ui모드 속성으로 다크모드 미리보기 설정
@Composable
fun GreetingPreview() {
    CodelabandroidcomposeTheme {
        Greetings()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    CodelabandroidcomposeTheme {
        OnboardingScreen()
    }
}