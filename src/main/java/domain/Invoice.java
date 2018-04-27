package domain;

import com.rekeningrijden.europe.interfaces.ISubInvoice;
import interfaces.domain.IInvoice;
import interfaces.domain.IInvoiceDetail;
import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
@Table(name = "Invoice")
public class Invoice implements IInvoice, ISubInvoice, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinTable(name= "invoice_details_join",
                joinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false),
                inverseJoinColumns = @JoinColumn(name = "invoice_detail_id", referencedColumnName = "id", nullable = false))
    private List<InvoiceDetails> invoiceDetails;
    private String paymentDetails;
    private String country;

    
    private boolean paymentStatus;
    private String invoiceDate;
    private BigDecimal price;
    private long totalDistance;

    @ManyToOne(optional = false)
    private Owner owner;

    public Invoice() { }

    public Invoice(ArrayList<InvoiceDetails> invoiceDetails, String country, String invoiceDate, BigDecimal price, Owner owner) {
        this.invoiceDetails.addAll(invoiceDetails);
        this.country = country;
        this.invoiceDate = invoiceDate;
        this.price = price;
        this.owner = owner;

        for(IInvoiceDetail detail : invoiceDetails) {
            this.totalDistance += detail.getDistance();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getInvoiceNumber() {
        throw new NotImplementedException("Can't retrieve invoice number");
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public boolean getPaymentStatus() {
        return this.paymentStatus;
    }

    @Override
    public String getInvoiceDate() {
        return this.invoiceDate;
    }

    @Override
    public double getPrice() {
        return this.price.doubleValue();
    }

    @Override
    public List<InvoiceDetails> invoiceDetails() {
        return this.invoiceDetails;
    }

    @Override
    public String paymentDetails() {
        return this.paymentDetails;
    }

    @Override
    public void setInvoiceDetails(ArrayList<InvoiceDetails> invoiceDetails) { this.invoiceDetails = invoiceDetails; }

    @Override
    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public void setInvoiceDate(String date) {
        this.invoiceDate = date;
    }

    @Override
    public ArrayList<InvoiceDetails> getInvoiceDetails() {
        return (ArrayList<InvoiceDetails>) this.invoiceDetails;
    }

    @Override
    public String getPaymentDetails() {
        return this.paymentDetails;
    }

    @Override
    public long getTotalDistance() {
        return this.totalDistance;
    }

    @Override
    public void setTotalDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }
}
