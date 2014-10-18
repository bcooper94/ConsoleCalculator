import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;
import java.util.Collections;
/**
 * Evaluate hexadecimal expressions from the terminal.
 *
 * @author Brandon Cooper
 * @version Fall 2014
 */
public class HexCalculator
{
    private PostfixCalculator postfixCalc;
    private static final int kHexA = 10;
    private static final int kBase16 = 16;

    /**
     * Constructor for a HexCalculator.
     */
    public HexCalculator()
    {
        //InfixToPostfix converter = new InfixToPostfix(true);
        //String postfixEquation = converter.toPostfix(equation);
        postfixCalc = new PostfixCalculator();
    }

    /**
     * Evaluate a hexadecimal expression.
     * @param hexEquation   Equation to be evaluated.
     * @return Hexadecimal solution to the equation entered.
     */
    public String evaluate(String hexEquation)
    {
        return answerToHex(Integer.toString(evaluateToDeci(hexEquation)));
    }
 
    /**
     * Evaluate a hexadecimal expression to a decimal answer.
     * @param hexEquation   Equation to be evaluated.
     * @return Decimal solution to the equation entered.
     */
    public int evaluateToDeci(String hexEquation)
    {
        InfixToPostfix postfixConverter = new InfixToPostfix();
        String infixDecimalEq = toDecimal(hexEquation);
        String postfixEquation = postfixConverter.toPostfix(infixDecimalEq);

        return postfixCalc.evaluate(postfixEquation);
    }

    /**
     * Convert hexadecimal operands in an equation to decimal values.
     * @param equation Equation to convert operands to decimal form.
     * @return equation as a String with decimal operands.
     */
    public String toDecimal(String equation)
    {
        String decimalEquation = "";
        String token = "";
        int decimalNum = 0;
        Scanner equationScanner = new Scanner(equation);

        // Read through entire equation
        while (equationScanner.hasNext())
        {
            token = equationScanner.next();

            // If the token is an operation
            if (token.equals("+") || token.equals("-")
                || token.equals("*") || token.equals("/")
                || token.equals("^") || token.equals(")")
                || token.equals("("))
            {
                decimalEquation += token + " ";
            }
            // If token is not an operation, try to parse number
            else
            {
                decimalEquation += tokenToDecimal(token) + " ";
            }
        }

        return decimalEquation.trim();
    }

    /**
     * Convert decimal solution to hex.
     * @param solution   Answer to a solved equation.
     * @return hexadecimal representation of numbers in a String.
     */
    public String answerToHex(String solution)
    {
      /*
      Stack<String> hexStack = new Stack<String>();
      Scanner equationScanner = new Scanner(equation);
      int currentNum = 0;
      String hex = "";
      String hexEquation = equation;

      // Convert each int in equation to hex
      while (equationScanner.hasNextInt())
      {
         currentNum = equationScanner.nextInt();

         // Convert the current int to hex
         while (currentNum >= 16)
         {
            hexStack.push(digitToHex(currentNum % 16));
            currentNum /= 16;
         }
         // Push last hex value onto stack
         hexStack.push(digitToHex(currentNum));

         // pop all hex values off of the stack into the hex String
         while (!hexStack.empty())
         {
            hex += hexStack.pop();
         }
      }
      */
        return Integer.toHexString(Integer.parseInt(solution));
    }

    /**
     * Get the hex value of a decimal number.
     * @param number   Decimal value to convert to hex.
     * @return hex representation of an integer.
     */
    private int tokenToDecimal(String number)
    {
        char[] digits = number.toCharArray();
        int decimalValue = 0;
        int index = 0;
        boolean negative = false;

        // Ignore negative sign
        if (digits[0] == '-')
        {
            negative = true;
            index++;
        }

        // Add up values for each place in the hex number
        while (index < digits.length)
        {
            decimalValue += digitToDecimal(digits[index])* Math.pow(
                kBase16, digits.length - index++ - 1);
        }

        // Convert to negative if was negative value
        if (negative)
        {
            decimalValue *= -1;
        }

        return decimalValue;
    }

    /**
    * Get the hex value of decimal values 10 to 15.
    * @return hex representation of decimal values 10 to 15.
    */
    private int digitToDecimal(char hexDigit)
    {
        int decDigit = 0;
        String[] hexVals = {"A", "B", "C", "D", "E", "F"};
        int index = Collections.binarySearch(
            Arrays.asList(hexVals), "" + hexDigit);

        // If hexDigit needs to be converted to hex
        if (index >= 0)
        {
            decDigit += index + kHexA;
        }
        // If hexDigit doesn't need to be converted to hex
        else
        {
            decDigit += Integer.parseInt("" + hexDigit);
        }

        return decDigit;
    }

    /**
     * Driver for the HexCalculator.
     * @param args Unused.
     */
    public static void main(String[] args)
    {
        Scanner userIn = new Scanner(System.in);
        HexCalculator hexCalc = new HexCalculator();
        boolean quit = false;
        String inputEquation = "";

        // Opening greeting:
        System.out.println("Welcome to Brandon's Hex Calculator!"
            + " Please enter a hexadecimal expression "
            + "and press Enter to evaluate it."
            + " Type \"q\" and press Enter to quit.");

        // Get user input, quit if user types "q"
        while (userIn.hasNextLine() && !quit)
        {
            try
            {
                inputEquation = userIn.nextLine();
                quit = inputEquation.equals(
                    "q") || inputEquation.equals("Q");

                // Only continue if not quitting
                if (!quit && !inputEquation.equals("\n"))
                {
                    System.out.println(
                        hexCalc.evaluate(inputEquation));
                }
            }
            catch (NumberFormatException except)
            {
                System.out.println("Syntax error.");
            }
            catch (Exception except)
            {
                System.out.println("Input error. Please try again.");
            }
        }
    }
}