package interfaces;

import domain.Invoice;
import exceptions.InvoiceException;

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
public interface IInvoiceDao {

    /**
     * Create a new Invoice
     * @param invoice Invoice to be created
     * @return whether the creation of the invoice was successful
     */
    boolean createInvoice(Invoice invoice) throws InvoiceException;

    /**
     * Create a new Invoice with the provided InvoiceDetails
     * @param invoiceDetails Arraylist containing the InvoiceDetails for creating the Invoice
     * @return Whether the creation of the invoice was successful
     */
    boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails) throws InvoiceException;

    /**
     * Find an invoice based on the invoice number
     * @param invoiceNumber number of the invoice you want to retrieve
     * @return returns IInvoice object containing the invoice if found, null if not found
     */
    IInvoice findInvoiceByInvoiceNumer(String invoiceNumber) throws InvoiceException;
}
