package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HorseRaceGUI {
    JFrame mainFrame;
    private JPanel mainPanel, trackSetUpPanel, raceDetailsPanel, bettingPanel;
    private JPanel statsPanel = new JPanel();
    private int numOfTracks = 3; 
    private int trackLength = 10;
    Horse horse1, horse2, horse3;
    RaceGUI race1;
    
    JTextArea [] horseTextArea = new JTextArea[5];
    JTextField [] horseNameFields = new JTextField[5];
    JComboBox [] horseColorFields = new JComboBox[5];
    JComboBox [] horseBreedFields = new JComboBox[5];
    JPanel [] trackPanels = new JPanel[5];
    JTextArea [] horseConfidenceLevels = new JTextArea[5];
    JTextArea winnerMessage;
    JTextArea [] horseNumberOfTicksTextAreas = new JTextArea[5];
    JTextArea [] horseTrackSpeedTextAreas = new JTextArea[5];

    public HorseRaceGUI() {
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        raceDetailsPanel = new JPanel();
        bettingPanel = new JPanel();

        setUpMainFrame();
        setUpRaceDetailsPanel();
        showMainFrame();
    }

    public void setUpMainFrame() {
        mainFrame.setLayout(new BorderLayout(10, 5));
        mainFrame.setTitle("HorseRaceSimulator");
        mainFrame.setSize(1300, 800);
        mainFrame.setResizable(false);
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
            trackPanels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
            trackPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));

            trackPanels[i].add(new JLabel("Horse " + (i + 1)));
            JPanel raceTrack = new JPanel(new FlowLayout(FlowLayout.LEFT));
            raceTrack.setBackground(Color.ORANGE);
            raceTrack.setPreferredSize(new Dimension(780, 50));
            
            horseTextArea[i] = new JTextArea("");
            JTextArea horse = horseTextArea[i];
            horse.setPreferredSize(new Dimension(780, 50));
            horse.setBackground(Color.ORANGE);
            horse.setFont(new Font("Ariel", Font.PLAIN, 25));
            raceTrack.add(horse);
            
            horseConfidenceLevels[i] = new JTextArea("Confidence Level: ");
            horseConfidenceLevels[i].setPreferredSize(new Dimension(150, 25));
            

            trackPanels[i].add(raceTrack);
            trackPanels[i].add(horseConfidenceLevels[i]);    
            mainPanel.add(trackPanels[i]);


        }


        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
    }

    public void setUpRaceDetailsPanel() {
        JLabel welcomeMessagePt1 = new JLabel ("Welcome to");
        welcomeMessagePt1.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel welcomeMessagePt2 = new JLabel ("Horse Race Simulator");
        welcomeMessagePt2.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel trackNum = new JLabel("Number of Tracks: ");
        String [] trackNums = {"2", "3", "4", "5"};
        JComboBox trackNumSelectBox = new JComboBox(trackNums);
        
        JLabel trackLen = new JLabel("Track Length: ");

        String [] trackLens = {"10", "20", "30"};
        JComboBox trackLenSelectBox = new JComboBox(trackLens);
        
        JButton setUpRace = new JButton("setUpRace");
        setUpRace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                numOfTracks = Integer.parseInt( (String) trackNumSelectBox.getSelectedItem());
                trackLength = Integer.parseInt( (String) trackLenSelectBox.getSelectedItem());
                setUpMainPanel(numOfTracks, trackLength);

                JPanel horseInfoPanel = new JPanel();
                horseInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                raceDetailsPanel.removeAll();

                for (int i = 0; i < numOfTracks; i++) {
                    JPanel horseInfoIndividual = new JPanel(new GridLayout(3,1));
                    JLabel horseName = new JLabel("Name:");
                    horseNameFields[i] = new JTextField();
                    horseNameFields[i].setText("Horse " + (i+1));
                    horseNameFields[i].setPreferredSize(new Dimension(100, 30));

                    JLabel horseColor = new JLabel("Color:");

                    String [] colors  = {"Black", "Green", "Red", "Magenta", "Dark Gray"};
                    horseColorFields[i] = new JComboBox(colors);

                    JLabel horseBreed = new JLabel("Breed:");
                    String [] breeds = {"Knight", "King", "Queen", "Rook", "Bishop", "Pawn"};
                    horseBreedFields[i] = new JComboBox(breeds);

                    horseInfoIndividual.add(horseName);
                    horseInfoIndividual.add(horseNameFields[i]);
                    horseInfoIndividual.add(horseColor);
                    horseInfoIndividual.add(horseColorFields[i]);
                    horseInfoIndividual.add(horseBreed);
                    horseInfoIndividual.add(horseBreedFields[i]);
                    horseInfoPanel.add(horseInfoIndividual);
                    raceDetailsPanel.add(horseInfoPanel);
                    setStatsPanel(numOfTracks);
                    statsPanel.revalidate();
                    statsPanel.repaint();

                }

                JButton setUpHorse = new JButton("setUpHorse");
                setUpHorse.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        race1 = new RaceGUI(trackLength, horseTextArea, numOfTracks, 
                        horseConfidenceLevels, winnerMessage, horseNumberOfTicksTextAreas, 
                        horseTrackSpeedTextAreas);

                        for (int i = 0; i < numOfTracks; i++) {
                            String nameHorse = horseNameFields[i].getText();
                            String colorHorse = (String) horseColorFields[i].getSelectedItem();
                            String horseBreed = (String) horseBreedFields[i].getSelectedItem();
                            char cBreed = ' ';
                            if (horseBreed.equals("Knight")) {
                                cBreed = '\u265E';
                            } else if (horseBreed.equals("King")) {
                                cBreed = '\u265A';
                            } else if (horseBreed.equals("Queen")) {
                                cBreed = '\u265B';
                            } else if (horseBreed.equals("Rook")) {
                                cBreed = '\u265C';
                            } else if (horseBreed.equals("Bishop")) {
                                cBreed = '\u265D';
                            } else if (horseBreed.equals("Pawn")) {
                                cBreed = '\u265F';
                            }
                            Horse horse = new Horse(cBreed, nameHorse, 0.5, colorHorse, horseBreed);
                            race1.addHorse(horse, i+1);
                        }
                        race1.setUpHorse(numOfTracks);
                    }
                });
                raceDetailsPanel.add(setUpHorse);
                
                JButton startRace = new JButton("Start Race");
                startRace.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        race1.startRaceGUI();
                    }
                });
                raceDetailsPanel.add(startRace);
                raceDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mainFrame.add(raceDetailsPanel, BorderLayout.NORTH);
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });

        trackSetUpPanel = new JPanel();
        trackSetUpPanel.setPreferredSize(new Dimension(200, 200));
        trackSetUpPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        trackSetUpPanel.add(welcomeMessagePt1);
        trackSetUpPanel.add(welcomeMessagePt2);
        trackSetUpPanel.add(trackNum);
        trackSetUpPanel.add(trackNumSelectBox);
        trackSetUpPanel.add(trackLen);
        trackSetUpPanel.add(trackLenSelectBox);
        trackSetUpPanel.add(setUpRace);

        mainFrame.add(trackSetUpPanel, BorderLayout.WEST);
    }

    public void setStatsPanel(int numOfTracks) {
        statsPanel.setPreferredSize(new Dimension(390, 200));
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statsPanel.removeAll();

        JLabel stats = new JLabel("Stats Window");
        stats.setFont(new Font("Arial", Font.BOLD, 30));
        
        //Winner panel
        JPanel winnerPanel = new JPanel();
        JLabel winner = new JLabel("Winner ");
        winnerMessage = new JTextArea("------------------");
        winnerPanel.add(winner);
        winnerPanel.add(winnerMessage);

        //Track Speed panel
        JPanel trackSpeedPanel = new JPanel(new GridLayout(0, 3));
        for(int i = 0; i < numOfTracks; i++) {
            JLabel trackSpeed = new JLabel("Horse " + (i+1) );
            horseNumberOfTicksTextAreas[i] = new JTextArea("0");
            horseNumberOfTicksTextAreas[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            horseTrackSpeedTextAreas[i] = new JTextArea("0");
            horseTrackSpeedTextAreas[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            trackSpeedPanel.add(trackSpeed);
            trackSpeedPanel.add(horseNumberOfTicksTextAreas[i]);
            trackSpeedPanel.add(horseTrackSpeedTextAreas[i]);
        }

        

        statsPanel.add(stats);
        statsPanel.add(winnerPanel);
        statsPanel.add(trackSpeedPanel);
        mainFrame.add(statsPanel, BorderLayout.EAST);
    }


    public static void main(String[] args) {
        new HorseRaceGUI();
        
    }
}
