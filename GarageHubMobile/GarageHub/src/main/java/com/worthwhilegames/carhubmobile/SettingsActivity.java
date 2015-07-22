package com.worthwhilegames.carhubmobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author jamiekujawa
 */
public class SettingsActivity extends AdActivity {

    /**
     * A SharedPreferences object to get the preferences set by the user
     */
    private SharedPreferences sharedPref;

    /**
     * A Spinner to represent the different distance options
     */
    @InjectView(R.id.spinnerradius) protected Spinner distanceSpinner;

    /**
     * A Spinner to represent the different fuel types
     */
    @InjectView(R.id.spinnerfueltype) protected Spinner fuelTypeSpinner;

    /**
     * A Spinner to represent the different sorting options
     */
    @InjectView(R.id.spinnersortby) protected Spinner sortBySpinner;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.inject(this);

        // create the shared preferences object
        sharedPref = Util.getSharedPrefs(this);

        if (distanceSpinner != null) {
            // get the value from shared preferences
            Integer distanceToSearch = sharedPref.getInt("Distance", 5);

            // make an array adapter of all options specified in the xml
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> distanceAdapter = (ArrayAdapter<String>) distanceSpinner.getAdapter();

            // find the current position
            int spinnerPosition = distanceAdapter.getPosition(distanceToSearch.toString());

            // set the correct position to true
            distanceSpinner.setSelection(spinnerPosition);
        }

        if (fuelTypeSpinner != null) {
            // get the value from shared preferences
            String fuelType = sharedPref.getString("Fuel Type", "Mid");

            // make an array adapter of all options specified in the xml
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> fuelTypeAdapter = (ArrayAdapter<String>) fuelTypeSpinner.getAdapter();

            // find the current position
            int spinnerPosition = fuelTypeAdapter.getPosition(fuelType);

            // set the correct position to true
            fuelTypeSpinner.setSelection(spinnerPosition);
        }

        if (sortBySpinner != null) {
            // get the value from shared preferences
            String sortBy = sharedPref.getString("Sort By", "Price");

            // make an array adapter of all options specified in the xml
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> sortByAdapter = (ArrayAdapter<String>) sortBySpinner.getAdapter();

            // find the current position
            int spinnerPosition = sortByAdapter.getPosition(sortBy);

            // set the correct position to true
            sortBySpinner.setSelection(spinnerPosition);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // set the result of the activity
        setResult(RESULT_OK);

        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        // set number of computers
        if (distanceSpinner != null) {
            prefsEditor.putInt("Distance", Integer.parseInt((String) distanceSpinner.getSelectedItem()));
        }

        // set difficulty of computers to preferences
        if (fuelTypeSpinner != null) {
            prefsEditor.putString("Fuel Type", (String) fuelTypeSpinner.getSelectedItem());
        }

        // set language to preferences
        if (sortBySpinner != null) {
            prefsEditor.putString("Sort By", sortBySpinner.getSelectedItem().toString());
        }

        // commit the changes to the shared preferences
        prefsEditor.commit();

        // finish the activity
        finish();
    }
}