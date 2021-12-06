package com.tiagodanin.mobile.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.tiagodanin.mobile.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: GamerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadGamer()

        setContent {
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
                }
            }
        }
    }

    @Composable
    fun MainContent() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.loadGamer()
                            }
                        ) {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Reload Game"
                            )
                        }
                    }
                )
            }
        ) {
            GridButtons()
        }
    }

    @Composable
    fun GridButtons() {
        val cards: List<List<BoxModel>> by viewModel.getBoxes().observeAsState(listOf())
        val currentGame: LiveData<GamerModel> = viewModel.getGamerStatus()
        var currentPlayer: String = if (currentGame.value?.currentPlayer == Status.PlayerX) "Player X" else "Player O"
        val isGamerEnding: Boolean = currentGame.value?.isGamerEnding == true
        val winingPlayer: String = if (currentGame.value?.winingPlayer == Status.PlayerX) "Player X" else "Player O"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
        ) {
            cards.forEach { rows ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    rows.forEach { card ->
                        ActionButton(card)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalArrangement = Arrangement.Center,
            ) {
                if (isGamerEnding) {
                    Text(
                        text = "Wining: $winingPlayer",
                        fontSize = 28.sp,
                        color = Color.Black,
                    )
                } else {
                    Text(
                        text = "Current: $currentPlayer",
                        fontSize = 28.sp,
                        color = Color.Black,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ActionButton(card: BoxModel) {
        Card(
            modifier = Modifier
                .padding(all = 10.dp)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(5.dp),
                )
                .height(100.dp)
                .width(100.dp),
            backgroundColor = Color.White,
            onClick = {
                viewModel.selectBox(card)
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = card.showText(),
                    fontSize = 34.sp,
                    color = Color.Black,
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TicTacToeTheme {
            MainContent()
        }
    }
}