package com.example.retrofit_retry_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.retrofit_retry_1.network.apiClient
import com.example.retrofit_retry_1.upcomingevents.data.model.ApiBranchData
import com.example.retrofit_retry_1.upcomingevents.data.model.ApiEventData_competitions

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var responseTextView: TextView
lateinit var responseButton: Button

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiClient.upcomingEvents().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage ?: "occurred failure")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseBody: ResponseBody = response.body()!!
                val responseJsonString: String = responseBody.string()
                val responseJsonObject = JSONObject(responseJsonString)

                val apiBranchDataList = parseBranchesJsonObject(responseJsonObject)

                Log.d("ParsingResult", apiBranchDataList.toString())
            }

        })

    }

    private fun parseBranchesJsonObject(
            responseJSONObject: JSONObject
    ): List<ApiBranchData> {
        val branclist = mutableListOf<ApiBranchData>()

        for (index in 0 until responseJSONObject.length()) {
            val branchJsonObject = (responseJSONObject[index.toString()] as? JSONObject) ?: continue


            val apiBranchData = parseBranchJsonObject(branchJsonObject)

            branclist.add(apiBranchData)

        }
        return branclist
    }

    private fun parseBranchJsonObject(
            branchJsonObject: JSONObject
    ) : ApiBranchData {

        val id = branchJsonObject.getInt("id")
        val eventJsonArray = branchJsonObject.getJSONArray("events")
        val apiEventsList = mutableListOf<ApiEventData_competitions>()


        for (index in 0 until eventJsonArray.length()){
            val eventsJsonObject = (eventJsonArray[index] as? JSONObject) ?: continue




            val apiEventData = parseEventJsonObject(eventsJsonObject)

            apiEventsList.add(apiEventData)
        }
        return ApiBranchData(
                id = id
//                events = apiEventsList
        )
    }

private fun parseEventJsonObject(
        eventJsonObject: JSONObject
): ApiEventData_competitions {
        val id = eventJsonObject.getInt("id")

    return ApiEventData_competitions(
            id = id,
            area = null
    )
}
//
}