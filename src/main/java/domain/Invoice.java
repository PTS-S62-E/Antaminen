package domain;

import interfaces.IInvoice;
import interfaces.IInvoiceDetail;

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
public class Invoice implements IInvoice {

    

    @Override
    public ArrayList<IInvoiceDetail> invoiceDetails() {
        return null;
    }

    @Override
    public String paymentDetails() {
        return null;
    }

    @Override
    public String getInvoiceNumber() {
        return null;
    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public boolean getPaymentStatus() {
        return false;
    }

    @Override
    public String getInvoiceDate() {
        return null;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
