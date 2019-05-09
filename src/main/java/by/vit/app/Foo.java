package by.vit.app;

import java.util.Arrays;
import java.util.stream.Stream;

public class Foo {
    public static void main(String[] args) {
        Double d = Stream.of(5d,6d,7d,8d,25d).reduce((a,b) -> a+b).get();
        System.out.println(d);
    }
}
