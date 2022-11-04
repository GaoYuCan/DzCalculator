package top.sinkdev.cal;

import top.sinkdev.cal.expression.Expression;
import top.sinkdev.cal.ui.XueBaoScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static top.sinkdev.cal.CalculatorConstants.*;


public class Main {

    private static final String[] buttons = {BTN_OP_MOD, BTN_OP_DIV, BTN_OP_CEL, BTN_OP_DEL,
            BTN_NUM_7, BTN_NUM_8, BTN_NUM_9, BTN_OP_MUL, BTN_NUM_4, BTN_NUM_5, BTN_NUM_6, BTN_OP_SUB, BTN_NUM_1,
            BTN_NUM_2, BTN_NUM_3, BTN_OP_ADD, BTN_DING_ZHEN, BTN_NUM_0, BTN_NUM_POINT, BTN_OP_EQ};
    private static final int BUTTON_PANEL_HEIGHT = 300;

    private static final int FRAME_WIDTH = 390;
    private static final int FRAME_HEIGHT = 500;

    private static final int MAIN_LABEL_HEIGHT = 80;

    public static void main(String[] args) {
        // 获取屏幕参数
        // 屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("screenSize = " + screenSize);
        // 屏幕分辨率，刷新率
        GraphicsDevice defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode displayMode = defaultScreenDevice.getDisplayMode();
        System.out.println("displayMode = " + displayMode);
        // 获取屏幕缩放比率
        GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
        System.out.println("defaultConfiguration = " + defaultConfiguration.getClass().getName());
        AffineTransform defaultTransform = defaultConfiguration.getDefaultTransform();
        System.out.printf("defaultTransform.getScale: X = %s, Y = %s\n", defaultTransform.getScaleX(), defaultTransform.getScaleY());

        Expression expression = new Expression();

        JFrame jFrame = new JFrame("Calculator");
        jFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        Insets jFrameInsets = jFrame.getInsets();
        System.out.println("jFrame.getInsets() = " + jFrameInsets);
        Dimension size = jFrame.getContentPane().getSize();
        System.out.println("jFrame.getSize() = " + size);
        jFrame.setVisible(false);
//        jFrame.setAlwaysOnTop(true);
        jFrame.setLayout(null);

        // 创建顶部输入布局
        JLabel mainLabel = new JLabel(expression.getExpression());
        // 设置字体大小
        mainLabel.setFont(new Font(null, Font.BOLD, 48));
        mainLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JScrollPane scroller = new JScrollPane(mainLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setBorder(null);
        JScrollBar jScrollBar = new JScrollBar(Adjustable.HORIZONTAL);
        jScrollBar.setBorder(null);
        jScrollBar.setUI(new XueBaoScrollBarUI());
        scroller.setHorizontalScrollBar(jScrollBar);
        final int mainLabelMargin = 8;
        scroller.setBounds(mainLabelMargin, size.height - BUTTON_PANEL_HEIGHT - MAIN_LABEL_HEIGHT - mainLabelMargin,
                size.width - mainLabelMargin * 2, MAIN_LABEL_HEIGHT);
        // 始终保证，在内容变化之后滚动条处于 end
        AtomicInteger preMaxVal = new AtomicInteger(-1);
        jScrollBar.addAdjustmentListener(e -> {
            // 判断内容是否变化
            if (preMaxVal.get() == e.getAdjustable().getMaximum()) {
                return;
            }
            // 移动到 end
            preMaxVal.set(e.getAdjustable().getMaximum());
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });

        // 创建底部按钮布局
        GridLayout gridLayout = new GridLayout(5, 4);
        JPanel jPanel = new JPanel(gridLayout);

        final Map<String, JButton> buttonMap = new WeakHashMap<>();

        for (int i = 0; i < buttons.length; i++) {
            JButton jButton = new JButton(buttons[i]);
            int cols = i % gridLayout.getColumns();
            int rows = i / gridLayout.getColumns();
            // 设置背景
            if (cols < 3 && rows > 0) {
                jButton.setBackground(Color.decode("#fafafa"));
            } else if (cols == 3 && rows == 4) {
                jButton.setBackground(Color.decode("#8abae0"));
            } else {
                jButton.setBackground(Color.decode("#f0f0f0"));
            }
            // 设置字体
            // 长度为 2 的默认即可
            if (buttons[i].length() == 1) {
                char c = buttons[i].charAt(0);
                if (c >= '0' && c <= '9') {
                    jButton.setFont(new Font(null, Font.BOLD, 18));
                } else {
                    jButton.setFont(new Font(null, Font.PLAIN, 18));
                }
            }
            jButton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    expression.op(e.getActionCommand());
                    mainLabel.setText(expression.getExpression());
                    // 保证输入，滚动到最后
                }
            });
            jPanel.add(jButton);
            // 缓存 Button
            buttonMap.put(buttons[i], jButton);
        }
        // 设置底部按钮位置
        jPanel.setBounds(0, size.height - BUTTON_PANEL_HEIGHT, size.width, BUTTON_PANEL_HEIGHT);

        jFrame.add(scroller);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        // 按键映射
        final Map<Character, String> keyMap = new HashMap<>();
        keyMap.put('\b', BTN_OP_DEL);
        keyMap.put('*', BTN_OP_MUL);
        keyMap.put('c', BTN_OP_CEL);
        keyMap.put('z', BTN_DING_ZHEN);

        // 监听按键
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (!(event instanceof final KeyEvent e) || event.getID() != KeyEvent.KEY_PRESSED) {
                return;
            }
            char keyChar = e.getKeyChar();
            // 映射优先
            if (keyMap.containsKey(keyChar)) {
                JButton jButton = buttonMap.get(keyMap.get(keyChar));
                if (jButton != null) {
                    jButton.doClick();
                }
                return;
            }
            // 其他按键直接使用
            String keyText = String.valueOf(keyChar);
            if (buttonMap.containsKey(keyText)) {
                buttonMap.get(keyText).doClick();
            }
        }, AWTEvent.KEY_EVENT_MASK);

    }

}