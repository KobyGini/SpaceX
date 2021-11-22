package com.example.spacex.data.local.launchdao

import androidx.room.TypeConverter
import com.example.spacex.model.Ship
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataTypeConvertor {

    @TypeConverter
    fun fromShipsListToJson(list:List<Ship>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToList(value : String) : List<Ship> = Gson().fromJson(value, object : TypeToken<List<Ship>>() {}.type)

}