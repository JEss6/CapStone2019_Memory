package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.id_edit_login);
        final EditText passwordText = (EditText) findViewById(R.id.password_edit);
        final Button loginbtn = (Button) findViewById(R.id.login_button);
        final TextView registerbtn = (TextView) findViewById(R.id.register_button);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                //String userID = jsonResponse.getString("userID");
                                //String userPassword = jsonResponse.getString("userPassword");
                                //System.out.println(jsonResponse);
                                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                Intent intent_admin = new Intent(LoginActivity.this,AdminMainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPassword",userPassword);
                                //intent.putExtra("userName",userName);
                                if(userID.equals("shake_admin")){
                                    LoginActivity.this.startActivity(intent_admin);
                                }
                                else {
                                    LoginActivity.this.startActivity(intent);
                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                //startActivity(new Intent(getApplication(),Main2Activity.class)); //Test용도 ( 로그인 누르면 바로 메인으로 이동 )

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);/**/ //실제 로그인 구현파트
            }
        });
    }

    public void registerbuttonclicked(View v){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent);
    }
}
