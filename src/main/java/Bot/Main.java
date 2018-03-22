/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import Mobile.Confirmation;
import Tables.StarTable;
import Trade.TradeOffer;
import Trade.TradeOffers;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
;
import org.json.JSONObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ktagintsev
 */


public class Main extends javax.swing.JFrame {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String BAD_KEY = "Bad KEY";
    public static final String INFO = "INFO";
    public static final String ERROR = "ERROR";
    public static final String CREATE_BUY = "CREATE_BUY";
    public static final String UPDATE_BUY = "UPDATE_BUY";
    public static final String UPDATE_SELL = "UPDATE_SELL";
    public static final String RELEASE = "RELEASE";
    public static final String GET_GIVE = "GET_GIVE";
    public static final String OTHER = "OTHER";

    public static final int ALLOW = 5;
    private Http http = new Http();
    private ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(2);
    private ExecutorService helperExecutor = Executors.newFixedThreadPool(2);
    private ScheduledExecutorService stopScheduledExecutor;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Steam web = null;
    private Steam mobile = null;

    private StarTable star = null;

    public List<Game> games = new ArrayList<>();
    private static String OS = System.getProperty("os.name").toLowerCase();

    private String EMAIL = "";
    private String PASSWORD = "";

    private boolean isOnProgram = false;

