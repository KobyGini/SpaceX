package com.example.spacex.repository

import com.example.spacex.data.local.model.LaunchLocal

import com.google.common.truth.Truth
import org.junit.Test

class RepositoryTest {

    @Test
    fun updateContents(){

        val launch = ArrayList<LaunchLocal>()
        val ships = listOf("4324239485234")
        launch.add(LaunchLocal("1","","","","","",ships))
        launch.add(LaunchLocal("2","","","","","",ships))
        launch.add(LaunchLocal("3","","","","","",ships))
        launch.add(LaunchLocal("4","","","","","",ships))
        launch.add(LaunchLocal("5","","","","","",ships))
        launch.add(LaunchLocal("6","","","","","",ships))

        val launchRemoveList = ArrayList<LaunchLocal>()
        launchRemoveList.add(LaunchLocal("1","","","","","",ships))

        val newList = launch.filter {
            it in launchRemoveList
        }

        Truth.assertThat(newList.size).isEqualTo(1)
    }

    @Test
    fun removeElementFromList(){
        val mutableList: MutableList<String> = mutableListOf("1", "4", "9", "16", "25")
        val isChanged = mutableList.remove("9")
        Truth.assertThat(isChanged).isTrue()
    }

    @Test
    fun removeArrayElementFromList(){
        val arrayList = ArrayList<String>()
        arrayList.add("1")
        arrayList.add("2")
        arrayList.add("3")
        arrayList.add("4")

        val removedArraylist = ArrayList<String>()
        removedArraylist.add("1")
        val isChanged = arrayList.toHashSet().remove("2")
//        Truth.assertThat(arrayList.size).isEqualTo(3)
//        Truth.assertThat(isChanged).isTrue()

        val newArrayList = arrayList.filter {
            it in removedArraylist
        }

        Truth.assertThat(newArrayList.size).isEqualTo(1)

    }


}