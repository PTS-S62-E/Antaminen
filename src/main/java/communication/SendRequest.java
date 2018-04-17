package communication;

import exceptions.CommunicationException;
import io.sentry.Sentry;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendRequest {

    public static String sendGet(String url) throws IOException, CommunicationException {
        if(url.isEmpty() || url == null) { throw new CommunicationException("Please provide an URL for the request"); }

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() != 200) {
            Sentry.capture(response.getStatusLine().toString());
            throw new CommunicationException("Received unexpected status code from external API");
        } else {

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }
    }
}
