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

    int price();

    void setLocationPoints(ArrayList<TranslocationDto> locationPoints);

    void setDescription(String description);

    void setPrice(int price);

    ArrayList<TranslocationDto> getLocationPoints();

    String getDescription();

    int getPrice();

    long getDistance();

    void setDistance(long distance);

}
