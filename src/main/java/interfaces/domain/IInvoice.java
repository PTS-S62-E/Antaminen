package interfaces.domain;

import com.rekeningrijden.europe.interfaces.ISubInvoice;

import java.math.BigDecimal;
import java.util.ArrayList;

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
    ArrayList<IInvoiceDetail> invoiceDetails();

    String paymentDetails();

    void setInvoiceDetails(ArrayList<IInvoiceDetail> invoiceDetails);

    void setPaymentDetails(String paymentDetails);

    void setInvoiceNumber(String invoiceNumber);

    void setCountry(String country);

    void setPaymentStatus(boolean paymentStatus);

    void setPrice(BigDecimal price);

    void setInvoiceDate(String date);

    ArrayList<IInvoiceDetail> getInvoiceDetails();

    String getPaymentDetails();

    long getTotalDistance();

    void setTotalDistance(long totalDistance);

}
