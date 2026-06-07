package com.example.myapplication.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.components.SettingsItem
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            SettingsItem(
                text = stringResource(R.string.share_app),
                icon = Icons.Default.Share
            ) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, stringResource(R.string.share_text))
                }
                context.startActivity(Intent.createChooser(intent, null))
            }

            SettingsItem(
                text = stringResource(R.string.support),
                icon = Icons.Default.SupportAgent
            ) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(stringResource(R.string.email)))
                    putExtra(Intent.EXTRA_SUBJECT, stringResource(R.string.email_subject))
                    putExtra(Intent.EXTRA_TEXT, stringResource(R.string.email_body))
                }
                context.startActivity(intent)
            }

            SettingsItem(
                text = stringResource(R.string.user_agreement),
                icon = Icons.Default.ChevronRight
            ) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(stringResource(R.string.agreement_url))
                )
                context.startActivity(intent)
            }
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    MyApplicationTheme() {
        Surface() {
            SettingsScreen(
                onBackClick = {}
            )
        }
    }
}