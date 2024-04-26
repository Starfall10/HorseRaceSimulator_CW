package Part2;


/**
 * Write a description of class Horse here.
 * 
 * @author Trung Kien Nguyen
 * @version 1.0
 */
public class HorseGUI
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    private String color;
    private String breed;
    int tickPerRace = 0;
    boolean isFinished = false;
    boolean firstToFinish = false;
    int numberOfWins, numberOfRaces;
    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public HorseGUI(char horseSymbol, String horseName, double horseConfidence, String horseColor , String horseBreed)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.distanceTravelled = 0;  
        this.hasFallen = false;
        this.color = horseColor;
        this.breed = horseBreed;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
        setConfidence(getConfidence()-0.1);
    }

    public String getColor()
    {
        return color;
    }

    public String getBreed()
    {
        return breed;
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
        tickPerRace = 0;
        hasFallen = false;
        isFinished = false;
        firstToFinish = false;
        numberOfRaces++;
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled += 1;
    }

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;

        if(confidence >= 1.0)
        {
            confidence = 0.9;
        }
        else if(confidence < 0.0)
        {
            confidence = 0.0;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }
    
}

