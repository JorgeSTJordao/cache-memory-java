package io;

import java.io.InputStream;
import java.io.OutputStream;

public class InputOutput {

    private final InputStream INPUT;
    private final OutputStream OUTPUT;

    public InputOutput(InputStream input, OutputStream output){
        this.INPUT = input;
        this.OUTPUT = output;
    }

    public void output(String string){
        try {
            byte[] bytes = string.getBytes();
            OUTPUT.write(bytes);
            OUTPUT.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
