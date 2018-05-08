package communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AdministrationDto;
import dto.TranslocationDto;
import dto.VehicleDto;
import exceptions.CommunicationException;
import io.sentry.Sentry;
import javax.ejb.Singleton;
import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
public class RegistrationMovement {

    private static String BASE_URL;
    private Properties properties;

    private static RegistrationMovement _instance;

    public static RegistrationMovement getInstance() {
        if(_instance == null) {
            new RegistrationMovement();
        }

        return _instance;
    }

    /**
     * Create a new RegistrationMoment instance. We want to load the "paths.properties" file here and throw an exception
     * is something goes wrong.
     *
     * The property file is used to get the endpoints that are needed for communication with the external api
     */
    private RegistrationMovement() {
        InputStream input = null;
        properties = new Properties();
        try {
            _instance = this;
            input = getClass().getClassLoader().getResourceAsStream("paths.properties");
            properties.load(input);

            BASE_URL = properties.getProperty("BASE_URL");
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
    public AdministrationDto getTranslocationsForVehicleId(long id, String startDate, String endDate) throws CommunicationException, IOException {
        if(id < 1) { throw new CommunicationException("Please provide a vehicleId"); }

        String urlPart = properties.getProperty("TRANSLOCATION_FOR_VEHICLE_ID");

        urlPart = urlPart.replace(":id", String.valueOf(id));
        urlPart = urlPart.replace(":startdate", startDate);
        urlPart = urlPart.replace(":enddate", endDate);
        urlPart = urlPart.replace(" ", "%20");
        urlPart = urlPart.replace(":", "%3A");


        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        if(response.isEmpty()) { return null; }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, AdministrationDto.class);
    }

    /**
     * Get a single translocation from the movement registration api based on the ID of the translocations
     * @param id ID of the translocation
     * @return Returns a TranslocationDTO containing the Translocation that was fetched from the external api
     * @throws CommunicationException Thrown when there's an exception in processing the data from the external api
     * @throws IOException Thrown when there's an exception in communication with the external api
     */
    public TranslocationDto getTranslocationById(long id) throws CommunicationException, IOException {
        if(id < 1) { throw new CommunicationException("Please provide a TranslocationId"); }

        String urlPart = properties.getProperty("TRANSLOCATION_BY_ID");
        urlPart = urlPart.replace(":id", String.valueOf(id));

        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        if(response.isEmpty()) { return null; }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, TranslocationDto.class);
    }

    public VehicleDto getVehicleById(long vehicleId) throws CommunicationException, IOException {
        if(vehicleId < 1) { throw new CommunicationException("Please provide a valid vehicleId"); }

        String urlPart = properties.getProperty("VEHICLE_BY_ID");
        urlPart = urlPart.replace(":id", String.valueOf(vehicleId));

        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        Logger logger = Logger.getLogger(getClass().getName());
        logger.warning(url);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, VehicleDto.class);
    }
}