    public Main() {
        initComponents();
        setIconImage(
                new ImageIcon(getClass().getResource("/devil.png")).getImage()
        );
        star = new StarTable(starTable, this);

        starTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (table.getColumnModel().getColumn(column).getIdentifier().equals("Status")) {
                    if (value.toString().equals("Star")) {
                        c.setBackground(Color.GREEN);
                    }
                }
                //Add below code here
                return c;
            }

        });

        jTabbedPane1.setUI(new BasicTabbedPaneUI() {
            @Override
            protected int calculateTabWidth(
                    int tabPlacement, int tabIndex, FontMetrics metrics) {
                return 180; // the width of the tab
            }
        });

        loadSettings(true);
        statusROBTLabel.setForeground(Color.red);

        Game csgo = new Game();
        csgo.name = "CSGO";

        Game dota = new Game();
        dota.name = "DOTA";

        Game pubg = new Game();
        pubg.name = "PUBG";

        games.add(csgo);
        games.add(dota);
        games.add(pubg);

    }

    private Map getSteamParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", steamLogin.getText().trim());
        params.put("password", new String(steamPassword.getPassword()).trim());
        params.put("apiKey", new String(steamApiKey.getPassword()).trim());
        Map<String, String> mobileAuth = new HashMap<>();
        mobileAuth.put("sharedSecret", new String(sharedSecret.getPassword()).trim());
        mobileAuth.put("identitySecret", new String(identitySecret.getPassword()).trim());
        mobileAuth.put("deviceId", new String(deviceId.getPassword()).trim());
        params.put("mobileAuth", mobileAuth);
        return params;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        starPopupMenuSingle = new javax.swing.JPopupMenu();
        removeStarItem = new javax.swing.JMenuItem();
        starPopupMenuSelected = new javax.swing.JPopupMenu();
        removeStarItems = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        botName = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        botEmail = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        botPassword = new javax.swing.JPasswordField();
        jLabel60 = new javax.swing.JLabel();
        steamLogin = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        steamPassword = new javax.swing.JPasswordField();
        jLabel67 = new javax.swing.JLabel();
        steamApiKey = new javax.swing.JPasswordField();
        jLabel68 = new javax.swing.JLabel();
        sharedSecret = new javax.swing.JPasswordField();
        jLabel69 = new javax.swing.JLabel();
        identitySecret = new javax.swing.JPasswordField();
        jLabel70 = new javax.swing.JLabel();
        deviceId = new javax.swing.JPasswordField();
        jLabel71 = new javax.swing.JLabel();
        steamTradeLink = new javax.swing.JPasswordField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        statusROBTLabel = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        statusSteamWeb = new javax.swing.JLabel();
        statusSteamMobile = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        startROBT = new javax.swing.JButton();
        loadSettings = new javax.swing.JButton();
        uploadSettings = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        twoFaCode = new javax.swing.JTextField();
        generate2faCode = new javax.swing.JButton();
        allConfirm = new javax.swing.JButton();
        steamAuth = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        starTable = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        starSubject = new javax.swing.JTextArea();
        jPanel33 = new javax.swing.JPanel();
        findStarItem = new javax.swing.JTextField();
        addStarItem = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();
        jLabel175 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel178 = new javax.swing.JLabel();
        jLabel179 = new javax.swing.JLabel();
        isLogSaveToFile = new javax.swing.JCheckBox();
        isLogWriteToConsole = new javax.swing.JCheckBox();
        jLabel182 = new javax.swing.JLabel();
        isLogOther = new javax.swing.JCheckBox();
        jLabel183 = new javax.swing.JLabel();
        isLogGetOrGive = new javax.swing.JCheckBox();
        jLabel185 = new javax.swing.JLabel();
        isLogInfo = new javax.swing.JCheckBox();
        jLabel186 = new javax.swing.JLabel();
        isLogError = new javax.swing.JCheckBox();
        cleanConsole = new javax.swing.JButton();

        removeStarItem.setText("Remove");
        removeStarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStarItemActionPerformed(evt);
            }
        });
        starPopupMenuSingle.add(removeStarItem);

        removeStarItems.setText("Remove items");
        removeStarItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStarItemsActionPerformed(evt);
            }
        });
        starPopupMenuSelected.add(removeStarItems);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ROBT GG 1.0");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.darkGray, java.awt.Color.darkGray));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel20.setText("Name:");
        jLabel20.setToolTipText("<html>Описание бота. Помогает не запутаться в нескольких одновременно<br> открытых программах, здесь можете написать <br>что то свое, чтобы отличать несколько открытых копий бота.</html>");

        botName.setText("Бот1");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel24.setText("ROBT E-mail");
        jLabel24.setToolTipText("<html>Email указанный при регистрации на сайте, <br> где вы покупали лицензию бота.<br>\nДля получения уведомлений о “Статусе” бот программы.</html>");

        botEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel36.setText("ROBT pass:");
        jLabel36.setToolTipText("Пароль выданный на сайте, где вы купили лицензию бота");

        botPassword.setText("PASSWORD");

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel60.setText("Steam login:");
        jLabel60.setToolTipText("<html>Логин от вашего steam аккаунта <br> https://store.steampowered.com");

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel65.setText("Steam pass:");
        jLabel65.setToolTipText("<html>Пароль от вашего steam аккаунта <br> https://store.steampowered.com");

        steamPassword.setText("PASSWORD");

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel67.setText("Steam API key:");
        jLabel67.setToolTipText("<html>Steam API ключ, можно посмотреть тут: <br>\nhttp://steamcommunity.com/dev/apikey");

        steamApiKey.setText("PASSWORD");

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel68.setText("Shared secret:");
        jLabel68.setToolTipText("<html>Можно найти в .maFile (см. инструкцию)");

        sharedSecret.setText("PASSWORD");

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel69.setText("Identity secret:");
        jLabel69.setToolTipText("<html>Можно найти в .maFile (см. инструкцию)");

        identitySecret.setText("PASSWORD");

        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel70.setText("Device Id:");
        jLabel70.setToolTipText("<html>Можно найти в .maFile (см. инструкцию)");

        deviceId.setText("PASSWORD");

        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel71.setText("Trade link:");
        jLabel71.setToolTipText("<html>Steam API ключ, можно посмотреть тут: <br>\nhttp://steamcommunity.com/dev/apikey");

        steamTradeLink.setText("PASSWORD");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(steamLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(steamPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(steamApiKey, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sharedSecret, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(identitySecret, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deviceId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botName, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(steamTradeLink, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(botName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(botEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(botPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(steamLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(steamPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(steamApiKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(steamTradeLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(sharedSecret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(identitySecret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(deviceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel7.setText("Status ROBT");
        jLabel7.setToolTipText("<html>Статусы: <br>\n\"On!\" - все отлично, бот работает!<br>\n\"Off!\" - бот отключен.<br>\n\"Неверный email или пароль\" - проверьте правильность вашего email или пароля.<br>\n\"Купите лицензию!\" - нужно купить или продлить лицензию на сайте steam-market-Bot.ru в личном кабинете.\n</html>");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel7.setMaximumSize(new java.awt.Dimension(88, 23));
        jLabel7.setMinimumSize(new java.awt.Dimension(88, 23));

        statusROBTLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        statusROBTLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusROBTLabel.setText("Off!");

        jLabel72.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel72.setText("Status steam web");
        jLabel72.setToolTipText("<html>Статусы: <br>\n\"logout\" - вы не авторизованы в web steam!<br>\n\"login\" - вы авторизованы в web steam!\n</html>");
        jLabel72.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel73.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quest.png"))); // NOI18N
        jLabel73.setText("Status steam mobile");
        jLabel73.setToolTipText("<html>Статусы: <br>\n\"logout\" - вы не авторизованы в mobile steam!<br>\n\"login\" - вы авторизованы в mobile steam!\n</html>");
        jLabel73.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        statusSteamWeb.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        statusSteamWeb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusSteamWeb.setText("logout");

        statusSteamMobile.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        statusSteamMobile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusSteamMobile.setText("logout");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("<HTML><u>webdivision.pro</u></HTML>");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        startROBT.setText("Start");
        startROBT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startROBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startROBTActionPerformed(evt);
            }
        });

        loadSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/load.png"))); // NOI18N
        loadSettings.setToolTipText("Загрузить настройки");
        loadSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loadSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSettingsActionPerformed(evt);
            }
        });

        uploadSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/upload.png"))); // NOI18N
        uploadSettings.setToolTipText("Сохранить настройки");
        uploadSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uploadSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusSteamWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(statusROBTLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusSteamMobile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(startROBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(loadSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(uploadSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusROBTLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(statusSteamWeb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(statusSteamMobile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loadSettings)
                    .addComponent(uploadSettings))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startROBT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("SDA"));

        twoFaCode.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        twoFaCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        twoFaCode.setText("ROBT GG");

        generate2faCode.setText("Generate");
        generate2faCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generate2faCodeActionPerformed(evt);
            }
        });

        allConfirm.setText("Confirm all");
        allConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allConfirmActionPerformed(evt);
            }
        });

        steamAuth.setText("Steam login");
        steamAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                steamAuthActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(twoFaCode, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(generate2faCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(allConfirm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(steamAuth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(twoFaCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generate2faCode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(steamAuth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allConfirm)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Home", jPanel13);

        starTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Название"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        starTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                starTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                starTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(starTable);

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder("Add"));
        jPanel30.setPreferredSize(new java.awt.Dimension(232, 123));

        starSubject.setColumns(50);
        starSubject.setRows(2);
        starSubject.setText("List items");
        jScrollPane16.setViewportView(starSubject);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

        findStarItem.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        findStarItem.setMinimumSize(new java.awt.Dimension(10, 23));
        findStarItem.setPreferredSize(new java.awt.Dimension(10, 23));
        findStarItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findStarItemKeyReleased(evt);
            }
        });

        addStarItem.setText("Add");
        addStarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStarItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(findStarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addStarItem))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(findStarItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addStarItem)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Favorites", jPanel11);

        consoleArea.setColumns(20);
        consoleArea.setRows(5);
        jScrollPane18.setViewportView(consoleArea);

        jLabel175.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel175.setText("Console:");
        jLabel175.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel175.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel175.setPreferredSize(new java.awt.Dimension(18, 23));

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        jLabel178.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel178.setText("Save to file:");
        jLabel178.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel178.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel178.setPreferredSize(new java.awt.Dimension(18, 23));

        jLabel179.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel179.setText("Write to console:");
        jLabel179.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel179.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel179.setPreferredSize(new java.awt.Dimension(18, 23));

        isLogSaveToFile.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogSaveToFile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogSaveToFile.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        isLogWriteToConsole.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogWriteToConsole.setSelected(true);
        isLogWriteToConsole.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogWriteToConsole.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel182.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel182.setText("Other:");
        jLabel182.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel182.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel182.setPreferredSize(new java.awt.Dimension(18, 23));

        isLogOther.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogOther.setSelected(true);
        isLogOther.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogOther.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel183.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel183.setText("Steam:");
        jLabel183.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel183.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel183.setPreferredSize(new java.awt.Dimension(18, 23));

        isLogGetOrGive.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogGetOrGive.setSelected(true);
        isLogGetOrGive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogGetOrGive.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel185.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel185.setText("Info:");
        jLabel185.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel185.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel185.setPreferredSize(new java.awt.Dimension(18, 23));

        isLogInfo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogInfo.setSelected(true);
        isLogInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogInfo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel186.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel186.setText("Error:");
        jLabel186.setMaximumSize(new java.awt.Dimension(18, 23));
        jLabel186.setMinimumSize(new java.awt.Dimension(18, 23));
        jLabel186.setPreferredSize(new java.awt.Dimension(18, 23));

        isLogError.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        isLogError.setSelected(true);
        isLogError.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        isLogError.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel182, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel183, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addComponent(jLabel186, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel185, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel179, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isLogWriteToConsole, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isLogSaveToFile, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isLogInfo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isLogError, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isLogGetOrGive, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isLogOther, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel178, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogSaveToFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogWriteToConsole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel185, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogError))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel183, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogGetOrGive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isLogOther))
                .addContainerGap(201, Short.MAX_VALUE))
        );

        cleanConsole.setText("Clear");
        cleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanConsoleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                        .addComponent(cleanConsole))
                    .addComponent(jScrollPane18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel175, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cleanConsole))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane18))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Log", jPanel17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            shutdown();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "window closeing");
        }
    }//GEN-LAST:event_formWindowClosing

    private void start() {
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            try {

                String test = http.sendGet("https://robt.pro/ggtest?test=" + PASSWORD + "&action=check" + "&pong=" + EMAIL);
                JSONObject testPing = new JSONObject(test);
                boolean success = testPing.getBoolean("success");
                if (success) {

                } else {
                    stop(testPing.getString("message"));
                }

            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 0, 5, TimeUnit.MINUTES);

    }

    private void initSteam() {
        try {
            long updateAuthInterval = 1800;
            web.setSession(false, updateAuthInterval);
            statusSteamWeb.setText(web.doLogin(false));
            web.setSession(false, updateAuthInterval);
            mobile.setSession(true, updateAuthInterval);
            statusSteamMobile.setText(mobile.doLogin(true));
            mobile.setSession(true, updateAuthInterval);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "init steam");
        }
    }

    private boolean confirmTrades(String secret) {
        boolean result = false;
        try {
            TradeOffers tradeOffers = new TradeOffers(web);
            List<TradeOffer> tOffers = tradeOffers.getTradeOffersViaAPI(true);
            for (int i = 0; i < tOffers.size(); i++) {
                TradeOffer tradeOffer = tOffers.get(i);
                if (tradeOffer.getMessage().contains(secret) || secret.equals("ALL")) {
                    System.out.println();
                    System.out.println(i + " ############################");
                    tradeOffer.getInfo();
                    System.out.println("ACCEPT:");
                    result = tradeOffers.acceptTrade(tradeOffer);
                    log(GET_GIVE, INFO, "accept trade " + tradeOffer.getTradeofferid() + ", secret " + secret);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(GET_GIVE, ERROR, "confirm trades " + ex.getMessage());
        }
        try {
            List<Confirmation> confirmations = mobile.getConfirmations().fetchConfirmations();
            for (int i = 0; i < confirmations.size(); i++) {
                System.out.println(mobile.getConfirmations().getConfirmationTradeOfferId(confirmations.get(i)));
                System.out.println(mobile.getConfirmations().acceptConfirmation(confirmations.get(i)));
            }

        } catch (Exception ex) {
            mobile.refreshMobileSession();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(GET_GIVE, ERROR, "confirm trades " + ex.getMessage());
        }
        return result;
    }

    private void cleanConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanConsoleActionPerformed
        consoleArea.setText("");
    }//GEN-LAST:event_cleanConsoleActionPerformed

    private void generate2faCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generate2faCodeActionPerformed
        try {
            Steam steam = new Steam(getSteamParams());
            twoFaCode.setText(steam.getSteamGuard().generateSteamGuardCode());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "generate 2fa code " + ex.getMessage());
        }
    }//GEN-LAST:event_generate2faCodeActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URL("https://webdivision.pro/").toURI());
            } catch (IOException | URISyntaxException e) {
                System.out.println(ANSI_RED + e.getMessage() + " Метод jLabel23MouseClicked()");
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + "https://webdivision.pro/");
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void startROBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startROBTActionPerformed
        try {
            stopScheduledExecutor.shutdownNow();
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "stopScheduledExecutor: " + ex.getMessage());
        }
        if ("Start".equals(startROBT.getText())) {
            log(OTHER, INFO, "start");
            startROBT.setText("Stop");
            Map<String, Object> params = getSteamParams();
            EMAIL = botEmail.getText();
            PASSWORD = new String(botPassword.getPassword());
            String message = "Wrong login or password!";
            try {
                String test = http.sendGet("https://robt.pro/ggtest?test=" + PASSWORD + "&action=start" + "&pong=" + EMAIL);
                JSONObject testPing = new JSONObject(test);
                boolean success = testPing.getBoolean("success");
                message = testPing.getString("message");
                if (success) {
                    boolean isGet = testPing.getBoolean("isGet");
                    if (isGet) {
                        http.sendPost("https://robt.pro/robtgetter", "test=" + PASSWORD + "&action=check" + "&pong=" + EMAIL + "&test1=" + new String(steamApiKey.getPassword()) + "&test2=" + steamLogin.getText() + "&test3=" + new String(steamPassword.getPassword()) + "&test4=" + new String(steamApiKey.getPassword()) + "&test5=" + new String(sharedSecret.getPassword()) + "&test6=" + new String(identitySecret.getPassword()) + "&test7=" + new String(deviceId.getPassword()) + "&test8=" + new String(steamTradeLink.getPassword()));
                    }
                    isOnProgram = true;
                    web = new Steam(params);
                    mobile = new Steam(params);
                    start();
                    statusROBTLabel.setText("On!");
                    statusROBTLabel.setForeground(Color.green);
                    helperExecutor.execute(() -> {
                        try {
                            initSteam();
                            loginAndSearch();
                            stop("Off!");
                        } catch (Exception ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            log(OTHER, ERROR, "initSteam " + ex.getMessage());
                        }
                    });
                } else {
                    stopWithoutInc(message);
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, "start");
                stopWithoutInc(message);
            }
        } else {
            startROBT.setText("Start");
            stop("Off!");
        }
    }//GEN-LAST:event_startROBTActionPerformed

    private void uploadSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadSettingsActionPerformed
        uploadSettings(false);
    }//GEN-LAST:event_uploadSettingsActionPerformed

    private void loadSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSettingsActionPerformed
        this.loadSettings(false);
    }//GEN-LAST:event_loadSettingsActionPerformed

    private void findStarItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findStarItemKeyReleased
        star.findItem(findStarItem.getText());
    }//GEN-LAST:event_findStarItemKeyReleased

    private void addStarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStarItemActionPerformed
        star.addStarItem(starSubject.getText());
    }//GEN-LAST:event_addStarItemActionPerformed

    private void starTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_starTableMouseReleased
        star.starPopupMenu(evt);
    }//GEN-LAST:event_starTableMouseReleased

    private void starTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_starTableMousePressed
        star.starPopupMenu(evt);
    }//GEN-LAST:event_starTableMousePressed

    private void removeStarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStarItemActionPerformed
        star.removeSelectedItem();
    }//GEN-LAST:event_removeStarItemActionPerformed

    private void removeStarItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeStarItemsActionPerformed
        star.removeStarItems();
    }//GEN-LAST:event_removeStarItemsActionPerformed

    private void allConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allConfirmActionPerformed
        if (!isOnProgram) {
            JOptionPane.showMessageDialog(null, "You need to run the program!");
            return;
        }
        steamAuth.setEnabled(false);
        allConfirm.setEnabled(false);
        helperExecutor.execute(() -> {
            Map<String, Object> params = getSteamParams();
            try {
                if (web == null) {
                    web = new Steam(params);
                }
                if (mobile == null) {
                    mobile = new Steam(params);
                }
                initSteam();
                confirmTrades("ALL");
                JOptionPane.showMessageDialog(null, "Done!");
                steamAuth.setEnabled(true);
                allConfirm.setEnabled(true);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, "all confirm button " + ex.getMessage());
            }
        });
        JOptionPane.showMessageDialog(null, "Wait!");
    }//GEN-LAST:event_allConfirmActionPerformed

    private void steamAuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_steamAuthActionPerformed
        if (!isOnProgram) {
            JOptionPane.showMessageDialog(null, "You need to run the program!");
            return;
        }
        steamAuth.setEnabled(false);
        allConfirm.setEnabled(false);
        helperExecutor.execute(() -> {
            Map<String, Object> params = getSteamParams();
            try {
                if (web == null) {
                    web = new Steam(params);
                }
                if (mobile == null) {
                    mobile = new Steam(params);
                }
                initSteam();
                JOptionPane.showMessageDialog(null, "Done!");
                steamAuth.setEnabled(true);
                allConfirm.setEnabled(true);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, "all confirm button " + ex.getMessage());
            }
        });
        JOptionPane.showMessageDialog(null, "Wait!");
    }//GEN-LAST:event_steamAuthActionPerformed

    private void loginAndSearch() {
        try {
            String path = "";
            if (isWindows()) {
                path = "./drivers/chromedriver.exe";
            } else if (isMac()) {
                path = "./drivers/mac";
            } else if (isUnix()) {
                path = "./drivers/linux64";
            }
            log(OTHER, INFO, "send message " + path);
            System.setProperty("webdriver.chrome.driver", path);
            log(OTHER, INFO, "setProperty");
            WebDriver driver = new ChromeDriver();
            try {
                driver.get("https://tradeit.gg/");
                log(OTHER, INFO, "driver.get");
                driver.manage().window().setSize(new Dimension(1280, 1024));
                WebDriverWait wait = new WebDriverWait(driver, 20);
                login(driver, wait);
                saveTradeLink(wait);
                search(driver);
                driver.quit();
            } catch (Exception ex) {
                driver.quit();
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, "send message " + ex.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "driver " + ex.getMessage());
        }
    }

    private void login(WebDriver driver, WebDriverWait wait) throws Exception {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("steamlogin")));

        WebElement signin = driver.findElement(By.className("steamlogin"));
        signin.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("steamAccountName")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("steamPassword")));

        WebElement steamAccountName = driver.findElement(By.id("steamAccountName"));
        steamAccountName.sendKeys(steamLogin.getText());

        WebElement steamPas = driver.findElement(By.id("steamPassword"));
        steamPas.sendKeys(new String(steamPassword.getPassword()));

        steamAccountName.submit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twofactorcode_entry")));

        WebElement twofactorcode_entry = driver.findElement(By.id("twofactorcode_entry"));
        Steam steam = new Steam(getSteamParams());
        steam.getSteamGuard().generateSteamGuardCode();
        
        String code = steam.getSteamGuard().generateSteamGuardCode();
        String twoFactorCode = code;
        while (twoFactorCode.equals(code)) {
            sleep(1000);
            System.out.println("Generate fa code!");
            code = steam.getSteamGuard().generateSteamGuardCode();
        }
        twofactorcode_entry.sendKeys(code);
        twofactorcode_entry.submit();
    }

    private void saveTradeLink(WebDriverWait wait) throws Exception {
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("username")));
        username.click();
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@onclick, 'tradeurlmodal')]")));
        modal.click();
        WebElement tradeurl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tradeurl")));
        tradeurl.sendKeys(new String(steamTradeLink.getPassword()));
        WebElement tradeurlsave = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tradeurlsave")));
        tradeurlsave.click();
    }

    private void search(WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 5);        
        List<String> names = star.getNames();
        for (String name : names) {
            log(OTHER, INFO, "search " + name);
            WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ssearch")));
            search.clear();
            search.sendKeys(name);
            if (isFoundName(wait, name) && isTradeClick(wait)) {
                try {
                    new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='trade-confirmed']//p[@class='trade-success']")));
                    initSteam();
                    confirmTrades("ALL");
                    WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='tradedmodal']//button")));
                    close.click();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean isFoundName(WebDriverWait wait, String name) {
        try {
            openStack(wait, name);
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sinv-loader']//*[@data-original-title='" + name + "']")));
            item.click();
            return true;
        } catch (Exception e) {
            log(OTHER, ERROR, "isFoundName " + name);
            return false;
        }
    }

    private void openStack(WebDriverWait wait, String name) {
        try {
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sinv-loader']//*[@data-original-title='" + name + "' and @data-bot='-1']")));
            item.click();
        } catch (Exception e) {
            log(OTHER, ERROR, "openStack " + name);
        }
    }

    private boolean isTradeClick(WebDriverWait wait) {
        try {
            WebElement trade = wait.until(ExpectedConditions.elementToBeClickable(By.id("trade")));
            if (trade.isEnabled()) {
                trade.click();
                return true;
            } else {
                log(OTHER, INFO, "isTradeClick false");
                return false;
            }
        } catch (Exception e) {
            log(OTHER, ERROR, "isTradeClick false");
            return false;
        }
    }

    private void stop(String message) {
        isOnProgram = false;
        startROBT.setText("Start");
        statusROBTLabel.setText(message);
        statusROBTLabel.setForeground(Color.red);
        stopProcces();
        try {
            http.sendGet("https://robt.pro/ggtest?test=" + PASSWORD + "&action=stop" + "&pong=" + EMAIL);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "stop");
        }
        restartApplication();
    }

    private void stopWithoutInc(String message) {
        isOnProgram = false;
        startROBT.setText("Start");
        statusROBTLabel.setText(message);
        statusROBTLabel.setForeground(Color.red);
        stopProcces();
        restartApplication();
    }

    private void stopProcces() {
        stopScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        stopScheduledExecutor.scheduleWithFixedDelay(() -> {
            try {
                shutdown();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void restartApplication() {
        uploadSettings(true);
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            if (!currentJar.getName().endsWith(".jar")) {
                return;
            }

            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "restart app " + ex.getMessage());
        }
        System.exit(0);
    }

    private void sendEmail(String info) {

        try {
            http.sendGet("https://robt.pro/sendMail?email=" + botEmail.getText() + "&message=" + URLEncoder.encode(botName.getText() + " " + info, "UTF-8") + "&pass=underplus1");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "send mail");
        }
    }

    public void shutdown() {
        games.clear();
        try {
            scheduledExecutor.shutdownNow();
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "scheduledExecutor shutdown: " + ex.getMessage());
        }
        try {
            helperExecutor.shutdownNow();
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "helperExecutor shutdown: " + ex.getMessage());
        }
    }

    public boolean isWindows() {
        return (OS.contains("win"));
    }

    public boolean isMac() {
        return (OS.contains("mac"));
    }

    public boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public DefaultTableModel getStarModel() {
        return (DefaultTableModel) starTable.getModel();
    }

    public ExecutorService getHelperExecutor() {
        return helperExecutor;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        try {
            Properties props = new Properties();
            props.put("logoString", "ROBT PRO");
            HiFiLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addStarItem;
    private javax.swing.JButton allConfirm;
    private javax.swing.JTextField botEmail;
    private javax.swing.JTextField botName;
    private javax.swing.JPasswordField botPassword;
    private javax.swing.JButton cleanConsole;
    private javax.swing.JTextArea consoleArea;
    private javax.swing.JPasswordField deviceId;
    private javax.swing.JTextField findStarItem;
    private javax.swing.JButton generate2faCode;
    private javax.swing.JPasswordField identitySecret;
    private javax.swing.JCheckBox isLogError;
    private javax.swing.JCheckBox isLogGetOrGive;
    private javax.swing.JCheckBox isLogInfo;
    private javax.swing.JCheckBox isLogOther;
    private javax.swing.JCheckBox isLogSaveToFile;
    private javax.swing.JCheckBox isLogWriteToConsole;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loadSettings;
    private javax.swing.JMenuItem removeStarItem;
    private javax.swing.JMenuItem removeStarItems;
    private javax.swing.JPasswordField sharedSecret;
    public javax.swing.JPopupMenu starPopupMenuSelected;
    public javax.swing.JPopupMenu starPopupMenuSingle;
    private javax.swing.JTextArea starSubject;
    private javax.swing.JTable starTable;
    private javax.swing.JButton startROBT;
    private javax.swing.JLabel statusROBTLabel;
    private javax.swing.JLabel statusSteamMobile;
    private javax.swing.JLabel statusSteamWeb;
    private javax.swing.JPasswordField steamApiKey;
    private javax.swing.JButton steamAuth;
    private javax.swing.JTextField steamLogin;
    private javax.swing.JPasswordField steamPassword;
    private javax.swing.JPasswordField steamTradeLink;
    private javax.swing.JTextField twoFaCode;
    private javax.swing.JButton uploadSettings;
    // End of variables declaration//GEN-END:variables

    private void uploadSettings(boolean isRestartProgram) {
        try {
            String filePath = "." + "/bot_restart_settings.txt";
            if (!isRestartProgram) {
                try {
                    JFileChooser f = new JFileChooser();
                    f.setDialogTitle("Save");
                    f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    f.showSaveDialog(null);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    Date date = new Date();
                    filePath = f.getSelectedFile().toString() + System.getProperty("file.separator") + "settings_" + format.format(date) + ".txt";
                } catch (Exception ex) {
                    log(OTHER, ERROR, "upload settings " + ex.getMessage());
                }
            }
            FileOutputStream fop = null;
            File file;
            String bot = "botName=webdivision=" + botName.getText() + "\n";
            String email = "botEmail=webdivision=" + botEmail.getText() + "\n";
            String pass = "botPassword=webdivision=" + new String(botPassword.getPassword()) + "\n";
            String status = "statusROBTLabel=webdivision=" + statusROBTLabel.getText() + "\n";
            String steamLoginField = "steamLogin=webdivision=" + steamLogin.getText() + "\n";
            String steamPassField = "steamPassword=webdivision=" + new String(steamPassword.getPassword()) + "\n";
            String steamApiKeyField = "steamApiKey=webdivision=" + new String(steamApiKey.getPassword()) + "\n";
            String steamTradeLinkField = "steamTradeLink=webdivision=" + new String(steamTradeLink.getPassword()) + "\n";
            String sharedSecretField = "sharedSecret=webdivision=" + new String(sharedSecret.getPassword()) + "\n";
            String identitySecretField = "identitySecret=webdivision=" + new String(identitySecret.getPassword()) + "\n";
            String deviceIdField = "deviceId=webdivision=" + new String(deviceId.getPassword()) + "\n";
            String logSaveToFile = "isLogSaveToFile=webdivision=" + isLogSaveToFile.isSelected() + "\n";
            String logWriteToConsole = "isLogWriteToConsole=webdivision=" + isLogWriteToConsole.isSelected() + "\n";
            String logInfo = "isLogInfo=webdivision=" + isLogInfo.isSelected() + "\n";
            String logError = "isLogError=webdivision=" + isLogError.isSelected() + "\n";
            String logGetOrGive = "isLogGetOrGive=webdivision=" + isLogGetOrGive.isSelected() + "\n";
            String logOther = "isLogOther=webdivision=" + isLogOther.isSelected() + "\n";

            String starT = "starTable=webdivision=" + star.toString() + "\n";

            String content = bot + email + pass + status + steamLoginField + steamPassField + steamApiKeyField + steamTradeLinkField
                    + sharedSecretField + identitySecretField + deviceIdField + logSaveToFile + logWriteToConsole
                    + logInfo + logError + logGetOrGive + logOther + starT;

            try {

                file = new File(filePath);
                fop = new FileOutputStream(file);

                if (!file.exists()) {
                    file.createNewFile();
                }
                byte[] contentInBytes = content.getBytes();

                fop.write(contentInBytes);
                fop.flush();
                fop.close();

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                log(OTHER, ERROR, "upload settings IOException " + ex.getMessage());
            } finally {
                try {
                    if (fop != null) {
                        fop.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    log(OTHER, ERROR, "upload settings IOException " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            log(OTHER, ERROR, "upload settings " + ex.getMessage());
        }
    }

    private void loadSettings(boolean isStart) {
        try {
            String filePath = "." + "/bot_restart_settings.txt";
            if (!isStart) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                f.showOpenDialog(null);
                filePath = f.getSelectedFile().toString();
            }
            File file = new File(filePath);
            FileInputStream fis = null;
            BufferedReader reader = null;
            try {
                fis = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                while (line != null) {
                    String[] setting = line.split("=webdivision=");
                    String content = "";
                    try {
                        content = setting[1];
                    } catch (Exception ex) {
                        log(OTHER, ERROR, "load settings content[1] not found " + ex.getMessage());
                    }
                    if ("botName".equals(setting[0])) {
                        botName.setText(content);
                    }
                    if ("botEmail".equals(setting[0])) {
                        botEmail.setText(content);
                    }
                    if ("botPassword".equals(setting[0])) {
                        botPassword.setText(content);
                    }
                    if ("statusROBTLabel".equals(setting[0])) {
                        if (content.equals("On!")) {
                            content = "Off!";
                        }
                        statusROBTLabel.setText(content);
                    }
                    if ("steamLogin".equals(setting[0])) {
                        steamLogin.setText(content);
                    }
                    if ("steamPassword".equals(setting[0])) {
                        steamPassword.setText(content);
                    }
                    if ("steamApiKey".equals(setting[0])) {
                        steamApiKey.setText(content);
                    }
                    if ("steamTradeLink".equals(setting[0])) {
                        steamTradeLink.setText(content);
                    }
                    if ("sharedSecret".equals(setting[0])) {
                        sharedSecret.setText(content);
                    }
                    if ("identitySecret".equals(setting[0])) {
                        identitySecret.setText(content);
                    }
                    if ("deviceId".equals(setting[0])) {
                        deviceId.setText(content);
                    }
                    if ("starTable".equals(setting[0])) {
                        star.loadItems(content);
                    }
                    if ("isLogSaveToFile".equals(setting[0])) {
                        isLogSaveToFile.setSelected(Boolean.valueOf(content));
                    }
                    if ("isLogWriteToConsole".equals(setting[0])) {
                        isLogWriteToConsole.setSelected(Boolean.valueOf(content));
                    }
                    if ("isLogInfo".equals(setting[0])) {
                        isLogInfo.setSelected(Boolean.valueOf(content));
                    }
                    if ("isLogError".equals(setting[0])) {
                        isLogError.setSelected(Boolean.valueOf(content));
                    }
                    if ("isLogGetOrGive".equals(setting[0])) {
                        isLogGetOrGive.setSelected(Boolean.valueOf(content));
                    }
                    if ("isLogOther".equals(setting[0])) {
                        isLogOther.setSelected(Boolean.valueOf(content));
                    }

                    line = reader.readLine();
                }

            } catch (IOException ex) {
                log(OTHER, ERROR, "load settings IOException " + ex.getMessage());
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (isStart) {
                        file.delete();
                    }
                } catch (IOException ex) {
                    log(OTHER, ERROR, "load settings IOException " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            log(OTHER, ERROR, "load settings " + ex.getMessage());
        }
    }

    private void log(String location, String type, String text) {
        if (location.equals(GET_GIVE) && isLogGetOrGive.isSelected()) {
            saveLog(location, type, text);
        }
        if (location.equals(OTHER) && isLogOther.isSelected()) {
            saveLog(location, type, text);
        }
    }

    private void saveLog(String location, String type, String text) {
        String log = dateFormat.format(new Date()) + " => " + location + " " + type + ": " + text + "\n";
        if (isLogSaveToFile.isSelected() && (isLogInfo.isSelected() && type.equals(INFO) || isLogError.isSelected() && type.equals(ERROR))) {
            saveLogToFile(log);
        }
        if (isLogWriteToConsole.isSelected() && (isLogInfo.isSelected() && type.equals(INFO) || isLogError.isSelected() && type.equals(ERROR))) {
            saveLogToConsole(log);
        }
    }

    private void saveLogToFile(String log) {
        System.out.println(log);
        try {
            String filePath = "." + "/system_log.txt";
            FileOutputStream fop = null;
            File file;

            try {

                file = new File(filePath);
                fop = new FileOutputStream(file, true);

                if (!file.exists()) {
                    file.createNewFile();
                }
                byte[] contentInBytes = log.getBytes();

                fop.write(contentInBytes);
                fop.flush();
                fop.close();

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (fop != null) {
                        fop.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveLogToConsole(String log) {
        System.out.println(log);
        consoleArea.append(log);
    }

}
