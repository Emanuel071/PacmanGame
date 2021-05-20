/**This program is to create a pacman game using grid/arrays 
 * and controllers to control the pacman movement 
 * @author Emanuel
 */
import java.util.Scanner;
import java.util.Random;
public class Project1_ecalderon81 
{
    public static void main(String[] args) 
    {
        //sets up all the variables used
        //x & y are user inputs
        //i,j row and col are used for the loops to set grid and sift through
        //userinput are for commands
        //random is for cookie randomization
        Scanner input = new Scanner( System.in );
        int x,y,i,j = 0,userInput;
        int random, position, row, col;
        int countMove=0,countCookie=0; //keeps track
        double stat;                   //finds statistic
        double cookie, cookieRound;    //helps set up cookies on grid
        
        //initial screen display and user inputs
        System.out.println("Choose an x (row) value for grid");
        x = input.nextInt();
        System.out.println("Choose a y (column) value for grid");
        y = input.nextInt();
        System.out.println();
        
        //main grid set up
        char grid[][] = new char [x][y];        
        int[] positions = new int[x*y]; //cookie randomization setup
        
        //you will be seeing alot of these for loops, we are going through
        //each of the array grid values to put in the dots
        for (i = 0; i<x; i++)           //first grid setup
        {
            for (j = 0; j<y; j++)
            {
                grid[i][j] = '.';       //dots on grid
                
                if (grid[i][j]==grid[0][0])
                {
                    grid[0][0] = '>';   //pacman on grid 0
                }                   
            }            
        } 
        
        for (i = 0; i < x*y; i++)       //initializer for cookie randomization
        {
            positions[i] = i;
        }
        
        //below math for 14% and round down
        cookie = (x*y)*.14;         
        cookieRound = Math.floor(cookie);
        
        //below loop uses random and initializer to sift through 
        //and place the cookies randomyl on the grid 
        for (i = 0; i < (cookieRound); i++) 
        {
            random = (int)(Math.random() * ((x*y) - i - 1));
            while (random == 0)
            {
                random = (int)(Math.random() * ((x*y) - i - 1));
            }
            position = positions[random];
            positions[random] = positions[(x*y) - i - 1];
            row = position / x;
            col = position % y;
            grid[row][col] = '0';
        }
        
        printGameIntroduction();        //method: game intro print
        printCommandInstructions();     //method: print command instructions
        
        printGrid(i, j, x, y, grid);    //method: prints grid in several areas
        
        for (userInput = 0; userInput != 4; ) //break out if user puts 4
        {
            System.out.println();
            System.out.print("Command:");     //command word is seen
            userInput = input.nextInt();
            System.out.println();
            
            if (userInput == 0)               //choice 0
            {
                printCommandInstructions();  
            }
            else if (userInput == 1)          //choice 1
            {
                countMove++;                  //tracks moves
                
                userInput1(i, j, x, y, grid); //method: CCW rotation
                
                printGrid(i, j, x, y, grid);  
            }
            else if (userInput == 2)          //choice 3
            {
                countMove++;
                
                userInput2(i, j, x, y, grid); //method: CW rotation
                
                printGrid(i, j, x, y, grid);                          
            }  
            else if (userInput == 3)          //choice 3 (moving)
            {
                countMove++;
                for (i = 0; i<x; i++)
                {
                    for (j = 0; j<y; j++)
                    {
                        //switch case to identify where pacman is looking
                        switch (grid[i][j]) 
                        {
                            case '>':
                                grid[i][j] = ' ';  //puts blank array
                                //checks to see if out of bounds on left grid
                                if (j==0)          
                                {       
                                    grid[i][j] = '>';
                                    System.out.println("Out of bounds!");
                                }
                                else            //perform the move
                                {
                                    j=j-1;      //logic for actual move
                                    if (grid[i][j] == '0') //checks if cookie
                                    {
                                        countCookie++;
                                        printCookie();     //method print 
                                    }                      //for cookie
                                    grid[i][j] = '>';      //puts pacman in
                                }                          //new location
                                break;
                            //does same see above comments
                            case 'V':                  
                                grid[i][j] = ' ';
                                //checks to see if out of bounds on top grid                               
                                if (i==0)
                                {       
                                    grid[i][j] = 'V';
                                    System.out.println("Out of bounds!");
                                }
                                else
                                {
                                    i=i-1;
                                    if (grid[i][j] == '0')
                                    {
                                        countCookie++;
                                        printCookie();
                                    }
                                    grid[i][j] = 'U';
                                }
                                break;
                            //does same see above comments
                            case '<':
                                grid[i][j] = ' ';
                                //checks to see if out of bounds on right grid
                                if (j==(y-1))
                                {       
                                    grid[i][j] = '<';
                                    System.out.println("Out of bounds!");
                                }
                                else
                                {
                                    j=j+1;
                                    if (grid[i][j] == '0')
                                    {
                                        countCookie++;
                                        printCookie();
                                    }
                                    grid[i][j] = '<';
                                }
                                break;
                            //does same see above comments
                            case '^':
                                grid[i][j] = ' ';
                                //checks to see if out of bounds below grid
                                if (i==(x-1))
                                {       
                                    grid[i][j] = '^';
                                    System.out.println("Out of bounds!");
                                }
                                else
                                {
                                    i=i+1;
                                    if (grid[i][j] == '0')
                                    {
                                        countCookie++;
                                        printCookie();
                                    }
                                    grid[i][j] = '^';
                                }
                                break;
                            default:            //just incase something goes
                                break;          //wrong
                        }
                    }
                }
                printGrid(i, j, x, y, grid);    //finally print grid       
            }            
        }
        //end of game output with counted cookies and moves
            System.out.println("Thanks for playing!");
            System.out.println("# of Move: " + countMove); 
            System.out.println("# Cookies eaten : " + countCookie);        
        //logic below to make sure not to divide by zero
        if (countCookie == 0)  
        {
            System.out.println("#Moves per #Cookies: N/A");
        }
        else
        {
            float cM, cC;                       //quick float input for math of
            cM = countMove; cC = countCookie;   //actual values instead of
            stat = cM/cC;                       //just two numbers
            System.out.println("#Moves per #Cookies: " + stat);            
        }
    }
    public static void printGameIntroduction()     //method: print intro
    {
        System.out.println("Welcome to Pacman!");
        System.out.println("Enter the number of the command desired.");
    }
    public static void printCommandInstructions()     //method: print commands
    {
        System.out.println("Display Commands (0):");
        System.out.println("Turn Left        (1):");
        System.out.println("Turn Right       (2):");
        System.out.println("Move             (3):");
        System.out.println("Exit             (4):");
        System.out.println();
    }
    
