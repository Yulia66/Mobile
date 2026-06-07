package com.example.myapplication.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val appContext = LocalContext.current
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_main_title),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Кнопка поиска
            MainActionCard(
                title = stringResource(R.string.main_btn_search),
                iconType = Icons.Rounded.Search,
                onClickAction = {
                    showTemporaryMessage(appContext, "Поиск открыт")
                    onSearchClick()
                }
            )
            
            // Кнопка настроек
            MainActionCard(
                title = stringResource(R.string.main_btn_settings),
                iconType = Icons.Rounded.Settings,
                onClickAction = {
                    showTemporaryMessage(appContext, "Настройки открыты")
                    onSettingsClick()
                }
            )
        }
    }
}

@Composable
private fun MainActionCard(
    title: String,
    iconType: androidx.compose.ui.graphics.vector.ImageVector,
    onClickAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = onClickAction
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )
            Icon(
                imageVector = iconType,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

private fun showTemporaryMessage(context: android.content.Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MyApplicationTheme {
        Surface {
            MainScreen(
                onSearchClick = {},
                onSettingsClick = {}
            )
        }
    }
}