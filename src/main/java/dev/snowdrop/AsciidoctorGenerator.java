package dev.snowdrop;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;

import java.io.File;

public class AsciidoctorGenerator {
    public static void main(String[] args) {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        asciidoctor.convertFile(
                new File(args[0]),
                OptionsBuilder.options()
                        .toFile(true)
                        .safe(SafeMode.UNSAFE));
    }
}
