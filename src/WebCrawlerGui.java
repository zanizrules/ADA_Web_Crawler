import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Shane Birdsall on 27/09/2016.
 * GUI Class created for Web Crawler.
 */
public class WebCrawlerGui extends JPanel implements ActionListener {
    private final static int PANEL_WIDTH = 400; // size of panel
    private final static int PANEL_HEIGHT = 400;

    private static JFrame frame;
    private JLabel label, inputUrlLabel, inputKeywordLabel;
    private JTextArea urlText, keywordText;
    private JButton searchButton;
    private BufferedImage img;

    private WebCrawlerGui() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        urlText = new JTextArea();
        urlText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        urlText.setBorder(BorderFactory.createEtchedBorder(1));

        keywordText = new JTextArea();
        keywordText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        keywordText.setBorder(BorderFactory.createEtchedBorder(1));

        label = new JLabel("Vini & Shane Web Crawler");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getName(), Font.BOLD, 28));

        try {
            img = ImageIO.read(new File("src/webImg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(img));
            add(picLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        inputUrlLabel = new JLabel("Please enter a URL below:");
        inputKeywordLabel = new JLabel("Please enter a search term below:");

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 140));
        buttonPanel.add(inputUrlLabel, BorderLayout.NORTH);
        buttonPanel.add(urlText, BorderLayout.NORTH);
        buttonPanel.add(inputKeywordLabel, BorderLayout.CENTER);
        buttonPanel.add(keywordText, BorderLayout.CENTER);
        buttonPanel.add(searchButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void initialiseFrame(WebCrawlerGui i) {
        if(frame == null) {
            frame = new JFrame("Web Crawler Gui");
            frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
            frame.setFocusable(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(i);
        }
    }

    public static void main(String[] args) {
        WebCrawlerGui gui = new WebCrawlerGui();
        initialiseFrame(gui);

        //gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); //resize frame appropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source.equals(searchButton)) {
            if (keywordText != null && urlText != null) {
                if (urlText.getText().trim().isEmpty()) {
                    // Tell user they have not given a url
                    JOptionPane.showMessageDialog(this, "Please enter a URL", "Error Occurred", JOptionPane.WARNING_MESSAGE);
                } else if (keywordText.getText().trim().isEmpty()) {
                    // Tell user they have not given a search term
                    JOptionPane.showMessageDialog(this, "Please enter a Search Term", "Error Occurred", JOptionPane.WARNING_MESSAGE);
                } else {
                    //happy scenario

                }
            }
        }
    }
}
