package edu.northeastern.numad22fa_wanchunwu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    private static final int REQUEST_LOCATION_PERMISSION = 99;
    private static final int REQUEST_COARSE_LOCATION_PERMISSION = 100;
    private static final int REQUEST_CHECK_SETTINGS = 101;
    private static final long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private static final long FASTEST_INTERVAL = 2 * 1000; /* 2 sec */
    private int priority;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView latitudeTV;
    private TextView longitudeTV;
    private TextView totalDistanceTV;
    private TextView locationAccuracyTV;
    private Double lastLocationLatitude;
    private Double lastLocationLongitude;
    private float totalDistance;
    private boolean requestingLocationUpdates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitudeTV = findViewById(R.id.latitudeTV);
        longitudeTV = findViewById(R.id.longitudeTV);
        totalDistanceTV = findViewById(R.id.totalDistanceTV);
        locationAccuracyTV = findViewById(R.id.locationAccuracyTV);

        lastLocationLatitude = null;
        lastLocationLongitude = null;
        totalDistance = 0;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (lastLocationLatitude != null && location != null) {
                    float[] results = new float[3];
                    Location.distanceBetween(
                            lastLocationLatitude, lastLocationLongitude,
                            location.getLatitude(), location.getLongitude(),
                            results);
                    totalDistance += results[0];
                    totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
                }
                if (location != null) {
                    lastLocationLatitude = location.getLatitude();
                    lastLocationLongitude = location.getLongitude();
                    latitudeTV.setText(String.valueOf(lastLocationLatitude));
                    longitudeTV.setText(String.valueOf(lastLocationLongitude));
                    locationAccuracyTV.setText(
                            String.format("Accuracy: %s", location.getAccuracy()));
                }
            }
        };

        priority = Priority.PRIORITY_HIGH_ACCURACY;

        Button highAccuracyBtN = findViewById(R.id.highAccuracyBtN);
        highAccuracyBtN.setOnClickListener(v -> {
            priority = Priority.PRIORITY_HIGH_ACCURACY;
            startLocationUpdates();
        });


        Button resetDistanceBtN = findViewById(R.id.resetDistanceBtN);
        resetDistanceBtN.setOnClickListener(v -> {
            totalDistance = 0;
            totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
        });
    }

    private void createLocationRequests() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        // priority is defined by user with pressing accuracy button
        locationRequest.setPriority(priority);
    }

    private void startLocationUpdates() {
        // create the location request to start receiving updates
        createLocationRequests();

        // get the current location settings of a user's device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // check whether the current location settings are satisfied
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            // All location settings are satisfied.
            // The client can initialize location requests here.
            requestingLocationUpdates = true;
            initializeRequestLocationUpdates();
        });

        task.addOnFailureListener(this, e -> {
            requestingLocationUpdates = false;
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied,
                // but this can be fixed by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(LocationActivity.this,
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    private void initializeRequestLocationUpdates() {
        // Priority.PRIORITY_HIGH_ACCURACY needs user to grant ACCESS_FINE_LOCATION permission
        if (priority == Priority.PRIORITY_HIGH_ACCURACY) {
            if (checkLocationPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper());
            } else {
                askLocationPermission();
            }
        } else { // Other priority is available with ACCESS_COARSE_LOCATION permission
            if (checkLocationPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper());
            } else {
                askCoarseLocationPermission();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                startLocationUpdates();
            }
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalDistanceTV.setText(String.format("Distance: %s", totalDistance));
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (requestingLocationUpdates) {
            outState.putFloat("total_distance", totalDistance);
            outState.putDouble("last_distance_latitude", lastLocationLatitude);
            outState.putDouble("last_distance_longitude", lastLocationLongitude);
            outState.putInt("priority", priority);
            outState.putBoolean("requestingLocationUpdates", requestingLocationUpdates);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        totalDistance = savedInstanceState.getFloat("total_distance");
        lastLocationLatitude = savedInstanceState.getDouble("last_distance_latitude");
        lastLocationLongitude = savedInstanceState.getDouble("last_distance_longitude");
        priority = savedInstanceState.getInt("priority");
        requestingLocationUpdates = savedInstanceState.getBoolean("requestingLocationUpdates");
    }

    public boolean checkLocationPermission(String permission) {
        boolean isPermitted = false;
        if (ContextCompat.checkSelfPermission(LocationActivity.this, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(LocationActivity.this,
                    permission + " already granted", Toast.LENGTH_SHORT).show();
            isPermitted = true;
        }
        return isPermitted;
    }

    // ask both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permission
    public void askLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("To show your current location with highest accuracy, requires " +
                            "your permission to access device's precise location")
                    .setPositiveButton("OK", (dialog, which) -> {
                        //Prompt the user once explanation has been show
                        ActivityCompat.requestPermissions(LocationActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                REQUEST_LOCATION_PERMISSION);
                    })
                    .setNegativeButton("No thanks", ((dialog, which) -> dialog.cancel()))
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    // ask only ACCESS_COARSE_LOCATION permission
    public void askCoarseLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("To show your current location, requires your permission to access" +
                            " device's location")
                    .setPositiveButton("OK", (dialog, which) -> {
                        //Prompt the user once explanation has been show
                        ActivityCompat.requestPermissions(LocationActivity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                REQUEST_COARSE_LOCATION_PERMISSION);
                    })
                    .setNegativeButton("No thanks", ((dialog, which) -> dialog.cancel()))
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(LocationActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If request is cancelled, the result arrays are empty.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // grantResults[0] is related to ACCESS_FINE_LOCATION. grantResults[1] is related to
            // ACCESS_COARSE_LOCATION.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ACCESS_FINE_LOCATION is granted.
                startLocationUpdates();
            } else if (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Precise location permission is denied. Highest Accuracy is " +
                                "not available at this time. Please grant the precise location " +
                                "permission or view the location in Balanced Accuracy.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.cancel())
                        .create().
                        show();
                priority = Priority.PRIORITY_HIGH_ACCURACY;
                startLocationUpdates();
            } else {
                requestingLocationUpdates = false;
            }
        } else if (requestCode == REQUEST_COARSE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                requestingLocationUpdates = false;
            }
        }
    }
}
