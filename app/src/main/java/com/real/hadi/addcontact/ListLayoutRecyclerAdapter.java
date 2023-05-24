package com.real.hadi.addcontact;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class ListLayoutRecyclerAdapter extends RecyclerView.Adapter<ListLayoutRecyclerAdapter.NewsViewHolder> {
    ArrayList<ListLayout> digital;
    RecyclerView recyclerView;
    ArrayList<ListLayout> sampleNews;
    String sms = "";
    String flag = "";
    private Context context;
    private int lastPosition = -1;

    public ListLayoutRecyclerAdapter(ArrayList<ListLayout> phonelap) {
        digital = new ArrayList<>();
        digital = phonelap;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout, parent, false);
        context = parent.getContext();
        return new NewsViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {


        final ListLayout dataModel = digital.get(position);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String checkall = prefs.getString("checkall", "");
        if (checkall.equals("checkall")) {
            holder.checkBox.setChecked(true);
            sms += dataModel.getPhone() + ";";
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("sms", sms); //InputString: from the EditText
            editor.commit();

        } else if (checkall.equals("")) {
            sms = "";
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("sms", sms); //InputString: from the EditText
            editor.commit();
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    StringRequest request1 = new StringRequest(Request.Method.POST, "http://171.22.25.90/api/people/update?where=%7B%22id%22%3A%22"+dataModel.getId()+"%22%7D", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(AddContact.this, "خطا در عملیات", Toast.LENGTH_SHORT).show();

                            // do something
                        }
                    }) {
                        Map<String, String> params = new HashMap<String, String>();

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            params.put("is_sms", "true");

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            // add headers <key,value>
                            params.put("access_token", dataModel.getToken());
                            return params;
                        }
                    };
                    ;

                    request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                    requestQueue1.add(request1);

                }else {
                    StringRequest request1 = new StringRequest(Request.Method.POST, "http://171.22.25.90/api/people/update?where=%7B%22id%22%3A%22"+dataModel.getId()+"%22%7D", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(AddContact.this, "خطا در عملیات", Toast.LENGTH_SHORT).show();

                            // do something
                        }
                    }) {
                        Map<String, String> params = new HashMap<String, String>();

                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            params.put("is_sms", "false");

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            // add headers <key,value>
                            params.put("access_token", dataModel.getToken());
                            return params;
                        }
                    };
                    ;

                    request1.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue1 = Volley.newRequestQueue(context);
                    requestQueue1.add(request1);
                }

            }
        });
        holder.txtName.setText(dataModel.getName());
        holder.txtFamily.setText(dataModel.getLastname());
        holder.txtPhone.setText(dataModel.getPhone());

        holder.btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddContact.class);
                intent.putExtra("id", dataModel.getId());
                intent.putExtra("firstname", dataModel.getName());
                intent.putExtra("lastname", dataModel.getLastname());
                intent.putExtra("phone", dataModel.getPhone());
                intent.putExtra("education", dataModel.getEducation());
                intent.putExtra("job", dataModel.getJob());
                intent.putExtra("workAddress", dataModel.getWorkAddress());
                intent.putExtra("birthday", dataModel.getBirthday());
                intent.putExtra("district", dataModel.getDistrict());
                intent.putExtra("fallowUplength", dataModel.getFallowUplength());
                intent.putExtra("field2", dataModel.getField2());
                intent.putExtra("field3", dataModel.getField3());
                intent.putExtra("field4", dataModel.getField4());
                intent.putExtra("field5", dataModel.getField5());
                intent.putExtra("userId", dataModel.getUserId());
                intent.putExtra("token", dataModel.getToken());
                intent.putExtra("fullname", dataModel.getFullname());
                ArrayList<String> dateArr = new ArrayList<>(Integer.parseInt(dataModel.getFallowUplength()));
                ArrayList<String> descArr = new ArrayList<>(Integer.parseInt(dataModel.getFallowUplength()));
                for (int i = 0; i < Integer.parseInt(dataModel.getFallowUplength()); i++) {
                    dateArr = dataModel.getTrackdate();
                    descArr = dataModel.getDescription();
                }
                intent.putStringArrayListExtra("trackDate", dateArr);
                intent.putStringArrayListExtra("description", descArr);
                intent.putExtra("fromList", "fromList");
                context.startActivity(intent);
            }
        });
        holder.btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                StringRequest request = new StringRequest(Request.Method.DELETE, context.getString(R.string.url) + "/api/people/" + dataModel.getId(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Intent intent = new Intent(view.getContext(), ContactList.class);
                                        intent.putExtra("message", "Delete");
                                        intent.putExtra("userId", dataModel.getUserId());
                                        intent.putExtra("fullname", dataModel.getFullname());
                                        intent.putExtra("token", dataModel.getToken());
                                        intent.putExtra("flag", "");
                                        view.getContext().startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    Map<String, String> params = new HashMap<String, String>();

                                    @Override
                                    public Map<String, String> getParams() throws AuthFailureError {
                                        params.put("id", dataModel.getId());
                                        return params;
                                    }
                                };


                                request.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                                requestQueue.add(request);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("آیا از حذف مخاطب مطمئن هستید؟").setPositiveButton("بله", dialogClickListener)
                        .setNegativeButton("خیر", dialogClickListener).show();
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
        public TextView txtPhone, txtFamily;
        public int id;
        Button btnEditContact, btnDeleteContact;
        ConstraintLayout layout;
        CheckBox checkBox;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtFamily = (TextView) itemView.findViewById(R.id.txtFamily);
            btnEditContact = (Button) itemView.findViewById(R.id.btnEditContact);
            btnDeleteContact = (Button) itemView.findViewById(R.id.btnDeleteContact);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layout);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }


    }

}
