import core.CPU;
import javax.swing.SwingUtilities;
import memory.Cache;
import memory.MainMemory;
import ui.SimulatorWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Visual ISA & Cache Simulator...");

        // 1. Initialize Main Memory with 1024 bytes of space
        MainMemory memory = new MainMemory(1024);

        // 2. Initialize the Cache, linking it to our Main Memory
        Cache cache = new Cache(memory);

        // 3. Initialize the CPU
        CPU cpu = new CPU(cache);

        // 4. Launch the Graphical User Interface (GUI)
        SwingUtilities.invokeLater(() -> {
            SimulatorWindow window = new SimulatorWindow(cpu, cache, memory, null);
            window.setVisible(true);
        });
    }
}


