package memory;

public class MainMemory {
    private final byte[] memory;

    public MainMemory(int sizeInBytes) {
        memory = new byte[sizeInBytes];
    }

    // Read a 4-byte integer from a specific memory address
    public int readWord(int address) {
        if (address < 0 || address + 3 >= memory.length) {
            throw new IndexOutOfBoundsException("Memory out of bounds at address: " + address);
        }

        // Combine 4 bytes into a single integer (Big-Endian format)
        return ((memory[address] & 0xFF) << 24) |
               ((memory[address + 1] & 0xFF) << 16) |
               ((memory[address + 2] & 0xFF) << 8) |
               (memory[address + 3] & 0xFF);
    }

    // Write a 4-byte integer to a specific memory address
    public void writeWord(int address, int value) {
        if (address < 0 || address + 3 >= memory.length) {
            throw new IndexOutOfBoundsException("Memory out of bounds at address: " + address);
        }
        
        memory[address]     = (byte) (value >> 24);
        memory[address + 1] = (byte) (value >> 16);
        memory[address + 2] = (byte) (value >> 8);
        memory[address + 3] = (byte) value;
    }
}