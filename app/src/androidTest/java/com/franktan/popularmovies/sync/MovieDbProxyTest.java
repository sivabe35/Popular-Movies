package com.franktan.popularmovies.sync;

import android.content.res.Resources;
import android.test.InstrumentationTestCase;

import com.franktan.popularmovies.model.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by tan on 16/08/2015.
 */
public class MovieDbProxyTest extends InstrumentationTestCase {

    /**
     * Test API key can be retrieved from environment variable
     * (For CI only)
     */
    public void testApiKey () {
        String apiKey = MovieDbProxy.getApiKey();
        assertTrue("API Key should not be null", (apiKey != null && apiKey != ""));
    }

    /**
     * Test parseJson method can correctly parse a typical movie json string
     */
    public void testParsingMovieJson () throws IOException {
        Resources res = getInstrumentation().getContext().getResources();
        InputStream in = res.getAssets().open("mock_movies.json");
        byte[] b  = new byte[(int) in.available()];
        int len = b.length;
        int total = 0;

        while (total < len) {
            int result = in.read(b, total, len - total);
            if (result == -1) {
                break;
            }
            total += result;
        }
        String movieJsonString = new String(b);

        List<Movie> movies = MovieDbProxy.parseJson(movieJsonString);
        assertNotNull("json string should be parsed and not returns null", movies);
        assertTrue("1st movie should match", movies.get(1).equals(SyncTestUtilities.createMovieNo1()));
        assertTrue("20th movie should match", movies.get(20).equals(SyncTestUtilities.createMovieNo20()));
    }

}