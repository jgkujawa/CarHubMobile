package com.worthwhilegames.carhubmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mobsandgeeks.adapters.InstantAdapter;
import com.worthwhilegames.carhubmobile.models.GasPriceRecord;
import com.worthwhilegames.carhubmobile.sync.FetchGasPricesTask;
import com.worthwhilegames.carhubmobile.sync.FetchGasPricesTask.FetchGasPricesTaskCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jamiekujawa
 */
@SuppressLint("DefaultLocale")
public class FindGasPricesActivity extends AdListActivity implements FetchGasPricesTaskCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.find_gas_prices);

        // Get an instance of the location client from Google Play Services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // search button listener
        Button search = (Button) findViewById(R.id.goButton);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getGasPrices();
            }
        });

        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                GasPriceRecord model = (GasPriceRecord) a.getItemAtPosition(position);

                Intent rowClick = new Intent(FindGasPricesActivity.this, UpdatePriceActivity.class);
                rowClick.putExtra(UpdatePriceActivity.EXTRA_STATION_NAME, model.getStation());
                rowClick.putExtra(UpdatePriceActivity.EXTRA_STATION_ADDRESS, model.getAddress());
                rowClick.putExtra(UpdatePriceActivity.EXTRA_STATION_ID, model.getStationId());
                rowClick.putExtra(UpdatePriceActivity.EXTRA_STATION_LAT, model.getLat());
                rowClick.putExtra(UpdatePriceActivity.EXTRA_STATION_LNG, model.getLng());

                startActivity(rowClick);
            }
        });
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This will cover the Android menu button press
        switch (item.getItemId()) {
        case R.id.menu_settings:
            Intent settingsButtonClick = new Intent(this, SettingsActivity.class);
            startActivity(settingsButtonClick);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // While we are waiting for location services,
        // just show our cached results
        updateUi();

        // Connect the client.
        mGoogleApiClient.connect();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();

        int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        switch (res) {
        case ConnectionResult.SERVICE_MISSING:
        case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
        case ConnectionResult.SERVICE_DISABLED:
            GooglePlayServicesUtil.getErrorDialog(res, this, 150);
            break;
        default:
            break;
        }
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private String getLocationFromGoogle() {
        if ((mGoogleApiClient != null) && mGoogleApiClient.isConnected()) {
            Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (l != null) {
                return l.getLatitude() + "~~" + l.getLongitude();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * This method will send a request to myGasFeed to get the gas prices
     */
    @SuppressLint("DefaultLocale")
    private String getLocationFromZip() {
        EditText zipCodeInput = (EditText)findViewById(R.id.zipCode);
        String zipCode = zipCodeInput.getText().toString();

        // pattern to match for the zip code
        Pattern pattern = Pattern.compile("[0-9]{5}");
        Matcher matcher = pattern.matcher(zipCode);

        if (!"".equals(zipCode) && matcher.matches()) {
            // Convert zip code to address
            try {
                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocationName(zipCode, 1);

                if (addresses != null && !addresses.isEmpty()) {
                    return addresses.get(0).getLatitude() + "~~" + addresses.get(0).getLongitude();
                } else {
                    return "";
                }
            } catch (IOException e) {
                Toast.makeText(this, "Unable to find location", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return "";
            }
        } else if (!"".equals(zipCode)) {
            Toast.makeText(this, "Please enter a valid zip code", Toast.LENGTH_LONG).show();
            return "";
        }

        return null;
    }

    public void getGasPrices() {
        setProgressBarIndeterminateVisibility(true);

        String preLocation = getLocationFromZip();
        if (null == preLocation) {
            preLocation = getLocationFromGoogle();
        }

        if (preLocation.matches("-?\\d+\\.\\d+~~-?\\d+\\.\\d+")) {
            double lat = Double.parseDouble(preLocation.split("~~")[0]);
            double lon = Double.parseDouble(preLocation.split("~~")[1]);

            if (Util.isDebugBuild) {
                Log.d("Longitude: ", "" + lat);
                Log.d("Latitude: ", "" + lon);
            }

            SharedPreferences sharedPref = Util.getSharedPrefs(this);
            int distance = sharedPref.getInt("Distance", 5);
            String fuelType = sharedPref.getString("Fuel Type", "Mid");
            String sortBy = sharedPref.getString("Sort By", "Price");
            FetchGasPricesTask request = new FetchGasPricesTask(this, this);

            String url = String.format("http://api.mygasfeed.com/stations/radius/%f/%f/%d/%s/%s/zax22arsix.json",
                    lat, lon, distance, fuelType.trim().toLowerCase(), sortBy.toLowerCase());
            request.execute(url, fuelType.trim().toLowerCase());
        }
    }

    private void updateUi() {
        // Get all GasPriceRecords from the database
        List<GasPriceRecord> gasRecords = GasPriceRecord.listAll(GasPriceRecord.class);

        List<GasPriceRecord> data = new ArrayList<GasPriceRecord>();
        List<GasPriceRecord> naData = new ArrayList<GasPriceRecord>();

        for (GasPriceRecord r : gasRecords) {
            if ("n/a".equalsIgnoreCase(r.getPrice())) {
                naData.add(r);
            } else {
                data.add(r);
            }
        }

        data.addAll(naData);

        setListAdapter(new InstantAdapter<GasPriceRecord>(this, R.layout.gaspricerowlayout, GasPriceRecord.class, data));

        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void gasPricesDidUpdate() {
        updateUi();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // Do nothing
    }

    @Override
    public void onConnected(Bundle bundle) {
        getGasPrices();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Do nothing
    }
}
