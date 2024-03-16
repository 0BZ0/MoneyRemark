package com.wky.mmbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wky.mmbook.db.DBManager;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText rgeditTextUsername, rgeditTextPassword, rgconfirmTextPassword;
    Button rgbuttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        rgeditTextUsername = findViewById(R.id.RG_editTextUsername);
        rgeditTextPassword = findViewById(R.id.RG_editTextPassword);
        rgconfirmTextPassword = findViewById(R.id.RG_confirmTextPassword);
        rgbuttonRegister = findViewById(R.id.RG_buttonRegister);
        rgbuttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RG_buttonRegister:
                isRegister();
                break;
        }
    }
    private void isRegister() {
        String username = rgeditTextUsername.getText().toString();
        String password = rgeditTextPassword.getText().toString();
        String confirmPassword = rgconfirmTextPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            rgconfirmTextPassword.setText("");
            return;
        }
        boolean success = DBManager.isValidUser(username, password);
        if (!success) {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            DBManager.RegisterUser(username,password);
            Intent it = new Intent(this, LoginActivity.class);  //跳转界面
            startActivity(it);
            finish(); // 返回登录页面
        } else {
            Toast.makeText(RegisterActivity.this, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show();
            rgeditTextUsername.setText("");
            rgeditTextPassword.setText("");
            rgconfirmTextPassword.setText("");
        }
    }
}