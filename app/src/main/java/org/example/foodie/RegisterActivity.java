package org.example.foodie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.example.foodie.models.ResponseUser;
import org.example.foodie.models.User;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static User user = new User("", "");
    public Button CreateAccountButton;
    public EditText InputName, InputPhoneNumber, InputPassword, InputAddress, InputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initWidgets();
        //  Log.i("Response", String.valueOf(user.getPhone()));

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  /*              Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);*/

                CreateUser(InputName.getText().toString(), InputEmail.getText().toString(), InputPassword.getText().toString(), InputAddress.getText().toString(), "+91" + InputPhoneNumber.getText().toString());


                RegisterUser(user);

            }
        });
        if (user.getToken() != null) {
            Log.i("ok", user.getToken());
            WelcomeActvity.getInstance().finish();
        }

    }

    public void RegisterUser(User user) {

        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);


        Call<ResponseUser> call = foodieClient.Register(user);

        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {


                //Log.i("Response", String.valueOf(response.body().getToken()));
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    intent.putExtra("token", response.body().getToken());
                    intent.putExtra("name", response.body().getUser().getName());
                    WelcomeActvity.token = response.body().getToken();
                    finish();
                    WelcomeActvity.getInstance().finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void initWidgets() {

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        InputAddress = (EditText) findViewById(R.id.register_address_input);

    }


    public void CreateUser(String name, String email, String password, String address, String phone) {
        user = new User(name, email, password, address, phone);
    }





}
