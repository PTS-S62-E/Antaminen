package domain;

import com.rekeningrijden.europe.interfaces.ITransLocation;
import communication.RegistrationMovement;
import dto.TranslocationDto;
import exceptions.CommunicationException;
import exceptions.InvoiceException;
import interfaces.domain.IInvoiceDetail;
import io.sentry.Sentry;
import util.DistanceCalculator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: domain
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

@Entity
@Table(name= "InvoiceDetails")
public class InvoiceDetails implements IInvoiceDetail, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private List<TranslocationDto> locationPoints;

    @ElementCollection
    private List<Long> locationPointsIds;

    private String description;
    private BigDecimal price;
    private long distance;

    public InvoiceDetails() { }

    public InvoiceDetails(ArrayList<TranslocationDto> locationPoints, String description, BigDecimal price) throws InvoiceException {
        if(locationPoints == null || locationPoints.size() < 2) { throw new InvoiceException("No locationPoints provided for InvoiceDetails or only 1 provided."); }
        if(description.isEmpty()) { throw new InvoiceException("Please provide a description for this InvoiceDetail."); }
        if(price == null || price.compareTo(BigDecimal.ZERO) < 0) { throw new InvoiceException("Please provide a positive price for this InvoiceDetail."); }

        for(int i = 0; i < locationPoints.size() -1; i++) {
            // Change the 0.0 params if you also want to take elevation into account
            int j = i + 1;
            this.distance += DistanceCalculator.distance(locationPoints.get(i).getLatitude(), locationPoints.get(j).getLatitude(),
                                                        locationPoints.get(i).getLongitude(), locationPoints.get(j).getLongitude(),
                                                        0.0, 0.0);
        }

        for (TranslocationDto locationPoint : locationPoints) {
            locationPointsIds.add(locationPoint.getTranslocationId());
        }

        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public ArrayList<TranslocationDto> locationPoints() {
        return (ArrayList<TranslocationDto>) this.locationPoints;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public void setLocationPoints(ArrayList<TranslocationDto> locationPoints) {
        this.locationPoints = locationPoints;

        for(TranslocationDto dto : locationPoints) {
            this.locationPointsIds.add(dto.getTranslocationId());
        }
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public ArrayList<TranslocationDto> getLocationPoints() {
        if(this.locationPoints == null || this.locationPoints.isEmpty()) {
            try {
                for (long id : this.locationPointsIds) {
                        this.locationPoints.add(RegistrationMovement.getInstance().getTranslocationById(id));
                }
            } catch (CommunicationException | IOException e) {
                Sentry.capture(e);
                e.printStackTrace();
            }
        }
        return (ArrayList<TranslocationDto>) this.locationPoints;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public long getDistance() {
        return this.distance;
    }

    @Override
    public void setDistance(long distance) {
        this.distance = distance;
    }

    public List<Long> getLocationPointsIds() {
        return locationPointsIds;
    }

    public void setLocationPointsIds(List<Long> locationPointsIds) {
        this.locationPointsIds = locationPointsIds;
    }
}
