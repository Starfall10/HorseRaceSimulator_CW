package Part2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.lang.Math;

import Part2.HorseGUI;
import Part2.HorseRaceGUI;
import Part2.RunGUI;

public class RaceGUI {
    private int raceLength;


    private HorseGUI [] horses = new HorseGUI[5];
    String [] horseTextStrings = new String[5];
    String horseText1 = "H";
    String horseText2 = "H";
    String horseText3 = "H";

    boolean finished = false;
    boolean winnerFound = false;

    ScheduledExecutorService executor;

    JTextArea [] horseTextArea;
    JTextArea [] horseConfidenceLevels;
    JTextArea winnerMessage;
    JTextArea [] horseNumberOfTicksTextAreas, horseTrackSpeedTextAreas, horseWinRateTextAreas;
    JTextArea bettingAmount;
    int currentBalance;
    HorseGUI winnerHorse;
    HorseGUI horseBetOn;
    JTextArea currentBalanceTextArea;
    JLabel bettingOddsTextArea;

    int numOfTracks;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public RaceGUI(int distance, JTextArea [] horseTextAreas, int numTracks, 
    JTextArea [] horseConfidenceLevels, JTextArea winnerMessage, JTextArea [] horseNumberOfTicksTextAreas, 
    JTextArea [] horseTrackSpeedTextAreas, JTextArea [] horseWinRateTextAreas, JTextArea bettingAmount, 
    int currentBalance, HorseGUI horseBetOn, JTextArea currentBalanceTextArea, JLabel bettingOddsTextArea)
    {
        // initialise instance variables
        this.raceLength = distance;
        this.horseTextArea = horseTextAreas;
        this.numOfTracks = numTracks;
        this.horseConfidenceLevels = horseConfidenceLevels;
        this.winnerMessage = winnerMessage;
        this.horseNumberOfTicksTextAreas = horseNumberOfTicksTextAreas;
        this.horseTrackSpeedTextAreas = horseTrackSpeedTextAreas;
        this.horseWinRateTextAreas = horseWinRateTextAreas;
        this.bettingAmount = bettingAmount;
        this.currentBalance = currentBalance;
        this.horseBetOn = horseBetOn;
        this.currentBalanceTextArea = currentBalanceTextArea;
        this.bettingOddsTextArea = bettingOddsTextArea;
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(HorseGUI theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            horses[0] = theHorse;
        }
        else if (laneNumber == 2)
        {
            horses[1] = theHorse;
        }
        else if (laneNumber == 3)
        {
            horses[2] = theHorse;
        }
        else if (laneNumber == 4)
        {
            horses[3] = theHorse; 
        }
        else if (laneNumber == 5)
        {
            horses[4] = theHorse;
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    

    public void startRaceGUI()
    {
        System.out.println(horseBetOn.getName());
        //declare a local variable to tell us when the race is finished
        finished = false;
        winnerFound = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (int i = 0; i < numOfTracks; i++)
        {
            horses[i].goBackToStart();
        }
        
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                moveGame();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
        
        
    }

    private void moveGame() {
        if(finished || allHorsesFallen(numOfTracks)) {
            executor.shutdown();

            if(finished == false)
            {
                System.out.println("All horses have fallen");
            }

            //print the winner
            for (int i = 0; i < numOfTracks; i++) {
                if(raceWonBy(horses[i]))
                {
                    displayEndStats();
                }
            }

            return;
        }
        //move each horse
        for (int i = 0; i < numOfTracks; i++)
        {
            if(horses[i].isFinished == false) {
                moveHorse(horses[i]);
            }
        }
        
        //print the race positions
        printRace();
        
        //if any of the three horses has won the race is finished
        for (int i = 0; i < numOfTracks; i++)
        {
            if (raceWonBy(horses[i]))
            {
                horses[i].isFinished = true;
                

                if(winnerFound == false) {
                    horses[i].firstToFinish = true;
                    printWinner(horses[i]);
                    winnerFound = true;
                    winnerHorse = horses[i];
                    horses[i].numberOfWins++;
                    System.out.println("Winner is " + horses[i].getName());
                }
            }
        }

        finished = true;
        for (int i = 0; i < numOfTracks; i++)
        {
            if(horses[i].isFinished == false) {
                finished = false;
            }
        }

        if (finished) {
            for (int i = 0; i < numOfTracks; i++) {
                if(horses[i].firstToFinish == true)
                {   
                    displayEndStats();
                }
            }
            updateBettingOdds();

            checkIfBetWon();
        }
        
    }

    int horseWithHighestWinRate = 0;
    int highestWinRate = 0;


    public void updateBettingOdds() {
        //System.out.println("here1"); this is good
        for (int i = 0; i < numOfTracks; i++)
        {
            if(horses[i].numberOfWins == 0)
            {
                continue;
            }
            double winRate = (double)horses[i].numberOfWins / (double)horses[i].numberOfRaces;
            if(winRate > highestWinRate)
            {
                //System.out.println("here2"); this is good
                highestWinRate = (int)winRate;
                horseWithHighestWinRate = i;
            }
        }
        //System.out.println("here3"); this is good
        double chanceOfWinningBet = (double)highestWinRate * (1-horses[horseWithHighestWinRate].getConfidence());

        bettingOddsTextArea.setText("Betting Odds: " + horses[horseWithHighestWinRate].getName()
         + " has the highest win rate of " + highestWinRate + ". So the chance of winning the bet is " + String.format("%.2f", chanceOfWinningBet));
         
        System.out.println("here");        
        
    }

    private void checkIfBetWon() {
        if(winnerHorse == horseBetOn)
        {
            currentBalance += Integer.parseInt(bettingAmount.getText());
            bettingAmount.setText("0");
            
        }
        else
        {
            currentBalance -= Integer.parseInt(bettingAmount.getText());
            bettingAmount.setText("0");
        }
        currentBalanceTextArea.setText("Current balance: " + currentBalance);
        System.out.println("Current balance: " + currentBalance);
    }


    //Print the winner and increase the confidence of the winner
    private void printWinner(HorseGUI theHorse)
    {
        
        theHorse.setConfidence(theHorse.getConfidence() + 0.1);
        winnerMessage.setText("And the winner is " + theHorse.getName());
    
        
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(HorseGUI theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //increase tick by 1
            theHorse.tickPerRace++;

            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
               
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.isFinished = true;
                theHorse.fall();
            }
        }
    }

    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(HorseGUI theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        
        for (int i = 0; i < numOfTracks; i++)
        {
            horseTextStrings[i] = printLane(horses[i]);
            horseTextArea[i].setText(horseTextStrings[i]);
            horseConfidenceLevels[i].setText("Confidence Level: " + String.format("%.1f" , horses[i].getConfidence()));
        }

        
    }


    
    private String printLane(HorseGUI theHorse)
    {
        String returnText = "";
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        
        //print the spaces before the horse
        returnText += multiplePrint("   ",spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            returnText += "X";
        }
        else
        {
            String horseBreed = theHorse.getBreed();
            if(horseBreed.equals("Knight"))
            {
                returnText += "\u265E";
            }
            else if(horseBreed.equals("King"))
            {
                returnText += "\u265A";
            }
            else if(horseBreed.equals("Queen"))
            {
                returnText += "\u265B";
            }
            else if(horseBreed.equals("Rook"))
            {
                returnText += "\u265C";
            }
            else if(horseBreed.equals("Bishop"))
            {
                returnText += "\u265D";
            }
            else if(horseBreed.equals("Pawn"))
            {
                returnText += "\u265F";
            }
            else
            returnText += "\u265E";
        }
        
        //print the spaces after the horse
        returnText += multiplePrint("   ",spacesAfter);
        
        //print the | for the end of the track
        returnText += "|";

        //print the horse's name along with its currrent confidence
        //returnText += " " + theHorse.getName() + " (Current confidence " + String.format("%.1f" , theHorse.getConfidence()) + ")";

        return returnText;
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private String multiplePrint(String aChar, int times)
    {
        String returnSpaces = "";
        int i = 0;
        while (i < times)
        {
            returnSpaces += aChar;
            i = i + 1;
        }

        return returnSpaces;
    }

    //Check if all horses have fallen
    private boolean allHorsesFallen(int numOfTracks)
    {
        boolean allFallen = true;

        for (int i = 0; i < numOfTracks; i++)
        {
            if (!horses[i].hasFallen())
            {
                allFallen = false;
            }
        }

        return allFallen;
    }


    public void setUpHorse(int numOfTracks)
    {
        for (int i = 0; i < numOfTracks; i++)
        {
            int spacesAfter = raceLength - horses[i].getDistanceTravelled();
            horseTextStrings[i] = horses[i].getSymbol()+ multiplePrint("   ",spacesAfter) + "|";
            horseTextArea[i].setText(horseTextStrings[i]);
            String color = horses[i].getColor();
            if (color.equals("Black"))
            {
                horseTextArea[i].setForeground(Color.BLACK);
            }
            else if (color.equals("Green"))
            {
                horseTextArea[i].setForeground(Color.GREEN);
            }
            else if (color.equals("Red"))
            {
                horseTextArea[i].setForeground(Color.RED);
            }
            else if (color.equals("Magenta"))
            {
                horseTextArea[i].setForeground(Color.MAGENTA);
            }
            else if (color.equals("Dark Gray"))
            {
                horseTextArea[i].setForeground(Color.DARK_GRAY);
            }
            

        }
    }

    public void displayEndStats() {
        for (int i = 0; i < numOfTracks; i++)
        {
            horseNumberOfTicksTextAreas[i].setText(horses[i].tickPerRace + " ticks");
            double speed = (double)horses[i].tickPerRace / (double)raceLength;
            horseTrackSpeedTextAreas[i].setText(String.format("%.2f" , speed) + " ticks/m");
            if(horses[i].numberOfWins == 0)
            {
                horseWinRateTextAreas[i].setText("0.00 win rate");
                continue;
            }
            double winRate = (double)horses[i].numberOfWins / (double)horses[i].numberOfRaces;
            horseWinRateTextAreas[i].setText(String.format("%.2f" , winRate) + " win rate");
        }
    }
}
