package interfaces.dao;

import com.rekeningrijden.europe.dtos.SubInvoiceDto;
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
     * Create a new invoice based on the SubInvoiceDto received from an external country
     * @param subInvoiceDto Instance of the invoice received by external country
     * @return true if invoice created, false if not (probably an exception)
     * @throws InvoiceException Thrown when an error occurs in saving the new invoice
     */
    boolean createExternalInvoice(SubInvoiceDto subInvoiceDto) throws InvoiceException;

    /**
     * Find an invoice based on the invoice number
     * @param invoiceNumber number of the invoice you want to retrieve
     * @return returns IInvoice object containing the invoice if found, null if not found
     */
    IInvoice findInvoiceByInvoiceNumer(long invoiceNumber) throws InvoiceException;

    /**
     *
     * @param userId
     * @return List with object array because the executed SQL query does not select all values in the database
     * @throws InvoiceException
     */
    List<Object[]> findInvoiceByUser(long userId) throws InvoiceException;

    /**
     *
     * @param vehicleId
     * @return List with object array because the executed SQL query does not select all values in the database
     * @throws InvoiceException
     */
    List<Object[]> findInvoicesByVehicleId(long vehicleId) throws InvoiceException;

    ArrayList<IInvoice> findInvoicesByUserAndVehicleId(long userId, long vehicleId) throws InvoiceException;

    boolean payInvoice(long invoiceNumber, String paymentDetails) throws InvoiceException;
}
