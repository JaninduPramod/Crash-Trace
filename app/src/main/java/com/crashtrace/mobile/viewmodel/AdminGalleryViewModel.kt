package com.crashtrace.mobile.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.ui.components.CardItemAdmin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminGalleryViewModel : ViewModel() {

    private val _adminNewsList = MutableStateFlow<List<CardItemAdmin>>(emptyList())
    val adminNewsList: StateFlow<List<CardItemAdmin>> = _adminNewsList.asStateFlow()


    init {
        loadMockAdminData()
    }

    private fun loadMockAdminData() {
        viewModelScope.launch {
            val mockData = listOf(
                CardItemAdmin(
                    cardId = "0024",
                    title = "Reported Accident at Main Street",
                    description = "A vehicle collision occurred around 8:30 AM. No A severe accident occurred unexpectedly at a busy intersection during morning traffic. Eyewitnesses reported multiple vehicles involved, causing significant delays. Emergency services responded promptly. Fortunately, no fatalities were confirmed. Investigation is ongoing to determine the cause. Authorities urge caution in the area and request any witnesses to come forward. injuries reported.",
                    date = "2023-11-15",
                    location = "Colombo, Sri Lanka",
                    locationUrl = "20.9271, 89.8612",
                    imageUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70",
                    vehicleNo = "WP-CBA-4567",
                    address = "Main Street, Colombo",
                    reporterId = "user_123",
                    status = "ok",
                    trustRate = 83,
                    damageRate = 38 ,
                    accentColor = Color.Red,
                    imagePlaceholderColor = Color.LightGray

                ),
                CardItemAdmin(
                    cardId = "0025",
                    title = "Hit and Run Report",
                    description = "A black van hit a motorcycle and fled the Emergency services responded promptly. Fortunately, no fatalities were confirmed. Investigation is ongoing scene.",
                    date = "2023-11-10",
                    location = "Kandy, Sri Lanka",
                    locationUrl = "7.2906, 80.6337",
                    imageUrl = "https://images.unsplash.com/photo-1549924231-f129b911e442",
                    vehicleNo = "SP-XZY-9876",
                    address = "Peradeniya Rd, Kandy",
                    reporterId = "user_789",
                    status = "pending",
                    trustRate = 55,
                    damageRate = 78 ,
                    accentColor = Color.DarkGray,
                    imagePlaceholderColor = Color.LightGray

                ),
                CardItemAdmin(
                    cardId = "0026",
                    title = "sqq",
                    description = "A severe accident occurred unexpectedly at a busy intersection during morning traffic. Eyewitnesses reported multiple vehicles involved, causing significant delays. Emergency services responded promptly. Fortunately, no fatalities were confirmed. Investigation is ongoing to determine the cause. Authorities urge caution in the area and request any witnesses to come forward..",
                    date = "2023-11-18",
                    location = "Negombo, Sri Lanka",
                    locationUrl = "7.2083, 79.8358",
                    imageUrl = "https://cdn.pixabay.com/photo/2016/03/27/22/16/accident-1282330_1280.jpg",
                    vehicleNo = "NC-7854",
                    address = "Beach Rd, Negombo",
                    reporterId = "user_456",
                    status = "bad",
                    trustRate = 24,
                    damageRate = 58 ,
                    accentColor = Color.Blue,
                    imagePlaceholderColor = Color.LightGray

                )
            )
            _adminNewsList.value = mockData
        }
    }

    fun updateItem(
        cardId: String,
        title: String,
        description: String,
        damageRate: Int,
        reporterId: String,
        vehicleNo: String,
        location: String
    ) {
        val list = adminNewsList.value.toMutableList()
        val index = list.indexOfFirst { it.cardId == cardId }
        if (index != -1) {
            val updatedItem = list[index].copy(
                title = title,
                description = description,
                damageRate = damageRate,
                reporterId = reporterId,
                vehicleNo = vehicleNo,
                location = location
            )
            list[index] = updatedItem
            _adminNewsList.value = list
        }
    }

    fun deleteItem(cardId: String) {
        val list = adminNewsList.value.toMutableList()
        val filteredList = list.filter { it.cardId != cardId }
        _adminNewsList.value = filteredList
    }


}

