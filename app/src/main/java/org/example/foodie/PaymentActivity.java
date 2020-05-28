package org.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.stripe.android.GooglePayConfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.example.foodie.models.Order;
import org.example.foodie.models.Payment;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ch.qos.logback.core.subst.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {


    final int UPI_PAYMENT = 0;
    ExpandableRelativeLayout expandableLayout1;
    RelativeLayout proceeToPay;
    Button COD;
    private androidx.appcompat.widget.Toolbar toolbar;
    private PaymentsClient paymentsClient;
    ProgressBar progressBar;
    private CheckBox defaultAdress;
    private EditText customAddress;

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        proceeToPay = findViewById(R.id.googlePaay);

        COD = findViewById(R.id.payOndelivery);
        toolbar = findViewById(R.id.toolbar);
        customAddress = findViewById(R.id.customAddress);
        defaultAdress = findViewById(R.id.defaultAddress);
        progressBar = findViewById(R.id.paymentWait);
        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();

        paymentsClient = Wallet.getPaymentsClient(this, walletOptions);


        setSupportActionBar(toolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("CHOOSE PAYMENT MODE");


        progressBar.setVisibility(View.GONE);
        customAddress.setEnabled(!defaultAdress.isChecked());

        defaultAdress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                customAddress.setEnabled(!defaultAdress.isChecked());
                if (defaultAdress.isChecked() || String.valueOf(customAddress.getText()).length() > 0) {
                    COD.setClickable(true);
                    proceeToPay.setClickable(true);
                } else {
                    COD.setClickable(false);
                    proceeToPay.setClickable(false);
                }
            }


        });

        customAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(customAddress.getText()).length() > 0 || defaultAdress.isChecked()) {
                    COD.setClickable(true);
                    proceeToPay.setClickable(true);
                } else {
                    COD.setClickable(false);
                    proceeToPay.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        proceeToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                COD.setClickable(false);
                proceeToPay.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                payUsingUpi(MainActivity.user, "mak25011999@oksbi",
                        "Payment for order", getIntent().getStringExtra("price"));


            }
        });


        COD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Payment payment = new Payment("COD", "UNPAID");
                COD.setClickable(false);
                proceeToPay.setClickable(false);
                if (defaultAdress.isChecked())
                    CartActivity.CreateOrder(payment, null);
                else CartActivity.CreateOrder(payment, String.valueOf(customAddress.getText()));
                placeOrder(CartActivity.order);
            }
        });
    }



    void payUsingUpi(String name, String upiId, String note, String amount) {
        Log.e("main ", "name " + name + "--up--" + upiId + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        if (isConnectionAvailable(PaymentActivity.this)) {
            // will always show a dialog to user to choose an app
            String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
            int GOOGLE_PAY_REQUEST_CODE = 123;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    Toast.makeText(getApplicationContext(), "NO UPI APP FOUND", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PaymentActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);
                if (defaultAdress.isChecked())
                    CartActivity.CreateOrder(new Payment("UPI", "PAID"), null);
                else
                    CartActivity.CreateOrder(new Payment("UPI", "PAID"), String.valueOf(customAddress.getText()));
                placeOrder(CartActivity.order);

            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(PaymentActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(PaymentActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void placeOrder(Order order) {


        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);
        Call<Order> call = foodieClient.placeOrder(MainActivity.token, order);
        Log.i("cartitems size: ", order.getRestaurantId() + " " + order.getFoodList().get(0).get_id());

        //        Log.i("tokenOrder", WelcomeActvity.token);
        //    progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                Log.i("Ordered: ", String.valueOf(response.message()));
                if (response.code() == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", MODE_PRIVATE);
                    Toast.makeText(getApplicationContext(), "Order placed", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PaymentActivity.this, MainActivity.class);
                    CartActivity.cartItems.clear();
                    FoodsActivity.rest_id = null;
                    CartActivity.orderFood.clear();
                    CartActivity.saveData(sharedPreferences);
                    finish();
                    startActivity(i);
                    //  CartActivity.getInstance().finish();

                    //CartActivity.this.finish();
                    //CartActivity.super.onBackPressed();
                }
                progressBar.setVisibility(View.GONE);
                COD.setClickable(true);
                proceeToPay.setClickable(true);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.i("Orderederr: ", t.getMessage());
                progressBar.setVisibility(View.GONE);
                COD.setClickable(true);
                proceeToPay.setClickable(true);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


}





