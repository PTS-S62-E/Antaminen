package unittests.domain;

import com.pts62.common.europe.ITransLocation;
import domain.Invoice;
import domain.InvoiceDetails;
import domain.TransLocation;
import exceptions.InvoiceException;
import interfaces.IInvoiceDetail;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: domain
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class InvoiceTest {

    private static Invoice invoice;
    private static ArrayList<ITransLocation> locationPoints;
    private static ArrayList<IInvoiceDetail> invoiceDetails;

    @BeforeClass
    public static void setup() throws InvoiceException {
        locationPoints = new ArrayList<>();
        invoiceDetails = new ArrayList<>();

        TransLocation tl1 = new TransLocation(1.0, 1.0, "01/01/2018", "abcABC123456", "FI");
        TransLocation tl2 = new TransLocation(2.0, 2.0, "01/01/2018", "abcABC123456", "FI");

        locationPoints.add(tl1);
        locationPoints.add(tl2);

        InvoiceDetails id1 = new InvoiceDetails(locationPoints, "Test route", new BigDecimal(10.0));

        invoiceDetails.add(id1);

        invoice = new Invoice(invoiceDetails, "FI", "01/01/2018", new BigDecimal(10.0));
    }

    @Test
    public void invoiceDetails() {
        Assert.assertEquals(invoiceDetails.size(), invoice.invoiceDetails().size());

        Assert.assertEquals(true, invoice.invoiceDetails().contains(invoiceDetails.get(0)));
    }

    @Test
    public void paymentDetails() {
        String paymentDetails = "Payed";

        invoice.setPaymentDetails(paymentDetails);

        Assert.assertEquals(paymentDetails, invoice.paymentDetails());
    }

    @Test
    public void getInvoiceNumber() {
        String invoiceNumber = "FI-Invoice-000001";

        invoice.setInvoiceNumber(invoiceNumber);

        Assert.assertEquals(invoiceNumber, invoice.getInvoiceNumber());
    }

    @Test
    public void getCountry() {
        Assert.assertEquals("FI", invoice.getCountry());

        String country = "NL";
        invoice.setCountry(country);

        Assert.assertEquals(country, invoice.getCountry());
    }

    @Test
    public void getPaymentStatus() {
        boolean paymentStatus = true;
        boolean paymentStastus2 = false;

        invoice.setPaymentStatus(paymentStatus);

        Assert.assertEquals(paymentStatus, invoice.getPaymentStatus());

        invoice.setPaymentStatus(paymentStastus2);

        Assert.assertEquals(paymentStastus2, invoice.getPaymentStatus());
    }

    @Test
    public void getInvoiceDate() {
        Assert.assertEquals("01/01/2018", invoice.getInvoiceDate());

        String newDate = "02/02/2018";

        invoice.setInvoiceDate(newDate);

        Assert.assertEquals(newDate, invoice.getInvoiceDate());
    }

    @Test
    public void getPrice() {
        Assert.assertEquals(new BigDecimal(10.0), invoice.getPrice());

        BigDecimal newPrice = new BigDecimal(20.0);
        invoice.setPrice(newPrice);

        Assert.assertEquals(newPrice, invoice.getPrice());
    }

    

    @Test
    public void setInvoiceDetails() {
    }

    @Test
    public void setPaymentDetails() {
    }

    @Test
    public void setInvoiceNumber() {
    }

    @Test
    public void setCountry() {
    }

    @Test
    public void setPaymentStatus() {
    }

    @Test
    public void setPrice() {
    }

    @Test
    public void setInvoiceDate() {
    }

    @Test
    public void getInvoiceDetails() {
    }

    @Test
    public void getPaymentDetails() {
    }

    @Test
    public void getTotalDistance() {
    }

    @Test
    public void setTotalDistance() {
    }

    @Test
    public void isPaymentStatus() {
    }
}