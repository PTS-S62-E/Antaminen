package interfaces.domain;

import com.rekeningrijden.europe.interfaces.ISubInvoice;
import domain.InvoiceDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: interfaces
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public interface IInvoice extends ISubInvoice {

    /**
     * Get information about the rows on an invoice
     *
     * @return Arraylist containing IInvoiceDetails instances that describe different rows on an invoice
     */
    List<InvoiceDetails> invoiceDetails();

    String paymentDetails();

    void setInvoiceDetails(ArrayList<InvoiceDetails> invoiceDetails);

    void setPaymentDetails(String paymentDetails);


    void setCountry(String country);

    void setPaymentStatus(boolean paymentStatus);

    boolean getPaymentStatus();

    void setPrice(int price);

    void setInvoiceDate(String date);

    ArrayList<InvoiceDetails> getInvoiceDetails();

    String getPaymentDetails();

    long getTotalDistance();

    void setTotalDistance(long totalDistance);

}
