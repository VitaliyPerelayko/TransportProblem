package by.vit.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Foo {
    private final PasswordEncoder encoder;

    public Foo(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public static void main(String[] args) {


        Foo foo = new Foo(new BCryptPasswordEncoder());

        foo.print();

    }

    public void print() {
        System.out.println(encoder.encode("password"));
        System.out.println(encoder.encode("123"));
        System.out.println(encoder.encode("1953"));
        System.out.println(encoder.encode("123qwa"));
        System.out.println(encoder.encode("qwerty"));
        System.out.println(encoder.encode("stroyka"));
    }
}
