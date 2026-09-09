package core;

public class ALU {
    public ALU() {

        // It doesn't hold state (memory), so the constructor is empty.
    }

    /**
     * Executes an arithmetic or bitwise operation.
     * 
     * @param operation The instruction type (e.g., "ADD", "SUB")
     * @param operand1  The first value (usually from a register)
     * @param operand2  The second value (from a register or an immediate value)
     * @return          The computed result */
    public int execute(String operation, int operand1, int operand2) {
        return switch (operation.toUpperCase()) {
            case "ADD" -> operand1 + operand2;
            case "SUB" -> operand1 - operand2;
            case "MUL" -> operand1 * operand2;
            case "AND" -> operand1 & operand2; // Bitwise AND
            case "OR" -> operand1 | operand2; // Bitwise OR
            default -> throw new IllegalArgumentException("Unsupported ALU operation: " + operation);
        };
    }
}