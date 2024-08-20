package com.pam.classicspin.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SlotColumn(currentIndex: Int, imageResources: List<Int>) {

    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 20.dp)
            .size(120.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResources[currentIndex]),
            contentDescription = "Slot symbol",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}