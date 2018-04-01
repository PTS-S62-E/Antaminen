package service;

import dao.InvoiceDaoImpl;
import domain.Invoice;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import interfaces.IInvoiceDetail;
import interfaces.IInvoiceService;
import org.jetbrains.annotations.Nullable;
import util.CountryCode;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ApplicationScoped
@Model
public class InvoiceService implements IInvoiceService {

    @Inject
    InvoiceDaoImpl invoiceDao;

    public InvoiceService() { }

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

    @Override
    public ArrayList<IInvoice> findInvoiceByUser(long userId) throws InvoiceException {
        if(userId < 0) { throw new InvoiceException("Please provide a valid userId"); }

        return invoiceDao.findInvoiceByUser(userId);
    }

    @Override
    public boolean payInvoice(String invoiceNumber, String paymentDetails) throws InvoiceException {
        if(invoiceNumber.isEmpty()) { throw new InvoiceException("Please provide an invoice number"); }
        if(paymentDetails.isEmpty()) { throw new InvoiceException("Please provide payment details"); }

        return invoiceDao.payInvoice(invoiceNumber, paymentDetails);
    }
}
