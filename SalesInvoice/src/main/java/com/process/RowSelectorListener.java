package com.process;

import com.Interface.SalesInvoiceWindow;
import com.data.SalesInvoiceHeader;
import com.data.SalesInvoiceLine;
import com.data.RightInvoiceTableModel;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RowSelectorListener implements ListSelectionListener {

    private SalesInvoiceWindow window;

    public RowSelectorListener(SalesInvoiceWindow window) {
        this.window = window;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvoiceIndex = window.getleftInvoicesTable().getSelectedRow();
        System.out.println("invoice selected: " + selectedInvoiceIndex);
        if (selectedInvoiceIndex != -1) {
            SalesInvoiceHeader selectedInvoice = window.getInvoicesArray().get(selectedInvoiceIndex);
            ArrayList<SalesInvoiceLine> lines = selectedInvoice.getInvoiceLines();
            RightInvoiceTableModel lineTableModel = new RightInvoiceTableModel(lines);
            window.setInvoiceLinesArray(lines);
            window.getItemsTable().setModel(lineTableModel);
            window.getCustomerNameLabelField().setText(selectedInvoice.getCustomerName());
            window.getInvoiceNumberLabelField().setText("" + selectedInvoice.getInvoiceNumber());
            window.getInvoiceTotalLabelField().setText("" + selectedInvoice.getInvoiceTotal());
            window.getInvoiceDateLabelField().setText(SalesInvoiceWindow.shortDateFormat.format(selectedInvoice.getInvoiceDate()));
        }
    }

}
