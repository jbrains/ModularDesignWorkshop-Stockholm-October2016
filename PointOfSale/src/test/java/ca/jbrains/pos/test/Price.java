package ca.jbrains.pos.test;

public class Price {
    public static Price kronor(int kronorValue) {
        return new Price();
    }

    @Override
    public String toString() {
        return "a Price";
    }
}
