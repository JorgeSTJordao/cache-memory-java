package memory.cache;

import io.InputOutput;
import memory.Memory;
import memory.cache.ramslicer.RAMSlicer;
import memory.cache.ramslicer.RAMSlicerException;
import memory.ram.RAM;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cache implements Memory { // Memória Cache

    /** A map of RAM, where the key represents an RAM address and the value represents an RAM word*/
    private Map<Integer, Integer> ramSlice; // Partição feita
    private final int RAM_SLICE_MAX_SIZE; // Tamanho máximo para extrair da RAM = Tamanho da Cache 
    private boolean isDifferentThanRAM; // Verificar se os valores diferem da RAM

    private final RAMSlicer RAM_SLICER; // SliCer de RAM
    private final RAM RAM; // Conexão com a memória RAM
    private final InputOutput INPUT_OUTPUT; // Atribuição de valores

    // Declaração do objeto Cache
    public Cache(int wordQuantity, RAM ram, InputOutput inputOutput, RAMSlicer ramSlicer) {
        this.ramSlice = new HashMap<>(); // Partição da memória
        this.RAM_SLICE_MAX_SIZE = wordQuantity; // Valor total da partição é feita pela quantidade de letras que podem ser armazendas na RAM
        
        this.RAM = ram; // Conexão de memória
        this.INPUT_OUTPUT = inputOutput; // Valores de I/O
        this.RAM_SLICER = ramSlicer; // Particionador 
    }

    @Override
    // Leitura de cache
    public int read(int address) {
        doBeforeReadAndWrite(address); // Verificação dos valores pedidos interior da Cache
        return ramSlice.get(address); // Irá chamar o ramSlicer para pegar a partição na memória
    }

    @Override
    // Armazenamento em cache
    public void write(int address, int word) {
        doBeforeReadAndWrite(address);
        ramSlice.put(address, word); // Escreve os endereços e as palavras na cache
        isDifferentThanRAM = true;
    }

    // Análise de Cache HIT ou Cache MISS
    private void doBeforeReadAndWrite(int address) {
        if (!contains(address)) { // Verifica se o endereço está presente
            INPUT_OUTPUT.output("Cache MISS: " + address + "\n");
            updateRAM(); // Compara e verifica se houve alterações na memória RAM
            createRAMSlice(address);
        } else INPUT_OUTPUT.output("Cache HIT: " + address + "\n");
    }

    @Override
    public boolean contains(int address) {
        return ramSlice.containsKey(address); // Partição da memória
    }

    private void updateRAM() { // Verifica se o valor da memória precisa ser atualizado
        if (isDifferentThanRAM) {
            ramSlice.forEach(RAM::write);
            isDifferentThanRAM = false; // Classifica não mais como diferente
        }
    }

    private void createRAMSlice(int ramAddress) {
        try {
            ramSlice = RAM_SLICER.getSlice(ramAddress, RAM_SLICE_MAX_SIZE);
        } catch (RAMSlicerException e) {
            throw new CacheException(String.format("The address %d is out of bounds of RAM", ramAddress));
        }
    }

    @Override
    public int getFinalAddress() {
        return RAM.getFinalAddress();
    }

    /**
     * Return the memory as a slice of RAM. This slice may differ from RAM because it does not update RAM yet.
     *
     * @return A map where the keys represent RAM addresses and the values represent RAM words.
     */
    public Map<Integer, Integer> getMemory() {
        return Collections.unmodifiableMap(ramSlice);
    }
}
