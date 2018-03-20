package domain;

import interfaces.IInvoice;
import interfaces.IInvoiceDetail;

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
public class Invoice implements IInvoice {

    private ArrayList<IInvoiceDetail> invoiceDetails;
    private String paymentDetails;
    private String invoiceNumber;
    private String country;
    private boolean paymentStatus;
    private String invoiceDate;
    private BigDecimal price;

    public Invoice() { }
    public Invoice(ArrayList<IInvoiceDetail> invoiceDetails, String country, String invoiceDate, BigDecimal price) {
        this.invoiceDetails = invoiceDetails;
        this.country = country;
        this.invoiceDate = invoiceDate;
        this.price = price;
    }

    @Override
    public ArrayList<IInvoiceDetail> invoiceDetails() {
        return this.invoiceDetails;
    }

    @Override
    public String paymentDetails() {
        return this.paymentDetails;
    }

    @Override
    public String getInvoiceNumber() {
        return this.invoiceNumber;
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
        try {
            throw new Exception("This method should return a BigDecimal instead of double");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
