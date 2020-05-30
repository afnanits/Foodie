package org.example.foodie.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.internal.cglib.proxy.$Callback;

import org.example.foodie.R;
import org.example.foodie.WelcomeActvity;
import org.example.foodie.models.Update.UpdateBody;
import org.example.foodie.models.Update.UpdateResponse;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateInfo extends Fragment {
   View rootView;
   FoodieClient foodieClient;
    public EditText InputName, InputPhoneNumber, InputPassword, InputAddress, InputEmail;
    private Button updateBtn;
    private ProgressBar progressBar;
    public UpdateInfo() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_update_info , container , false);
        updateBtn= (Button) rootView.findViewById(R.id.update_btn);
        InputName = (EditText) rootView.findViewById(R.id.update_username_input);
        InputPhoneNumber = (EditText) rootView.findViewById(R.id.update_phone_input);
        InputPassword = (EditText) rootView.findViewById(R.id.update_password_input);
        InputEmail = (EditText) rootView.findViewById(R.id.update_email_input);
        InputAddress = (EditText) rootView.findViewById(R.id.update_address_input);
        progressBar=rootView.findViewById(R.id.updateProgressBar);

       updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    updateInfo();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        return rootView;
    }

    private void updateInfo() {
        foodieClient = ServiceGenerator.createService(FoodieClient.class);
        UpdateBody updateBody=new UpdateBody(InputName.getText().toString(),
                InputEmail.getText().toString()
                ,InputPassword.getText().toString()
                ,InputAddress.getText().toString()
                ,InputPhoneNumber.getText().toString());

        Call<UpdateResponse> call=foodieClient.updateUserInfo(WelcomeActvity.token,updateBody);
        call.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call , Response<UpdateResponse> response) {
                if (response.code()==200)
                {
                    Toast.makeText(getActivity() , "Successfully Updated" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call , Throwable t) {
                Toast.makeText(getActivity() , "Error: "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
