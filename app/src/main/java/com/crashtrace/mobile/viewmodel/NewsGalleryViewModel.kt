package com.crashtrace.mobile.viewmodel


import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.entity.Report
import com.crashtrace.mobile.data.repository.ReportRepository
import com.crashtrace.mobile.ui.components.CardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NewsGalleryViewModel(private val repository: ReportRepository,private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _approvedReports = MutableStateFlow<List<Report>>(emptyList())
    val approvedReports: StateFlow<List<Report>> get() = _approvedReports


    private val _newsList = MutableStateFlow<List<CardItem>>(emptyList())
    val newsList: StateFlow<List<CardItem>> = _newsList.asStateFlow()


    fun getNewsList() {
        println("Fetching news list...")
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
            val response = repository.approvedReports(jwtToken)
            if (response?.success == true) {
                val reports = response.data ?: emptyList()
                _approvedReports.value = reports

                val mappedNews = reports.mapIndexed { index, report ->
                    CardItem(
                        cardId = report._id ?: "report_$index",
                        title = "Accident - ${report.vehicleNo ?: "Unknown"}",
                        date = report.date ?: "Unknown Date",
                        location = report.address ?: "Unknown Location",
                        locationUrl = report.location?.joinToString(",") ?: "",
                        vehiclenub = report.vehicleNo ?: "N/A",
                        description = report.description ?: "No description",
                        imageUrl = getDefaultImageForReport(index),
                        accentColor = if (index % 2 == 0) Color.Green else Color.Blue,
                        imagePlaceholderColor = Color.LightGray
                    )
                }

                _newsList.value = mappedNews
                println(">>> News list size: ${mappedNews.size}")
            } else {
                println("Failed to fetch news: ${response?.message ?: "Unknown error"}")
            }
        }
    }
    private fun getDefaultImageForReport(index: Int): String {
        return when (index % 3) {
            0 -> "https://cdn.pixabay.com/photo/2016/03/27/22/16/accident-1282330_1280.jpg"
            1 -> "https://images.unsplash.com/photo-1494526585095-c41746248156"
            else -> "https://cdn.pixabay.com/photo/2017/04/06/09/00/car-2209439_1280.jpg"
        }
    }


    init {
        getNewsList()
    }

}
