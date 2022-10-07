package edu.uchicago.brmarcus.fetchassessment.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.uchicago.brmarcus.fetchassessment.data.Item
import edu.uchicago.brmarcus.fetchassessment.data.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

class MainViewModel(private val repository: Repository): ViewModel() {

    //encapsulate itemlist by making it private
    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList


    val errorMessage = MutableLiveData<String>()

    fun getAllItems() {
        //uses the repo to get all of the items
        repository.getItems()?.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                var allItems = response.body()
                var nonNullItems = ArrayList<Item>()
                //filters out the items with a null or "" name
                if (allItems != null) {
                    nonNullItems = getNonNullItems(allItems)
                }
                //sorts the items first by listID then by the int in the name and adds them to the itemlist
                _itemList.postValue(customSortItems(nonNullItems))
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                t.message?.let { Log.d("FAILURE", it) }
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getNonNullItems(allItems:List<Item>): ArrayList<Item> {
        var nonNullItems = ArrayList<Item>()
        for (item in allItems) {
            if ((item.name != null) && (item.name != "")) {
                nonNullItems.add(item)
            }
        }
        return nonNullItems
    }

    //start with secondary sort on the number in the name based on instructions
    //fallback to secondary sort on the listId if the name isn't in the expected format
    fun customSortItems(items: ArrayList<Item>): List<Item> {
        return items.sortedWith(compareBy( {it.listId}, {
            try {
                it.name!!.split(" ")[1].toInt()
            } catch (e: NumberFormatException) {
                it.listId
            }}))
    }
}

class MyViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}