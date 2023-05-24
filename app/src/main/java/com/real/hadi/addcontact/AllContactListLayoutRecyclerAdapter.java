package com.real.hadi.addcontact;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AllContactListLayoutRecyclerAdapter extends RecyclerView.Adapter<AllContactListLayoutRecyclerAdapter.NewsViewHolder> {
    ArrayList<AllContactListLayout> digital;
    RecyclerView recyclerView;
    ArrayList<AllContactListLayout> sampleNews;
    private Context context;
    private int lastPosition = -1;
    ArrayList<String> namelist;
    ArrayList<String> lastnamelist;
    ArrayList<String> phonelist;
    JSONArray jsonArray;
    List<EditText> list;
    List<EditText> list1;
    String url = R.string.url +"/api/people";
    String userId, phone, NumberExist = null;

    private final SparseBooleanArray array = new SparseBooleanArray();


    public AllContactListLayoutRecyclerAdapter(ArrayList<AllContactListLayout> phonelap) {
        digital = new ArrayList<>();
        digital = phonelap;
        namelist = new ArrayList<>();
        lastnamelist = new ArrayList<>();
        phonelist = new ArrayList<>();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allcontactlistlayout, parent, false);
        context = parent.getContext();
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {

        final AllContactListLayout dataModel = digital.get(position);

        holder.txtName.setText(dataModel.getName());
//        holder.txtFamily.setText(dataModel.getLastname());
        if (dataModel.getPhone().length() >= 3) {
            if (dataModel.getPhone().substring(0, 3).equals("+98")) {
                String s = dataModel.getPhone().replace("+98", "0");
                holder.txtPhone.setText(s);
            }
            if (dataModel.getPhone().substring(0, 2).equals("98")) {
                String s = dataModel.getPhone().replace("98", "0");
                holder.txtPhone.setText(s);
            }
            if (dataModel.getPhone().substring(0, 3).equals("+98") && dataModel.getPhone().contains(" ")) {
                String s = dataModel.getPhone().replace("+98", "0");
                String d=s.replace(" ","");
                holder.txtPhone.setText(d);
            }
            if (dataModel.getPhone().substring(0, 2).equals("98") && dataModel.getPhone().contains(" ")) {
                String s = dataModel.getPhone().replace("98", "0");
                String d=s.replace(" ","");
                holder.txtPhone.setText(d);
            }
            if (dataModel.getPhone().substring(0, 2).equals("09") || dataModel.getPhone().substring(0, 1).equals("9")) {
                holder.txtPhone.setText(dataModel.getPhone());
            }
            if (dataModel.getPhone().substring(0, 2).equals("09") || dataModel.getPhone().substring(0, 1).equals("9")) {
                if (dataModel.getPhone().contains(" ")) {
                    String s=dataModel.getPhone().replace(" ","");
                    holder.txtPhone.setText(s);
                }
            }
        }

        setAnimation(holder.itemView, position);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                SharedPreferences pref = view.getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                userId = pref.getString("Id", null); // getting String
                final JSONArray json1 = new JSONArray();

                StringRequest request1 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        holder.imgCheck.setVisibility(View.VISIBLE);
                        holder.btnDeselect.setVisibility(View.VISIBLE);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "شماره قبلا ثبت شده است", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        int mStatusCode = response.statusCode;
                        return super.parseNetworkResponse(response);
                    }

                    Map<String, String> params = new HashMap<String, String>();

                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        String name = dataModel.getName();
                        String[] name1 = name.split(" ");
                        if (name1.length == 1) {
                            params.put("firstname", name1[0]);
                            params.put("lastname", name1[0]);
                        } else if (name1.length == 2) {
                            params.put("firstname", name1[0]);
                            params.put("lastname", name1[1]);
                        } else if (name1.length == 3) {
                            params.put("firstname", name1[0]);
                            params.put("lastname", name1[1] + " " + name1[2]);

                        } else if (name1.length == 4) {
                            params.put("firstname", name1[0] + " " + name1[1]);
                            params.put("lastname", name1[2] + " " + name1[3]);

                        }
                        params.put("phone", dataModel.getPhone());
                        params.put("education", "");
                        params.put("job", "");
                        params.put("workAddress", "");
                        params.put("birthdate", "");
                        params.put("district", "");
                        params.put("fallowUp", json1.toString());
                        params.put("userId", userId);

                        return params;
                    }
                };


                request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue1 = Volley.newRequestQueue(view.getContext());
                requestQueue1.add(request1);
            }
//            }
        });
        holder.btnDeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, context.getString(R.string.url) + "/api/people?filter=%7B%22where%22%3A%7B%22phone%22%3A%22" + dataModel.getPhone() + "%22%7D%7D"

                        , null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                StringRequest request1 = new StringRequest(Request.Method.DELETE, context.getString(R.string.url) + "/api/people/" + id, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        holder.imgCheck.setVisibility(View.INVISIBLE);
                                        holder.btnDeselect.setVisibility(View.INVISIBLE);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }) {
                                    Map<String, String> params = new HashMap<String, String>();

                                    @Override
                                    public Map<String, String> getParams() throws AuthFailureError {
//                                        params.put("id", id);
                                        return params;
                                    }
                                };


                                request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                RequestQueue requestQueue1 = Volley.newRequestQueue(view.getContext());
                                requestQueue1.add(request1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // do something
//                        Toast.makeText(view.getContext(), error+"", Toast.LENGTH_LONG).show();

                    }
                }) {
                    Map<String, String> params = new HashMap<String, String>();

                    public Map<String, String> getParams() throws AuthFailureError {
                        params.put("phone", dataModel.getPhone());
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(request);
//                Toast.makeText(view.getContext(),id,Toast.LENGTH_SHORT).show();
//                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//                /////////////////////////////////////////////////////////////////////////////////////////////////////////
//
            }
        });
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return digital.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtFamily;
        public TextView txtPhone;
        public int id;
        Button btnAdd, btnDeselect;
        ImageView imgCheck;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtFamily = (TextView) itemView.findViewById(R.id.txtFamily);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            btnDeselect = (Button) itemView.findViewById(R.id.btnDeselect);
            imgCheck = (ImageView) itemView.findViewById(R.id.imgCheck);

        }


    }

}
