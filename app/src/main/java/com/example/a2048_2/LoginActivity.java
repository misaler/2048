package com.example.a2048_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private Button btnlog,btnreg;
    private EditText etAcount,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }
    private void initView() {
        btnlog=(Button) findViewById(R.id.btn_login);
        btnreg=(Button) findViewById(R.id.btn_register);
        etAcount=(EditText) findViewById(R.id.et_account);
        etPassword=(EditText) findViewById(R.id.et_pwd);
        btnlog.setOnClickListener(new View.OnClickListener() {
            //登录按钮的监听
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("user", MODE_PRIVATE);
                String number=sharedPreferences.getString("number", "");
                int a,b;
                if (number=="") {
                    a=0;//记录用户个数
                }else {
                    a=Integer.parseInt(number);
                }
                String account;
                String password;
                for (b=0;b<a;b++) {
                    account="account"+b;
                    password="password"+b;
                    String jname=sharedPreferences.getString(account,"");
                    String jpwd=sharedPreferences.getString(password, "");
                    if (jname.equals(etAcount.getText().toString())||jpwd.equals(etPassword.getText().toString())) {
                        if (jname.equals(etAcount.getText().toString())&&jpwd.equals(etPassword.getText().toString())) {
                            finish();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("currentaccount",account);
                            intent.putExtras(bundle);
                            String best1="best"+b;
                            Log.d("currentuserbest:",sharedPreferences.getString(best1,""));
                            startActivity(intent);
                            break;
                        }else if (jname.equals(etAcount.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (b==a) {
                    Toast.makeText(LoginActivity.this, "用户未注册", Toast.LENGTH_SHORT).show();
                }
            }
        });
//注册按钮的监听
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account;
                String password;
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                String number=sharedPreferences.getString("number", "");
                int a;
                int b;
                if (number=="") {
                    a=0;
                }else {
                    a=Integer.parseInt(number);
                }
                for (b = 0; b <a; b++) {
                    account="account"+b;
                    password="password"+b;
                    String jname=sharedPreferences.getString(account,"");
                    if (jname.equals(etAcount.getText().toString())) {
                        break;
                    }
                }
                if (b==a) {
                    if (etAcount.getText().toString().equals("")&&etPassword.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this,"用户名密码不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        account="account"+b;
                        password="password"+b;
                        a++;
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(account,etAcount.getText().toString());
                        editor.putString(password,etPassword.getText().toString());
                        editor.putString("number",a + "");
                        editor.commit();
                        Toast.makeText(LoginActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"该账户已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
