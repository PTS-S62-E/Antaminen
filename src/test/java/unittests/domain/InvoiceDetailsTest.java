package unittests.domain;

import com.rekeningrijden.europe.interfaces.ITransLocation;
import domain.InvoiceDetails;
import domain.TransLocation;
import exceptions.InvoiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import util.CountryCode;

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
    private static long distance = 157225;

    @BeforeClass
    public static void setup() throws InvoiceException {
        locationPoints = new ArrayList<>();

        TransLocation tl1 = new TransLocation(1.0, 1.0, "01/01/2018", "abcABC123456", "FI");
        TransLocation tl2 = new TransLocation(2.0, 2.0, "01/01/2018", "abcABC123456", "FI");

        locationPoints.add(tl1);
        locationPoints.add(tl2);

        defaultDescription = "Sample description";
        defaultPrice = new BigDecimal(10.0);

//        invoiceDetails = new InvoiceDetails(locationPoints, defaultDescription, defaultPrice);

    }

    @Ignore
    @Test(expected = InvoiceException.class)
    public void constructorTest() throws InvoiceException {
//        InvoiceDetails id1 = new InvoiceDetails(locationPoints, defaultDescription, defaultPrice);

//        try {
//            // Locationpoints may not be null, expect Invoice exception
//            InvoiceDetails id2 = new InvoiceDetails(null, defaultDescription, defaultPrice);
//        } catch (InvoiceException ie) {
//            // Intentionally left blank
//        }
//
//        try {
//            // Description may not be null, expect Invoice exception
//            InvoiceDetails id3 = new InvoiceDetails(locationPoints, "", defaultPrice);
//        } catch (InvoiceException ie) {
//            // Intentionally left blank
//        }
//
//        // Price may not be negative, expect Invoice exception
//        InvoiceDetails id4 = new InvoiceDetails(locationPoints, defaultDescription, new BigDecimal(-10.0));

    }

    @Ignore
    @Test
    public void locationPoints() {
        Assert.assertEquals(locationPoints.size(), invoiceDetails.locationPoints().size());
        Assert.assertEquals(true, invoiceDetails.locationPoints().contains(locationPoints.get(0)));
        Assert.assertEquals(true, invoiceDetails.locationPoints().contains(locationPoints.get(1)));
    }

    @Ignore
    @Test
    public void description() {
        Assert.assertEquals(defaultDescription, invoiceDetails.description());

        String newDescription = "Sample description 2";
        invoiceDetails.setDescription(newDescription);

        Assert.assertEquals(newDescription, invoiceDetails.description());
    }

    @Ignore
    @Test
    public void price() {
        Assert.assertEquals(defaultPrice, invoiceDetails.price());

        BigDecimal newPrice = new BigDecimal(20.0);
//        invoiceDetails.setPrice(newPrice);

        Assert.assertEquals(newPrice, invoiceDetails.price());
    }

    @Ignore
    @Test
    public void setLocationPoints() {
        ArrayList<ITransLocation> points = new ArrayList<>();

        TransLocation loc1 = new TransLocation(1.0, 1.0, "2018-03-27", "0000000000", CountryCode.FINLAND);
        TransLocation loc2 = new TransLocation(2.0, 2.0, "2018-03-27", "0000000000", CountryCode.FINLAND);

        points.add(loc1);
        points.add(loc2);

        InvoiceDetails details = new InvoiceDetails();
//         details.setLocationPoints(points);

        Assert.assertEquals(points.size(), details.getLocationPoints().size());
    }

    @Ignore
    @Test
    public void setDescription() {
        String description = "Sample description";

        InvoiceDetails details = new InvoiceDetails();
        details.setDescription(description);

        Assert.assertEquals(description, details.getDescription());

        String newDescription = "Test description";
        details.setDescription(newDescription);

        Assert.assertEquals(newDescription, details.getDescription());
    }

    @Ignore
    @Test
    public void setPrice() {
        BigDecimal price = new BigDecimal(12.50);

        InvoiceDetails details = new InvoiceDetails();
//        details.setPrice(price);

        Assert.assertEquals(price, details.getPrice());

        BigDecimal newPrice = new BigDecimal(99.09);
//        details.setPrice(newPrice);

        Assert.assertEquals(newPrice, details.getPrice());
    }

    @Ignore
    @Test
    public void getLocationPoints() {
        Assert.assertEquals(locationPoints.size(), invoiceDetails.getLocationPoints().size());
    }

    @Ignore
    @Test
    public void getDescription() {
        String description = "Sample description";
        Assert.assertEquals(description, invoiceDetails.getDescription());
    }

    @Ignore
    @Test
    public void getPrice() {
        BigDecimal price = new BigDecimal(10.0);

        Assert.assertEquals(price, invoiceDetails.getPrice());
    }

    @Ignore
    @Test
    public void getDistance() {
        Assert.assertEquals(distance, invoiceDetails.getDistance(), 0.01);
    }

    @Ignore
    @Test
    public void setDistance() {
        long distance = 3441903;
        InvoiceDetails details = new InvoiceDetails();

        details.setDistance(distance);

        Assert.assertEquals(distance, details.getDistance());

        long newDistance = 3242901;

        details.setDistance(newDistance);

        Assert.assertEquals(newDistance, details.getDistance());
    }
}