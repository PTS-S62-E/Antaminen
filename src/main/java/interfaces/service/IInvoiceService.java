package interfaces.service;

import exceptions.InvoiceException;
import interfaces.domain.IInvoice;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;

public interface IInvoiceService {

    IInvoice findInvoiceByInvoiceNumber(String invoiceNumber) throws InvoiceException;

    ArrayList<IInvoice> findInvoiceByUser(long userId) throws InvoiceException;

    boolean payInvoice(String invoiceNumber, String paymentDetails) throws InvoiceException;
}
