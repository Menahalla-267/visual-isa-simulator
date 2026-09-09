package memory;

public class Cache {
    private static final int CACHE_LINES = 4; // Total number of lines in the cache
    private static final int BLOCK_SIZE = 4; // Size of each block in bytes (1 word)

    // Structure of a Cache Line
    private static class CacheLine {
        boolean validBit;
        int tag;
        int data;

        CacheLine() {
            this.validBit = false;
            this.tag = 0;
            this.data = 0;
        }
    }

    private final CacheLine[] lines;
    private final MainMemory mainMemory;

    // Statistics for visualization later
    private int hits = 0;
    private int misses = 0;

    public Cache(MainMemory mainMemory) {
        this.mainMemory = mainMemory;
        this.lines = new CacheLine[CACHE_LINES];
        for (int i = 0; i < CACHE_LINES; i++) {
            lines[i] = new CacheLine();
        }
    }

    /**
     * Reads a word from the cache using a Direct-Mapped mapping strategy.
     * If there's a miss, it fetches from MainMemory and updates the cache line.
     */
    public int read(int address) {
        // Direct-Mapped breakdown:
        // Index = (Address / BlockSize) % NumberOfCacheLines
        int index = (address / BLOCK_SIZE) % CACHE_LINES;
        int tag = address / (BLOCK_SIZE * CACHE_LINES);

        CacheLine line = lines[index];

        // Check for Cache Hit
        if (line.validBit && line.tag == tag) {
            hits++;
            System.out.println("[Cache HIT] Address: " + address + " found in Cache Line " + index);
            return line.data;
        }

        // Cache Miss: Fetch from Main Memory
        misses++;
        System.out.println("[Cache MISS] Address: " + address + " not in cache. Fetching from MainMemory...");

        int data = mainMemory.readWord(address);

        // Update Cache Line (Bring data into cache)
        line.validBit = true;
        line.tag = tag;
        line.data = data;

        return data;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}