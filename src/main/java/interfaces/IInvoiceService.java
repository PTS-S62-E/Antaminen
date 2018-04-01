package interfaces;

import domain.Invoice;
import exceptions.InvoiceException;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IInvoiceService {

    IInvoice findInvoiceByInvoiceNumber(String invoiceNumber) throws InvoiceException;

    ArrayList<IInvoice> findInvoiceByUser(long userId) throws InvoiceException;

    boolean payInvoice(String invoiceNumber, String paymentDetails) throws InvoiceException;
}
