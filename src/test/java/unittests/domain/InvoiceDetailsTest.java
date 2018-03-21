package unittests.domain;

import com.pts62.common.europe.ITransLocation;
import domain.InvoiceDetails;
import domain.TransLocation;
import exceptions.InvoiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 21-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: unittests.domain
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class InvoiceDetailsTest {

    private static InvoiceDetails invoiceDetails;
    private static ArrayList<ITransLocation> locationPoints;
    private static String defaultDescription;
    private static BigDecimal defaultPrice;

    @BeforeClass
    public static void setup() throws InvoiceException {
        locationPoints = new ArrayList<>();

        TransLocation tl1 = new TransLocation(1.0, 1.0, "01/01/2018", "abcABC123456", "FI");
        TransLocation tl2 = new TransLocation(2.0, 2.0, "01/01/2018", "abcABC123456", "FI");

        locationPoints.add(tl1);
        locationPoints.add(tl2);

        defaultDescription = "Sample description";
        defaultPrice = new BigDecimal(10.0);

        invoiceDetails = new InvoiceDetails(locationPoints, defaultDescription, defaultPrice);

    }

    @Test(expected = InvoiceException.class)
    public void constructorTest() throws InvoiceException {
        InvoiceDetails id1 = new InvoiceDetails(locationPoints, defaultDescription, defaultPrice);

        try {
            // Locationpoints may not be null, expect Invoice exception
            InvoiceDetails id2 = new InvoiceDetails(null, defaultDescription, defaultPrice);
        } catch (InvoiceException ie) {
            // Intentionally left blank
        }

        try {
            // Description may not be null, expect Invoice exception
            InvoiceDetails id3 = new InvoiceDetails(locationPoints, "", defaultPrice);
        } catch (InvoiceException ie) {
            // Intentionally left blank
        }

        // Price may not be negative, expect Invoice exception
        InvoiceDetails id4 = new InvoiceDetails(locationPoints, defaultDescription, new BigDecimal(-10.0));

    }

    @Test
    public void locationPoints() {
        Assert.assertEquals(locationPoints.size(), invoiceDetails.locationPoints().size());
        Assert.assertEquals(true, invoiceDetails.locationPoints().contains(locationPoints.get(0)));
        Assert.assertEquals(true, invoiceDetails.locationPoints().contains(locationPoints.get(1)));
    }

    @Test
    public void description() {
        Assert.assertEquals(defaultDescription, invoiceDetails.description());

        String newDescription = "Sample description 2";
        invoiceDetails.setDescription(newDescription);

        Assert.assertEquals(newDescription, invoiceDetails.description());
    }

    @Test
    public void price() {
        Assert.assertEquals(defaultPrice, invoiceDetails.price());

        BigDecimal newPrice = new BigDecimal(20.0);
        invoiceDetails.setPrice(newPrice);

        Assert.assertEquals(newPrice, invoiceDetails.price());
    }
}