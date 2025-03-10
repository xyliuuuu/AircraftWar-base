package edu.hitsz.application;

import edu.hitsz.application.game.BaseGame;
import edu.hitsz.dao.Round;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;

/**
 * Used to display the ranking menu.
 */
public class RankingMenu {
    private JPanel mainRankingMenu;
    private JPanel topMenu;
    private JPanel bottomMenu;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton bottomDelete;
    private JScrollPane tableScrollPane;
    private JLabel labelDifficulty;

    private DefaultTableModel model;
    private final String[] columnName = {"Rank", "Player Name", "Score", "Time"};

    /**
     * Constructor.
     */
    public RankingMenu() {
        mainRankingMenu = new JPanel(new BorderLayout()); // Use BorderLayout to manage the main panel layout
        topMenu = new JPanel(new BorderLayout());         // Top panel
        bottomMenu = new JPanel(new FlowLayout());        // Bottom panel
        headerLabel = new JLabel("Ranking List");
        scoreTable = new JTable();
        scoreTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        bottomDelete = new JButton("Delete Record");
        tableScrollPane = new JScrollPane();
        labelDifficulty = new JLabel();

        String[] difficultyNames = {"EASY", "NORMAL", "HARD"};
        updateData();

        // Set the model to the table and initialize the scroll pane
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);

        // Set the top and bottom panels
        topMenu.add(headerLabel, BorderLayout.CENTER);
        bottomMenu.add(bottomDelete);
        bottomMenu.add(labelDifficulty);

        // Add panels to the main panel
        mainRankingMenu.add(topMenu, BorderLayout.NORTH);
        mainRankingMenu.add(tableScrollPane, BorderLayout.CENTER);
        mainRankingMenu.add(bottomMenu, BorderLayout.SOUTH);

        // Delete function
        bottomDelete.addActionListener(e -> {
            int[] rows = scoreTable.getSelectedRows(); // Get all selected rows
            if (rows.length > 0) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all selected records?");
                if (result == JOptionPane.YES_OPTION) {
                    // Delete from back to front to avoid errors caused by changing row numbers during deletion
                    for (int i = rows.length - 1; i >= 0; i--) {
                        model.removeRow(rows[i]);
                        BaseGame.dao.removeRound(rows[i]);
                    }
                    updateData();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select the records to delete first!");
            }
        });

        labelDifficulty.setText("Difficulty: " + difficultyNames[BaseGame.gameDifficulty]);
    }

    /**
     * Update the panel to display the latest data.
     */
    public void updateData() {
        List<Round> roundList = BaseGame.dao.getAllRounds();
        String[][] tableData = new String[roundList.size()][4];
        for (int i = 0; i < roundList.size(); i++) {
            tableData[i][0] = String.valueOf(i + 1);
            tableData[i][1] = roundList.get(i).getName();
            tableData[i][2] = String.valueOf(roundList.get(i).getScore());
            tableData[i][3] = roundList.get(i).getRecordTime();
        }
        if (model == null) {
            model = new DefaultTableModel(tableData, columnName) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false; // Disable cell editing
                }
            };
        }
        model.setDataVector(tableData, columnName);
        BaseGame.dao.save(); // Save data changes
    }

    public JPanel getMainPanel() {
        return mainRankingMenu;
    }
}


