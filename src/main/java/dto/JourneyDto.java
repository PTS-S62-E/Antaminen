package dto;

import com.rekeningrijden.europe.interfaces.IJourney;

import java.util.ArrayList;
import java.util.List;

public class JourneyDto {

    private List<IJourney> journeys;

    public JourneyDto(List<IJourney> journeys) {
        this.journeys = journeys;
    }

    public JourneyDto() {
        this.journeys = new ArrayList<>();
    }

    public List<IJourney> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<IJourney> journeys) {
        this.journeys = journeys;
    }
}
