package communication;

import exceptions.CommunicationException;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class SendRequest {

    /**
     * Send a GET request to the provided url
     * @param url The URL to send the GET request to
     * @return Returns a JSON string containing the data that is retrieved from the request
     * @throws IOException thrown when there's an exception in the communication with the external api
     * @throws CommunicationException thrown when the connection with the external api was OK, but the returned status code is
     *  different from the expected 200 (Status OK) or 204 (Status NO_CONTENT) status codes.
     */
    public static String sendGet(String url) throws IOException, CommunicationException {
        if(url.isEmpty() || url == null) { throw new CommunicationException("Please provide an URL for the request"); }

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Status Code: " + response.getStatusLine().getStatusCode() + "\n ReasonPhrase: " + response.getStatusLine().getReasonPhrase()).build());
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
