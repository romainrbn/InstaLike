package test.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static app.controller.Helpers.checkStringForSignUp;
import static org.junit.jupiter.api.Assertions.*;

class Helpers {

    String noCapitalLetterString;
    String hasCapitalLetterString;
    String hasOnlyCapitalLetters;
    String isEmptyString;
    String hasANumber;
    String isTooShort;

    @BeforeEach
    void setUp() {
        noCapitalLetterString    = "abcdef";
        hasCapitalLetterString   = "Abcdef";
        hasOnlyCapitalLetters    = "ABCDEF";
        isEmptyString            = "";
        hasANumber               = "ABc1ef";
        isTooShort               = "aBc1";
    }

    @Test
    public void testHasCapitalLetter() {
        assertFalse(checkStringForSignUp(hasCapitalLetterString),   "La chaîne contient au moins une " +
                "majuscule mais pas de chiffre.");
        assertFalse(checkStringForSignUp(hasOnlyCapitalLetters),    "La chaîne ne contient que des " +
                "majuscules.");
        assertFalse(checkStringForSignUp(noCapitalLetterString),    "La chaîne ne contient pas de " +
                "majuscule.");
        assertFalse(checkStringForSignUp(isEmptyString),            "La chaîne vide ne contient pas de " +
                "majuscule.");
        assertTrue(checkStringForSignUp(hasANumber),                "La chaîne possède au moins une " +
                "majuscule, au moins une minuscule et au moins un chiffre.");
        assertFalse(checkStringForSignUp(isTooShort),               "La chaîne possède moins de 6 caractères.");
    }
}