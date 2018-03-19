/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ktagintsev
 */
public class Game {

    private JPasswordField marketKey;

    public String name = "";
    public String app = "";

    public long balance = 0;
    public double discount = 0;
    public double commission = 10;

    public String server = "";
    public String sell_event = "";
    public String buy_event = "";
    public String item_db = "";

    public JCheckBox isEnabled;
    public JRadioButton isAutoBuy;
    public JRadioButton isStarBuy;
    public JCheckBox isCreateBuy;
    public JCheckBox isUpdateBuy;
    public JCheckBox isDelBuyBeforeStart;
    public JCheckBox isDeleteBuyFromTo;
    public JCheckBox isLoadBuy;
    public JCheckBox isLoadStar;
    public JCheckBox isLoadBlack;
    public JCheckBox isAddStar;
    public JCheckBox isCalMaxPriceStar;
    public JCheckBox isCreateDuplicate;
    public JCheckBox isQuickBuy;
    public JCheckBox isMediana;

    public JTextField from;
    public JTextField to;
    public JTextField count;
    public JTextField minProfit;
    public JTextField minProfitForSell;
    public JTextField countSubject;
    public JTextField morePeak;
    public JTextField maxBalance;
    public JCheckBox isGenStar;
    public JTextField maxPriceHigherByPercentage;
    public JTextField countSell;
    public JTextField countDateSell;

    public JRadioButton isAutoSell;
    public JRadioButton isExSell;
    public JCheckBox isAutoRelease;
    public JCheckBox isGiveOrGetItems;
    public JCheckBox isSellOld;
    public JCheckBox isLoadSell;
    public JCheckBox isAddExItemsToAutoSell;

    public JTextField fromHistory;
    public JTextField doMoreProfit;

    public JCheckBox isLoadInv;
    public JCheckBox isLoadAnal;
    public JCheckBox isLoadEx;
    public JCheckBox isLoadHis;

    public JLabel totalLabel;
    public JLabel discountLabel;
    public JLabel commissionLabel;

    public JLabel createBuyLabel;
    public JLabel countCreateBuyLabel;
    public JLabel autoBuyLabel;
    public JLabel countBuyLabel;
    public JLabel autoSellLabel;
    public JLabel countSellLabel;
    public JLabel countInvLabel;
    public JLabel inItemsMoneyLabel;

    public JButton sendMessChat;
    public JTextArea chatMessage;

    Game() {

    }

    public void setMarketKey(JPasswordField key) {
        this.marketKey = key;
    }

    public String getMarketKey() {
        return new String(marketKey.getPassword()).trim();
    }

    public boolean isEnabled() {
        return isEnabled.isSelected();
    }

    public boolean isAutoBuy() {
        return isAutoBuy.isSelected();
    }

    public boolean isStarBuy() {
        return isStarBuy.isSelected();
    }

    public boolean isCreateBuy() {
        return isCreateBuy.isSelected();
    }

    public boolean isUpdateBuy() {
        return isUpdateBuy.isSelected();
    }

    public boolean isDelBuyBeforeStart() {
        return isDelBuyBeforeStart.isSelected();
    }

    public boolean isDeleteBuyFromTo() {
        return isDeleteBuyFromTo.isSelected();
    }

    public boolean isLoadBuy() {
        return isLoadBuy.isSelected();
    }

    public boolean isLoadStar() {
        return isLoadStar.isSelected();
    }

    public boolean isLoadBlack() {
        return isLoadBlack.isSelected();
    }

    public boolean isAddStar() {
        return isAddStar.isSelected();
    }

    public boolean isCalMaxPriceStar() {
        return isCalMaxPriceStar.isSelected();
    }

    public boolean isCreateDuplicate() {
        return isCreateDuplicate.isSelected();
    }

    public boolean isQuickBuy() {
        return isQuickBuy.isSelected();
    }

    public boolean isMediana() {
        return isMediana.isSelected();
    }

    public int getFrom() {
        return Integer.parseInt(from.getText()) * 100;
    }

    public int getTo() {
        return Integer.parseInt(to.getText()) * 100;
    }

