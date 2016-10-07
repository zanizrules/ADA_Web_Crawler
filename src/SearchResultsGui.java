import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Shane Birdsall on 7/10/2016.
 */
public class SearchResultsGui extends JPanel {
    public final static int PANEL_WIDTH = 600; // size of panel
    public final static int PANEL_HEIGHT = 600;

    private static JFrame frame;
    private JScrollPane scrollPane;
    private JLabel results;
    private SearchResultsGui() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));


        try {
            URL a = new URL("https://www.youtube.com/watch?v=Q0oIoR9mLwc");
            Page b = new Page(a);

            results = b.getLabel();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //scrollPane = new JScrollPane(results);
        //scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH-200, PANEL_HEIGHT));

        //add(scrollPane, BorderLayout.EAST);
        add(results, BorderLayout.NORTH);



    }

    public static void initialiseFrame(SearchResultsGui i) {
        if (frame == null) {
            frame = new JFrame("Search Results");
            frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
            frame.setFocusable(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(i);
        }
    }


    public static void main(String[] args) {
        SearchResultsGui gui = new SearchResultsGui();
        initialiseFrame(gui);

        //gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); // resize frame appropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}
