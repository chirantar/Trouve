package thenvengers.com.trouve.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import thenvengers.com.trouve.FlickrApi;

public class Http {
    final static String tag = "Http";
    public static String getRequest(String uri) throws IOException {
        //String uri1 = FlickrApi.getFinalUrl("fun");
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {

            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String res = readStream(in);
            return res;
        }
        catch (Exception e) {
            Log.e(tag,"Error in getRequest " + e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    private static String readStream(BufferedReader in) throws IOException {
        String inputLine ;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine())!=null) {
            response.append(inputLine);
        }
        return response.toString();
    }
}
