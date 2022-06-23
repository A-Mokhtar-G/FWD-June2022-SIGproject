
package com.data;

import com.Interface.SalesInvoiceWindow;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class LeftInvoiceTableModel extends AbstractTableModel{
    
    private ArrayList<SalesInvoiceHeader> invoicesArray;

    public LeftInvoiceTableModel(ArrayList<SalesInvoiceHeader> invoicesArray) {
        this.invoicesArray = invoicesArray;
    }

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SalesInvoiceHeader invoice = invoicesArray.get(rowIndex);
        if (columnIndex == 0){
            return invoice.getInvoiceNumber();
        }else if (columnIndex == 1){
            return SalesInvoiceWindow.shortDateFormat.format(invoice.getInvoiceDate());
        }else if (columnIndex == 2){
            return invoice.getCustomerName();
        }else if (columnIndex == 3){
            return invoice.getInvoiceTotal();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int column){
        switch (column) {
            case 0 -> {
                return "Invoice ID";
            }
            case 1 -> {
                return "Date";
            }
            case 2 -> {
                return "Customer Name";
            }
            case 3 -> {
                return "Total";
            }
            default -> {
                    return"Unnamed column";
            }
        }
        
    }
    
}
