package dao;

import domain.Invoice;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import interfaces.IInvoiceDao;
import interfaces.IInvoiceDetail;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: dao
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class InvoiceDaoImpl implements IInvoiceDao {


    @Override
    public boolean createInvoice(Invoice invoice) throws InvoiceException {
        throw new NotImplementedException();
    }

    @Override
    public boolean createInvoice(ArrayList<IInvoiceDetail> invoiceDetails) throws InvoiceException {
        return false;
    }

    @Override
    public IInvoice findInvoiceByInvoiceNumer(String invoiceNumber) throws InvoiceException {
        return null;
    }
}
