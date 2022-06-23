package com.Interface;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewInvoiceDialog extends JDialog {

    private JTextField customerNameField;
    private JTextField invoiceDateField;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLabel;
    private JButton okButton;
    private JButton cancelButton;

    public NewInvoiceDialog(SalesInvoiceWindow window) {
        customerNameLabel = new JLabel("Customer Name");
        customerNameField = new JTextField(20);
        invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.setActionCommand("newInvoiceOK");
        cancelButton.setActionCommand("newInvoiceCancel");

        okButton.addActionListener(window.getActionListener());
        cancelButton.addActionListener(window.getActionListener());
        setLayout(new GridLayout(3, 2));

        add(invoiceDateLabel);
        add(invoiceDateField);
        add(customerNameLabel);
        add(customerNameField);
        add(okButton);
        add(cancelButton);

        pack();

    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }

}
