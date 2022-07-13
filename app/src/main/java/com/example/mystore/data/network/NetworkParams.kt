package com.example.mystore.data.network

class NetworkParams {

    companion object{
        const val CONSUMER_KEY = "ck_63f4c52da932ddad1570283b31f3c96c4bd9fd6f"
        const val CONSUMER_SECRET = "cs_294e7de35430398f323b43c21dd1b29f67b5370b"

        fun getBaseOptions() : Map<String,String>{
            val optionHashMap = HashMap<String, String>()
            optionHashMap["consumer_key"] = CONSUMER_KEY
            optionHashMap["consumer_secret"] = CONSUMER_SECRET
            return optionHashMap
        }


    }
}