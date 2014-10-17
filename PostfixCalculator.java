import java.util.Stack;
import java.util.Scanner;
/**
 * Evaluate an equation with decimal operands 
 * written as a String in infix notation.
 *
 * @author Brandon Cooper
 * @version Fall 2014
 */
public class PostfixCalculator
{
   private Stack<Integer> operands;

   /**
    * Constructor for PostfixCalculator.
    * @param infixEquation   Mathematical equation in infix notation
    */
   public PostfixCalculator()
   {
      operands = new Stack<Integer>();
   }

   /**
    * Evaluate an expression.
    * @param equation   postfix equation to evaluate
    * @return solution to equation
    */
   public int evaluate(String equation)
      throws NumberFormatException
   {
      int operandOne;
      int operandTwo;
      int answer = 0;
      String token = "";
      Scanner equationScanner = new Scanner(equation);

      try
      {
         // Parse equation
         while (equationScanner.hasNext())
         {
            token = equationScanner.next();

            // If the token is an operation
            if (token.equals("+") || token.equals("-")
                  || token.equals("*") || token.equals("/")
                  || token.equals("^"))
            {
               operands.push(singleEvaluate(operands.pop(), operands.pop(), token));
            }
            // If token is not an operation, try to parse number
            else
            {
               operands.push(Integer.parseInt(token));
            }
         }

         answer = operands.pop();
      }
      catch (java.util.EmptyStackException except)
      {
         throw new NumberFormatException();
      }

      return answer;
   }

   /**
    * Apply an operator to the two operands.
    * @param rightOperand   Operand on right side of operator.
    * @param leftOperand    Operand on left side of operator.
    * @param operator   Operator to be applied to two operands
    * @return answer to single expression.
    */
   private int singleEvaluate(
      int rightOperand, int leftOperand, String operator)
   {
      int solution = 0;

      // Apply the operator to both operands
      switch (operator)
      {
         case "+":
            solution = leftOperand + rightOperand;
            break;
         case "-":
            solution = leftOperand - rightOperand;
            break;
         case "*":
            solution = leftOperand * rightOperand;
            break;
         case "/":
            solution = leftOperand / rightOperand;
            break;
         case "^":
            solution = (int) Math.pow(leftOperand, rightOperand);
            break;
      }

      return solution;
   }
}