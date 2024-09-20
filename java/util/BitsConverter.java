package util;

public class BitsConverter {

    private BitsConverter() {}

    public static int convertToMaxPositiveNumber(int bitQuantity) {
        if(bitQuantity < 1) throw new IllegalArgumentException("The bit quantity should be higher than 0");
        return (int) Math.pow(2.0, bitQuantity);
    }

    public static int convertToBitsQuantity(int positiveNumber){
        if(positiveNumber < 0) throw new IllegalArgumentException("The number should be positive");
        return (int) Math.round(Math.log(positiveNumber) / Math.log(2));
    }
}

