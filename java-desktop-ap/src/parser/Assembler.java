package parser;

public class Assembler {

    /**
     * Parses an assembly string like "ADD R3, R1, R2" into an Instruction object.
     */
    public Instruction parseLine(String line) {
        // Clean up formatting and remove commas
        line = line.trim().replaceAll(",", "");
        String[] parts = line.split("\\s+");

        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid instruction format: " + line);
        }

        String op = parts[0];
        int rd = parseRegister(parts[1]);
        int rs1 = parseRegister(parts[2]);
        int rs2 = parseRegister(parts[3]);

        return new Instruction(op, rd, rs1, rs2);
    }

    private int parseRegister(String regStr) {
        regStr = regStr.toUpperCase().replace("R", "");
        return Integer.parseInt(regStr);
    }

    public static class Instruction {
        private final String op;
        private final int rd;
        private final int rs1;
        private final int rs2;

        Instruction(String op, int rd, int rs1, int rs2) {
            this.op = op;
            this.rd = rd;
            this.rs1 = rs1;
            this.rs2 = rs2;
        }

        public String getOp() {
            return op;
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
}