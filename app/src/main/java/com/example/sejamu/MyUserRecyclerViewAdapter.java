package com.example.sejamu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<DataItem> mValues;

    public MyUserRecyclerViewAdapter(Context context, List<DataItem> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_profil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Bind Data
        holder.mJudulView.setText(String.valueOf(mValues.get(position).getJudul()));
        holder.mHargaView.setText(String.valueOf(mValues.get(position).getHarga()));

        // Bind Image
        GlideClient.downloadImage(context, ApiClient.WEB_URL + mValues.get(position).getPath(), holder.imageViewHolder);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mJudulView, mHargaView;
        public ImageView imageViewHolder;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mJudulView = (TextView) view.findViewById(R.id.tvJudul);
            mHargaView = (TextView) view.findViewById(R.id.tvHarga);
            imageViewHolder = (ImageView) view.findViewById(R.id.ivItem);
        }
    }
}