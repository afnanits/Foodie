package org.example.foodie;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.navigation.NavigationView;

import org.example.foodie.models.ResponseUser;
import org.example.foodie.org.example.foodie.apifetch.FoodieClient;
import org.example.foodie.org.example.foodie.apifetch.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ProgressBar progressBar;
    //DrawerLayout drawer;
    //NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    public static String user;
    //Toolbar toolbar;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.GONE);

        Intent i = getIntent();

        String token = i.getStringExtra("token");
        user = i.getStringExtra("name");

        if (user == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            user = sharedPreferences.getString("name", null);
        }


        if (token != null) {
            Log.i("TOKEN", token);

            WelcomeActvity.getInstance().finish();
        }







        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);


        View headerView = nvDrawer.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.userName);
        userName.setText("USER: " + String.valueOf(user));


        // Setup drawer view
        setupDrawerContent(nvDrawer);
        //FragmentManager fragmentManager=new F;

        frameLayout = (FrameLayout) findViewById(R.id.flContent);
        //View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();


        try {
            fragmentManager.beginTransaction().replace(R.id.flContent,Home.class.newInstance(),"Home");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        //set default fragment
        loadFragment(new Home());


// We can now look up items within the header if needed

        // ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_cart:
                Toast.makeText(this,"Cart empty!",Toast.LENGTH_SHORT).show();

        }


        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        boolean id=false;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {

            /*case R.id.childDetails:
                fragmentClass = ChildDetails.class;
                break;*/

            case R.id.home:
                id=true;
                fragmentClass = Home.class;
                break;
            case R.id.logout:
                LogoutUser();
                return;



            default:
                fragmentClass = Home.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();


//else {
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("fragBack").commit();

        if(!id){
        }getFragmentManager().popBackStack();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    public void LogoutUser() {

        FoodieClient foodieClient = ServiceGenerator.createService(FoodieClient.class);


        Call<Void> call = foodieClient.Logout(WelcomeActvity.token);
        progressBar.setVisibility(View.VISIBLE);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {



                // Log.i("Response", String.valueOf(response.body().getToken()));
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();

                    WelcomeActvity.token = null;
                    Intent intent = new Intent(MainActivity.this, WelcomeActvity.class);
                    progressBar.setVisibility(View.GONE);


                    startActivity(intent);

                    finish();
                    deleteToken();
                } else {
                    progressBar.setVisibility(View.GONE);

                    Log.i("Response", response.raw().toString());
                    Toast.makeText(getApplicationContext(), response.raw().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //  progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    //delete Token on logging out
    public void deleteToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("org.example.foodie", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        editor.commit();
    }

    public void loadData() {


    }


    public interface DataLoadedListener {
        public void onDataLoaded(ArrayList<String> data);

    }



}
