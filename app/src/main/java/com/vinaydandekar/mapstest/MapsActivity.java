package com.vinaydandekar.mapstest;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        System.out.println("Connecting...");
        String targetUrl = "http://54.234.224.194/api/v1/flights/find_lat_long?lat=38.964022&long=-77.378791&quantity=5";
        GetFlightsTask t = new GetFlightsTask();
        t.execute(targetUrl);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(33, -77)).title("Marker"));
        System.out.println("Marker made!");

    }

    private int round_to_15(int a) {
        if (0 == a)
            return 0;
        a = 10 * Math.round(a / 10);
        switch (a) {
            case 0:
                return 0;
            case 10:
                return 15;
            case 20:
                return 15;
            case 30:
                return 30;
            case 40:
                return 45;
            case 50:
                return 45;
            case 60:
                return 60;
            case 70:
                return 75;
            case 80:
                return 75;
            case 90:
                return 90;
            case 100:
                return 105;
            case 110:
                return 105;
            case 120:
                return 120;
            case 130:
                return 135;
            case 140:
                return 135;
            case 150:
                return 150;
            case 160:
                return 165;
            case 170:
                return 165;
            case 180:
                return 180;
            case 190:
                return 195;
            case 200:
                return 195;
            case 210:
                return 210;
            case 220:
                return 225;
            case 230:
                return 225;
            case 240:
                return 240;
            case 250:
                return 255;
            case 260:
                return 255;
            case 270:
                return 270;
            case 280:
                return 285;
            case 290:
                return 285;
            case 300:
                return 300;
            case 310:
                return 315;
            case 320:
                return 315;
            case 330:
                return 330;
            case 340:
                return 345;
            case 350:
                return 345;
            case 360:
                return 0;
            default:
                return 0;
        }
    }



    class GetFlightsTask extends AsyncTask<String, Void, ArrayList<Flight>> {

        private Exception exception;
        private ArrayList<Flight> flights = new ArrayList<Flight>();

        @Override
        protected ArrayList doInBackground(String... params) {

            String targetUrl = params[0];
            //String result = "";
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(targetUrl);
            get.setHeader("Authorization", "Token token=" + getToken());
            System.out.println(get.getFirstHeader("Authorization").toString());
            System.out.println(get.toString());
            try{
                HttpResponse response = client.execute(get);
                String result = EntityUtils.toString(response.getEntity());
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject j = ja.getJSONObject(i);
                    Flight f = new Flight();
                    f.setFlightNum(j.getString("ICAOflightnum"));
                    f.setOrigin(j.getString("origin"));
                    f.setDestination(j.getString("destination"));
                    f.setLatitude(j.getJSONArray("location").getDouble(0));
                    f.setLongitude(j.getJSONArray("location").getDouble(1));
                    flights.add(f);
                }
                System.out.println(ja.toString());
                //StatusLine statusLine = response.getStatusLine();
                //int statusCode = statusLine.getStatusCode();
                //System.out.println(statusCode);
                System.out.println(response.getStatusLine().getStatusCode());
                for (Flight f : flights) {
                    System.out.print(f.toString());
                }

                return flights;

            } catch (Exception e){
                System.out.println(e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Flight> flights) {
            for (Flight f : flights) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(f.getLatitude(), f.getLongitude())).title(f.getFlightNum()).snippet(f.getOrigin() + " ➟ " + f.getDestination()).anchor(0.5f, 0.5f));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_large)));
                System.out.println(round_to_15(f.getTrack()));
                //Marker m = new Marker
                //mMap.addMarker(new MarkerOptions().)
                //Bitmap image = R.drawable.yellow_large;
            }
        }

        private String getToken() {
            return "212d9ca7e9090a71925d158646130ab4";
        }

        public ArrayList<Flight> getFlights() {
            return flights;
        }
    }


}