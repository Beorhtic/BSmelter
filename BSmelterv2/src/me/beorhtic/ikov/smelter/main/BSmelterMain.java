package me.beorhtic.ikov.smelter.main;

import me.beorhtic.ikov.smelter.main.bars.BSmelterBronze;
import me.beorhtic.ikov.smelter.main.bars.BSmelterIron;
import me.beorhtic.ikov.smelter.main.bars.BSmelterSteel;
import simple.api.script.ScriptManifest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static simple.api.script.Category.SMITHING;

@ScriptManifest(author = "Beorhtic", name = "BSmelter", category = SMITHING, version = "1.0", description = "Smelts bars", discord = "", servers = {"Ikov"})

public class BSmelterMain extends JFrame implements ActionListener {

    private JComboBox<String> scriptList;
    private JButton runButton;

    public BSmelterMain() {
        setTitle("BSmelter -- Select Bar Type!!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);

        // Create JComboBox
        scriptList = new JComboBox<>();
        scriptList.addItem("Bronze Bar");
        scriptList.addItem("Iron Bar");
        scriptList.addItem("Steel Bar");

        // Create JButton
        runButton = new JButton("Run Script");
        runButton.addActionListener(this);

        // Add components to JFrame
        Container container = getContentPane();
        container.setLayout(new GridLayout(2, 1));
        container.add(scriptList);
        container.add(runButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedScript = (String) scriptList.getSelectedItem();
        switch (selectedScript) {
            case "Bronze Bar":
                BSmelterBronze.main(null);
                break;
            case "Iron Bar":
                BSmelterIron.main(null);
                break;
            case "Steel Bar":
                BSmelterSteel.main(null);
                break;
            default:
                JOptionPane.showMessageDialog(this, "TEST TEST TEST TEST TEST");
                break;
        }
    }

    public static void main(String[] args) {
        new BSmelterMain() {
            @Override
            public void onExecute() {
            }

            @Override
            public int onDelayedProcess() {
                return 0;
            }

            @Override
            public void onProcess() {
            }

            @Override
            public void onTerminate() {

            }
        };
    }

    public void onExecute() {

    }

    public int onDelayedProcess() {
        return 0;
    }

    public void onProcess() {

    }

    public void onTerminate() {

    }
}