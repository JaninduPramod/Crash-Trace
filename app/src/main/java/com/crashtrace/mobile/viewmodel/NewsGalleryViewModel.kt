package com.crashtrace.mobile.viewmodel


import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.ui.components.CardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsGalleryViewModel : ViewModel() {


    private val _newsList = MutableStateFlow<List<CardItem>>(emptyList())
    val newsList: StateFlow<List<CardItem>> = _newsList.asStateFlow()

    init {
        loadMockNews() // Load mock data when ViewModel is created
    }

    private fun loadMockNews() {
        viewModelScope.launch {

            val mockData = listOf(
                CardItem(
                    cardId = "mock1",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
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
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
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
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
                    vehiclenub = "azy-1234",
                    description = "The final mock item demonstrating the list.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
                ) ,
                CardItem(
                        cardId = "mock1",
                    title = "Mock News Title 4",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
                    vehiclenub = "azy-1234",
                    description = "This is the description for the first mock news item. It's quite interesting 111.",
                    imageUrl = "",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
            ),
            CardItem(
                    cardId = "mock2",
                    title = "Second Mock Article5",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
                    vehiclenub = "azy-1234",
                    description = "Another piece of mock news to display in the gallery for testing purposes.",
                    imageUrl = "https://images.unsplash.com/photo-1494526585095-c41746248156",
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray,
            ),
            CardItem(
                    cardId = "mock3",
                    title = "Mock Data Showcase6",
                    date = "2023-10-01",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
                    vehiclenub = "azy-1234",
                    description = "The final mock item demonstrating the list.",
                    imageUrl = "",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
            )


            )
            _newsList.value = mockData
        }
    }
}
