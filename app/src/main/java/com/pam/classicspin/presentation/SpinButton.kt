package com.pam.classicspin.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pam.classicspin.R

@Composable
fun SpinButton(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.button_spin),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentScale = ContentScale.Fit
    )
}
