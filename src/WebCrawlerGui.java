import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Shane Birdsall on 27/09/2016.
 * GUI Class created for Web Crawler.
 */
public class WebCrawlerGui extends JPanel {
    public final static int PANEL_WIDTH = 400; // size of panel
    public final static int PANEL_HEIGHT = 400;

    private static JFrame frame;
    private JLabel label, inputUrlLabel;
    private JTextArea text;
    private JButton button;
    private BufferedImage img;

    public WebCrawlerGui() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        text = new JTextArea();
        text.setPreferredSize(new Dimension(PANEL_WIDTH-30, 22));
        text.setBorder(BorderFactory.createEtchedBorder(1));



        label = new JLabel("Vini + Shane Web Crawler");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getName(), Font.BOLD, 28));

        try {
            img = ImageIO.read(new File("src/webImg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(img));
            add(picLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }


        inputUrlLabel = new JLabel("Please enter a URL below");

        button = new JButton("Search");

        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 120));
        buttonPanel.add(inputUrlLabel, BorderLayout.NORTH);
        buttonPanel.add(text, BorderLayout.NORTH);
        buttonPanel.add(button, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        WebCrawlerGui gui = new WebCrawlerGui();
        frame = new JFrame("Web Crawler Gui");
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(gui);
        //gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); //resize frame apropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth/2)-(frame.getWidth()/2),
                (screenHeight/2)-(frame.getHeight()/2)));
        frame.setVisible(true);
    }

}