    public int getCount() {
        return Integer.parseInt(count.getText());
    }

    public double getMaxBalance() {
        return Double.parseDouble(maxBalance.getText());
    }

    public int getMinProfit() {
        return Integer.parseInt(minProfit.getText());
    }

    public double getMinProfitForSell() {
        return Double.parseDouble(minProfitForSell.getText());
    }

    public int getCountSubject() {
        return Integer.parseInt(countSubject.getText());
    }

    public int getMorePeak() {
        return Integer.parseInt(morePeak.getText());
    }

    public boolean isAutoSell() {
        return isAutoSell.isSelected();
    }

    public boolean isExSell() {
        return isExSell.isSelected();
    }

    public boolean isAutoRelease() {
        return isAutoRelease.isSelected();
    }

    public boolean isGiveOrGetItems() {
        return isGiveOrGetItems.isSelected();
    }

    public boolean isSellOld() {
        return isSellOld.isSelected();
    }

    public boolean isLoadSell() {
        return isLoadSell.isSelected();
    }

    public boolean isAddExItemsToAutoSell() {
        return isAddExItemsToAutoSell.isSelected();
    }

    public long getFromHistory() {
        Calendar calendar = Calendar.getInstance();
        int days = Integer.parseInt(fromHistory.getText());
        calendar.add(Calendar.DATE, -days);
        return calendar.getTimeInMillis() / 1000;
    }

    public double getDoMoreProfit() {
        return Double.parseDouble(doMoreProfit.getText());
    }

    public String getChatMessage() {
        return chatMessage.getText();
    }

    public boolean isLoadInv() {
        return isLoadInv.isSelected();
    }

    public boolean isLoadAnal() {
        return isLoadAnal.isSelected();
    }

    public boolean isLoadEx() {
        return isLoadEx.isSelected();
    }

    public boolean isLoadHis() {
        return isLoadHis.isSelected();
    }

    public boolean isGenStar() {
        return isGenStar.isSelected();
    }

    public int getMaxPriceHigherByPercentage() {
        return Integer.parseInt(maxPriceHigherByPercentage.getText());
    }

    public int getCountSell() {
        return Integer.parseInt(countSell.getText());
    }

    public int getCountDateSell() {
        return Integer.parseInt(countDateSell.getText());
    }

