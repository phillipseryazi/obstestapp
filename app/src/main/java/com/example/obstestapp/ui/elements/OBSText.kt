package com.example.obstestapp.ui.elements

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OBSText(
    text: String,
    style: TextStyle = MaterialTheme.typography.h4,
    color: Color = MaterialTheme.colors.primary,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        style = style,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
