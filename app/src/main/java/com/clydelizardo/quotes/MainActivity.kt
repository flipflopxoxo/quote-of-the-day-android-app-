package com.clydelizardo.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.clydelizardo.quotes.quoteList.QuoteListViewModel
import com.clydelizardo.quotes.ui.QuoteListView
import com.clydelizardo.quotes.ui.QuoteOfTheDayPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var quoteService: QuoteService

    private val quoteOfTheDayViewModel: QuoteOfTheDayViewModel by viewModels()
    private val quoteListViewModel: QuoteListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalityTestTheme {
                // A surface container using the 'background' color from the theme
                val tabs = mapOf(
                    "Random" to R.drawable.baseline_card_giftcard_24,
                    "Search" to R.drawable.baseline_search_24
                )
                var selectedTab by remember {
                    mutableStateOf("Random")
                }
                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when (selectedTab) {
                            "Random" -> {
                                val quoteOfTheDayState by quoteOfTheDayViewModel.state.observeAsState()
                                quoteOfTheDayState?.let {
                                    QuoteOfTheDayPage(
                                        quoteOfTheDayState = it,
                                        onRefresh = quoteOfTheDayViewModel::refresh
                                    )
                                }
                            }
                            "Search" -> {
                                val lazyPagingItems = quoteListViewModel.pager.collectAsLazyPagingItems()
                                QuoteListView(lazyPagingItems)
                            }
                        }
                    }
                    NavigationBar(modifier = Modifier.fillMaxWidth()) {
                        tabs.forEach {
                            val icon = it.value
                            val tabName = it.key
                            NavigationBarItem(
                                icon = {
                                    Icon(painterResource(id = icon), contentDescription = null)
                                },
                                label = { Text(tabName) },
                                selected = selectedTab == tabName,
                                onClick = { selectedTab = tabName }
                            )
                        }
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