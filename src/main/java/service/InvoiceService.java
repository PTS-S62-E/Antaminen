package service;

import dao.InvoiceDaoImpl;
import domain.Invoice;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import interfaces.IInvoiceDetail;
import interfaces.IInvoiceService;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.CountryCode;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InvoiceService implements IInvoiceService {

    @Inject
    InvoiceDaoImpl invoiceDao;

    public InvoiceService() { }

    /**
     * Create a new invoice based on the provided invoice object
     * @param invoice Object that is used to create the invoice
     * @return true if the invoice is created, false if not (probably an InvoiceException)
     * @throws InvoiceException thrown when the invoice couldn't be created. Check exception message for more details when thrown
     */
    @Override
    public boolean createInvoice(Invoice invoice) throws InvoiceException {
        if(invoice == null) { throw new InvoiceException("Please provide an invoice"); }
        if(invoice.invoiceDetails() == null || invoice.invoiceDetails().size() < 1) { throw new InvoiceException("No Invoice details found."); }
        if(invoice.getCountry().isEmpty()) { throw new InvoiceException("No country code provided in Invoice."); }
        if(invoice.getInvoiceDate().isEmpty()) { throw new InvoiceException("No InvoiceDate provided in Invoice."); }
        if(invoice.getPrice().compareTo(BigDecimal.ZERO) < 0) { throw new InvoiceException("No positive price provided in Invoice."); }

        boolean result = invoiceDao.createInvoice(invoice);
        return result;
    }

    /**
     *
     * @param invoiceDetails Arraylist of IInvoiceDetails containing the rows that are visible on the invoice with more information
     * @param countryCode Country for the invoice
     * @param invoiceDate Date for the invoice
     * @return true if the invoice is created, false if not (probably an InvoiceException)
     * @throws InvoiceException thrown when the invoice couldn't be created. Check exception message for more details when thrown
     */
    @Override
    public boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails, @Nullable String countryCode, @Nullable LocalDateTime invoiceDate) throws InvoiceException {
        if(invoiceDetails.size() < 1) { throw new InvoiceException("Please provide invoice details."); }
        for(IInvoiceDetail detail : invoiceDetails) {
            if(detail.price().compareTo(BigDecimal.ZERO) <= 0) { throw new InvoiceException("Please provide a positive price for the invoice datail."); }
            if(detail.locationPoints().size() < 1) { throw new InvoiceException("Please provide the locationpoints for the invoice detail."); }
            if(detail.description().isEmpty()) {
                detail.setDescription("No description provided");
            }
        }

        boolean result = false;

        if(countryCode == null && invoiceDate == null) {
            result = invoiceDao.createInvoice(invoiceDetails);
        } else if(countryCode == null) {
            result = invoiceDao.createInvoice(invoiceDetails, CountryCode.FINLAND, invoiceDate);
        } else if(invoiceDate == null) {
            result = invoiceDao.createInvoice(invoiceDetails, countryCode);
        } else {
            result = invoiceDao.createInvoice(invoiceDetails, countryCode, invoiceDate);
        }

        return result;
    }

    /**
     * Find an invoice based on the invoiceNumber
     * @param invoiceNumber number of the invoice that it should return
     * @return returns the invoice, if found, else null (probably InvoiceException)
     * @throws InvoiceException thrown when the invoice couldn't be found
     */
    @Override
    public IInvoice findInvoiceByInvoiceNumber(String invoiceNumber) throws InvoiceException {
        if(invoiceNumber.isEmpty()) { throw new InvoiceException("Please provide an invoice number"); }

        return invoiceDao.findInvoiceByInvoiceNumer(invoiceNumber);
    }
}
