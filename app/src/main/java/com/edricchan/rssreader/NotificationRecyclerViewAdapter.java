package com.edricchan.rssreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edricchan.rssreader.NotificationFragment.OnListFragmentInteractionListener;
import com.edricchan.rssreader.object.NotificationItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NotificationItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private final List<NotificationItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public NotificationRecyclerViewAdapter(List<NotificationItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).title);
        holder.mMessageView.setText(mValues.get(position).message);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mTitleView;
        public final TextView mMessageView;
        public NotificationItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.notification_list_item_title);
            mMessageView = (TextView) view.findViewById(R.id.notification_list_item_message);
        }
    }
}
