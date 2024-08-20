package com.pam.classicspin.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pam.classicspin.modules.appModule
import com.pam.classicspin.presentation.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun CasinoScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CasinoBackground()

        SlotMachineGame()
    }
}


@Composable
fun SlotMachineGame(
    viewModel: AppViewModel = koinViewModel()
) {

    val coins by viewModel.userCoins.collectAsState()
    val matchFound by viewModel.matchFound.collectAsState()
    val spinning by viewModel.spinning.collectAsState()
    val columnStates by viewModel.columnStates.collectAsState()
    val currentJackpot by viewModel.currentJackpot.collectAsState()
    var showInfoDialog by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = !matchFound && !showInfoDialog) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(top = 60.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    for (i in 0 until 5) {
                        SlotColumn(
                            currentIndex = columnStates[i],
                            imageResources = imageResources
                        )
                    }

                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    CoinsBalanceInformation(coins = coins, onInfoClick = {
                        showInfoDialog = true
                    })

                    SpinButton {
                        coroutineScope.launch {
                            viewModel.onSpinClick()
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = matchFound && !showInfoDialog) {
            JackpotDialog(currentJackpot)
        }

        AnimatedVisibility(visible = showInfoDialog) {
            InfoDialog(
                onCLoseClick = {
                    showInfoDialog = false
                }
            )
        }
    }

    LaunchedEffect(matchFound) {
        Log.d("","matchFound changed to: $matchFound")
    }

    LaunchedEffect(spinning) {

        Log.d("","spinning is $spinning")
//        Log.d("","Column states: ${columnStates.map { it.value }}")

        if (!spinning) {
            viewModel.onSpinStop()
        }
    }
}

@Preview(device = "spec:width=1344px,height=2992px,dpi=480,orientation=landscape")
@Composable
private fun CasinoScreenPreview() {
    KoinApplication(application = {
        // your preview config here
        modules(appModule)
    }) {
        CasinoScreen()
    }
}