/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package argumentValidation;

/**
 *
 * @author Steyskal
 */
public class ArgumentValidator {

    private String[] arguments;

    public ArgumentValidator(String[] arguments) {
        this.arguments = arguments;
    }

    public boolean Validate() {
        boolean isValid = true;

        if (arguments.length != 5) {
            System.err.println("The number of arguments is invalid!");
            isValid = false;
            return isValid;
        }

        // Number of rows validation
        try {
            int rowNum = Integer.parseInt(arguments[0]);
            if (rowNum < 24 || rowNum > 40) {
                System.err.println("The number of rows is out of bounds! (24 - 40)");
                isValid = false;
            }
        } catch (Exception e) {
            System.err.println("The number of rows is not a digit! (24 - 40)");
            isValid = false;
        }

        // Number of columns validation
        try {
            int columnNum = Integer.parseInt(arguments[1]);
            if (columnNum < 80 || columnNum > 160) {
                System.err.println("The number of columns is out of bounds! (80 - 160)");
                isValid = false;
            }
        } catch (Exception e) {
            System.err.println("The number of columns is not a digit! (80 - 160)");
            isValid = false;
        }
        
        // Screen separation choice validation
        String screenSeparation = arguments[2];
        if(!screenSeparation.equalsIgnoreCase("V") && !screenSeparation.equalsIgnoreCase("O")){
            System.err.println("The screen separation choice is invalid! (V/O)");
            isValid = false;
        }
                
        // Number of seconds validation
        try {
            int secondsNum = Integer.parseInt(arguments[4]);
            if (secondsNum < 1 || secondsNum > 120) {
                System.err.println("The number of seconds is out of bounds! (1 - 120)");
                isValid = false;
            }
        } catch (Exception e) {
            System.err.println("The number of seconds is not a digit! (1 - 120)");
            isValid = false;
        }
        
        return isValid;
    }
}
