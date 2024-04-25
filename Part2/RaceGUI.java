package Part2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import java.lang.Math;

public class RaceGUI {
    private int raceLength;


    private Horse [] horses = new Horse[5];
    String [] horseTextStrings = new String[5];
    String horseText1 = "H";
    String horseText2 = "H";
    String horseText3 = "H";

    ScheduledExecutorService executor;

    JTextArea [] horseTextArea;

    int numOfTracks;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public RaceGUI(int distance, JTextArea [] horseTextAreas, int numTracks)
    {
        // initialise instance variables
        raceLength = distance;
        horseTextArea = horseTextAreas;
        numOfTracks = numTracks;
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
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
    boolean finished = false;

    public void startRaceGUI()
    {
        //declare a local variable to tell us when the race is finished
        finished = false;
        
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
        if(finished || allHorsesFallen()) {
            executor.shutdown();

            if(finished == false)
            {
                System.out.println("All horses have fallen");
            }

            //print the winner
            for (int i = 0; i < numOfTracks; i++) {
                if(raceWonBy(horses[i]))
                {
                    printWinner(horses[i]);
                }
            }

            return;
        }
        //move each horse
        for (int i = 0; i < numOfTracks; i++)
        {
            moveHorse(horses[i]);
        }
        
        //print the race positions
        printRace();
        
        //if any of the three horses has won the race is finished
        for (int i = 0; i < numOfTracks; i++)
        {
            if (raceWonBy(horses[i]))
            {
                finished = true;
            }
        }
    }

    //Print the winner and increase the confidence of the winner
    private void printWinner(Horse theHorse)
    {
        theHorse.setConfidence(theHorse.getConfidence() + 0.1);
        System.out.println("And the winner is " + theHorse.getName());
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
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
                //theHorse.fall();
            }
        }
    }

    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
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
        }

        
    }


    
    private String printLane(Horse theHorse)
    {
        String returnText = "";
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        //returnText += "|";
        
        //print the spaces before the horse
        returnText += multiplePrint("   ",spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            //System.out.print('\u2322');
            returnText += "X";
        }
        else
        {
            //System.out.print(theHorse.getSymbol());
            returnText += "H";
        }
        
        //print the spaces after the horse
        returnText += multiplePrint("   ",spacesAfter);
        
        //print the | for the end of the track
        //System.out.print('|');
        returnText += "|";

        //print the horse's name along with its currrent confidence
        //System.out.print(" " + theHorse.getName() + " (Current confidence " + String.format("%.1f" , theHorse.getConfidence()) + ")");
        returnText += " " + theHorse.getName() + " (Current confidence " + String.format("%.1f" , theHorse.getConfidence()) + ")";

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
            //System.out.print(aChar);
            returnSpaces += aChar;
            i = i + 1;
        }

        return returnSpaces;
    }

    //Check if all horses have fallen
    private boolean allHorsesFallen()
    {
        for (int i = 0; i < numOfTracks; i++)
        {
            if (horses[i].hasFallen())
            {
                return true;
            }
        }

        return false;
    }


    public void setUpHorse(int numOfTracks)
    {
        for (int i = 0; i < numOfTracks; i++)
        {
            int spacesAfter = raceLength - horses[i].getDistanceTravelled();

        }
    }
}
