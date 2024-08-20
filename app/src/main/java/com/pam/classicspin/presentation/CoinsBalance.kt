package com.pam.classicspin.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pam.classicspin.R

@Composable
fun CoinsBalanceInformation(
    coins: Int,
    onInfoClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = painterResource(id = R.drawable.btn_info),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(50.dp)
                .height(40.dp)
                .clickable {
                    Log.d("", "info clicked")
                    onInfoClick()
                }
        )

        Spacer(modifier = Modifier.width(20.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.coin_bg),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "$coins",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE9C7BB),
                fontSize = 18.sp
            )
        }

    }
}

@Preview
@Composable
private fun CoinsBalancePreview() {
    CoinsBalanceInformation(
        coins = 1000,
        onInfoClick = {}

    )
}