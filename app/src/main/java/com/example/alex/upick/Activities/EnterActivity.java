package com.example.alex.upick.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.upick.Adapters.ViewPagerAdapter;
import com.example.alex.upick.Fragments.TokenDialogFragment;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EnterActivity extends AppCompatActivity implements LocationListener {
    ViewPager viewPager;
    ArrayList<Venue> venueList = new ArrayList<>();
    ViewPagerAdapter adapter;
    EditText txtLocalization;
    TextView txtCoord;
    RetrofitInterface myApi;
    Venue venue;
    Location location;
    Intent i;
    double earthRadius = 6371000, checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        getSupportActionBar().setTitle(R.string.string_enter);


        //trocar este pedido de permissao por algo mais amigavel aos olhos.
        if (ActivityCompat.checkSelfPermission(EnterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EnterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EnterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }


        init();
        refreshSpotToken();


        Call<ArrayList<Venue>> mService = myApi.getVenues("application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList<Venue>>() {


            @Override
            public void onResponse(Call<ArrayList<Venue>> call, Response<ArrayList<Venue>> response) {

                if (response.body() != null) {

                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //Fazer a verificação se tem permissao gps
                    @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {

                        for (int i = 0; i < response.body().size(); i++) {

                            checker = VenueDistanceCalculator(response.body().get(i).getPosLat(), response.body().get(i).getPosLong(), location.getLatitude(), location.getLongitude(), earthRadius);

                            if (checker >= 0 && checker <= Integer.parseInt(response.body().get(i).getRange())) {

                                venueList.add(response.body().get(i));

                            }

                        }

                        adapter = new ViewPagerAdapter(venueList, EnterActivity.this);
                        viewPager.setAdapter(adapter);

                    }


                }

            }

            @Override
            public void onFailure(Call<ArrayList<Venue>> call, Throwable t) {

            }
        });


        txtLocalization.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Fazer a verificação se tem permissao gps
                @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                //ve qual e a ultima localizacao "conhecida"
                if (location != null) {
                    txtCoord.setText("Latitude : " + location.getLatitude() + "\n Longitude : " + location.getLongitude());
                } else {
                    Toast.makeText(getApplicationContext(), "Couldnt get location", Toast.LENGTH_LONG).show();
                }


                return false;
            }
        });


        float scale = getResources().getDisplayMetrics().density;
        int leftDpAsPixels = (int) (67 * scale + 0.5f);
        int rightDpAsPixels = (int) (45 * scale + 0.5f);

        viewPager.setPadding(leftDpAsPixels, 0, rightDpAsPixels, 0);

        viewPager.setClipToPadding(false);
        viewPager.setCurrentItem(Math.round(venueList.size() / 2));


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

                //TENTAR MUDAR COR DO PAGER ATUAL
                //TENTAR FAZER SCROLL INFINITO

                //View view = viewPager.getFocusedChild();
                //
                //
                // CardView card = view.findViewById(R.id.cardView);


            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //ignorar
            }
        });


    }

    private void refreshSpotToken() {

        Call<ArrayList> mService = myApi.getToken(LoginActivity.loggedUserId, "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

            }
        });

    }

    private double VenueDistanceCalculator(Double latitudeFrom, Double longitudeFrom, Double latitudeTo, Double longitudeTo, Double earthRadius) {

        Double latFrom = Math.toRadians(latitudeFrom);
        Double lonFrom = Math.toRadians(longitudeFrom);
        Double latTo = Math.toRadians(latitudeTo);
        Double lonTo = Math.toRadians(longitudeTo);

        Double latDelta = latTo - latFrom;
        Double lonDelta = lonTo - lonFrom;

        Double angle = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latDelta / 2), 2) + Math.cos(latFrom) * Math.cos(latTo) * Math.pow(Math.sin(lonDelta / 2), 2)));

        return (angle * earthRadius);


    }


    public void clickedCard(int position) {

        venue = new Venue(venueList.get(position).getId(), venueList.get(position).getName(), venueList.get(position).getVenue_owner_id(), venueList.get(position).getDescription(), venueList.get(position).getTwitterLink(), venueList.get(position).getFacebookLink(), venueList.get(position).getInstagramLink(), venueList.get(position).getPosLat(), venueList.get(position).getPosLong(), venueList.get(position).getToken(), venueList.get(position).getQueue(), venueList.get(position).getRange(), venueList.get(position).getImagepath());


       LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Fazer a verificação se tem permissao gps
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        checker = VenueDistanceCalculator(venueList.get(position).getPosLat(), venueList.get(position).getPosLong(), location.getLatitude(), location.getLongitude(), earthRadius);

        if (checker >= 0 && checker <= Integer.parseInt(venueList.get(position).getRange())) {

            Bundle args = new Bundle();
            args.putString("venue", new Gson().toJson(venue));

            FragmentManager fm = getSupportFragmentManager();
            TokenDialogFragment edf = new TokenDialogFragment();
            edf.setArguments(args);
            edf.show(fm, "TAG");

        } else {
            Toast.makeText(getApplicationContext(), "estas longe pra crl", Toast.LENGTH_LONG).show();
        }


    }


    private void init() {
        viewPager = findViewById(R.id.establishment_viewpager);
        txtLocalization = findViewById(R.id.txt_localization);
        txtCoord = findViewById(R.id.txt_coord);
        //init Api
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);
    }





        @Override
        public void onLocationChanged(Location loc) {

        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                i = new Intent(EnterActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        i = new Intent(EnterActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}
