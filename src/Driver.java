import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Created by NiftyNico on 6/8/14.
 */


public class Driver {

    public static int numClicks = 1;

    public static JFrame frame = new JFrame("Are you going to die in the ocean today?");
    public static JButton swimButton;
    public static JLabel message;

    public static String OCEAN_URL = "http://images.nationalgeographic.com/wpf/media-live/photos/000/138/overrides/save-the-ocean-tips_13821_600x450.jpg";
    public static String SHARK_URL = "http://www.environmentalaska.us/uploads/3/2/1/3/3213168/52238_orig.jpg";
    public static String DROWNING_URL = "http://wakemedvoices.org/wp-content/uploads/2013/05/drowning.jpg";
    public static String SHARK_DEATH_URL = "http://1.media.collegehumor.cvcdn.com/35/20/181e8d1d0ed7e564b5e03c7a23dd7842-baby-gets-eaten-by-shark-cries.jpg";

    public static ImageIcon OCEAN_ICON;
    public static ImageIcon SHARK_ICON;
    public static ImageIcon DROWNED_ICON;
    public static ImageIcon SHARK_DEATH;

    static {
        try{
            OCEAN_ICON = new ImageIcon(ImageIO.read(new URL(OCEAN_URL)));
            SHARK_ICON = new ImageIcon(ImageIO.read(new URL(SHARK_URL)));
            DROWNED_ICON = new ImageIcon(ImageIO.read(new URL(DROWNING_URL)));
            SHARK_DEATH = new ImageIcon(ImageIO.read(new URL(SHARK_DEATH_URL)));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int DROWNED = 3500000;
    public static int SHARK_ATTACK_INJURY = 11500000;
    public static int SHARK_ATTACK_FATALITY = 264000000;

    public static String DROWNED_MESSAGE = "Wow you drowned.\n Noob\n";
    public static String SHARK_ATTACK_INJURY_MESSAGE = "You were attacked by a shark!\nBut you survived!\n";
    public static String SHARK_ATTACK_FATALITY_MESSAGE = "You have been killed by a shark.\n That took a while didn't it?\nNow you can haunt me\n";

    public static String[] NOTHING_BAD = new String[]{
            "Nothing bad happened\n",
            "You had a great time!\n",
            "The water isn't really that dangerous\n",
            "You are still alive\n",
            "Nope still not dead\n",
            "Summer is great!\n",
            "It's so much fun not being a weenie\n",
            "Life is good\n",
            "Water is cool\n",
            "I bet you're having fun\n",
            "Alive and kicking\n",
            "Have you realized how long it's going to take to die?\n",
            "Keep clickin\n"
    };


    public static ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random(new Date().getTime());
            int roll = Math.abs(random.nextInt());
            String messageText = "";
            if(roll % DROWNED == 0){
                messageText += DROWNED_MESSAGE;
                updateButtonImage(DROWNED_ICON, false);
            } if(roll % SHARK_ATTACK_INJURY == 0) {
                messageText += SHARK_ATTACK_INJURY_MESSAGE;
                updateButtonImage(SHARK_ICON, false);
            } if(roll % SHARK_ATTACK_FATALITY == 0){
                messageText += SHARK_ATTACK_FATALITY_MESSAGE;
                updateButtonImage(SHARK_DEATH, false);
            }

            if(roll % DROWNED == 0 || roll % SHARK_ATTACK_INJURY == 0 || roll % SHARK_ATTACK_FATALITY == 0){
                frame.setBackground(Color.RED);
                messageText += "You went in the ocean " + numClicks + " times before the incident\nWorth it right?\n";
            } else {
                messageText = NOTHING_BAD[roll % NOTHING_BAD.length];
            }

            updateMessageText(messageText);
            numClicks++;
        }
    };

    private static void updateMessageText(String text){
        if(message != null)
            frame.remove(message);

        message = new JLabel("<html>" + text.replace("\n", "<br>") + "</html>");
        frame.add(message, BorderLayout.SOUTH);
        refresh();
    }

    private static void updateButtonImage(ImageIcon icon, boolean clickable){
        if(swimButton != null){
            frame.remove(swimButton);
        }
        swimButton = new JButton();

        try {
            swimButton.setIcon(icon);
            swimButton.setDisabledIcon(icon);
        } catch(Exception e){
            swimButton.setText("Time to go into the ocean\n");
            e.printStackTrace();
        } finally {
            swimButton.addActionListener(buttonListener);
            swimButton.setPreferredSize(new Dimension(400, 400));
            swimButton.setBorder(BorderFactory.createEmptyBorder());
            swimButton.setContentAreaFilled(false);
            swimButton.setEnabled(clickable);
            frame.add(swimButton, BorderLayout.CENTER);
            refresh();
        }

    }

    private static void refresh(){
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateButtonImage(OCEAN_ICON, true);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
