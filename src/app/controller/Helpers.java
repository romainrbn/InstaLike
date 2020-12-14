package app.controller;

public class Helpers {

    public static boolean checkStringForSignUp(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;

        if (str.isEmpty() || str.length() < 6)
            return false;

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if(Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
}
