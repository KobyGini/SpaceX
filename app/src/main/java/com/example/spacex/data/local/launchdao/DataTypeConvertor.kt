package com.example.spacex.data.local.launchdao

import androidx.room.TypeConverter
import com.example.spacex.model.Ship
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataTypeConvertor {

    @TypeConverter
    fun fromShipsListToJson(list:List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun fromJsonToList(value : String) : List<String> = Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)

}