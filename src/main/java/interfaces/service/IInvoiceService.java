package interfaces.service;

import dto.ThinInvoiceDto;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.ArrayList;

public interface IInvoiceService {

    IInvoice findInvoiceByInvoiceNumber(long invoiceNumber) throws InvoiceException;

    ArrayList<ThinInvoiceDto> findInvoiceByUser(long userId) throws InvoiceException;

    ArrayList<ThinInvoiceDto> findInvoicesByLicensePlate(String licensePlate) throws InvoiceException;

    boolean payInvoice(long invoiceNumber, String paymentDetails) throws InvoiceException;

    void generateInvoices() throws InvoiceException;

    void generateInvoicesForVehiclesOfForeignCountries() throws InvoiceException;
}
