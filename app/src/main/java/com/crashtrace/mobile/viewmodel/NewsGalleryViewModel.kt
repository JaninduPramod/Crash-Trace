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
            val response = repository.approvedReports(jwtToken);
            if(response?.success == true)
            {
                _approvedReports.value = response.data ?: emptyList()
                println(_approvedReports.value)
            }
            else{
                println("Failed to fetch news: ${response?.message ?: "Unknown error"}")
            }
        }

    }


    init {
        loadMockNews() // Load mock data when ViewModel is created
    }

    private fun loadMockNews() {
        viewModelScope.launch {

            val mockData = listOf(
                CardItem(
                    cardId = "mock0",
                    title = "Second Mock Article0",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = " 20.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    description = "Another piece of mock news to display in the gallery for testing purposes.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray,
                ),

                CardItem(
                    cardId = "mock1",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "20.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    title = "Mock News Title 1",
                    description = "This is the description for the first mock news item. It's quite interesting 111.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
                ),
                CardItem(
                    cardId = "mock2",
                    title = "Second Mock Article 2",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "",
                    vehiclenub = "azy-1234",
                    description = "Another piece of mock news to display in the gallery for testing purposes.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray,
                ),
                CardItem(
                    cardId = "mock3",
                    title = "Mock Data Showcase 3",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "20.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    description = "The final mock item demonstrating the list.",
                    imageUrl = "https://cdn.pixabay.com/photo/2016/03/27/22/16/accident-1282330_1280.jpg",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
                ) ,
                CardItem(
                        cardId = "mock4",
                    title = "Mock News Title 4",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl =  "20.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    description = "This is the description for the first mock news item. It's quite interesting 111.",
                    imageUrl = "https://cdn.pixabay.com/photo/2017/04/06/09/00/car-2209439_1280.jpg",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
            ),
            CardItem(
                    cardId = "mock5",
                    title = "Second Mock Article5",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "20.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    description = "Another piece of mock news to display in the gallery for testing purposes.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray,
            ),
            CardItem(
                    cardId = "mock6",
                    title = "Mock Data Showcase6",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "22.9271, 89.8612",
                    vehiclenub = "azy-1234",
                    description = "The final mock item demonstrating the list.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
            )


            )
            _newsList.value = mockData
        }
    }
}
