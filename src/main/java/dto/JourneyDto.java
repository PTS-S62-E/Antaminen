package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rekeningrijden.europe.interfaces.IJourney;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JourneyDto implements Serializable {

    private List<TranslocationDto> translocations;

    public JourneyDto(List<TranslocationDto> journeys) {
        this.translocations = journeys;
    }

    public JourneyDto() {
        this.translocations = new ArrayList<>();
    }

    public List<TranslocationDto> getTranslocations() {
        return translocations;
    }

    public void setTranslocations(List<TranslocationDto> translocations) {
        this.translocations = translocations;
    }
}
