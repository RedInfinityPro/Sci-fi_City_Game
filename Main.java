import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    public static final int WINDOW_WIDTH = 960;
    public static final int WINDOW_HEIGHT = 540;
    // assets
    private static final String PATH = "assets/jupiter2_4k.jpg";

    Main() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("App");
        setBackground(Color.BLACK);
        // Transparent panel
        TransparentPanel transparent_panel = new TransparentPanel();
        // Background
        ScrollingBackground scrolling_background = new ScrollingBackground(PATH);
        scrolling_background.setLayout(new BorderLayout());
        scrolling_background.add(transparent_panel, BorderLayout.CENTER);
        // Add layered pane to frame
        setContentPane(scrolling_background);
        setVisible(true);
    }

    static class ScrollingBackground extends JPanel implements ActionListener {
        private Image backgroundImage;
        private int imageX, imageY;
        private Timer timer;
        private int imageWidth, imageHight;

        private ScrollingBackground(String PATH) {
            backgroundImage = new ImageIcon(getClass().getResource(PATH)).getImage();
            imageX = 0;
            timer = new Timer(30, this);
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            imageHight = backgroundImage.getHeight(this);
            imageWidth = backgroundImage.getWidth(this);
            imageWidth = getWidth();
            imageHight = getHeight();
            g.drawImage(backgroundImage, imageX, 0, this);
            g.drawImage(backgroundImage, imageX - imageWidth, 0, imageWidth, imageHight, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            imageX -= 2;
            if (imageX < -imageWidth) {
                imageX = 0;
            }
            repaint();
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