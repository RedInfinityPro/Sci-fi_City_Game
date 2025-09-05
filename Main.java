import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Main extends JFrame {
    private Container cp;
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 540;

    Main() {
        setTitle("Home");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Setup container
        cp = getContentPane();
        cp.setLayout(new BorderLayout(10, 10));
        cp.setBackground(Color.DARK_GRAY);
        ((JPanel) cp).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // scroll bar
        JPanel body = new JPanel();
        RecomputeGrid recompute_grid = new RecomputeGrid(body, 1000);
        body.setBackground(Color.DARK_GRAY);
        // Fill grid with empty panels to make it visible
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 100; y++) {
                JPanel cell = new Cell(0, 0, x, y);
                body.add(cell);
            }
        }
        // Wrap grid in scroll pane
        JScrollPane scrollPane = new JScrollPane(body, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cp.add(scrollPane, BorderLayout.CENTER);
        // visable
        setVisible(true);
    }

    private class RecomputeGrid {
        private int number_rows;
        
        RecomputeGrid(JPanel body, int number_columns) {
            number_rows = (9/100) * number_columns;
            body.setLayout(new GridLayout(number_rows, number_columns, 0, 0));
        }
    }

    private class Cell extends JPanel {
        private Color original_color;
        private Color active_color;
        private Random number;
        
        Cell(int width, int height, int x, int y) {
            this.number = new Random();
            PaintArea(x, y);
            BuildPanel(width, height);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    setBackground(active_color);
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    setBackground(original_color);
                    repaint();
                }
            });
        }

        private void BuildPanel(int width, int height) {
            setPreferredSize(new Dimension(width, height));
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createDashedBorder(null, 1.0f, 1.0f));
            setBackground(original_color);
        }

        private void PaintArea(int x, int y) {
            Color panel_color = Color.WHITE;
            if (x > 0) {
                double state = number.nextDouble();
                if (state <= 0.5) {
                    panel_color = Color.GREEN;
                } else {
                    panel_color = Color.CYAN;
                }
            } else {
                panel_color = Color.CYAN;
            }

            original_color = panel_color;
            active_color = original_color.darker();
        }
    }

    // run
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Main::new);
    }
}