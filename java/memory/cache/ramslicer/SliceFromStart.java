package memory.cache.ramslicer;

import memory.ram.RAM;
import memory.ram.RAMException;

import java.util.HashMap;
import java.util.Map;

public class SliceFromStart extends RAMSlicer {

    public SliceFromStart(RAM ram) {
        super(ram);
    }

    @Override
    public Map<Integer, Integer> getSlice(int ramAddress, int sliceSize) {
        verifyRAMAddress(ramAddress);
        verifySliceSize(sliceSize);
        return createSlice(ramAddress, sliceSize);
    }

    private void verifySliceSize(int sliceSize) {
        if (sliceSize < 1) throw new RAMSlicerException("The slice size should be higher than 0. Provided: " + sliceSize);
    }

    private void verifyRAMAddress(int ramAddress) {
        if (!RAM.contains(ramAddress)) throw new RAMSlicerException(String.format("The address %d is out of bounds of RAM", ramAddress));
    }

    private Map<Integer, Integer> createSlice(int ramAddress, int sliceSize) {
        int sliceStartAddress = calculateSliceStartAddress(ramAddress, sliceSize);
        int sliceFinalAddress = sliceStartAddress + sliceSize - 1;
        return makeSlice(sliceStartAddress, sliceFinalAddress);
    }

    private Map<Integer, Integer> makeSlice(int startAddress, int finalAddress) {
        Map<Integer, Integer> ramSlice = new HashMap<>();
        try {
            for (int ramAddress = startAddress; ramAddress <= finalAddress; ramAddress++) {
                int ramWord = RAM.read(ramAddress);
                ramSlice.put(ramAddress, ramWord);
            }
        } catch (RAMException ignored) {}
        return ramSlice;
    }

    private int calculateSliceStartAddress(int ramAddress, int sliceSize) {
        int quantityHigherThanRamAddress = RAM.getFinalAddress() - ramAddress;
        if (quantityHigherThanRamAddress < sliceSize) {
            ramAddress -= sliceSize - quantityHigherThanRamAddress;
            if (ramAddress < 0) ramAddress = 0;
        }
        return ramAddress;
    }
}
