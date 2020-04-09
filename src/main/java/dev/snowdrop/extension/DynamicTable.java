package dev.snowdrop.extension;

import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Treeprocessor;
import org.jruby.RubyProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicTable extends Treeprocessor {

    @Override
    public Document process(Document document) {
        processBlock((StructuralNode) document);
        return document;
    }

    private void processBlock(StructuralNode node) {
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

        System.out.println("==== Tree navigation");
        List<StructuralNode> sections = node.getBlocks();
        for (int i = 0; i < sections.size(); i++) {
            final StructuralNode currentBlock = sections.get(i);
            System.out.println("Node : " + currentBlock.getContext() + ", title \"" + currentBlock.getTitle() + "\"");

            if ("section".equals(currentBlock.getContext())) {
                List<StructuralNode> subLevels = currentBlock.getBlocks();
                for (int j = 0; j < subLevels.size(); j++) {
                    final StructuralNode subLevel = subLevels.get(j);
                    if (subLevel.getTitle() == null) {
                        System.out.println("  Node : " + subLevel.getContext());
                    } else {
                        System.out.println("  Node : " + subLevel.getContext() + ", title \"" + subLevel.getTitle() + "\"");
                    }
                }
            }

/*
                if ("paragraph".equals(currentBlock.getContext())) {
                    List<StructuralNode> subLevels = currentBlock.getBlocks();
                    for (int j = 0; j < subLevels.size(); i++) {
                        StructuralNode currentSubLevel = subLevels.get(j);
                        currentSubLevel.getContent();
                    }


                    List<String> lines = ((Block) currentBlock).getLines();
                    if (lines != null
                            && lines.size() > 0) {
                        blocks.set(i, convertToTable((Block) currentBlock));
                    }
                } else {
                    // It's not a paragraph, so recursively descend into the child node
                    processBlock(currentBlock);
                }
                            }
                */
        }
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
}
