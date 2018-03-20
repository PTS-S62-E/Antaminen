package interfaces;

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
public interface IInvoice extends com.pts62.common.europe.ISubInvoice {

    /**
     * Get information about the rows on an invoice
     *
     * @return Arraylist containing IInvoiceDetails instances that describe different rows on an invoice
     */
    ArrayList<IInvoiceDetail> invoiceDetails();

    String paymentDetails();
}
