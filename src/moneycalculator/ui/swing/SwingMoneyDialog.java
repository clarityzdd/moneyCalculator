package moneycalculator.ui.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import moneycalculator.model.Currency;
import moneycalculator.model.Money;
import moneycalculator.ui.MoneyDialog;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {
    
    private final Currency[] currencies;
    private String amount;
    private Currency currency;

    public SwingMoneyDialog(Currency[] currencies) {
        this.currencies = currencies;
        this.add(amount());
        this.add(currency());
    }

    @Override
    public Money get() {
        return new Money(Double.parseDouble(amount), currency);
    }

    private Component amount() {
        JTextField field = new JTextField("0");
        field.setColumns(10);
        field.getDocument().addDocumentListener(amountChanged());
        amount = field.getText();
        return field;
    }

    private Component currency() {
        JComboBox combo = new JComboBox(currencies);
        combo.addItemListener(currencyChanged());
        currency = (Currency) combo.getSelectedItem();
        return combo;
    }

    private DocumentListener amountChanged() {
        return new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAmount(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAmount(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAmount(e.getDocument());
            }

            private void updateAmount(Document document) {
                try {
                    amount = document.getText(0, document.getLength());
                } 
                catch (BadLocationException ex) {
                    Logger.getLogger(SwingMoneyDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    private ItemListener currencyChanged() {
        return new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) return;
                currency = (Currency) e.getItem();
            }
        };
    }
    
}
