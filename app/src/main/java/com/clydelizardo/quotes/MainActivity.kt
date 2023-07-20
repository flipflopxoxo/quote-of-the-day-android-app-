package com.clydelizardo.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.clydelizardo.quotes.qotd.QuoteOfTheDayViewModel
import com.clydelizardo.quotes.ui.theme.PersonalityTestTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import com.clydelizardo.quotes.ui.QuoteOfTheDayPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var quoteService: QuoteService

    private val quoteOfTheDayViewModel: QuoteOfTheDayViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalityTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val quoteOfTheDayState by quoteOfTheDayViewModel.state.observeAsState()
                    quoteOfTheDayState?.let {
                        QuoteOfTheDayPage(
                            quoteOfTheDayState = it,
                            onRefresh = quoteOfTheDayViewModel::refresh
                        )
                    }
                }
            }
        }
//        GlobalScope.launch {
//            val quoteList = quoteService.quoteList()
//            Log.d("Quote list", quoteList.toString())
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersonalityTestTheme {
        Greeting("Android")
    }
}