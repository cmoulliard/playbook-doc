package dev.snowdrop.extension;

import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockProcessor;
import org.asciidoctor.extension.Name;
import org.asciidoctor.extension.Reader;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static dev.snowdrop.Helper.roles;

@Name(GenerateTableBlockProcessor.NAME)
public class GenerateTableBlockProcessor extends BlockProcessor {

    public static final String NAME = "myroles";

    @Override
    public Object process(StructuralNode parent, Reader reader, Map<String, Object> attributes) {
        System.out.println("Role 0 - name :" + roles.get(0).getName());
        List<String> output = new LinkedList<>();
        output.add("line 1");
        output.add("line 2");
        attributes.put("name", "value");
        return createBlock(parent, "open", output, attributes);
    }
}
