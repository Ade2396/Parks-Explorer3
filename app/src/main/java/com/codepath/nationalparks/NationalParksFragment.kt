package com.codepath.nationalparks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

class NationalParksFragment : Fragment() {

    companion object {
        // <-- put your real key here
        private const val API_KEY = "bB507gMIfTXkLlSVlzPDxy1FUiveKhP4s2FEBbNM"

        fun newInstance(columnCount: Int): NationalParksFragment = NationalParksFragment()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // IMPORTANT: inflate the LIST layout (fragment_national_parks_list.xml)
        return inflater.inflate(R.layout.fragment_national_parks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // These IDs must match the ones in fragment_national_parks_list.xml below
        recyclerView = view.findViewById(R.id.list)
        progressBar = view.findViewById(R.id.progress)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchParks()
    }

    private fun fetchParks() {
        progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        val params = RequestParams().apply {
            this["api_key"] = API_KEY
            this["limit"] = "50"
        }

        client["https://developer.nps.gov/api/v1/parks", params,
            object : JsonHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    progressBar.visibility = View.GONE

                    val data: JSONArray = json.jsonObject.get("data") as JSONArray
                    val raw = data.toString()

                    val gson = Gson()
                    val listType = object : TypeToken<List<NationalPark>>() {}.type
                    val models: List<NationalPark> = gson.fromJson(raw, listType)

                    recyclerView.adapter = NationalParksRecyclerViewAdapter(
                        items = models,
                        listener = object : OnListFragmentInteractionListener {
                            override fun onItemClick(item: NationalPark) {
                                // simple action so it compiles & runs
                                Log.d("NationalParksFragment", "Clicked: ${item.name}")
                                // (Optional) Toast:
                                // Toast.makeText(requireContext(), item.name ?: "Unknown", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )


                    Log.d("NationalParksFragment", "response successful")
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    progressBar.visibility = View.GONE
                    Log.e("NationalParksFragment", "request failed: $statusCode\n$response")
                }
            }
        ]
    }
}
