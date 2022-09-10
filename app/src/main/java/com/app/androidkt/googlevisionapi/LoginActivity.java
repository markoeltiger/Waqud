package com.app.androidkt.googlevisionapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText user_name , password ;
    AppCompatButton login_btn ;
    ProgressBar p_load ;
    SharedPreferences sharedPreferences;
    String userName , Password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
if (sharedPreferences.getInt("loggedin",0)==1){
Intent gotocamera = new Intent(LoginActivity.this,MyOCRActivity.class);
startActivity(gotocamera);
}

        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        p_load = findViewById(R.id.p_load);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Password = password.getText().toString();
                userName = user_name.getText().toString();
                if (!isValidEmail(userName)){
                    user_name.setError("Email Not Valid");
                }else if (!isValidPassword(Password)){
                    password.setError("Password Not Valid");
                }else {
                    p_load.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPassword(CharSequence target) {
        return (!TextUtils.isEmpty(target) && target.length()>=6);
    }


    private void login() {

        String url = sheared.main_url + "login";
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {

                            p_load.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("status")){

                                Toast.makeText(LoginActivity.this, "iam here", Toast.LENGTH_SHORT).show();
                                com.app.androidkt.googlevisionapi.PreferenceManager.getInstance(LoginActivity.this).saveInteger("user_id",
                                        jsonObject.getJSONObject("user").getInt("id"));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("loggedin", 1);
                                editor.apply();
                                PreferenceManager.getInstance(LoginActivity.this).saveString("user_name",userName);
                                startActivity(new Intent(LoginActivity.this,MyOCRActivity.class));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        p_load.setVisibility(View.INVISIBLE);
                        Log.d("ERROR","error => "+error.toString());

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",userName);
                params.put("password",Password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AppKey",getResources().getString(R.string.app_key));
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }
    }
