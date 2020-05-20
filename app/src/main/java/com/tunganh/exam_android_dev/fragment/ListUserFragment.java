package com.tunganh.exam_android_dev.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tunganh.exam_android_dev.R;
import com.tunganh.exam_android_dev.adapter.UserAdapter;
import com.tunganh.exam_android_dev.common.Common;
import com.tunganh.exam_android_dev.model.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUserFragment extends Fragment{

    @BindView(R.id.rcv)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog loading;
    private ArrayList<UserModel> arrayList;
    private UserAdapter adapter;

    public ListUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        arrayList = new ArrayList<>();
        loading = new ProgressDialog(getContext());
        loading.show();

        callApi();
        adapter = new UserAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    // get list user from api
    private void callApi() {

        if (getContext() == null)
            return;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Common.JSON_1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                UserModel model = new UserModel();
                                model.setLogin(item.optString(Common.login, ""));;
                                model.setAvatarUrl(item.optString(Common.avatar_url,""));
                                model.setId(item.optInt(Common.id,-1));
                                model.setNodeId(item.optString(Common.node_id,""));
                                model.setGravatarId(item.optString(Common.gravatar_id,""));
                                model.setUrl(item.optString(Common.url,""));
                                model.setHtmlUrl(item.optString(Common.html_url,""));
                                model.setFollowersUrl(item.optString(Common.followers_url,""));
                                model.setFollowingUrl(item.optString(Common.following_url,""));
                                model.setGistsUrl(item.optString(Common.gists_url,""));
                                model.setStarredUrl(item.optString(Common.starred_url,""));
                                model.setSubscriptionsUrl(item.optString(Common.subscriptions_url,""));
                                model.setOrganizationsUrl(item.optString(Common.organizations_url,""));
                                model.setReposUrl(item.optString(Common.repos_url,""));
                                model.setEventsUrl(item.optString(Common.events_url,""));
                                model.setReceivedEventsUrl(item.optString(Common.received_events_url,""));
                                model.setType(item.optString(Common.type,""));
                                model.setSiteAdmin(item.optBoolean(Common.site_admin,false));

                                arrayList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            Log.d(Common.TAG, ex.toString());
                            Toast.makeText(getContext(), "" + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(Common.TAG, error.toString());
                    }
                });

        requestQueue.add(request);
    }

}



