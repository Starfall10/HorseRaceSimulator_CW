
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
    
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        symbol = horseSymbol;
        name = horseName;
        confidence = horseConfidence;
        distanceTravelled = 0;  
        hasFallen = false;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
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
    }
    
    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }
    
}

class Test {
    public static void main(String[] args) {
        Horse horse = new Horse('H', "Horse", 0.5);
        System.out.println(horse.hasFallen());

        horse.fall();
        System.out.println(horse.hasFallen());

        horse.moveForward();
        System.out.println(horse.getDistanceTravelled());
        horse.goBackToStart();
        System.out.println(horse.getDistanceTravelled());
     
        horse.setConfidence(0.7);
        System.out.println(horse.getConfidence());

        horse.setSymbol('A');
        System.out.println(horse.getSymbol());

    }
}