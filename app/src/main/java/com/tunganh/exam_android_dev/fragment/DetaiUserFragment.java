package com.tunganh.exam_android_dev.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tunganh.exam_android_dev.R;
import com.tunganh.exam_android_dev.adapter.DetailAdapter;
import com.tunganh.exam_android_dev.adapter.UserAdapter;
import com.tunganh.exam_android_dev.common.Common;
import com.tunganh.exam_android_dev.model.DetailModel;
import com.tunganh.exam_android_dev.model.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetaiUserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.layout_section_1)
    RelativeLayout layoutSection1;
    @BindView(R.id.img_section_1)
    ImageView imgSection_1;
    @BindView(R.id.layout_name)
    LinearLayout layoutName;
    @BindView(R.id.layout_company)
    LinearLayout layoutCompany;
    @BindView(R.id.layout_location)
    LinearLayout layoutLocation;
    @BindView(R.id.layout_blog)
    LinearLayout layoutBlog;
    @BindView(R.id.img_section_2)
    ImageView imgSection_2;
    @BindView(R.id.layout_section_2)
    LinearLayout layoutSection2;


    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_company_user)
    TextView tvCompanyUser;
    @BindView(R.id.tv_location_user)
    TextView tvLocationUser;
    @BindView(R.id.tv_blog_user)
    TextView tvBlogUser;
    @BindView(R.id.tv_fl_1)
    TextView tvFollow_1;
    @BindView(R.id.tv_fl_2)
    TextView tvFollow_2;

    private ProgressDialog loading;
    private String url;
    private String follower_url;
    private String location;
    private String blog;
    private String company;
    private String name;
    boolean isHideSection1 = false;
    boolean isHideSection2 = false;
    private int follower_from_url;

    @BindView(R.id.rcv_detail)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<DetailModel> arrayList;
    private DetailAdapter adapter;
    private int count = 0;

    public DetaiUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detai_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            url = getArguments().getString(Common.url);
            follower_url = getArguments().getString(Common.followers_url);
        }

        ButterKnife.bind(this, view);
        imgSection_1.setOnClickListener(this);
        imgSection_2.setOnClickListener(this);

        loading = new ProgressDialog(getContext());
        loading.show();


        arrayList = new ArrayList<>();

        callApiSection1();
        callApiSection2();


    }

    // get value from api
    private void callApiSection2() {
        if (getContext() == null)
            return;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, follower_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Convert json array to jsonobject
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                DetailModel model = new DetailModel();
                                model.setLogin(item.optString(Common.login, ""));;
                                model.setAvatarUrl(item.optString(Common.avatar_url,""));

                                arrayList.add(model);
                                count = count + 1;
                            }

                            adapter = new DetailAdapter(arrayList,getContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                            updateUI();


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

    // get value from api
    private void callApiSection1() {
        if (getContext() == null)
            return;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Convert json array to jsonobject

                        Log.d(Common.TAG, response.toString());

                        company = response.optString("company", "");
                        blog = response.optString("blog", "");
                        location = response.optString("location", "");
                        name = response.optString("name", "").toString();
                        follower_from_url = response.optInt("followers", -1);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Log.d(Common.TAG, error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // -1
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private void updateUI() {

        if (getActivity() == null)
            return;

        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (name != null && !name.isEmpty() && !name.equals("null")) {
                    tvNameUser.setText(name);
                } else {
                    layoutName.setVisibility(View.GONE);
                }

                if (blog != null && !blog.isEmpty() && !blog.equals("null")) {
                    tvBlogUser.setText(blog);
                } else {
                    layoutBlog.setVisibility(View.GONE);
                }

                if (company != null && !company.isEmpty() && !company.equals("null")) {
                    tvCompanyUser.setText(company);
                } else {
                    layoutCompany.setVisibility(View.GONE);
                }

                if (location != null && !location.isEmpty() && !location.equals("null")) {
                    tvLocationUser.setText(location);
                } else {
                    layoutLocation.setVisibility(View.GONE);
                }


                tvFollow_1.setText(getResources().getString(R.string.follower_from_url )+ follower_from_url);
                tvFollow_2.setText(getResources().getString(R.string.follower_from_followers_url ) + count);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_section_1:
                if (isHideSection1) {
                    expand(layoutSection1);
                    isHideSection1 = false;
                    imgSection_1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_down));
                } else {
                    collapse(layoutSection1);
                    isHideSection1 = true;
                    imgSection_1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_up));
                }
                break;

            case R.id.img_section_2:
                if (isHideSection2) {
                    expand(layoutSection2);
                    isHideSection2 = false;
                    imgSection_2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_down));
                } else {
                    collapse(layoutSection2);
                    isHideSection2 = true;
                    imgSection_2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_up));
                }
                break;
        }
    }

    // expand view

    public void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(1);
        v.startAnimation(a);
    }

    // collapse view
    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(1);
        v.startAnimation(a);
    }


}
