package communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekeningrijden.europe.interfaces.ITransLocation;
import exceptions.CommunicationException;
import io.sentry.Sentry;
import org.apache.http.HttpResponse;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class RegistrationMovement {

    private final static String BASE_URL = "http://192.168.24.100:8082";
    private Properties properties;

    private static RegistrationMovement _instance;

    public static RegistrationMovement getInstance() {
        if(_instance == null) {
            new RegistrationMovement();
        }

        return _instance;
    }

    private RegistrationMovement() {
        InputStream input = null;
        try {
            _instance = this;
            input = new FileInputStream("paths.properties");
            properties.load(input);
        } catch (IOException e) {
            Sentry.capture(e);
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Sentry.capture(e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get the translocations for a vehicle by its ID and based on a start- and enddate.
     *
     * The date strings MUST be formatted as yyyy-MM-dd HH:mm
     * @param id VehicleID
     * @param startDate start date for your search
     * @param endDate end date for your search
     * @return Returns a list of ITranslocation for the specific period
     * @throws CommunicationException Thrown when there's an exception in communicating with the external API
     * @throws IOException Thrown when there is an exception in processing the data received from the external API
     */
    public List<ITransLocation> getTranslocationsForVehicleId(long id, String startDate, String endDate) throws CommunicationException, IOException {
        if(id < 1) { throw new CommunicationException("Please provide a vehicleId"); }

        String url = BASE_URL + properties.getProperty("TRANSLOCATION_FOR_VEHICLE_ID");
        url.replace(":id", String.valueOf(id));
        url.replace(":startdate", startDate);
        url.replace(":enddate", endDate);

        String response = SendRequest.sendGet(url);

        ObjectMapper mapper = new ObjectMapper();

        List<ITransLocation> result = mapper.readValue(response, new TypeReference<List<ITransLocation>>(){});

        return result;
    }
}
