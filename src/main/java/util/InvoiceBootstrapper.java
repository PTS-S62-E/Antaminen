package util;

import interfaces.IInvoiceDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class InvoiceBootstrapper {

    private ArrayList<IInvoiceDetail> invoiceDetails;
    private String countryCode;
    private String invoiceDate;

    public InvoiceBootstrapper() { }

    public ArrayList<IInvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(ArrayList<IInvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
