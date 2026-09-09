package parser;

public class Instruction {
    private final String operation;
    private final int rd; // Destination register
    private final int rs1; // Source register 1
    private final int rs2; // Source register 2 (or immediate value)

    public Instruction(String operation, int rd, int rs1, int rs2) {
        this.operation = operation.toUpperCase();
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    public String getOperation() {
        return operation;
    }

    public int getRd() {
        return rd;
    }

    public int getRs1() {
        return rs1;
    }

    public int getRs2() {
        return rs2;
    }
}