package com.srikanth.covid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CountriesFragment extends Fragment {

    public CountriesFragment()
    {

    }
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

private EditText search;
    private ArrayList<Item> mitemList;
    private RequestQueue mRequestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.activity_frag_countries,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mitemList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        search=(EditText)rootView.findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
        AparseJson();

        return rootView;


    }
private void filter(String country)
{
    ArrayList<Item> filteredList=new ArrayList<>();
    for (Item item:mitemList)
    {
        if (item.getCountry().toLowerCase().contains(country.toLowerCase()))
        {
            filteredList.add(item);
        }
    }
    mAdapter.filterList(filteredList);
}



    private void AparseJson() {

        String url = "https://coronavirus-tracker-api.herokuapp.com/v2/locations";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            JSONArray jsonArray=response.getJSONArray("locations");

                            for (int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject c=jsonArray.getJSONObject(i);
                                String country=c.getString("country");
                                JSONObject latest = c.getJSONObject("latest");
                                String con = latest.getString("confirmed");
                                String dea = latest.getString("deaths");
                                String rec = latest.getString("recovered");
                                String pro=c.getString("province");

                                mitemList.add (new Item(country,con,dea,rec,pro));
                            }


                            mAdapter=new Adapter(getActivity(),mitemList);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mRequestQueue.add(request);
    }

}
