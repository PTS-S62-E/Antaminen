package service;

import dao.InvoiceDao;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;
import interfaces.service.IInvoiceService;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
@LocalBean
public class InvoiceService implements IInvoiceService {

    @EJB
    InvoiceDao invoiceDao;

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