    public List<String[]> filterByRarity(BufferedReader csv, List<String> rars) throws IOException {
        List<String[]> items = new ArrayList<>();
        int c = 0;
        String csvLine = "";
        while ((csvLine = csv.readLine()) != null) {
            try {
                if (c != 0) {
                    String[] item = csvLine.split(";", -1);
                    String rarity = item[5].replaceAll("\"", "");
                    if (isSelected(rarity, rars) || this.name.equals("PUBG")) {
                        items.add(item);
                    }
                }
                c++;
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    public List<String[]> filterByGrade(List<String[]> csv, List<String> grades) throws IOException {
        List<String[]> items = new ArrayList<>();
        for (int i = 0; i < csv.size(); i++) {
            try {
                String[] item = csv.get(i);
                String grade = item[6].replaceAll("\"", "");
                if (isSelected(grade, grades) || this.name.equals("PUBG")) {
                    items.add(item);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    List<String[]> filterByType(List<String[]> csv, List<String> types) {
        List<String[]> items = new ArrayList<>();
        for (int i = 0; i < csv.size(); i++) {
            try {
                String[] item = csv.get(i);
                String type = item[7].replaceAll("\"", "");
                if (isSelected(type, types) || this.name.equals("PUBG")) {
                    items.add(item);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    List<String[]> filterByCategories(List<String[]> csv, List<String> categories) {
        List<String[]> items = new ArrayList<>();
        for (int i = 0; i < csv.size(); i++) {
            try {
                String[] item = csv.get(i);
                String category = item[8].replaceAll("\"", "");
                if (isSelected(category, categories) || this.name.equals("PUBG") || this.name.equals("DOTA")) {
                    items.add(item);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    public List<String[]> filterByPrice(List<String[]> csv) throws IOException {
        List<String[]> items = new ArrayList<>();
        boolean isNoStickers = true;
        for (int i = 0; i < csv.size(); i++) {
            try {
                String[] item = csv.get(i);
                long price = Long.parseLong(item[2]);
                long offers = Long.parseLong(item[3]);
                if (this.item_db.equals("current_730.json")) {
                    isNoStickers = "0".equals(item[9]);
                }
                if (price > this.getFrom() && price <= this.getTo() && offers > 3 && isNoStickers) {
                    items.add(item);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    public void sortByPop(List<String[]> items) {
        Collections.sort(items, (String[] strings, String[] otherStrings) -> {
            long pop = Long.parseLong(strings[4]);
            long popPrice = Long.parseLong(otherStrings[4]);
            int result = (int) (pop - popPrice);
            return result;
        });
        Collections.reverse(items);
    }

    public JSONArray getItems(List<String[]> full) {
        JSONArray items = new JSONArray();
        int c = 1;
        for (int i = 0; i < full.size(); i++) {
            try {
                if (c <= this.getCount()) {
                    String[] item = full.get(i);
                    long classid = Long.parseLong(item[0]);
                    long instanceid = Long.parseLong(item[1]);
                    JSONObject obj = new JSONObject();
                    obj.put("i_classid", classid);
                    obj.put("i_instanceid", instanceid);
                    items.put(obj);
                    c++;
                } else if (c == this.getCount()) {
                    break;
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return items;
    }

    public void loadDB() {
        createBuyLabel.setText("Соз. автопокупок " + name);
        countCreateBuyLabel.setText("load..");
    }

    public void countCreateBuyInc() {
        int countCreateBuy = Integer.parseInt(countCreateBuyLabel.getText()) + 1;
        countCreateBuyLabel.setText(countCreateBuy + "");
    }

    public void countCreateBuyZero() {
        createBuyLabel.setText("Соз. автопокупок " + name);
        countCreateBuyLabel.setText("0");
    }

    public void countBuyInc() {
        int countBuy = Integer.parseInt(countBuyLabel.getText()) + 1;
        countBuyLabel.setText(countBuy + "");
    }

    public void countBuyZero() {
        autoBuyLabel.setText("Обн. автопокупок " + name);
        countBuyLabel.setText("0");
    }

    public void countSellInc() {
        int countBuy = Integer.parseInt(countSellLabel.getText()) + 1;
        countSellLabel.setText(countBuy + "");
    }

    public void countSellZero() {
        autoSellLabel.setText("Автопродажа " + name);
        countSellLabel.setText("0");
    }

    private boolean isSelected(String value, List<String> array) {
        for (int i = 0; i < array.size(); i++) {
            if (value.equals(array.get(i))) {
                return true;
            }
        }
        return false;
    }

    public double getMoneyInItems() {
        return Double.parseDouble(this.inItemsMoneyLabel.getText().split(" ")[0]);
    }

    public boolean isMaxBalance() {
        if (this.getMoneyInItems() >= this.getMaxBalance()) {
            return true;
        }
        return false;
    }

    public JSONObject getPaid(long classid, long instanceid, JSONObject obj, JSONArray history, int k) {
        for (int i = k; i < history.length(); i++) {
            try {
                JSONObject his = new JSONObject(history.get(i).toString());
                String event = his.getString("h_event");
                if (this.sell_event.equals(event)) {
                    long historyClassid = his.getLong("classid");
                    long historyInstanceid = his.getLong("instanceid");
                    int stage = his.getInt("stage");
                    if (classid == historyClassid && instanceid == historyInstanceid && stage == 2) {
                        return obj;
                    }
                }
                if (this.buy_event.equals(event)) {
                    long historyClassid = his.getLong("classid");
                    long historyInstanceid = his.getLong("instanceid");
                    int stage = his.getInt("stage");
                    if (classid == historyClassid && instanceid == historyInstanceid && stage == 2) {
                        long paid = his.getLong("paid");
                        long time = his.getLong("h_time");
                        if (paid > obj.getLong("paid")) {
                            obj.put("paid", paid);
                            obj.put("h_time", time);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }
}
