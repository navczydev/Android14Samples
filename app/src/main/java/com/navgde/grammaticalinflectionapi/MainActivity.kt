package com.navgde.grammaticalinflectionapi

import android.app.GrammaticalInflectionManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.navgde.grammaticalinflectionapi.ui.theme.GrammaticalInflectionAPITheme

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
    val context = LocalContext.current
    var showGenderDialog by rememberSaveable {
        mutableStateOf(true)
    }
    val grammaticalInflectionManager =
        context.applicationContext?.getSystemService(ComponentActivity.GRAMMATICAL_INFLECTION_SERVICE) as GrammaticalInflectionManager
    if (showGenderDialog) {
        SelectGrammaticalGenderDialogComposable { selectedValue ->
            grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(selectedValue)
            showGenderDialog = !showGenderDialog
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_14_logo),
            contentDescription = "Android 14 logo",
            modifier = Modifier.size(300.dp),
        )
        Text(
            text = "Grammatical Inflection API 🇫🇷",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedButton(onClick = {
                Toast.makeText(
                    context,
                    "${grammaticalInflectionManager.applicationGrammaticalGender}",
                    Toast.LENGTH_SHORT,
                ).show()
            }) {
                Text(text = "Check app's Grammatical gender")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                showGenderDialog = true
            }) {
                Text(text = "Change grammatical gender")
            }
        }

        Text(
            text = stringResource(id = R.string.baker_job_title),
            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 32.sp),
        )

        Text(
            text = stringResource(id = R.string.dear_cleint),
            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 32.sp),
        )
        AccessPhotoComposable()
    }
}

@RequiresApi(34)
// @Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectGrammaticalGenderDialogComposable(alertAction: (Int) -> Unit) {
    GrammaticalInflectionAPITheme {
        // Greeting("Android")
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = { /*TODO*/ },
            text = {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Masculine",
                        style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp),
                        modifier = Modifier.clickable {
                            alertAction(Configuration.GRAMMATICAL_GENDER_MASCULINE)
                        },
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Feminine",
                        style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp),
                        modifier = Modifier.clickable {
                            alertAction(Configuration.GRAMMATICAL_GENDER_FEMININE)
                        },
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Neutral",
                        style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp),
                        modifier = Modifier.clickable {
                            alertAction(Configuration.GRAMMATICAL_GENDER_NEUTRAL)
                        },
                    )
                    Spacer(Modifier.height(4.dp))
                }
            },
            title = {
                Text(text = "Select the Grammatical gender for your app", style = TextStyle(fontSize = 14.sp))
            },
        )
    }
}
