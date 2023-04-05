package com.navgde.grammaticalinflectionapi

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.navgde.grammaticalinflectionapi.data.ImageDataModel
import com.navgde.grammaticalinflectionapi.data.queryContentResolver

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AccessPhotoComposable() {
    val context = LocalContext.current
    var imageDataModelList by remember {
        mutableStateOf(emptyList<ImageDataModel>())
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { mapResults ->
            Log.d(TAG, "RESULT_CODE $mapResults")
            mapResults.forEach {
                Log.d(TAG, "Permission: ${it.key} Status: ${it.value}")
            }
            if (mapResults.values.any { it }) {
                queryContentResolver(context) { listOfImages ->
                    imageDataModelList = listOfImages
                }
            }
        }
    OutlinedButton(onClick = {
        permissionLauncher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
    }) {
        Text("Allow to read images")
    }
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(imageDataModelList) {
            AsyncImage(
                model = it.uri,
                contentDescription = "Image from photo picker",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clip(CircleShape),
            )
        }
    }
}

@Preview
@Composable
fun TestPreview() {
    Text(text = "Hi from preview")
}

private const val TAG = "AccessPhotoComposable"
