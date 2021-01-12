package com.example.sejamu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApiInterface mApiInterface;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserItem.MY_TOKEN != null && !UserItem.MY_TOKEN.isEmpty()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<PostPutDelUser> postUserCall = mApiInterface.whoami(UserItem.MY_TOKEN);
            postUserCall.enqueue(new Callback<PostPutDelUser>() {
                @Override
                public void onResponse(Call<PostPutDelUser> call, Response<PostPutDelUser> response) {
                    if (response.isSuccessful()) {
                        PostPutDelUser result = response.body();
                        Log.d(MainActivity.DEBUG_TAG, result.getDataItem().getNama());
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
                public void onFailure(Call<PostPutDelUser> call, Throwable t) {
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_LONG).show();
                    Log.d(MainActivity.DEBUG_TAG, "Error ProfilFragment: " + t.getMessage());
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton location = view.findViewById(R.id.btLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btLocation) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}