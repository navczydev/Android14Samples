package com.navgde.grammaticalinflectionapi

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.util.LocalePreferences
import com.navgde.grammaticalinflectionapi.ui.theme.GrammaticalInflectionAPITheme
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

typealias lp = LocalePreferences

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class MainActivity : ComponentActivity() {
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContent {
            GrammaticalInflectionAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainComposable()
                }
            }
        }
    }
}

@RequiresApi(34)
@Composable
fun MainComposable(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_14_logo),
            contentDescription = "Android 14 logo",
            modifier = Modifier.size(200.dp),
        )
        Text(
            text = "Regional preferences API",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Weather unit ⛅️ ${lp.getTemperatureUnit().uppercase()}\nFirst day of week 🗓️ ${
                lp.getFirstDayOfWeek().uppercase()
            }\n Hour cycle ⏰ ${lp.getHourCycle().uppercase()}\nCalendar Type 📆 ${lp.getCalendarType().uppercase()}",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Current weather: ${
                when (lp.getTemperatureUnit()) {
                    LocalePreferences.TemperatureUnit.CELSIUS -> "20 ℃"
                    LocalePreferences.TemperatureUnit.FAHRENHEIT -> "68 ℉"
                    else -> {
                        ""
                    }
                }
            }",
        )
        Spacer(modifier = Modifier.height(16.dp))
        val localDate = LocalDate.now()
        val weekField = WeekFields.of(Locale.getDefault())
        Text(text = "Current day no: ${localDate.get(weekField.dayOfWeek())}")
    }
}
