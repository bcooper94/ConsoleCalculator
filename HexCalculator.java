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

   public HexCalculator()
   {
      //InfixToPostfix converter = new InfixToPostfix(true);
      //String postfixEquation = converter.toPostfix(equation);
      postfixCalc = new PostfixCalculator();
   }

   /**
    * Convert hexadecimal operands in an equation to decimal values.
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
               || token.equals("^"))
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
   public int tokenToDecimal(String number)
   {
      char[] num = number.toCharArray();

      return 0;
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
}