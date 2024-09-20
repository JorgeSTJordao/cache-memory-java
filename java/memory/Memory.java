package memory;

public interface Memory {

    int read(int address);

    void write(int address, int word);

    /**
     * Verifies if address is storage in local memory.
     *
     * @param address The address to verify.
     * @return True if address is storage in local memory, false otherwise.
     */
    boolean contains(int address);

    int getFinalAddress();
}
