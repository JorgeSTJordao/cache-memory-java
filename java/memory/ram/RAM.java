package memory.ram;

import memory.Memory;
import util.BitsConverter;

public class RAM implements Memory { // Memory é a classe pai

    private final int[] MEMORY;

    // Declaração da memória RAM
    public RAM(int addressBitQuantity) {
        int size = BitsConverter.convertToMaxPositiveNumber(addressBitQuantity);
        this.MEMORY = new int[size]; // Tamanho da memória RAM
    }



    public RAM(int[] memory) {
        verifyMemory(memory);
        this.MEMORY = memory; // Atribuição da memória 
    }

    private void verifyMemory(int[] memory) { // Condições para não extrapolar o espaço de memória
        if (memory == null) throw new RAMException("The memory can't be null");
        if (memory.length == 0) throw new RAMException("The memory length should be higher than 0");
    }

    @Override

    // Leitura de endereço
    public int read(int address) {
        verifyAddress(address); // Verifica se não excede o espaço de endereços
        return MEMORY[address];
    }

    @Override

    // Armazenamento de valores
    public void write(int address, int word) {
        verifyAddress(address); // Verifica se não excede o espaço de endereços
        MEMORY[address] = word;
    }

    private void verifyAddress(int address) {
        if (!contains(address)) { // O valor excede?
            throw new RAMException(String.format("The address %d is out of bounds", address));
        }
    }

    @Override
    public boolean contains(int address) { // Menor que 0
        return -1 < address && address < MEMORY.length;
    }

    @Override
    public int getFinalAddress() {
        return MEMORY.length - 1;
    }

    public int[] getMemory() {
        return MEMORY.clone();
    }
}
