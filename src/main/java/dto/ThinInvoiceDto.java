package dto;

import java.io.Serializable;

public class ThinInvoiceDto implements Serializable {
    private long id;
    private String invoiceDate;
    private int price;
    private boolean paymentStatus;

    public ThinInvoiceDto() { }

    public ThinInvoiceDto(long id, String invoiceDate, int price, boolean paymentStatus) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
