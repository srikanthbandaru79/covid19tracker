package com.srikanth.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

   private TextView conf, reco, deat;

   private String data = "";
  private String data1 = "";
    private String data2 = "";
    private RequestQueue mRequestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_frag_home, container, false);
        conf = (TextView) rootView.findViewById(R.id.conformed_txt);
        reco = (TextView) rootView.findViewById(R.id.recovered_txt);
        deat = (TextView) rootView.findViewById(R.id.deaths_txt);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        AparseJson();


        return rootView;


    }

    private void AparseJson() {

        String url = "https://coronavirus-tracker-api.herokuapp.com/v2/locations";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            JSONObject jsonObject = response.getJSONObject("latest");
                            String con = jsonObject.getString("confirmed");

                            String dea = jsonObject.getString("deaths");
                            String rec = jsonObject.getString("recovered");
                            data += "Confirmed : \n" + con;
                            data1 += "Deaths : " + dea;
                            data2 += "Recovered :\n " + rec;
                            conf.setText(data);
                            reco.setText(data2);
                            deat.setText(data1);

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
