package com.example.obstestapp.ui.elements

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.obstestapp.R

@Composable
fun OBSNavbar(
    modifier: Modifier = Modifier,
    heading: @Composable () -> Unit,
    showNavIcon: Boolean = false,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            heading()
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            if (showNavIcon) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_arrow_back
                        ),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {},
        elevation = 8.dp,
    )
}
