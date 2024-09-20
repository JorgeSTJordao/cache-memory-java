package memory.cache.ramslicer;

import memory.ram.RAM;

import java.util.Map;

public abstract class RAMSlicer {

    protected final RAM RAM;

    public RAMSlicer(RAM ram) {
        if (ram == null) throw new RAMSlicerException("The RAM can't be null");
        this.RAM = ram;
    }

    public abstract Map<Integer, Integer> getSlice(int ramAddress, int sliceSize);
}
