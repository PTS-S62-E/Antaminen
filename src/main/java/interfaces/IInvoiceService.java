package interfaces;

import domain.Invoice;
import exceptions.InvoiceException;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IInvoiceService {

    boolean createInvoice(Invoice invoice) throws InvoiceException;

    boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails, @Nullable String countryCode, @Nullable LocalDateTime invoiceDate) throws InvoiceException;

    IInvoice findInvoiceByInvoiceNumber(String invoiceNumber) throws InvoiceException;
}
