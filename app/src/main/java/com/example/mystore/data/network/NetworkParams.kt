package com.example.mystore.data.network

class NetworkParams {

    companion object{
        const val CONSUMER_KEY = "ck_6b55bb0ff3ea0b7bf4c0aa879af50061964ce38f"
        const val CONSUMER_SECRET = "cs_9b09e5125acffdd27bbe72843ced49db5f8bffb4"

        fun getBaseOptions() : Map<String,String>{
            val optionHashMap = HashMap<String, String>()
            optionHashMap["consumer_key"] = CONSUMER_KEY
            optionHashMap["consumer_secret"] = CONSUMER_SECRET
            return optionHashMap
        }


    }
}