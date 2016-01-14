package com.vmware.evorack.evoalert.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.vmware.evorack.evoalert.R;
import com.vmware.evorack.evoalert.model.AlertItem;
import com.vmware.evorack.evoalert.fragments.TrendingFragment;
import com.vmware.evorack.evoalert.utils.App;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AlertItem} and makes a call to the
 * specified {@link TrendingFragment.OnTrendingFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<TrendingRecyclerViewAdapter.ViewHolder> {

    private final List<AlertItem> mValues;
    private final TrendingFragment.OnTrendingFragmentInteractionListener mListener;

    public TrendingRecyclerViewAdapter(List<AlertItem> items, TrendingFragment.OnTrendingFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTrendingFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;

        public AlertItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);

        }

        @Override
        public String toString() {
            return super.toString() + "'";
        }
    }
    /* Add the latest item at the top -- stack */
    public void addItem(AlertItem item) {
        mValues.add(0,item);
    }

    public void clearList() {
        mValues.clear();
    }


}
