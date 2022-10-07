package edu.uchicago.brmarcus.fetchassessment.data

import edu.uchicago.brmarcus.fetchassessment.data.RetrofitService.Companion.retrofitService

class Repository(retrofitService: RetrofitService) {

    fun getItems() = retrofitService?.getAllItems()
}