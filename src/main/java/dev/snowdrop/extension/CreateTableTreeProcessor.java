package dev.snowdrop.extension;

import org.asciidoctor.ast.*;
import org.asciidoctor.extension.Treeprocessor;
import org.asciidoctor.jruby.ast.impl.TableImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.snowdrop.Helper.roles;

public class CreateTableTreeProcessor extends Treeprocessor {

    @Override
    public Document process(Document document) {

        // Define a selector to find the sections starting with name "Command"
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("context", ":table");
        // Search about the section within the document
        List<StructuralNode> findBy = document.findBy(selector);
        System.out.println("FindBy size: " + findBy.size());

        for (int i = 0; i < findBy.size(); i++) {
            StructuralNode table = (TableImpl)findBy.get(i);
            table.getBlocks().add(populateTable((Table) table));
        }
        return document;
    }

    private Table populateTable(Table table) {
        // Create the needed columns and add them to the table
        Column roleColumn = createTableColumn(table, 0);
        Column typeColumn = createTableColumn(table, 1);
        Column descriptionColumn = createTableColumn(table, 2);

        // Define how the content will be aligned within the column
        alignColumnData(roleColumn);
        alignColumnData(typeColumn);
        alignColumnData(descriptionColumn);

        table.getColumns().add(roleColumn);
        table.getColumns().add(typeColumn);
        table.getColumns().add(descriptionColumn);

        // Create a row and the cells
        Row row = createTableRow(table);
        Cell cell = createTableCell(roleColumn, "jenkins");
        row.getCells().add(cell);

        cell = createTableCell(roleColumn, "ocp");
        row.getCells().add(cell);

        cell = createTableCell(roleColumn, "this is a jenkins role");
        row.getCells().add(cell);

        // Append the row to the table
        table.getBody().add(row);

        System.out.println("Table generated !");
        return table;
    }

    private void alignColumnData(Column column) {
        column.setHorizontalAlignment(Table.HorizontalAlignment.CENTER);
        column.setVerticalAlignment(Table.VerticalAlignment.BOTTOM);
    }

}
