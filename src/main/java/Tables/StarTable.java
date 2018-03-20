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

    public static int starClassidCol = 6;
    public static int starInstanceidCol = 7;
    public static int starServerCol = 8;

    public StarTable(JTable table, Main robt) {
        super(table, robt);
        super.nameColumn = 0;

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        super.setCellRenderer(super.nameColumn, cellRenderer);

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
            row = this.table.convertRowIndexToModel(this.table.getSelectedRow());
            long classid = (long) this.model.getValueAt(row, this.classidColumn);
            long instanceid = (long) this.model.getValueAt(row, this.instanceidColumn);
            String server = (String) this.model.getValueAt(row, this.serverColumn);
            this.robt.starPopupMenuSingle.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        if (evt.isPopupTrigger() && this.table.getSelectedRows().length > 1) {
            this.robt.starPopupMenuSelected.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        int column = this.table.columnAtPoint(evt.getPoint());
        if (evt.getClickCount() == 2 && column == nameColumn) {
            int row = this.table.convertRowIndexToModel(this.table.getSelectedRow());
            this.oldPrice = (double) this.model.getValueAt(row, this.nameColumn);
        }
    }

    public void addStarItem(String content) {
        String names[] = content.split("\n");
        if (!"List items".equals(names[0])) {
            for (String name : names) {
                try {
                    if (isInTable(name)) {
                        continue;
                    }
                    this.model.addRow(new Object[]{name});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка создания! Проверьте ссылки на предметы!");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Добавлено!");
        }
    }

    public void showStarItems() {
        String filter = "";
        if (!"".equals(filter)) {
            filter = filter.substring(0, filter.length() - 1);
        } else {
            filter = "ktagintsev";
        }
        this.rowSorter.setRowFilter(RowFilter.regexFilter("(?i)(" + filter + ")", this.gnameColumn));
    }

    public void updateStarItem(TableModelEvent evt) {
        if (evt.getType() == 0) {
            int colume = evt.getColumn();
            int row = evt.getLastRow();
            if (colume == this.maxPriceColumn) {
                if (!this.isSetOldPrice) {
                    try {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Обновить максимальную цену?",
                                "Обновить",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {

                        } else {
                            this.isSetOldPrice = true;
                            this.model.setValueAt(this.oldPrice, row, colume);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.isSetOldPrice = false;
                }
            }
            if (colume == this.countColumn) {
                if (!this.isSetOldCount) {
                    try {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Обновить количество?",
                                "Обновить",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {

                        } else {
                            this.isSetOldCount = true;
                            this.model.setValueAt(this.oldCount, row, colume);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.isSetOldCount = false;
                }
            }
            if (colume == this.minPriceColumn) {
                if (!this.isOldMinPrice) {
                    try {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Обновить минимальную цену?",
                                "Обновить",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {

                        } else {
                            this.isOldMinPrice = true;
                            this.model.setValueAt(this.oldMinPrice, row, colume);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.isOldMinPrice = false;
                }
            }
        }
    }

    public void removeAllStarItems() {
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Удалить вcе избранные предметы?",
                "Удалить всё",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            for (int i = 0; i < this.robt.games.size(); i++) {
                Game game = this.robt.games.get(i);
                if (!game.isEnabled()) {
                    continue;
                }
                if (game.isLoadStar()) {
                    super.removeAllItems(game);
                }
            }
        }
    }

    public void findItem(String text) {
        super.findItem(text, gnameColumn, nameColumn, maxPriceColumn, classidColumn, instanceidColumn);
    }

    public void removeStarItems() {
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Удалить из избранных выделенное?",
                "Удалить",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            int[] starRows = this.table.getSelectedRows();
            for (int i = starRows.length - 1; i >= 0; i--) {
                int row = this.table.convertRowIndexToModel(starRows[i]);
                Item item = new Item();
                item.classid = (long) this.model.getValueAt(row, this.classidColumn);
                item.instanceid = (long) this.model.getValueAt(row, this.instanceidColumn);
                String server = (String) this.model.getValueAt(row, this.serverColumn);
                for (int j = 0; j < this.robt.games.size(); j++) {
                    Game game = this.robt.games.get(j);
                    if (!game.isEnabled()) {
                        continue;
                    }
                    if (game.server.equals(server)) {
                        this.model.removeRow(row);
                    }
                }
            }
        }
    }

    public JSONArray getStarItems(Game game) {
        JSONArray items = new JSONArray();
        for (int i = 0; i < this.model.getRowCount(); i++) {
            try {
                long classid = (long) this.model.getValueAt(i, this.classidColumn);
                long instanceid = (long) this.model.getValueAt(i, this.instanceidColumn);
                String server = (String) model.getValueAt(i, serverColumn);
                if (server.equals(game.server)) {
                    JSONObject obj = new JSONObject();
                    obj.put("i_classid", classid);
                    obj.put("i_instanceid", instanceid);
                    items.put(obj);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    @Override
    public String toString() {
        JSONArray items = new JSONArray();
        for (int i = 0; i < this.model.getRowCount(); i++) {
            try {
                String gname = (String) this.model.getValueAt(i, this.gnameColumn);
                String iconUrl = (String) this.model.getValueAt(i, this.iconUrlColumn);
                String name = (String) this.model.getValueAt(i, this.nameColumn);
                double minPrice = (double) this.model.getValueAt(i, this.minPriceColumn);
                double maxPrice = (double) this.model.getValueAt(i, this.maxPriceColumn);
                int count = (int) this.model.getValueAt(i, this.countColumn);
                long classid = (long) this.model.getValueAt(i, this.classidColumn);
                long instanceid = (long) this.model.getValueAt(i, this.instanceidColumn);
                String server = (String) model.getValueAt(i, this.serverColumn);
                JSONObject obj = new JSONObject();
                obj.put("gname", gname);
                obj.put("iconUrl", iconUrl);
                obj.put("name", name);
                obj.put("minPrice", minPrice);
                obj.put("maxPrice", maxPrice);
                obj.put("count", count);
                obj.put("classid", classid);
                obj.put("instanceid", instanceid);
                obj.put("server", server);
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
            CountDownLatch sync = new CountDownLatch(len);
            int pool = 5;
            ExecutorService executor = Executors.newFixedThreadPool(pool);
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                executor.execute(() -> {
                    try {
                        String url = item.getString("iconUrl");
                        long classid = item.getLong("classid");
                        long instanceid = item.getLong("instanceid");
                        String server = item.getString("server");
                        URL obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("User-Agent", "Mozilla/5.0");
                        ImageIcon icon = null;
                        try {
                            icon = new ImageIcon(new ImageIcon(ImageIO.read(con.getInputStream())).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                        } catch (Exception ex) {
                            icon = new ImageIcon(new ImageIcon(getClass().getResource("/not_found.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                        }
                        if (!this.isInTable(this.model, classid, instanceid, server, this.classidColumn, this.instanceidColumn, this.serverColumn)) {
                            rows.add(new Object[]{item.getString("gname"), icon, item.getString("name"), item.getDouble("minPrice"), item.getDouble("maxPrice"), item.getInt("count"), classid, instanceid, server, url});
                        }
                        sync.countDown();
                    } catch (Exception ex) {
                        sync.countDown();
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            int wait = len > pool ? (len / pool) + pool : pool;
            sync.await(wait, TimeUnit.SECONDS);
            executor.shutdownNow();
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
