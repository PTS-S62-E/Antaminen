package communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import exceptions.CategoryException;
import exceptions.CommunicationException;
import io.sentry.Sentry;
import util.LocalDateUtil;

import javax.ejb.Singleton;
import java.io.*;
import java.util.ArrayList;
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

    /**
     * Get a category by name from the registration movement application
     * @param name Name of the category
     * @return Returns a DTO object containing the Category information
     * @throws CategoryException Thrown if the {@param name} is not provided or empty
     * @throws IOException Thrown when there's an exception in reading the response for the HTTP request
     * @throws CommunicationException Thrown when an unexpected response code is returned form the Registration Movement application
     */
    public CategoryDto getCategory(String name) throws CategoryException, IOException, CommunicationException {
        if (name.isEmpty()){
            throw new CategoryException("name cannot be empty");
        }

        String urlPart = properties.getProperty("GET_CATEGORY");
        urlPart = urlPart.replace(":name", name);
        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        System.out.println(response);

        if (response.isEmpty()){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, CategoryDto.class);
    }

    /**
     * Get information about a vehicle from the Registration Movement application using the vehicleID
     * @param vehicleId ID of the vehicle you want to request information from
     * @return Returns a VehicleDTO containing information about the vehicle
     * @throws CommunicationException Thrown when an unexpected response code is returned from the Registration Movement application
     * @throws IOException Thrown when there's an exception in reading the response for the HTTP request
     */
    public VehicleDto getVehicleById(long vehicleId) throws CommunicationException, IOException {
        if(vehicleId < 1) { throw new CommunicationException("Please provide a valid vehicleId"); }

        String urlPart = properties.getProperty("VEHICLE_BY_ID");
        urlPart = urlPart.replace(":id", String.valueOf(vehicleId));

        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        Logger logger = Logger.getLogger(getClass().getName());
        logger.warning(url);

        if (response.isEmpty()){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, VehicleDto.class);
    }

    /**
     * Get information about a vehicle from the Registration Mevement application using the license plate of the vehicle.
     * This only difference between this method and {@link RegistrationMovement#getVehicleById(long)} is the use of the licensePlate property instead of the vehicleId property
     * @param licensePlate Licenseplate of the vehicle
     * @return Returns a DTO object containing information about the vehicle
     * @throws CommunicationException Thrown when an unexpected response code is returned from the Registration Movement application
     * @throws IOException Thrown when there's an exception in reading the response for the HTTP request
     */
    public VehicleDto getVehicleByLicensePlate(String licensePlate) throws CommunicationException, IOException {
        if(licensePlate == null ||  licensePlate.equals("")) { throw new CommunicationException("Please provide a licenseplate"); }

        String urlPart = properties.getProperty("VEHICLE_BY_LICENSEPLATE");
        urlPart = urlPart.replace(":licensePlate", licensePlate);

        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        Logger logger = Logger.getLogger(getClass().getName());
        logger.warning(url);

        if (response.isEmpty()){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, VehicleDto.class);
    }

    /**
     * Create a new category in the Registration Movement application
     * Please make sure that the category is created in the Registration Movement application
     * before storing the same category in the database that's been used by this application
     *
     * To check whether the category was created on the Registration Movement application, you can use {@link RegistrationMovement#getCategory(String)}
     * @param categoryDto DTO object containing the information about the category that needs to be created
     * @throws CategoryException Thrown when {@param categoryDto} is null or doesn't have a name
     * @throws IOException Thrown when there's an exception in reading the response for the HTTP request
     * @throws CommunicationException Thrown when an unexpected response code is returned from the Registration Movement application
     */
    public void createCategory(CategoryDto categoryDto) throws CategoryException, IOException, CommunicationException {
        if (categoryDto == null){
            throw new CategoryException("CategoryDto cannot be null");
        }

        if (categoryDto.getName().equals("")){
            throw new CategoryException("name cannot be empty");
        }

        String urlPart = properties.getProperty("CREATE_CATEGORY");
        String url = BASE_URL + urlPart;

        ObjectMapper mapper = new ObjectMapper();
        String categoryDtoAsJson = mapper.writeValueAsString(categoryDto);

        SendRequest.sendPost(url, categoryDtoAsJson);
    }

    /**
     * Get all translocations from vehicles that are not registered in Finland. These vehicles are foreign vehicles.
     *
     * The params {@param startDate} and {@param endDate} are a string representation of dates that need to be
     * formatted as yyyy-MM-dd HH:mm
     * @param startDate The date you want to start searching for translocations from foreign vehicles
     * @param endDate The date you want to stop searching for translocations from foreign vehicles
     * @return Returns an arraylist of {@link ForeignVehicleDto ForeignVehicleDto objects}
     * @throws CommunicationException Thrown when an unexpected response code is returned from the Registration Movement application
     * @throws IOException Thrown when there's an exception in reading the response for the HTTP request
     */
    public ArrayList<ForeignVehicleDto> getTranslocationsForForeignCars(String startDate, String endDate) throws CommunicationException, IOException {
        if(startDate.isEmpty()) { throw new CommunicationException("Please provide a startDate"); }
        if(endDate.isEmpty()) { throw new CommunicationException("Please provide an endDate"); }

//        if(!LocalDateUtil.isStringDateValid(startDate)) { throw new CommunicationException("Provided startDate is in an incorrect format."); }
//        if(!LocalDateUtil.isStringDateValid(endDate)) { throw new CommunicationException("Provided endDate is in an incorrect format."); }

        String urlPart = properties.getProperty("TRANSLOCATION_FOR_FOREIGN_VEHICLE");
        urlPart = urlPart.replace(":startDate", startDate);
        urlPart = urlPart.replace(":endDate", endDate);
        urlPart = urlPart.replace(" ", "%20");

        String url = BASE_URL + urlPart;

        String response = SendRequest.sendGet(url);

        if(response.isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response, new TypeReference<ArrayList<ForeignVehicleDto>>(){});
    }
}
