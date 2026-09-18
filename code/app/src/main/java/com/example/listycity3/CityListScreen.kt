package com.example.listycity3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var cityName by remember { mutableStateOf("") }
    var provinceName by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("City Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = provinceName,
            onValueChange = { provinceName = it },
            label = { Text("Province") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(
                onClick = {
                    if (cityName.isNotBlank() && provinceName.isNotBlank()) {
                        onAddCity(City(cityName, provinceName))
                        cityName = ""
                        provinceName = ""
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            ) {
                Text("Add City")
            }

            Button(
                onClick = {
                    val currentSelected = selectedCity
                    if (currentSelected != null && cityName.isNotBlank() && provinceName.isNotBlank()) {
                        onUpdateCity(currentSelected, City(cityName, provinceName))
                        selectedCity = null
                        cityName = ""
                        provinceName = ""
                    }
                },
                enabled = selectedCity != null,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                Text("Update City")
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    isSelected = selectedCity == city,
                    onCityClick = {
                        selectedCity = it
                        cityName = it.name
                        provinceName = it.province
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onCityClick: (City) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCityClick(city) }
            .background(if (isSelected) Color(0xFFD6E9FF) else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ -> }
        )
    }
}
