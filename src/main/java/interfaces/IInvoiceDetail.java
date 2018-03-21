package interfaces;

import com.pts62.common.europe.ITransLocation;

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

    ArrayList<ITransLocation> locationPoints();

    String description();

    BigDecimal price();

    void setLocationPoints(ArrayList<ITransLocation> locationPoints);

    void setDescription(String description);

    void setPrice(BigDecimal price);

}
