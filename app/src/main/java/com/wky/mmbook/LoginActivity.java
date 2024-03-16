package com.wky.mmbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wky.mmbook.adapter.LoginSliderAdapter;
import com.wky.mmbook.db.DBManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    LinearLayout indicatorLayout;
    TextView textViewUsername, textViewPassword;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager1);
        LoginSliderAdapter adapter = new LoginSliderAdapter(this, viewPager);
        viewPager.setAdapter(adapter);
    }
    private void initView() {
        viewPager = findViewById(R.id.viewPager1);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        textViewUsername = findViewById(R.id.textViewUsername);
        editTextUsername = findViewById(R.id.editTextUsername);
        textViewPassword = findViewById(R.id.textViewPassword);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                isValid();
                break;
            case R.id.buttonRegister:
                Intent it = new Intent(this, RegisterActivity.class);  //跳转界面
                startActivity(it);
                break;
        }

    }

    private void isValid() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean Valid = DBManager.isValidUser(username, password);
        if (Valid) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent MainIt = new Intent(this, MainActivity.class);  //跳转界面
            startActivity(MainIt);
        }else{
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}