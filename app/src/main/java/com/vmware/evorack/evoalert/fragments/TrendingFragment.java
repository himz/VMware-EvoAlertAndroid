package com.vmware.evorack.evoalert.fragments;

import android.content.Context;
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
        final String SERVER_URL = "http://localhost:8080/rest/get/alert";
        String response = "";
        RestHandler restHandler = new RestHandler();
        response = restHandler.getJsonData(SERVER_URL);
        RecyclerView recyclerView = (RecyclerView)this.getView().findViewById(R.id.list);
        TrendingRecyclerViewAdapter adapter = (TrendingRecyclerViewAdapter)recyclerView.getAdapter();

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
