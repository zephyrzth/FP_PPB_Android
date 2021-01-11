package com.example.sejamu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 */
public class BelanjaFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 2;
    private ApiInterface mApiInterface;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BelanjaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BelanjaFragment newInstance(int columnCount) {
        BelanjaFragment fragment = new BelanjaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_belanja_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        Call<GetItem> getPhotoCall = mApiInterface.listItem();
        getPhotoCall.enqueue(new Callback<GetItem>() {
            @Override
            public void onResponse(Call<GetItem> call, Response<GetItem> response) {
                if (response.isSuccessful()) {
                    GetItem listResult = response.body();

                    Log.d(MainActivity.DEBUG_TAG, listResult.getListDataItem().get(0).getPath());

                    MyItemRecyclerViewAdapter itemAdapter = new MyItemRecyclerViewAdapter(getActivity(),
                            listResult.getListDataItem());
                    recyclerView.setAdapter(itemAdapter);
                } else {
                    try {
                        String responseBodyString = response.errorBody().string();
                        Log.d(MainActivity.DEBUG_TAG, responseBodyString);
                        JSONObject jsonObject = new JSONObject(responseBodyString);

                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d(MainActivity.DEBUG_TAG, "Error Body JSON: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetItem> call, Throwable t) {
                Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                Log.d(MainActivity.DEBUG_TAG, "Error Retrofit List: " + t.getMessage());
            }
        });
    }
}