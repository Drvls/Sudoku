package org.alexvsi.sudoku.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CheckGameStatusButton extends JButton {
    public CheckGameStatusButton(final ActionListener actionListener) {
        this.setText("Verificar status do jogo");
        this.addActionListener(actionListener);
    }
}
