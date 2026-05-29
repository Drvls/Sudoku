package org.alexvsi.sudoku.ui.custom.screen;

import org.alexvsi.sudoku.model.Space;
import org.alexvsi.sudoku.service.BoardService;
import org.alexvsi.sudoku.service.NotifierService;
import org.alexvsi.sudoku.ui.custom.button.CheckGameStatusButton;
import org.alexvsi.sudoku.ui.custom.button.FinishGameButton;
import org.alexvsi.sudoku.ui.custom.button.ResetGameButton;
import org.alexvsi.sudoku.ui.custom.frame.MainFrame;
import org.alexvsi.sudoku.ui.custom.input.NumberText;
import org.alexvsi.sudoku.ui.custom.panel.MainPanel;
import org.alexvsi.sudoku.ui.custom.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.*;
import static org.alexvsi.sudoku.service.EventEnum.CLEAR_SPACE;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton resetGameButton;
    private JButton finishGameButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for(int r = 0; r < 9; r+=3){
            var endRow = r+2;
            for(int c = 0; c < 9; c+=3){
                var endCol = c+2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                mainPanel.add(generateSection(spaces));
            }
        }


        addResetButton(mainPanel);
        addShowGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
                                            final int initCol, final int endCol,
                                            final int initRow, final int endRow
    ){
        List<Space> spaceSector = new ArrayList<>();
        for(int r = initRow; r <= endRow; r++){
            for(int c = initCol; c <= endCol; c++){
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscrib(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if(boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null, "Parabéns, você concluiu o jogo");
                resetGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }
            else{
                var msg = "Seu jogo tem alguma inconsistência, ajuste e tente novamente";
                JOptionPane.showMessageDialog(null, msg);
            }
        });

        mainPanel.add(finishGameButton);
    }

    private void addShowGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo não esta completo";
                case COMPLETE -> "O jogo esta completo";
            };
            message += hasErrors ? " e contém erros" : " e não contém erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetGameButton = new ResetGameButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente reiniciar o jogo?",
                    "Reiniciar jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );
            if(dialogResult==0){
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetGameButton);
    }
}
