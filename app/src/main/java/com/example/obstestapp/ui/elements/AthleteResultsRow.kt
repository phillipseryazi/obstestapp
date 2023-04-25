package com.example.obstestapp.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.obstestapp.R
import com.example.obstestapp.domain.Results
import com.example.obstestapp.ui.theme.bronze
import com.example.obstestapp.ui.theme.gold
import com.example.obstestapp.ui.theme.silver

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AthleteResultsRow(modifier: Modifier, results: Results) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OBSText(
            text = results.city,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Row {
            if (results.gold > 0) {
                Chip(
                    modifier = Modifier.padding(start = 2.dp),
                    onClick = {},
                    enabled = true,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_sports_score),
                            contentDescription = null,
                            tint = gold
                        )
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = gold.copy(alpha = 0.2f),
                        contentColor = gold
                    ),
                    content = {
                        OBSText(
                            text = "${results.gold}",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                )
            }
            if (results.silver > 0) {
                Chip(
                    modifier = Modifier.padding(start = 2.dp),
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_sports_score),
                            contentDescription = null,
                            tint = silver
                        )
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = silver.copy(alpha = 0.2f),
                        contentColor = silver
                    ),
                    content = {
                        OBSText(
                            text = "${results.silver}",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                )
            }
            if (results.bronze > 0) {
                Chip(
                    modifier = Modifier.padding(start = 2.dp),
                    onClick = {},
                    enabled = true,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_sports_score),
                            contentDescription = null,
                            tint = bronze
                        )
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = bronze.copy(alpha = 0.2f),
                        contentColor = bronze
                    ),
                    content = {
                        OBSText(
                            text = "${results.bronze}",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                )
            }
        }
    }
}
