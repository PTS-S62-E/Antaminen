package communication;

import exceptions.CommunicationException;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;


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

        Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Request URL: " + url).build());

        return getResult(response);
    }


    public static String sendPost(String url, String StringifiedJson) throws IOException, CommunicationException {
        if(url.isEmpty() || url == null) {
            throw new CommunicationException("Please provide an URL for the request");
        }

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        StringEntity requestEntity = new StringEntity(
                StringifiedJson,
                ContentType.APPLICATION_JSON);

        request.setEntity(requestEntity);

        HttpResponse response = client.execute(request);

        Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Request URL: " + url).build());

        return getResult(response);
    }

    private static String getResult(HttpResponse response) throws CommunicationException, IOException {
        if(response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
            Sentry.getContext().recordBreadcrumb(
                    new BreadcrumbBuilder()
                            .setMessage("Status Code: " + response.getStatusLine().getStatusCode()
                                    + "\n ReasonPhrase: " + response.getStatusLine().getReasonPhrase()
                                    + "\n" + EntityUtils.toString(response.getEntity())).build());
            Sentry.capture(response.getStatusLine().toString());
            throw new CommunicationException("Received unexpected status code from external API");
        } else {

            String result = EntityUtils.toString(response.getEntity());
            return result.toString();
        }
    }
}
