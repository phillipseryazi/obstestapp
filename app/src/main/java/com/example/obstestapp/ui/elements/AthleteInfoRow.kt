package com.example.obstestapp.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.obstestapp.utils.Tags

@Composable
fun AthleteInfoRow(key: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.testTag(Tags.DETAILS_SCREEN_INFO_ROW_TAGS)
    ) {
        OBSText(
            text = "$key :",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(4.dp))
        OBSText(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )
    }
}
