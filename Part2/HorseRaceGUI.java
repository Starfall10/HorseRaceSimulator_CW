package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Flow;


public class HorseRaceGUI {
    JFrame mainFrame;
    private JPanel mainPanel, raceDetailsPanel, bettingPanel;
    private int numOfTracks = 3; 
    private int trackLength = 10;
    Horse horse1, horse2, horse3;
    String horseText1 = "H";
    String horseText2 = "H";
    String horseText3 = "H";
    JTextArea [] horseTextArea = new JTextArea[3];

    public HorseRaceGUI(Horse horse1, Horse horse2, Horse horse3) {
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        raceDetailsPanel = new JPanel();
        bettingPanel = new JPanel();

        setUpMainFrame();
        setUpRaceDetailsPanel();
        setUpMainPanel(numOfTracks, trackLength);
        showMainFrame();
    }

    public void setUpMainFrame() {
        mainFrame.setLayout(new BorderLayout(10, 5));
        mainFrame.setTitle("HorseRaceSimulator");
        mainFrame.setSize(800, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
    }

    public void showMainFrame() {
        mainFrame.setVisible(true);
    }   

    public void setUpMainPanel(int numOfTracks, int trackLength) {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(numOfTracks,1));
        for (int i = 0; i < numOfTracks; i++) {
            JPanel trackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            trackPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            trackPanel.add(new JLabel("Track " + (i + 1)));
            trackPanel.add(new JLabel("Horse " + (i + 1)));
            JPanel raceTrack = new JPanel(new FlowLayout(FlowLayout.LEFT));
            raceTrack.setBackground(Color.ORANGE);
            raceTrack.setPreferredSize(new Dimension(750, 50));
            
            horseTextArea[i] = new JTextArea(horseText1);
            JTextArea horse = horseTextArea[i];
            horse.setPreferredSize(new Dimension(750, 50));
            horse.setBackground(Color.ORANGE);
            horse.setFont(new Font("Ariel", Font.PLAIN, 20));
            raceTrack.add(horse);
            
            

            trackPanel.add(raceTrack);
            mainPanel.add(trackPanel);

            

        }


        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
    }

    public void setUpRaceDetailsPanel() {
        //numTracks, trackLength 
        JLabel trackNum = new JLabel("Number of Tracks: ");
        JTextField trackNumField = new JTextField();
        trackNumField.setPreferredSize(new Dimension(100,30));
        JLabel trackLen = new JLabel("Track Length: ");
        JTextField trackLenField = new JTextField();
        trackLenField.setPreferredSize(new Dimension(100,30));

        JButton setUpRace = new JButton("setUpRace");
        setUpRace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfTracks = Integer.parseInt(trackNumField.getText());
                trackLength = Integer.parseInt(trackLenField.getText());
                setUpMainPanel(numOfTracks, trackLength);
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        raceDetailsPanel.add(trackNum);
        raceDetailsPanel.add(trackNumField);
        raceDetailsPanel.add(trackLen);
        raceDetailsPanel.add(trackLenField);
        raceDetailsPanel.add(setUpRace);

        mainFrame.add(raceDetailsPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        
        
        Horse horse1 = new Horse('\u2654', "Horse1", 0.5);
        Horse horse2 = new Horse('\u2654', "Horse2", 0.5);
        Horse horse3 = new Horse('\u2654', "Horse3", 0.5);
        HorseRaceGUI GUI = new HorseRaceGUI(horse1, horse2, horse3);
        RaceGUI race1 = new RaceGUI(10, GUI);
        race1.addHorse(horse1, 1);
        race1.addHorse(horse2, 2);
        race1.addHorse(horse3, 3);

        race1.startRaceGUI();
    }
}
