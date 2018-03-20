/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Bot.Game;
import Bot.Item;
import Bot.Main;
import java.awt.Desktop;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Константин
 */
public class Table {

    public JTable table;
    public DefaultTableModel model;
    public Main robt;

    public int gnameColumn;
    public int iconColumn;
    public int nameColumn;
    public int maxPriceColumn;
    public int minPriceColumn;
    public int priceColumn;
    public int posColumn;
    public int buyLeaderColumn;
    public int sellLeaderColumn;
    public int classidColumn;
    public int instanceidColumn;
    public int serverColumn;
    public int uiIdColumn;
    public int iconUrlColumn;

    public int countColumn;

    public int autoMaxPriceColumn;
    public int autoMinPriceColumn;

    public int paidColumn;

    public int boughtDateColumn;
    public int soldDateColumn;
    public int boughtColumn;
    public int soldColumn;
    public int profitColumn;

    public int typeColumn;
    public int dateColumn;

    public TableRowSorter<TableModel> rowSorter;

    public String oldName;
    public boolean isSetOldName = false;

    public int oldCount;
    public boolean isSetOldCount = false;

    public double oldMinPrice;
    public boolean isOldMinPrice = false;

    public Table(JTable table, Main robt) {
        this.table = table;
        this.robt = robt;
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        this.model = (DefaultTableModel) this.table.getModel();
        this.model.setRowCount(0);
        this.table.setRowHeight(50);
        this.rowSorter = new TableRowSorter<>(model);
        this.table.setRowSorter(this.rowSorter);
    }

    protected void setMaxWidth(int column, int size) {
        this.table.getColumnModel().getColumn(column).setMaxWidth(size);
    }

    protected void setMinWidth(int column, int size) {
        this.table.getColumnModel().getColumn(column).setMinWidth(size);
    }

    protected void setPreferredWidth(int column, int size) {
        this.table.getColumnModel().getColumn(column).setPreferredWidth(size);
    }

    protected void setCellRenderer(int column, DefaultTableCellRenderer position) {
        this.table.getColumnModel().getColumn(column).setCellRenderer(position);
    }

    protected void findItem(String text, int... columns) {
        if (text.trim().length() == 0) {
            this.rowSorter.setRowFilter(null);
        } else {
            this.rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, columns));
        }
    }

    protected boolean isInTable(DefaultTableModel model, long classid, long instanceid, String server, int classidColumn, int instanceidColumn, int serverColumn) {
        for (int i = 0; i < model.getRowCount(); i++) {
            long i_classid = (long) model.getValueAt(i, classidColumn);
            long i_instanceid = (long) model.getValueAt(i, instanceidColumn);
            String i_server = (String) model.getValueAt(i, serverColumn);
            if (classid == i_classid && instanceid == i_instanceid && server.equals(i_server)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInTable(Item item, Game game) {
        for (int i = 0; i < this.model.getRowCount(); i++) {
            long i_classid = (long) this.model.getValueAt(i, classidColumn);
            long i_instanceid = (long) this.model.getValueAt(i, instanceidColumn);
            String i_server = (String) this.model.getValueAt(i, serverColumn);
            if (item.classid == i_classid && item.instanceid == i_instanceid && game.server.equals(i_server)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInTable(long classid, long instanceid, Game game) {
        for (int i = 0; i < this.model.getRowCount(); i++) {
            long i_classid = (long) this.model.getValueAt(i, classidColumn);
            long i_instanceid = (long) this.model.getValueAt(i, instanceidColumn);
            String i_server = (String) this.model.getValueAt(i, serverColumn);
            if (classid == i_classid && instanceid == i_instanceid && game.server.equals(i_server)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInTable(String name) {
        for (int i = 0; i < this.model.getRowCount(); i++) {
            String n = (String) this.model.getValueAt(i, nameColumn);
            if (name.equals(n)) {
                return true;
            }
        }
        return false;
    }

    public void removeSelectedItem() {
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Remove item?",
                "Remove",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            int row = this.table.convertRowIndexToModel(this.table.getSelectedRow());
            model.removeRow(row);
            JOptionPane.showMessageDialog(null, "Removed!");
        }
    }

    protected boolean removeItemFromTable(DefaultTableModel model, long classid, long instanceid, String server, int classidColumn, int instanceidColumn, int serverColumn) {
        for (int i = 0; i < model.getRowCount(); i++) {
            long i_classid = (long) model.getValueAt(i, classidColumn);
            long i_instanceid = (long) model.getValueAt(i, instanceidColumn);
            String i_server = (String) model.getValueAt(i, serverColumn);
            if (classid == i_classid && instanceid == i_instanceid && server.equals(i_server)) {
                model.removeRow(i);
                return true;
            }
        }
        return false;
    }

    protected void removeAllItems(Game game) {
        for (int i = this.model.getRowCount() - 1; i >= 0; i--) {
            String server = (String) this.model.getValueAt(i, serverColumn);
            if (server.equals(game.server)) {
                this.model.removeRow(i);
            }
        }
    }

    public void lookItem() {
        int row = this.table.convertRowIndexToModel(this.table.getSelectedRow());
        long classid = (long) this.model.getValueAt(row, this.classidColumn);
        long instanceid = (long) this.model.getValueAt(row, this.instanceidColumn);
        String server = (String) this.model.getValueAt(row, this.serverColumn);
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URL(server + "/item/" + classid + "-" + instanceid).toURI());
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + server + "/item/" + classid + "-" + instanceid);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
