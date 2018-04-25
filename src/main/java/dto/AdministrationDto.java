package dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdministrationDto implements Serializable {
    List<JourneyDto> journeys;

    public AdministrationDto(List<JourneyDto> journeys){
        this.journeys = journeys;
    }

    public AdministrationDto() {
        this.journeys = new ArrayList<>();
    }

    public List<JourneyDto> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyDto> journeys) {
        this.journeys = journeys;
    }
}
