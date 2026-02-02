package com.santubabu.nextplayerpro.settings.screens.appearance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santubabu.nextplayerpro.core.model.ThemeConfig
import com.santubabu.nextplayerpro.core.ui.R
import com.santubabu.nextplayerpro.core.ui.components.ListSectionTitle
import com.santubabu.nextplayerpro.core.ui.components.NextTopAppBar
import com.santubabu.nextplayerpro.core.ui.components.PreferenceSwitch
import com.santubabu.nextplayerpro.core.ui.components.PreferenceSwitchWithDivider
import com.santubabu.nextplayerpro.core.ui.components.RadioTextButton
import com.santubabu.nextplayerpro.core.ui.designsystem.NextIcons
import com.santubabu.nextplayerpro.core.ui.theme.NextPlayerTheme
import com.santubabu.nextplayerpro.core.ui.theme.supportsDynamicTheming
import com.santubabu.nextplayerpro.settings.composables.OptionsDialog
import com.santubabu.nextplayerpro.settings.extensions.name

@Composable
fun AppearancePreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: AppearancePreferencesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AppearancePreferencesContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateUp = onNavigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun AppearancePreferencesContent(
    uiState: AppearancePreferencesUiState,
    onEvent: (AppearancePreferencesEvent) -> Unit,
    onNavigateUp: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            NextTopAppBar(
                title = stringResource(id = R.string.appearance_name),
                navigationIcon = {
                    FilledTonalIconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = NextIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            ListSectionTitle(text = stringResource(id = R.string.appearance_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = if (supportsDynamicTheming()) 3 else 2
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.dark_theme),
                    description = uiState.preferences.themeConfig.name(),
                    isChecked = uiState.preferences.themeConfig == ThemeConfig.ON,
                    onChecked = { onEvent(AppearancePreferencesEvent.ToggleDarkTheme) },
                    icon = NextIcons.DarkMode,
                    onClick = { onEvent(AppearancePreferencesEvent.ShowDialog(AppearancePreferenceDialog.Theme)) },
                    index = 0,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(R.string.high_contrast_dark_theme),
                    description = stringResource(R.string.high_contrast_dark_theme_desc),
                    icon = NextIcons.Contrast,
                    isChecked = uiState.preferences.useHighContrastDarkTheme,
                    onClick = { onEvent(AppearancePreferencesEvent.ToggleUseHighContrastDarkTheme) },
                    index = 1,
                    count = totalRows,
                )
                if (supportsDynamicTheming()) {
                    PreferenceSwitch(
                        title = stringResource(id = R.string.dynamic_theme),
                        description = stringResource(id = R.string.dynamic_theme_description),
                        icon = NextIcons.Appearance,
                        isChecked = uiState.preferences.useDynamicColors,
                        onClick = { onEvent(AppearancePreferencesEvent.ToggleUseDynamicColors) },
                        index = 2,
                        count = totalRows,
                    )
                }
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                AppearancePreferenceDialog.Theme -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.dark_theme),
                        onDismissClick = { onEvent(AppearancePreferencesEvent.ShowDialog(null)) },
                    ) {
                        items(ThemeConfig.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = (it == uiState.preferences.themeConfig),
                                onClick = {
                                    onEvent(AppearancePreferencesEvent.UpdateThemeConfig(it))
                                    onEvent(AppearancePreferencesEvent.ShowDialog(null))
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AppearancePreferencesScreenPreview() {
    NextPlayerTheme {
        AppearancePreferencesContent(
            uiState = AppearancePreferencesUiState(),
            onEvent = {},
        )
    }
}
