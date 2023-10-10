import java.io.*;
import java.util.Scanner;
import java.util.Stack;

@SuppressWarnings("Unchecked")
public class SymbolBalance implements SymbolBalanceInterface{

    private String file;
    private Stack<Character> theStack; // declare a stack to keep track of opening symbols

    // set the filename to check
    public void setFile(String filename) {
        file = filename;
    }

    // check if the file has balanced symbols
    public BalanceError checkFile() {
        try {
            theStack = new Stack<>(); // create a new stack for each file
            File testerFile = new File(file); // create a file object from the filename
            Scanner in = new Scanner(testerFile); // create a scanner to read the file
            int line = 1; // initialize a counter for line numbers
            String input = ""; // initialize a string to hold each line of input
            boolean skip = false; // initialize a flag to skip checking symbols inside comments or quotes
            boolean quotes = false; // initialize a flag to track if the current symbol is inside a quote

            // loop through each line of the file
            while(in.hasNextLine()) {
                input = in.nextLine();
                for(int i = 0; i < input.length(); i ++) { // loop through each character in the line

                    char c = input.charAt(i); // get the current character

                    // if the character is a double quote and not inside a comment or quote
                    if(c == '"' && !quotes && !skip) {
                        theStack.push(c); // push the character onto the stack
                        quotes = true; // set the quote flag to true
                    }
                    // if the character is an asterisk and is following a forward slash and not inside a comment or quote
                    else if(c == '*' && input.charAt(i-1) == '/' && !skip && !quotes) {
                        theStack.push(c); // push the character onto the stack
                        skip = true; // set the skip flag to true
                    }
                    // if the character is an opening symbol and not inside a comment or quote
                    else if (((c == '{')||(c == '(')||(c=='[')) && !skip && !quotes) {
                        theStack.push(c); // push the character onto the stack
                    }

                    // if the character is an asterisk and following a forward slash and inside a comment or quote
                    if(c == '*' && input.charAt(i-1) == '/' && !quotes && skip) {
                        continue; // skip the current iteration of the loop and move to the next character
                    }
                    // if the character is an asterisk and preceding a forward slash and inside a comment or quote
                    else if(c == '*' && input.charAt(i+1) == '/' && !quotes && skip) {
                        // check if the stack is empty
                        if(theStack.isEmpty()) {
                            BalanceError f = new EmptyStackError(line); // create an EmptyStackError object
                            return f; // return the error object
                        }
                        // if the stack is not empty and skip flag is not set
                        else if(!skip) {
                            char popped = theStack.pop(); // pop the top character from the stack
                            BalanceError e = new MismatchError(line, c, popped); // create a MismatchError object
                            return e; // return the error object
                        }
                        // if the stack is not empty and skip flag is set
                        else {
                            theStack.pop(); // pop the top character from the stack
                            skip = false; // set the skip flag to false
                        }
                    } else if((c == ')' || c == '}' || c == ']') && !skip && !quotes) {
                        // If a closing symbol is encountered and not within a comment or quotes
                        if(theStack.isEmpty()) {
                            // If the stack is empty, return an EmptyStackError
                            BalanceError f = new EmptyStackError(line);
                            return f;
                        } else {
                            // Otherwise, check for matching opening symbol
                            if(c == '}' && theStack.peek() == '{') {
                                char popped = theStack.pop(); // If matching, pop from stack
                            } else if(c == ')' && theStack.peek() == '(') {
                                char popped = theStack.pop(); // If matching, pop from stack
                            } else if(c == ']' && theStack.peek() == '[') {
                                char popped = theStack.pop(); // If matching, pop from stack
                            } else {
                                // If not matching, return MismatchError
                                char popped = theStack.pop();
                                BalanceError e = new MismatchError(line, c, popped);
                                return e;
                            }
                        }
                    }

                }
                line++;
            }
            // System.out.println(theStack.peek());
            if(!theStack.isEmpty()) {
                BalanceError g = new NonEmptyStackError(theStack.peek(), theStack.size());
                return g;
            }
            //will check for exception
        } catch(FileNotFoundException e) {
            System.out.println("Invalid input");
        }
        return null;
    }
}