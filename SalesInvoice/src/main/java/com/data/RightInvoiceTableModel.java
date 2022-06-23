
package com.data;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class RightInvoiceTableModel extends AbstractTableModel {
    
    
    private ArrayList<SalesInvoiceLine> itemsArray;

    public RightInvoiceTableModel(ArrayList<SalesInvoiceLine> itemsArray) {
        this.itemsArray = itemsArray;
    }
    

    @Override
    public int getRowCount() {
        return itemsArray == null ? 0 : itemsArray.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (itemsArray == null)
            return "";
        SalesInvoiceLine line = itemsArray.get(rowIndex);
        if (columnIndex == 0){
            return line.getItemName();
        }else if (columnIndex == 1){
            return line.getItemPrice();
        }else if (columnIndex == 2){
            return line.getCount();
        }else if (columnIndex == 3){
            return line.getTotal();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int column){
        switch (column) {
            case 0 -> {
                return "Item Name";
            }
            case 1 -> {
                return "Item Price";
            }
            case 2 -> {
                return "Item Quantity";
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
