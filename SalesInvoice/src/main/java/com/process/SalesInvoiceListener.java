package com.process;

import com.Interface.NewInvoiceDialog;
import com.Interface.SalesInvoiceWindow;
import com.Interface.NewItemDialog;
import com.data.SalesInvoiceHeader;
import com.data.SalesInvoiceLine;
import com.data.LeftInvoiceTableModel;
import com.data.RightInvoiceTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SalesInvoiceListener implements ActionListener {

    private SalesInvoiceWindow window;
    private NewInvoiceDialog headerDialog;
    private NewItemDialog lineDialog;

    public SalesInvoiceListener(SalesInvoiceWindow window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load File":
                loadFile();
                break;

            case "Save File":
                saveFile();
                break;

            case "Create New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "Save":
                saveNewItem();
                break;

            case "Cancel":
                cancelInvoiceItem();
                break;

            case "newInvoiceOK":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
                break;

        }

    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(window);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                ArrayList<SalesInvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] rowValues = headerLine.split(",");
                    String invID = rowValues[0];
                    String date = rowValues[1];
                    String customerName = rowValues[2];
                    int invoiceID = Integer.parseInt(invID);
                    Date invoiceDate = SalesInvoiceWindow.shortDateFormat.parse(date);
                    SalesInvoiceHeader header = new SalesInvoiceHeader(invoiceID, customerName, invoiceDate);
                    invoiceHeaders.add(header);
                }
                window.setInvoicesArray(invoiceHeaders);

                result = fileChooser.showOpenDialog(window);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fileChooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<SalesInvoiceLine> invoiceLines = new ArrayList<>();
                    for (String lineLine : lineLines) {
                        String[] rowValues = lineLine.split(",");
                        String invID = rowValues[0];
                        String itemName = rowValues[1];
                        String price = rowValues[2];
                        String quantity = rowValues[3];
                        int invoiceID = Integer.parseInt(invID);
                        double itemPrice = Double.parseDouble(price);
                        int itemQuantity = Integer.parseInt(quantity);
                        SalesInvoiceHeader invoice = window.getInvoiceObject(invoiceID);
                        SalesInvoiceLine line = new SalesInvoiceLine(itemName, itemPrice, itemQuantity, invoice);
                        invoice.getInvoiceLines().add(line);
                    }
                }
                LeftInvoiceTableModel headerTableModel = new LeftInvoiceTableModel(invoiceHeaders);
                window.setLeftTableModel(headerTableModel);
                window.getleftInvoicesTable().setModel(headerTableModel);
                System.out.println("Files read");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cancelInvoiceItem() {
        int selectedLineIndex = window.getleftInvoicesTable().getSelectedRow();
        int selectedInvoiceIndex = window.getleftInvoicesTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            window.getInvoiceLinesArray().remove(selectedLineIndex);
            RightInvoiceTableModel lineTableModel = (RightInvoiceTableModel) window.getleftInvoicesTable().getModel();
            lineTableModel.fireTableDataChanged();
            window.getInvoiceTotalLabelField().setText("" + window.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            window.getLeftTableModel().fireTableDataChanged();
            window.getleftInvoicesTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void saveFile() {
        ArrayList<SalesInvoiceHeader> invoicesArray = window.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showSaveDialog(window);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                String headers = "";
                String lines = "";
                for (SalesInvoiceHeader invoice : invoicesArray){
                    headers += invoice.toString();
                    headers += "\n";
                    for(SalesInvoiceLine line : invoice.getInvoiceLines()){
                        lines += line.toString();
                        lines+= "\n";
                    }
                }
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                result = fc.showSaveDialog(window);
                File lineFile = fc.getSelectedFile();
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(window, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void createNewInvoice() {
        headerDialog = new NewInvoiceDialog(window);
        headerDialog.setVisible(true);

    }

    private void deleteInvoice() {
        int selectedInvoiceIndex = window.getleftInvoicesTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            window.getInvoicesArray().remove(selectedInvoiceIndex);
            window.getLeftTableModel().fireTableDataChanged();

            window.getleftInvoicesTable().setModel(new RightInvoiceTableModel(null));
            window.setInvoiceLinesArray(null);
            window.getCustomerNameLabelField().setText("");
            window.getInvoiceNumberLabelField().setText("");
            window.getInvoiceTotalLabelField().setText("");
            window.getInvoiceDateLabelField().setText("");
        }
    }

    private void saveNewItem() {
        lineDialog = new NewItemDialog(window);
        lineDialog.setVisible(true);
    }

    private void newInvoiceDialogCancel() {
        headerDialog.setVisible(false);
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newInvoiceDialogOK() {
        headerDialog.setVisible(false);

        String custName = headerDialog.getCustomerNameField().getText();
        String str = headerDialog.getInvoiceDateField().getText();
        Date d = new Date();
        try {
            d = SalesInvoiceWindow.shortDateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(window, "Cannot parse date, resetting to today.", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (SalesInvoiceHeader inv : window.getInvoicesArray()) {
            if (inv.getInvoiceNumber() > invNum) {
                invNum = inv.getInvoiceNumber();
            }
        }
        invNum++;
        SalesInvoiceHeader newInv = new SalesInvoiceHeader(invNum, custName, d);
        window.getInvoicesArray().add(newInv);
        window.getLeftTableModel().fireTableDataChanged();
        headerDialog.dispose();
        headerDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);

        String name = lineDialog.getItemNameField().getText();
        String str1 = lineDialog.getItemQuantityField().getText();
        String str2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(str2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(window, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = window.getleftInvoicesTable().getSelectedRow();
        if (selectedInvHeader != -1) {
            SalesInvoiceHeader invHeader = window.getInvoicesArray().get(selectedInvHeader);
            SalesInvoiceLine line = new SalesInvoiceLine(name, price, count, invHeader);
            window.getInvoiceLinesArray().add(line);
            RightInvoiceTableModel lineTableModel = (RightInvoiceTableModel) window.getleftInvoicesTable().getModel();
            lineTableModel.fireTableDataChanged();
            window.getLeftTableModel().fireTableDataChanged();
        }
        window.getleftInvoicesTable().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

}
