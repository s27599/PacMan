package model;

import javax.swing.table.AbstractTableModel;

public class BoardModel extends AbstractTableModel {

    private Integer[][] elements;
    private Integer[]  columns;

    public BoardModel(Integer[][] elements, Integer[] columns) {
        this.elements = elements;
        this.columns = columns;
    }



    @Override
    public int getRowCount() {
        return elements.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return elements[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;

    }
}
