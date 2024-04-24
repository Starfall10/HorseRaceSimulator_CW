package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HorseRaceGUI {
    private JFrame mainFrame;
    private JPanel mainPanel, raceDetailsPanel, bettingPanel;
    private int numOfTracks = 2; 
    private int trackLength = 10;

    public HorseRaceGUI() {
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
            JPanel trackPanel = new JPanel();
            trackPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            trackPanel.add(new JLabel("Track " + (i + 1)));
            trackPanel.add(new JLabel("Horse " + (i + 1)));
            mainPanel.add(trackPanel);
        }


        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        System.out.println("Main Panel Set Up" + numOfTracks + " " + trackLength);
    }

    public void setUpRaceDetailsPanel() {
        //numTracks, trackLength 
        JLabel trackNum = new JLabel("Number of Tracks: ");
        JTextField trackNumField = new JTextField();
        trackNumField.setPreferredSize(new Dimension(100,30));
        JLabel trackLen = new JLabel("Track Length: ");
        JTextField trackLenField = new JTextField();
        trackLenField.setPreferredSize(new Dimension(100,30));

        JButton startRace = new JButton("Start Race");
        startRace.addActionListener(new ActionListener() {
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
        raceDetailsPanel.add(startRace);

        mainFrame.add(raceDetailsPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new HorseRaceGUI();
    }
}
