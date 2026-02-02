package com.santubabu.nextplayerpro.core.ui.composables

import android.Manifest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.santubabu.nextplayerpro.core.ui.R
import com.santubabu.nextplayerpro.core.ui.preview.DayNightPreview
import com.santubabu.nextplayerpro.core.ui.theme.NextPlayerTheme

@Composable
fun PermissionRationaleDialog(
    text: String,
    modifier: Modifier = Modifier,
    onConfirmButtonClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.permission_request),
            )
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            Button(onClick = onConfirmButtonClick) {
                Text(stringResource(R.string.grant_permission))
            }
        },
    )
}

@DayNightPreview
@Composable
fun PermissionRationaleDialogPreview() {
    NextPlayerTheme {
        Surface {
            PermissionRationaleDialog(
                text = stringResource(
                    id = R.string.permission_info,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                onConfirmButtonClick = {},
            )
        }
    }
}
