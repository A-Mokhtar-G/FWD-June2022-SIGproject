
package com.data;

public class SalesInvoiceLine {
    
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private SalesInvoiceHeader header;

    public SalesInvoiceLine() {
    }

    public SalesInvoiceLine(String itemName, double itemPrice, int count, SalesInvoiceHeader header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = count;
        this.header = header;
    }

    public SalesInvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(SalesInvoiceHeader header) {
        this.header = header;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return itemCount;
    }

    public void setCount(int count) {
        this.itemCount = count;
    }
    
    public double getTotal(){
        return itemPrice * itemCount;
    }

    @Override
    public String toString() {
        return header.getInvoiceNumber() + "," + itemName + "," + itemCount;
    }
    
}
