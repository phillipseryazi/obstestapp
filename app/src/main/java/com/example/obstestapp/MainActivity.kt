package com.example.obstestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.obstestapp.ui.navigation.NavigationComposable
import com.example.obstestapp.ui.theme.OBSTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sizeClass = calculateWindowSizeClass(this)
            val navController = rememberNavController()
            OBSTestAppTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    NavigationComposable(
                        isLandScape = sizeClass.widthSizeClass > WindowWidthSizeClass.Compact,
                        navController = navController
                    )
                }
            }
        }
    }
}
