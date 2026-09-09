package ui;

import core.CPU;
import core.RegisterFile;
import memory.Cache;
import memory.MainMemory;
import parser.Assembler;

// Instruction is provided by the core instruction model package used by the assembler/CPU.
// Keep this import aligned with the project definition to avoid compile issues.

import javax.swing.*;
import java.awt.*;

public class SimulatorWindow extends JFrame {
    private final CPU cpu;
    private final Cache cache;
    private final MainMemory memory;
    private final Assembler assembler;

    private JTextArea logArea;
    private JTextField inputField;
    private JLabel statusLabel;
    private JLabel[] registerLabels;

    public SimulatorWindow(CPU cpu, Cache cache, MainMemory memory, Assembler assembler) {
        this.cpu = cpu;
        this.cache = cache;
        this.memory = memory;
        this.assembler = assembler;

        setTitle("Visual ISA & Cache Simulator");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        updateRegisterDisplay();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Top Panel: Status & Input
        statusLabel = new JLabel("Status: Initialized and Ready", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        inputPanel.add(new JLabel("Assembly Instruction: "), BorderLayout.WEST);
        
        inputField = new JTextField("ADD R3, R1, R2");
        inputField.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel topWrapper = new JPanel(new BorderLayout(5, 5));
        topWrapper.add(statusLabel, BorderLayout.NORTH);
        topWrapper.add(inputPanel, BorderLayout.CENTER);
        add(topWrapper, BorderLayout.NORTH);

        // Center Panel: Execution Log (Left) & Register Viewer (Right)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Register Viewer Panel
        JPanel registerPanel = new JPanel(new GridLayout(16, 2, 5, 2));
        registerPanel.setBorder(BorderFactory.createTitledBorder("Registers (R0 - R15)"));
        registerLabels = new JLabel[16];
        
        RegisterFile rf = cpu.getRegisterFile();
        for (int i = 0; i < 16; i++) {
            registerLabels[i] = new JLabel(" R" + i + ": " + rf.read(i));
            registerLabels[i].setFont(new Font("Consolas", Font.PLAIN, 12));
            registerPanel.add(registerLabels[i]);
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, registerPanel);
        splitPane.setDividerLocation(650);
        add(splitPane, BorderLayout.CENTER);

        // Bottom Panel: Control Buttons
        JPanel controlPanel = new JPanel();
        JButton stepButton = new JButton("Step Instruction");
        JButton resetButton = new JButton("Reset Simulator");

        stepButton.addActionListener(e -> {
            try {
                String line = inputField.getText();
                Instruction inst = assembler.parseLine(line);
                
                String executionLog = cpu.executeStep(inst);
                logArea.append(executionLog + "\n");
                updateRegisterDisplay();
                statusLabel.setText("Status: Executed successfully");
            } catch (Exception ex) {
                logArea.append("Error: " + ex.getMessage() + "\n");
                statusLabel.setText("Status: Error in instruction format");
            }
        });

        resetButton.addActionListener(e -> {
            logArea.setText("Simulator reset.\n");
            updateRegisterDisplay();
            statusLabel.setText("Status: Reset");
        });

        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void updateRegisterDisplay() {
        RegisterFile rf = cpu.getRegisterFile();
        for (int i = 0; i < 16; i++) {
            registerLabels[i].setText(" R" + i + ": " + rf.read(i));
        }
    }
}