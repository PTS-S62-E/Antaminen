package dao;

import domain.Invoice;
import domain.Owner;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;
import interfaces.dao.IInvoiceDao;
import interfaces.domain.IInvoiceDetail;
import temp.InvoiceMock;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: dao
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

@Stateless
@LocalBean
public class InvoiceDao implements IInvoiceDao {

    private final String countryCode = "FI";
    private ArrayList<IInvoice> invoices = new ArrayList<>();

    public InvoiceDao() { }


    @Override
    public boolean createInvoice(Invoice invoice) throws InvoiceException {
        if(invoice == null) { throw new InvoiceException("Please provide an invoice"); }
        if(invoice.invoiceDetails() == null || invoice.invoiceDetails().size() < 1) { throw new InvoiceException("No Invoice details found."); }
        if(invoice.getCountry() == null || invoice.getCountry().isEmpty()) { throw new InvoiceException("No country code provided in Invoice."); }
        if(invoice.getInvoiceDate() == null) { throw new InvoiceException("No InvoiceDate provided in Invoice."); }
        if (invoice.getPrice() < 0) {
            throw new InvoiceException("No positive price provided in Invoice");
        }

        invoices.add(invoice);

        //TODO: Persist to database

        return true;
    }

    @Override
    public boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails, Owner owner, long vehicleId) throws InvoiceException {
        if(invoiceDetails == null || invoiceDetails.size() < 1) { throw new InvoiceException("Please provide invoiceDetails to generate the invoice."); }

        BigDecimal price = this.calculateTotalInvoicePrice(invoiceDetails);
        LocalDateTime currentDate = LocalDateTime.now();

        Invoice invoice = new Invoice(invoiceDetails, this.countryCode, currentDate.toString(), price);

        invoices.add(invoice);
        //TODO: Persist to database

        return true;

    }

    @Override
    public boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails, Owner owner, long vehicleId, String countryCode) throws InvoiceException {
        if(invoiceDetails == null || invoiceDetails.size() < 1) { throw new InvoiceException("Please provide invoiceDetails to generate the invoice."); }
        if(countryCode == null || countryCode.isEmpty()) { throw new InvoiceException("Please provide the country for this invoice."); }

        BigDecimal price = this.calculateTotalInvoicePrice(invoiceDetails);
        LocalDateTime currentDate = LocalDateTime.now();

        Invoice invoice = new Invoice(invoiceDetails, countryCode, currentDate.toString(), price);

        invoices.add(invoice);
        //TODO: Persist to database

        return true;    }

    @Override
    public boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails, Owner owner, long vehicleId, String countryCode, LocalDateTime invoiceDate) throws InvoiceException {
        if(invoiceDetails == null || invoiceDetails.size() < 1) { throw new InvoiceException("Please provide invoiceDetails to generate the invoice."); }
        if(countryCode == null || countryCode.isEmpty()) { throw new InvoiceException("Please provide the country for this invoice."); }
        if(invoiceDate == null) { throw new InvoiceException("Please provide an Invoice date."); }

        BigDecimal price = this.calculateTotalInvoicePrice(invoiceDetails);
        Invoice invoice = new Invoice(invoiceDetails, countryCode, invoiceDate.toString(), price);

        invoices.add(invoice);
        //TODO: Persist to database

        return true;
    }

    @Override
    public IInvoice findInvoiceByInvoiceNumer(String invoiceNumber) throws InvoiceException {
        if(invoiceNumber.isEmpty()) { throw new InvoiceException("Please provide an Invoice number."); }

        return InvoiceMock.getInstance().findInvoice(invoiceNumber);
    }

    @Override
    public ArrayList<IInvoice> findInvoiceByUser(long userId) throws InvoiceException {
        if(userId < 0) { throw new InvoiceException("Please provide a valid userId"); }

        return InvoiceMock.getInstance().getAllInvoices();
    }

    @Override
    public ArrayList<IInvoice> findInvoicesByUserAndVehicleId(long userId, long vehicleId) throws InvoiceException {
        return null;
    }

    @Override
    public boolean payInvoice(String invoiceNumber, String paymentDetails) throws InvoiceException {
        if(invoiceNumber.isEmpty()) { throw new InvoiceException("Please provide an invoice number"); }
        if(paymentDetails.isEmpty()) { throw new InvoiceException("Please provide payment details"); }

        return InvoiceMock.getInstance().payInvoice(invoiceNumber, paymentDetails);
    }

    private BigDecimal calculateTotalInvoicePrice(ArrayList<IInvoiceDetail> invoiceDetails) throws InvoiceException {
        if(invoiceDetails == null || invoiceDetails.size() < 1) { throw new InvoiceException("Please provide Invoice details to calculate the total price."); }

        BigDecimal totalPrice = BigDecimal.valueOf(0.0);

        for(IInvoiceDetail detail : invoiceDetails) {
            totalPrice = totalPrice.add(detail.price());
        }

        return totalPrice;
    }
}
