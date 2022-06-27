
package com.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SalesInvoiceHeader {
    
    private int invoiceNumber;
    private String customerName;
    private Date invoiceDate;
    private ArrayList<SalesInvoiceLine> invoiceLines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public SalesInvoiceHeader() {
    }

    public SalesInvoiceHeader(int InvoiceNumber, String CustomerName, Date InvoiceDate) {
        this.invoiceNumber = InvoiceNumber;
        this.customerName = CustomerName;
        this.invoiceDate = InvoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date InvoiceDate) {
        this.invoiceDate = InvoiceDate;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int InvoiceNumber) {
        this.invoiceNumber = InvoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String CustomerName) {
        this.customerName = CustomerName;
    }

    public ArrayList<SalesInvoiceLine> getInvoiceLines() {
        if (invoiceLines == null){
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }
    
    public void setInvoiceLines(ArrayList<SalesInvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
    
    public double getInvoiceTotal(){
        double total = 0.0;
         for (int i = 0; i<getInvoiceLines().size(); i++)
         {
             total += getInvoiceLines().get(i).getTotal();
         }
        return total;
    }

    @Override
    public String toString() {
        return invoiceNumber + "," + df.format(invoiceDate) + "," + customerName;
    }
    
    
    
}
