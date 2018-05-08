package interfaces.dao;

import domain.Invoice;
import domain.InvoiceDetails;
import domain.Owner;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;
import interfaces.domain.IInvoiceDetail;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
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
    boolean createInvoice(ArrayList<InvoiceDetails> invoiceDetails, Owner owner, long vehicleId) throws InvoiceException;

    boolean createInvoice(ArrayList<InvoiceDetails> invoiceDetails, Owner owner, long vehicleId, String countryCode) throws InvoiceException;

    boolean createInvoice(ArrayList<InvoiceDetails> invoiceDetails, Owner owner, long vehicleId, String countryCode, LocalDateTime invoiceDate) throws InvoiceException;

    /**
     * Find an invoice based on the invoice number
     * @param invoiceNumber number of the invoice you want to retrieve
     * @return returns IInvoice object containing the invoice if found, null if not found
     */
    IInvoice findInvoiceByInvoiceNumer(long invoiceNumber) throws InvoiceException;

    ArrayList<IInvoice> findInvoiceByUser(long userId) throws InvoiceException;

    ArrayList<IInvoice> findInvoicesByUserAndVehicleId(long userId, long vehicleId) throws InvoiceException;

    boolean payInvoice(long invoiceNumber, String paymentDetails) throws InvoiceException;
}
