import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GUI Class created for Web Crawler.
 */
public class WebCrawlerGui extends JPanel implements ActionListener {
    private final static int PANEL_WIDTH = 450; // size of panel
    private final static int PANEL_HEIGHT = 450;

    private static JFrame frame;
    private static final JLabel label = new JLabel("Vini & Shane Web Crawler"),
            inputUrlLabel = new JLabel("Please enter a URL below:"),
            inputKeywordLabel = new JLabel("Please enter a search term below:");

    private static JTextArea urlText, keywordText;
    private JButton searchButton, randomInputButton;
    private SearchResultsGui searchResultsScreen;

    public String getUrlText() {
        return urlText.getText();
    }
    public String getKeywordText() {
        return keywordText.getText();
    }

    private WebCrawlerGui() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        urlText = new JTextArea();
        urlText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        urlText.setBorder(BorderFactory.createEtchedBorder(1));

        keywordText = new JTextArea();
        keywordText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        keywordText.setBorder(BorderFactory.createEtchedBorder(1));


        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getName(), Font.BOLD, 28));

        try {
            BufferedImage img = ImageIO.read(new File("src/webImg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(img));
            add(picLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        randomInputButton = new JButton("Generate Random Search");
        randomInputButton.addActionListener(this);

        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 170));
        buttonPanel.add(randomInputButton); // TODO: fix
        buttonPanel.add(inputUrlLabel);
        buttonPanel.add(urlText);
        buttonPanel.add(inputKeywordLabel);
        buttonPanel.add(keywordText);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void initialiseFrame(WebCrawlerGui i) {
        if (frame == null) {
            frame = new JFrame("Web Crawler Gui");
            frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
            frame.setFocusable(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(i);
        }
    }

    public void makeVisible() {
        frame.setVisible(true);
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
                    JOptionPane.showMessageDialog(this, "Please wait while your search is processed", "Searching...", JOptionPane.INFORMATION_MESSAGE);

                    if (searchResultsScreen == null) {
                        searchResultsScreen = new SearchResultsGui(this);
                    }
                    searchResultsScreen.initialiseFrame();
                    frame.setVisible(false);
                }
            }
        } else if (source.equals(randomInputButton)) {
            // add random here
            urlText.setText("http://aut.ac.nz");
            keywordText.setText("Students");
        }
    }
}
