package domain;

import interfaces.domain.IInvoice;
import interfaces.domain.IInvoiceDetail;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
@NamedQueries({
        @NamedQuery(name = "Invoice.findByInvoiceNumber", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
        @NamedQuery(name = "Invoice.findByUserId", query = "SELECT i FROM Invoice i WHERE i.owner.id = :userId"),
        @NamedQuery(name = "Invoice.findInvoiceByUserIdAndVehicleId", query = "SELECT i FROM Invoice i WHERE i.owner.id = :userId AND i.vehicleId = :vehicleId"),
        @NamedQuery(name = "Invoice.findThinInvoiceByUserId", query = "SELECT i.id as id, i.invoiceDate as invoicedate, i.price as price, i.paymentStatus as paymentstatus FROM Invoice i WHERE i.owner.id = :userId")
})
public class Invoice implements IInvoice, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "invoice_details_join",
                joinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "invoice_detail_id", referencedColumnName = "id"))
    private List<InvoiceDetails> invoiceDetails;
    private String paymentDetails;
    private String country;


    private boolean paymentStatus;
    private String invoiceDate;
    private int price; // Price in cents
    private long totalDistance;

    @ManyToOne(optional = false)
    private Owner owner;

    private long vehicleId;

    public Invoice() { }

    public Invoice(ArrayList<InvoiceDetails> invoiceDetails, String country, String invoiceDate, Owner owner, long vehicleId) {
        this.invoiceDetails = new ArrayList<>();
        this.invoiceDetails.addAll(invoiceDetails);
        this.country = country;
        this.invoiceDate = invoiceDate;
        this.owner = owner;
        this.vehicleId = vehicleId;

        for(IInvoiceDetail detail : invoiceDetails) {
            this.totalDistance += detail.getDistance();
            this.price += detail.getPrice();
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getInvoiceNumber() {
        return String.valueOf(this.id);
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
        return (double) this.price;
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
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void setInvoiceDate(String date) {
        this.invoiceDate = date;
    }

    @Override
    public ArrayList<InvoiceDetails> getInvoiceDetails() {
        return new ArrayList<>(this.invoiceDetails);
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
