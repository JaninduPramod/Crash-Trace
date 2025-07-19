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
                    title = "Mock News Title 1",
                    description = "This is the description for the first mock news item. It's quite interesting 111.",

                    //image = "https://example.com/image1.jpg",
                    accentColor = Color.Red,
                    imagePlaceholderColor = Color.Gray,

                ),
                CardItem(
                    cardId = "mock2",
                    title = "Second Mock Article",
                    description = "Another piece of mock news to display in the gallery for testing purposes.",
                    //image = "https.example.com/image2.jpg",
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray,
                ),
                CardItem(
                    cardId = "mock3",
                    title = "Mock Data Showcase",
                    description = "The final mock item demonstrating the list.",
                    //image = "https.example.com/image3.jpg",
                    accentColor = Color.Green,
                    imagePlaceholderColor = Color.DarkGray,
                )
            )
            _newsList.value = mockData
        }
    }
}
