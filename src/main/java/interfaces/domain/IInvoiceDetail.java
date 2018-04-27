package interfaces.domain;

import com.rekeningrijden.europe.interfaces.ITransLocation;
import dto.TranslocationDto;

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

public interface IInvoiceDetail {

    ArrayList<TranslocationDto> locationPoints();

    String description();

    BigDecimal price();

    void setLocationPoints(ArrayList<TranslocationDto> locationPoints);

    void setDescription(String description);

    void setPrice(BigDecimal price);

    ArrayList<TranslocationDto> getLocationPoints();

    String getDescription();

    BigDecimal getPrice();

    long getDistance();

    void setDistance(long distance);

}
