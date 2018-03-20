/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Bot.Game;
import Bot.Item;
import Bot.Main;
import java.awt.Image;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Константин
 */
public class StarTable extends Table {

    public StarTable(JTable table, Main robt) {
        super(table, robt);
        super.nameColumn = 0;

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(5);
        sortKeys.add(new RowSorter.SortKey(this.nameColumn, SortOrder.ASCENDING));
        super.rowSorter.setSortKeys(sortKeys);

        this.model.addTableModelListener((TableModelEvent evt) -> {
            updateStarItem(evt);
        });
    }

    public void starPopupMenu(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger() && this.table.getSelectedRows().length <= 1) {
            int row = this.table.rowAtPoint(evt.getPoint());
            this.table.setRowSelectionInterval(row, row);
            this.robt.starPopupMenuSingle.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        if (evt.isPopupTrigger() && this.table.getSelectedRows().length > 1) {
            this.robt.starPopupMenuSelected.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        int column = this.table.columnAtPoint(evt.getPoint());
        if (evt.getClickCount() == 2 && column == nameColumn) {
            int row = this.table.convertRowIndexToModel(this.table.getSelectedRow());
            this.oldName = (String) this.model.getValueAt(row, this.nameColumn);
        }
    }

    public void addStarItem(String content) {
        String names[] = content.split("\n");
        if (!"List items".equals(names[0])) {
            for (String name : names) {
                try {
                    if (isInTable(name) && "".equals(name)) {
                        continue;
                    }
                    this.model.addRow(new Object[]{name});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error Added! Send to support!");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Added!");
        }
    }

    public void showStarItems() {
        String filter = "";
        if (!"".equals(filter)) {
            filter = filter.substring(0, filter.length() - 1);
        } else {
            filter = "ktagintsev";
        }
        this.rowSorter.setRowFilter(RowFilter.regexFilter("(?i)(" + filter + ")", this.nameColumn));
    }

    public void updateStarItem(TableModelEvent evt) {
        if (evt.getType() == 0) {
            int colume = evt.getColumn();
            int row = evt.getLastRow();
            if (colume == this.nameColumn) {
                if (!this.isSetOldName) {
                    try {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Update name?",
                                "Update",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {

                        } else {
                            this.isSetOldName = true;
                            this.model.setValueAt(this.oldName, row, colume);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.isSetOldName = false;
                }
            }
        }
    }

    public void findItem(String text) {
        super.findItem(text, gnameColumn, nameColumn, maxPriceColumn, classidColumn, instanceidColumn);
    }

    public void removeStarItems() {
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Remove items?",
                "Remove",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            int[] starRows = this.table.getSelectedRows();
            for (int i = starRows.length - 1; i >= 0; i--) {
                int row = this.table.convertRowIndexToModel(starRows[i]);
                this.model.removeRow(row);
            }
            JOptionPane.showMessageDialog(null, "Removed!");
        }
    }

    @Override
    public String toString() {
        JSONArray items = new JSONArray();
        for (int i = 0; i < this.model.getRowCount(); i++) {
            try {
                String name = (String) this.model.getValueAt(i, this.nameColumn);
                JSONObject obj = new JSONObject();
                obj.put("name", name);
                items.put(obj);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items.toString();
    }

    public void loadItems(String content) {
        this.model.setRowCount(0);
        List<Object[]> rows = new ArrayList<>();
        try {
            JSONArray items = new JSONArray(content);
            int len = items.length();
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                String name = item.getString("name");
                if (!this.isInTable(name)) {
                    rows.add(new Object[]{item.getString("name")});
                }
            }
            if (len != rows.size()) {
                this.loadItems(content);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < rows.size(); i++) {
            if (!"".equals(rows.get(i)[0])) {
                this.model.addRow(rows.get(i));
            }
        }
    }
}