    //Method: below method to print pacman grid
    public static void printGrid(int i, int j, int x, int y, char grid[][]) 
    {
        for (i = 0; i<x; i++)
        {
            for (j = 0; j<y; j++)
            {
                System.out.print(grid[i][j]);      
            }
            System.out.println();
        }
    }
    
    //Method: user input algorithm for command 1, returns grid
    public static char[][] userInput1(int i, int j, int x, 
            int y, char grid[][])
    {
        for (i = 0; i<x; i++)
        {
            for (j = 0; j<y; j++)
            {
                //below switch identifies current position of pacman and
                //rotates it CCW
                switch (grid[i][j]) 
                {
                    case '>':
                        grid[i][j] = '^';
                        break;
                    case 'V':
                        grid[i][j] = '>';
                        break;
                    case '<':
                        grid[i][j] = 'V';
                        break;
                    case '^':
                        grid[i][j] = '<';
                        break;
                    default:
                        break;
                }
            }
        }
        return grid;
    } 
    
    //Method: user input algorithm for command 2
    public static char[][] userInput2(int i, int j, int x, 
            int y, char grid[][])
    {
        for (i = 0; i<x; i++)
        {
            for (j = 0; j<y; j++)
            {
                //below switch identifies current position of pacman and
                //rotates it CW
                switch (grid[i][j]) 
                {
                    case '>':
                        grid[i][j] = 'V';
                        break;
                    case 'V':
                        grid[i][j] = '<';
                        break;
                    case '<':
                        grid[i][j] = '^';
                        break;
                    case '^':
                        grid[i][j] = '>';
                        break;
                    default:
                        break;
                }
            }
        }
        return grid;
    } 
    //method below prints out when pacman takes cookie spot
    public static void printCookie()
    {   
        System.out.println("Yum Yum.");
        System.out.println("That was good!");
    }

}
 
