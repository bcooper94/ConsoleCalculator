import java.util.Stack;
import java.util.Scanner;
/**
 * Converts a mathematical equation with only integers 
 * written as a String in infix notation to postfix notation.
 * 
 * @author Brandon Cooper
 * @version Fall 2014
 */
public class InfixToPostfix
{
   private Stack<String> operations;
   private boolean integerMode;

   /**
    * Constructor for InfixToPostfix.
    * @param integerMode   whether or not to only allow integers.
    */
   public InfixToPostfix(boolean integerMode)
   {
      operations = new Stack<String>();
      this.integerMode = integerMode;
   }

   /**
    * Convert last equation added to InfixToPostfix to postfix.
    * @return Equation in postfix notation.
    * @throws NumberFormatException if infix expression is invalid.
    */
   public String toPostfix(String equation) throws NumberFormatException
   {
      String token = null;
      String postfix = "";
      Scanner operationReader = new Scanner(equation);
      Scanner numberReader = new Scanner(equation);

      // Add operations to operations Stack and 
      // numbers to numbers Stack
      while (operationReader.hasNext())
      {
         token = operationReader.next();
         // If token is an operation
         if (token.equals("+") || token.equals("-")
               || token.equals("*") || token.equals("/")
               || token.equals("^") || token.equals("(")
               || token.equals(")"))
         {
            postfix = processOperator(token, postfix);
         }
         // If token is not an operation, try to parse number
         else
         {
            try
            {
               // If in integer only mode
               if (integerMode)
               {
                  postfix += Integer.parseInt(token) + " ";
               }
               // If not in integer only mode
               else
               {
                  postfix += Double.parseDouble(token) + " ";
               }
            }
            catch (NumberFormatException except)
            {
               throw new NumberFormatException("Syntax error");
            }
         }
      }

      // Pop remainder of stack from operators
      while (!operations.empty())
      {
         postfix += operations.pop() + " ";
      }

      return postfix.trim();
   }

   /**
    * Process an operator by adding to the stack, popping the stack,
    * and/or appending it to the postfix String.
    * @param operator   Operator to be processed
    * @param postfix    postfix mathematical expression
    * @return new postfix String after operator is processed
    */
   private String processOperator(
      String operator, String postfix)
   {
      String lastPopped = "";

      // If operations is empty or
      // operator has higher precedence over top of operations stack
      if (operations.empty()
         || opPrecedence(operations.peek()) <= opPrecedence(operator)
         || operations.peek().equals("("))
      {
         operations.add(operator);
      }
      // If operator has a lower precedence over top of the operations stack
      else if (!operator.equals(")"))
      {
         // Pop the stack til 
         // opPrecedence(operator) > opPrecedence(top of operations stack),
         // add to postfix String
         while (!operations.empty()
               && opPrecedence(operator) <= opPrecedence(operations.peek()))
         {
            // Don't write parentheses to postfix expression
            if (!(operations.peek().equals("(")
                  || operations.peek().equals(")")))
            {
               postfix += operations.pop() + " ";
            }
            // If parenthesis on top of stack, just pop it off
            else
            {
               operations.pop();
            }
         }

         operations.add(operator);
      }
      // If operator is closing parenthesis
      else
      {
         try
         {
            // Pop all operators off the stack up
            // through first open parenthesis
            while (!operations.peek().equals("("))
            {
               postfix += operations.pop() + " ";
            }
            // Pop closing parenthesis off without adding to equation
            operations.pop();
         }
         catch (java.util.EmptyStackException except)
         {
            throw new NumberFormatException("Syntax error");
         }
      }

      return postfix;
   }

   /**
    * Get the precedence of a mathematical operator.
    * @param operator   Operator to evaluate precedence.
    * @return predence of operator.
    */
   private int opPrecedence(String operator)
   {
      int precedence;
      
      // If operator is )
      if (operator.equals(")"))
      {
         precedence = -1;
      }
      // If operator is + or -
      else if (operator.equals("+") || operator.equals("-"))
      {
         precedence = 0;
      }
      // If operator is * or /
      else if (operator.equals("*") || operator.equals("/"))
      {
         precedence = 1;
      }
      // If operator is ^
      else if (operator.equals("^"))
      {
         precedence = 2;
      }
      // If operator is (
      else
      {
         precedence = 3;
      }

      return precedence;
   }
}