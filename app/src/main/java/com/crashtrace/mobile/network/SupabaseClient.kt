package com.crashtrace.mobile.network

import android.content.Context
import android.net.Uri
import android.util.Log
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

object SupabaseClient {
    private const val SUPABASE_URL = "https://stkdtcpydslyreapshwg.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN0a2R0Y3B5ZHNseXJlYXBzaHdnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyOTI4MTQsImV4cCI6MjA2ODg2ODgxNH0.Vyb3kzVZJ7r7hbNx5BKyKrsfJwHX2d8Y1gxXaKYm_Hk"
    private const val STORAGE_BUCKET = "crash-reports"

    private val client by lazy { // Use lazy initialization for client
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Storage)
        }
    }

    suspend fun uploadImage(context: Context, uri: Uri, id: String): String? {
        Log.d("SupabaseUpload", "Attempting to upload image for ID: $id, URI: $uri")
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val imageBytes = inputStream?.readBytes()
                inputStream?.close() // Always close the input stream

                if (imageBytes != null) {
                    Log.d("SupabaseUpload", "Image bytes successfully read. Size: ${imageBytes.size} bytes")
                    val storage = client.storage
                    val bucket = storage[STORAGE_BUCKET]
                    bucket.upload("$id.jpg", imageBytes, upsert = true)

                    // --- CRITICAL CHANGE HERE ---
                    // Append a unique timestamp as a query parameter to bust the cache
                    val cacheBuster = System.currentTimeMillis()
                    val uploadedUrl = "${SUPABASE_URL}/storage/v1/object/public/${STORAGE_BUCKET}/$id.jpg?t=$cacheBuster"
                    Log.d("SupabaseUpload", "Image uploaded successfully. URL (with cache buster): $uploadedUrl")
                    uploadedUrl
                } else {
                    Log.e("SupabaseUpload", "Image byte array is null or empty from URI: $uri")
                    null
                }
            } catch (e: FileNotFoundException) {
                Log.e("SupabaseUpload", "File not found for URI: $uri", e)
                null
            } catch (e: Exception) {
                Log.e("SupabaseUpload", "Error during image upload for ID: $id", e)
                null
            }
        }
    }
}
