package cpu;

import io.InputOutput; // Importa os valores de I/O
import memory.Memory; // Importa os parâmetros de memória

public class CPU {

    private final Memory MEMORY; // Classe memória
    private final InputOutput IO; // Classe I/O
    private int programCounter; // Contador de programa

    // Registradores de apoio
    private int registerA= 0;
    private int registerB= 0;
    private int registerC= 0;

    // Atribuições da CPU
    public CPU(Memory memory, InputOutput io) {
        this.MEMORY = memory; // Memória
        this.IO = io; // I/O
        programCounter = 0; // Valor inicial do PC
    }

    /**
     * Simula o funcionamento de um programa na memoria.
     * O programa consiste em ler 2 valores A e B nos endereços PC e
     * PC+1 , respectivamente, e escrever os número de 1 a B-A+1 nos endereços de A a B , enviando-
     * os também para a saída do IO conectado.
     *
     * @param memoryAddress Endereço na memoria do programa
     */

    // Chamada da função "run" na CPU no código "Main.java"
    public void run(int memoryAddress) {
        programCounter = memoryAddress;
        registerA = MEMORY.read(programCounter); // Leitura na memória cache

        programCounter++;
        registerB = MEMORY.read(programCounter); // Leitura na memória cache

        programCounter++;

        registerC = 1;
        // Acréscimo de unidades nos endereços
        while(registerA <= registerB){
            MEMORY.write(registerA, registerC);
            IO.output(String.format("> {%d} = {%d}\n", registerA, registerC));
            registerC++;
            registerA++;
        }
    }
}
