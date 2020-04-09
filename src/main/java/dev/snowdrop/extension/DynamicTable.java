package dev.snowdrop.extension;

import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.Section;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Treeprocessor;
import org.asciidoctor.jruby.ast.impl.BlockImpl;
import org.asciidoctor.jruby.ast.impl.SectionImpl;
import org.jruby.RubyProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicTable extends Treeprocessor {

    private final static String KEYWORD_ROLE = "Command";
    private final static String KEYWORD_TYPE = "Cluster target type";
    private final static String KEYWORD_DESCRIPTION = "Description";

    @Override
    public Document process(Document document) {
        processBlock((StructuralNode) document);
        return document;
    }

    private void showContent(StructuralNode node) {
        System.out.println("==== FindBy \":paragraph\" selector");
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("context", ":paragraph");
        List<StructuralNode> findBy = node.findBy(selector);
        for (int j = 0; j < findBy.size(); j++) {
            final StructuralNode subNode = findBy.get(j);
            System.out.println("Node content: " + subNode.getContent());
        }

        System.out.println("==== FindBy \":section\" selector");
        selector = new HashMap<Object, Object>();
        selector.put("context", ":section");
        findBy = node.findBy(selector);
        for (int j = 0; j < findBy.size(); j++) {
            final StructuralNode subNode = findBy.get(j);
            System.out.println("Node content: " + subNode.getContent());
        }
    }

    private void processBlock(StructuralNode node) {
        HashMap<Integer, Role> roles = new HashMap<Integer, Role>();

        // Define a selector to find the sections starting with name "Command"
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("context", ":section");

        // Search about the section within the document
        List<StructuralNode> findBy = node.findBy(selector);

        // Role
        Role role;
        int counter = 0;
        for (int i = 0; i < findBy.size(); i++) {
            final StructuralNode subNode = findBy.get(i);
            role = new Role();
            String sectionTitle = subNode.getTitle();
            List<StructuralNode> blocks = subNode.getBlocks();

            if (sectionTitle.startsWith(KEYWORD_ROLE)) {
                // If the node's title is equal to the keyword, then extract the name
                String[] roleName = subNode.getTitle().split(": ");
                // System.out.println("Name: " + roleName[1]);
                role.setName(roleName[1]);

                // Iterate through the nodes to find either the block containing the paragraph
                // where the paragraph contains the Type of the cluster
                for (int j = 0; j < blocks.size(); j++) {
                    final StructuralNode currentBlock = blocks.get(j);
                    if (currentBlock instanceof BlockImpl) {
                        // Search about the paragraph containing the "Type"
                        String content = currentBlock.getContent().toString();
                        if (content.startsWith(KEYWORD_TYPE)) {
                            String[] typeName = content.split(": ");
                            role.setType(typeName[1]);
                        }
                        continue;
                    }

                    if (currentBlock instanceof SectionImpl) {
                        Section section = (SectionImpl) currentBlock;
                        if (section.getTitle().startsWith(KEYWORD_DESCRIPTION)) {
                            role.setDescription(section.getBlocks().get(0).getContent().toString());
                            roles.put(++counter, role);
                        }
                        continue;
                    }
                }
            }
        }


        for (Integer key : roles.keySet()) {
            final Role aRole = roles.get(key);
            System.out.println("idx: " + key + ", role name: " + aRole.getName() + " type: " + aRole.getType() + " and description: " + aRole.getDescription());
        }

        /*
        System.out.println("=== Tree navigation");
        List<StructuralNode> blocks = node.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
        final StructuralNode currentBlock = blocks.get(i);
        if ("paragraph".equals(currentBlock.getContext())) {
            List<String> lines = ((Block) currentBlock).getLines();
            if (lines != null
                    && lines.size() > 0) {
                blocks.set(i, convertToTable((Block) currentBlock));
            }
        } else {
            // It's not a paragraph, so recursively descend into the child node
            processBlock(currentBlock);
        }
        */
    }

    public Block convertToTable(Block originalBlock) {
        Map<Object, Object> options = new HashMap<Object, Object>();
        Block block = createBlock(
                (StructuralNode) originalBlock.getParent(),
                "listing",
                originalBlock.getLines(),
                originalBlock.getAttributes(),
                options);

        block.addRole("mytable");
        return block;
    }

    private class Role {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        String name;
        String type;
        String tags;
        String description;
    }
}
