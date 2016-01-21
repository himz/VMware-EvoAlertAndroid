package com.vmware.evorack.evoalert.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.vmware.evorack.evoalert.utils.AlertHelperFunctions;
import com.vmware.evorack.evoalert.utils.App;
import com.vmware.evorack.evoalert.R;
import com.vmware.evorack.evoalert.adapters.TrendingRecyclerViewAdapter;
import com.vmware.evorack.evoalert.model.AlertItem;
import com.vmware.evorack.evoalert.model.DummyContent;
import com.vmware.evorack.evoalert.network.RestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTrendingFragmentInteractionListener}
 * interface.
 */
public class TrendingFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String LOCALHOST_SERVER_URL = "http://10.0.2.2:8080/rest/get/alerts";
    private static final String REAL_SERVER_URL = "http://10.160.109.11:8080/rest/get/alerts";

    //Change debug mode to false for real server url
    private static final boolean DEBUG_MODE = true;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnTrendingFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TrendingRecyclerViewAdapter(DummyContent.ITEMS, mListener));

        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTrendingFragmentInteractionListener) {
            mListener = (OnTrendingFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTrendingFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /* Function to refresh the item list*/
    public void updateItemList() {
        String url;
        if(DEBUG_MODE)
            url = LOCALHOST_SERVER_URL;
        else
            url = REAL_SERVER_URL;
       /* RestHandler restHandler = new RestHandler();*/
        String response = "";
        //new RetrieveLatestAlert().execute(SERVER_URL);
        new GetAllAlert().execute(url);


    }


    class RetrieveLatestAlert extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String url = urls[0];
            try {
                //final String SERVER_URL = "http://10.0.2.2:8080/rest/get/alert";
                //final String SERVER_URL = "http://10.160.109.11:8080/rest/get/alerts";

                String response = "";
                RestHandler restHandler = new RestHandler();
                response = restHandler.getJsonData(url);
                return response;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(final String response) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    View ll = getView();
                    RecyclerView recyclerView = (RecyclerView) ll;

                    TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter) recyclerView.getAdapter();
                    AlertItem newAlertItem = new AlertItem(response, response, "details for 26");
                    adapter.addItem(newAlertItem);
                    adapter.notifyDataSetChanged();
                }
            });

            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }


    class GetAllAlert extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String url = urls[0];
            try {
                //final String SERVER_URL = "http://10.0.2.2:8080/rest/get/alerts";
                System.out.println("Using url" + url);
                String response = "";
                RestHandler restHandler = new RestHandler();
                response = restHandler.getJsonData(url);
                return response;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(final String response) {
            if (response == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    View ll = getView();
                    RecyclerView recyclerView = (RecyclerView) ll;
                    try {
                        TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter) recyclerView.getAdapter();
                        System.out.println(response);
                        App.globalAlertJsonString = response;
                        JSONArray ja = new JSONArray(response);
                        //Create global alertjson objectlist
                        for (int i = 0; i < ja.length(); i++) {
                            App.globalAlertJsonObjectList.add(ja.getJSONObject(i));
                        }
                        int counter = 0;
                        // Create alertItem list from the global alert json object list
                        App.globalAlertItemList = AlertHelperFunctions.getAlertItemList();

                        // clear current adapter
                        adapter.clearList();

                        // Add all the alerts from the the alert item list to the adapter
//                        AlertItem tempAlertItem = new AlertItem("26", "Content 26", "Details 26");
                        for (AlertItem ai : App.globalAlertItemList) {
                            adapter.addItem(ai);
                        }
                      //  adapter.addItem(tempAlertItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter) recyclerView.getAdapter();
  //                  AlertItem newAlertItem = new AlertItem("27", "27", "details for 26");
    //                adapter.addItem(newAlertItem);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTrendingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTrendingFragmentInteraction(AlertItem item);
    }
}
