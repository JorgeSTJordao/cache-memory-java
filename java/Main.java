import cpu.CPU;
import io.InputOutput;
import memory.cache.Cache;
import memory.cache.ramslicer.RAMSlicer;
import memory.cache.ramslicer.SliceFromStart;
import memory.ram.RAM;

public class Main {

    // Criação das classes (objetos)
    public static void main(String[] args) {
        InputOutput io = new InputOutput(System.in, System.out);
        RAM ram = new RAM(7); // Tamanho da RAM
        RAMSlicer ramSlicer = new SliceFromStart(ram); // Slice a RAM
        Cache cache = new Cache(8, ram, io, ramSlicer); // Memória Cache
        CPU cpu = new CPU(cache, io); // CPU

    
        int startAddress = 10; // Endereço inicial
        ram.write(startAddress, 118); // Endereço e o valor armazenado 
        ram.write(startAddress + 1, 130); // 2º Endereço e o valor armazenado 
        cpu.run(startAddress); // Execução da CPU
    }
}
