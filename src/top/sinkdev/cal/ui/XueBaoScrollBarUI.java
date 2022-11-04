package top.sinkdev.cal.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;


public class XueBaoScrollBarUI extends MetalScrollBarUI {
    @Override
    protected JButton createDecreaseButton(int orientation) {
        BasicArrowButton button = new BasicArrowButton(orientation);
        button.setBackground(Color.decode("#eeeeee"));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        button.setBorder(emptyBorder);
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        BasicArrowButton button = new BasicArrowButton(orientation);
        button.setBackground(Color.decode("#eeeeee"));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        button.setBorder(emptyBorder);
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (!c.isEnabled()) {
            return;
        }
        boolean leftToRight = c.getComponentOrientation().isLeftToRight();
        g.translate(thumbBounds.x, thumbBounds.y);
        Color thumbColor = Color.decode("#cdcdcd");

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            if (!isFreeStanding) {
                thumbBounds.width += 2;
                if (!leftToRight) {
                    g.translate(-1, 0);
                }
            }
            g.setColor(thumbColor);
            g.fillRect(0, 0, thumbBounds.width - 2, thumbBounds.height - 1);
            if (!isFreeStanding) {
                thumbBounds.width -= 2;
                if (!leftToRight) {
                    g.translate(1, 0);
                }
            }
        } else {
            if (!isFreeStanding) {
                thumbBounds.height += 2;
            }
            g.setColor(thumbColor);
            g.fillRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 2);
            if (!isFreeStanding) {
                thumbBounds.height -= 2;
            }
        }
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }


}
