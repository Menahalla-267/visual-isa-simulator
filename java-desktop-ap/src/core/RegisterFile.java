package core;

public class RegisterFile {
    // Array to hold our 32 integer registers
    private final int[] registers;
    public RegisterFile() {
        registers = new int[32];
        // Initialize all registers to 0
        for (int i = 0; i < 32; i++) {
            registers[i] = 0;
        }
    }

    // Read the value from a specific register (R0 to R31)
    public int read(int registerIndex) {
        if (registerIndex >= 0 && registerIndex < 32) {
            return registers[registerIndex];
        }
        throw new IllegalArgumentException("Invalid register index: " + registerIndex);
    }

    // Write a value to a specific register
    public void write(int registerIndex, int value) {
        // In most RISC architectures, Register 0 is hardwired to 0 and cannot be changed.
        if (registerIndex == 0) {
            System.out.println("Warning: Cannot write to R0. It is hardwired to 0.");
            return; 
        }
        
        if (registerIndex > 0 && registerIndex < 32) {
            registers[registerIndex] = value;
        } else {
            throw new IllegalArgumentException("Invalid register index: " + registerIndex);
        }
    }
    
    // A helper method to print the registers to your VS Code terminal
    // (This will be very useful for testing before we build the visual UI)
    public void printRegisters() {
        System.out.println("--- Register States ---");
        // We will just print the first 8 to keep the terminal clean during early testing
        for (int i = 0; i < 8; i++) { 
            System.out.println("R" + i + ": " + registers[i]);
        }
    }
}