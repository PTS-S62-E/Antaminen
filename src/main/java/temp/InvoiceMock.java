package temp;

import com.pts62.common.europe.ITransLocation;
import domain.Invoice;
import domain.InvoiceDetails;
import domain.TransLocation;
import exceptions.InvoiceException;
import interfaces.IInvoiceDetail;
import util.CountryCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Logger;

public class InvoiceMock {

    private static InvoiceMock _instance;

    private ArrayList<Invoice> invoices;
    private ArrayList<ITransLocation> locationPoints;
    private ArrayList<IInvoiceDetail> invoiceDetails;

    private InvoiceMock() {
        _instance = this;

        invoices = new ArrayList<>();
        locationPoints = new ArrayList<>();
        invoiceDetails = new ArrayList<>();

        try {
            TransLocation loc1 = new TransLocation(1.0, 1.0, "2018-03-27", "0000000000", CountryCode.FINLAND);
            TransLocation loc2 = new TransLocation(2.0, 2.0, "2018-03-27", "0000000000", CountryCode.FINLAND);

            locationPoints.add(loc1);
            locationPoints.add(loc2);

            InvoiceDetails detail1 = new InvoiceDetails(locationPoints, "No Description Provided", new BigDecimal(25.50));
            InvoiceDetails detail2 = new InvoiceDetails(locationPoints, "No Description Provided", new BigDecimal(14.99));

            invoiceDetails.add(detail1);
            invoiceDetails.add(detail2);

            Invoice invoice1 = new Invoice(invoiceDetails, CountryCode.FINLAND, "2018-03-27", detail1.getPrice().add(detail2.getPrice()));
            invoice1.setInvoiceNumber("1");

            invoices.add(invoice1);
            locationPoints.clear();
            invoiceDetails.clear();

            TransLocation loc3 = new TransLocation(16.0, 12.9, "2018-03-26", "0000000000", CountryCode.FINLAND);
            TransLocation loc4 = new TransLocation(12.9, 16.0, "2018-03-26", "0000000000", CountryCode.FINLAND);

            locationPoints.add(loc3);
            locationPoints.add(loc4);

            InvoiceDetails detail3 = new InvoiceDetails(locationPoints, "No Description Provided", new BigDecimal(9.94));
            InvoiceDetails detail4 = new InvoiceDetails(locationPoints, "No Description Provided", new BigDecimal(16.21));

            invoiceDetails.add(detail3);
            invoiceDetails.add(detail4);

            Invoice invoice2 = new Invoice(invoiceDetails, CountryCode.FINLAND, "2018-03-27", detail1.getPrice().add(detail2.getPrice()));
            invoice2.setInvoiceNumber("2");

            invoices.add(invoice2);
        } catch (InvoiceException e) {
            e.printStackTrace();
        }
    }

    public static InvoiceMock getInstance() {
        if(_instance == null) {
            new InvoiceMock();
        }

        return _instance;
    }

    public Invoice findInvoice(String invoiceNumber) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.severe("severe");
        logger.info("info");
        logger.fine("fine");

        logger.warning("Searching for invoice with number " + invoiceNumber);
        for(Invoice invoice : invoices) {
            logger.warning("Invoice number is: " + invoice.getInvoiceNumber());
            if(invoice.getInvoiceNumber().toLowerCase().equals(invoiceNumber.toLowerCase())) {
                return invoice;
            }
        }

        return null;
    }

}
