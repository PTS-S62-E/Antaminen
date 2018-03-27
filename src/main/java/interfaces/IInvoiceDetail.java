package interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pts62.common.europe.ITransLocation;
import domain.InvoiceDetails;
import util.Deserializers.InvoiceDetailDeserializer;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: interfaces
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
@JsonDeserialize(as = InvoiceDetails.class)
public interface IInvoiceDetail {

    ArrayList<ITransLocation> locationPoints();

    String description();

    BigDecimal price();

    void setLocationPoints(ArrayList<ITransLocation> locationPoints);

    void setDescription(String description);

    void setPrice(BigDecimal price);

    ArrayList<ITransLocation> getLocationPoints();

    String getDescription();

    BigDecimal getPrice();

}
