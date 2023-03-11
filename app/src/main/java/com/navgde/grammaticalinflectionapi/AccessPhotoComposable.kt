package com.navgde.grammaticalinflectionapi

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AccessPhotoComposable() {
    val context = LocalContext.current
    var result by rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("AccessPhotoComposable", "RESULT $it")
            result = it.data?.data
            /*
    * Get the file's content URI from the incoming Intent, then
    * get the file's MIME type
    */
            val mimeType: String? = it.data?.data?.let { returnUri ->
                context.applicationContext.contentResolver.getType(returnUri)
            }
            Log.d("AccessPhotoComposable", "MIME_TYPE : $mimeType")
            /*
 * Get the file's content URI from the incoming Intent,
 * then query the server app to get the file's display name
 * and size.
 */
            it.data?.data?.let { returnUri ->
                context.applicationContext.contentResolver.query(returnUri, null, null, null, null)
            }?.use { cursor ->
                /*
                 * Get the column indexes of the data in the Cursor,
                 * move to the first row in the Cursor, get the data,
                 * and display it.
                 */
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                Log.d("AccessPhotoComposable", cursor.getString(nameIndex))
                Log.d("AccessPhotoComposable", "${cursor.getLong(sizeIndex)}")
            }
        }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        Log.d("AccessPhotoComposable", "RESULT_CODE $it")
        if (!it) {
            if (context.applicationContext.checkSelfPermission(READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED) {
                Log.d("AccessPhotoComposable", "NEW PERMISSION GRANTED")
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedButton(onClick = {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        }) {
            Text("Allow to read images")
        }
        OutlinedButton(onClick = {
            launcher.launch(Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        }) {
            Text("Pick image")
        }
        AsyncImage(
            model = result,
            contentDescription = "Image from photo picker",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp, 200.dp)
                .clip(CircleShape),
        )
    }
}
