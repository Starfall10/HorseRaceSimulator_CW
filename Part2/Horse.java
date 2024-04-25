package Part2;


/**
 * Write a description of class Horse here.
 * 
 * @author Trung Kien Nguyen
 * @version 1.0
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    private String breed;
    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseBreed)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        distanceTravelled = 0;  
        hasFallen = false;
        breed = horseBreed;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
        setConfidence(getConfidence()-0.1);
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
        hasFallen = false;
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

        if(confidence > 1.0)
        {
            confidence = 1.0;
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

