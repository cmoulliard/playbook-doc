package dev.snowdrop;

import dev.snowdrop.extension.FindRolesPreProcessor;
import dev.snowdrop.extension.FindRolesTreeProcessor;
import dev.snowdrop.extension.GenerateTableBlockProcessor;
import dev.snowdrop.type.Role;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;

import java.io.File;
import java.util.HashMap;

public class AsciidoctorGenerator {

    public static void main(String[] args) {
        String adocFile = args[0];
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        // Include the Table extension
        asciidoctor.javaExtensionRegistry()
                .preprocessor(FindRolesPreProcessor.class)
                .block(GenerateTableBlockProcessor.class);

        // Generate HTML
        asciidoctor.convertFile(
                new File(adocFile),
                OptionsBuilder.options()
                        .toFile(true)
                        .safe(SafeMode.UNSAFE));
        // Generate PDF
        /*
        asciidoctor.convertFile(
        new File(adocFile),
        OptionsBuilder.options()
                .backend("pdf")
                .toFile(true)
                .safe(SafeMode.UNSAFE));
        */
    }
}
