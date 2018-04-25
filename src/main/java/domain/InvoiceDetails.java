package domain;

import com.rekeningrijden.europe.interfaces.ITransLocation;
import exceptions.InvoiceException;
import interfaces.domain.IInvoiceDetail;
import util.DistanceCalculator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

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
    private ArrayList<ITransLocation> locationPoints;

    private String description;
    private BigDecimal price;
    private long distance;

    public InvoiceDetails() { }

    public InvoiceDetails(ArrayList<ITransLocation> locationPoints, String description, BigDecimal price) throws InvoiceException {
        if(locationPoints == null || locationPoints.size() < 2) { throw new InvoiceException("No locationPoints provided for InvoiceDetails or only 1 provided."); }
        if(description.isEmpty()) { throw new InvoiceException("Please provide a description for this InvoiceDetail."); }
        if(price == null || price.compareTo(BigDecimal.ZERO) < 0) { throw new InvoiceException("Please provide a positive price for this InvoiceDetail."); }

        for(int i = 0; i < locationPoints.size() -1; i++) {
            // Change the 0.0 params if you also want to take elevation into account
            int j = i + 1;
            this.distance += DistanceCalculator.distance(locationPoints.get(i).getLat(), locationPoints.get(j).getLat(),
                                                        locationPoints.get(i).getLon(), locationPoints.get(j).getLon(),
                                                        0.0, 0.0);
        }

        this.locationPoints = locationPoints;
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
    public ArrayList<ITransLocation> locationPoints() {
        return this.locationPoints;
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
    public void setLocationPoints(ArrayList<ITransLocation> locationPoints) {
        this.locationPoints = locationPoints;
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
    public ArrayList<ITransLocation> getLocationPoints() {
        return this.locationPoints;
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
}
