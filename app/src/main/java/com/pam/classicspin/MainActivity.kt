package com.pam.classicspin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pam.classicspin.presentation.CasinoScreen
import com.pam.classicspin.ui.theme.RoyalFlushPamTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoyalFlushPamTheme {
                CasinoScreen()
            }
        }
    }
}