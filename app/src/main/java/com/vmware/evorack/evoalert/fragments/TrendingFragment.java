package com.vmware.evorack.evoalert.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vmware.evorack.evoalert.R;
import com.vmware.evorack.evoalert.adapters.TrendingRecyclerViewAdapter;
import com.vmware.evorack.evoalert.dummy.DummyContent;
import com.vmware.evorack.evoalert.network.RestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnTrendingFragmentInteractionListener}
 * interface.
 */
public class TrendingFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_SECTION_NUMBER = "section_number";
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
            // Send the event to the host activity
            mListener.onTrendingFragmentInteraction(new DummyContent.DummyItem("1trending", "content1trending", "details1"));

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
        final String SERVER_URL = "http://10.0.2.2:8080/rest/get/alerts";
       /* RestHandler restHandler = new RestHandler();*/
        String response = "";
        //new RetrieveLatestAlert().execute(SERVER_URL);
        new GetAllAlert().execute(SERVER_URL);


    }


    class RetrieveLatestAlert extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String url = urls[0];
            try {
                final String SERVER_URL = "http://10.0.2.2:8080/rest/get/alert";
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
                    DummyContent.DummyItem newDummyItem = new DummyContent.DummyItem(response, response, "details for 26");
                    adapter.addItem(newDummyItem);
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
                final String SERVER_URL = "http://10.0.2.2:8080/rest/get/alerts";
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
                    try {
                        JSONArray ja = new JSONArray(response);
                        DummyContent.DummyItem tempDummyItem = new DummyContent.DummyItem("26","Content 26","Details 26");
                        TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter) recyclerView.getAdapter();
                        adapter.clearList();
                        adapter.addItem(tempDummyItem);
                        for(int i =0; i < ja.length(); i++) {
                            adapter.addItem(new DummyContent.DummyItem(ja.getJSONObject(i).toString(),"asdf", "sadfasd"));
                        }
/*                      This code is to replace the current adapter -- which is an overkill
                        for(int i = 0; i < ja.length(); i++){
                            di.add(new DummyContent.DummyItem(ja.getJSONObject(i).toString(),"asdf", "sadfasd"));
                        }
                        adapter.notifyDataSetChanged();
/*
                        TrendingRecyclerViewAdapter adapter = new TrendingRecyclerViewAdapter(di, mListener);
                        ((RecyclerView) ll).setAdapter(adapter);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter) recyclerView.getAdapter();
                    DummyContent.DummyItem newDummyItem = new DummyContent.DummyItem("27", "27", "details for 26");
                    adapter.addItem(newDummyItem);
                    adapter.notifyDataSetChanged();
                }
            });

            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTrendingFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTrendingFragmentInteraction(DummyContent.DummyItem item);
    }
}
