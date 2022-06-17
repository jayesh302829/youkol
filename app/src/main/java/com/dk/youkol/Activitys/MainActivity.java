package com.dk.youkol.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.dk.youkol.R;
import com.dk.youkol.databinding.ActivityMainBinding;
import com.dk.youkol.utils.Const;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Activity activity = this;
    private boolean isEmailValid;
    private boolean isPasswordValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);

        onclick();

    }

    private void onclick() {
        binding.clLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPassword.getText().toString();

                if (email.isEmpty()) {
                    binding.edtEmail.setError(getString(R.string.email_not_empty));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.setError(getString(R.string.validemail));
                    isEmailValid = false;
                } else  {
                    binding.edtEmail.setError(null);
                    isEmailValid = true;
                }

                if (password.isEmpty()) {
                    binding.edtPassword.setError(getString(R.string.password_not_empty));
                    isPasswordValid = false;
                } else if (password.length() <= 5) {
                    binding.edtPassword.setError(getString(R.string.valid_password));
                    isPasswordValid = false;
                } else  {
                    binding.edtPassword.setError(null);
                    isPasswordValid = true;
                }

                if (isEmailValid && isPasswordValid){

                    if (email.equalsIgnoreCase("johndoe@comminsur.com") && password.equals("123456")){
                        Intent intent = new Intent(activity, DashboardActivity.class);
                        intent.putExtra("type", Const.Admin);
                        startActivity(intent);
                    }else {
                        Toast.makeText(activity, "Email and Password Wrong\n PLease Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.clContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DrivingActivity.class);
                intent.putExtra("type", Const.Guest);
                startActivity(intent);
            }
        });
    }
}
