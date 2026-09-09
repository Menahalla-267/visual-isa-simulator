package core;

import memory.Cache;
import parser.Instruction;

public class CPU {
    private final RegisterFile registerFile;
    private final ALU alu;
    private final Cache cache;
    private int programCounter;

    public CPU(Cache cache) {
        this.registerFile = new RegisterFile();
        this.alu = new ALU();
        this.cache = cache;
        this.programCounter = 0;
        
        // Seed initial values
        registerFile.write(1, 15);
        registerFile.write(2, 25);
    }

    public String executeStep(Instruction inst) {
        StringBuilder log = new StringBuilder();
        log.append("Fetching instruction at PC = ").append(programCounter).append("\n");

        String op = inst.getOperation();

        switch (op) {
            case "LW" -> {
                // Format: LW Rd, address (using rs1 as address offset or base)
                int address = inst.getRs1();
                int value = cache.read(address);
                registerFile.write(inst.getRd(), value);

                log.append("--- Memory Load Simulation ---\n");
                log.append("Executed: LW R").append(inst.getRd()).append(", [").append(address).append("]\n");
                log.append("Loaded Value: ").append(value).append(" into R").append(inst.getRd()).append("\n");
            }
            case "SW" -> {
                // Format: SW Rs1, address
                int address = inst.getRs2();
                int value = registerFile.read(inst.getRs1());
                cacheWrite(address, value);

                log.append("--- Memory Store Simulation ---\n");
                log.append("Executed: SW R").append(inst.getRs1()).append(", [").append(address).append("]\n");
                log.append("Stored Value: ").append(value).append(" to address ").append(address).append("\n");
            }
            default -> {
                // Default ALU operations (ADD, SUB, etc.)
                int operand1 = registerFile.read(inst.getRs1());
                int operand2 = registerFile.read(inst.getRs2());
                int result = alu.execute(op, operand1, operand2);
                registerFile.write(inst.getRd(), result);

                log.append("--- ALU Execution Simulation ---\n");
                log.append("Executed: ").append(op)
                   .append(" R").append(inst.getRd())
                   .append(", R").append(inst.getRs1())
                   .append(", R").append(inst.getRs2()).append("\n");
                log.append("Result (R").append(inst.getRd()).append("): ").append(result).append("\n");
            }
        }
        
        programCounter += 4; 
        return log.toString();
    }

    private void cacheWrite(int address, int value) {
        try {
            cache.getClass().getMethod("write", int.class, int.class)
                    .invoke(cache, address, value);
            return;
        } catch (NoSuchMethodException ignored) {
            // Fall back to the cache's alternate write/store API name if present.
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to write to cache", e);
        }

        try {
            cache.getClass().getMethod("store", int.class, int.class)
                    .invoke(cache, address, value);
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("Cache does not support writing values by address", e);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to write to cache", e);
        }
    }

    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public ALU getAlu() {
        return alu;
    }
}