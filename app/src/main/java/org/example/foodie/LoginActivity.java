package org.example.foodie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.User;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static User user = new User("", "");
    private Button LoginButton;
    private EditText InputEmail, InputPassword;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);


        initWidgets();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser(String.valueOf(InputEmail.getText()), String.valueOf(InputPassword.getText()));

                Log.i("Credentials", user.getEmail() + " " + user.getPassword());
                LoginUser(user);
            }
        });
        if (user.getToken() != null) {
            Log.i("ok", user.getToken());
            WelcomeActvity.getInstance().finish();
        }

    }


    //Login user function
    public void LoginUser(User user) {

        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);


        Call<ResponseUser> call = foodieClient.Login(user);


        spinner.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {


                //Get user logged if resposne code is 200
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    User use = response.body().getUser();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("token", response.body().getToken());
                    intent.putExtra("name", use.getName());

                    startActivity(intent);


                    Log.i("name", use.getName());


                    //setting token value here

                    SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    WelcomeActvity.token = response.body().getToken();
                    editor.putString("name", use.getName());
                    editor.putString("token", response.body().getToken());
                    editor.commit();
                    spinner.setVisibility(View.GONE);


                    WelcomeActvity.getInstance().finish();

                    finish();

                } else {

                    spinner.setVisibility(View.GONE);
                    Log.i("Response", "Invalid Credentials");
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void initWidgets() {


        LoginButton = (Button) findViewById(R.id.login_btn);
        InputEmail = (EditText) findViewById(R.id.login_email_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);

    }


    public void CreateUser(String email, String password) {
        user = new User(email, password);
    }

}
