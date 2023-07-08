/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parkingsystem.Controllers;

/**
 *
 * @author ahmed maher
 */
public class ArrayString {

    private final StringBuilder string = new StringBuilder();
    private final String lineSeperator = System.lineSeparator();

    public void ArrayString() {
    }

    /**
     *
     * @param args Elements to intialize the array with
     */
    public void intializeArray(String... args) {
        for (String arg : args) {
            this.push(arg);
        }
    }

    /**
     *
     * @return Returns an array of strings
     */
    public String[] array() {
        String[] array = this.string.toString().split(this.lineSeperator);
        array[array.length - 1] = removeBreakLine(array[array.length - 1]);
        return array;
    }

    /**
     *
     * @param value Element of type string, to be pushed in the array
     */
    public void push(String value) {
        this.string.append(value).append(this.lineSeperator);
    }

    /**
     *
     * @param index Index of the element to be returned
     * @return Returns the element
     */
    public String pop(int index) {
        return this.removeBreakLine(array()[index]);
    }

    /**
     *
     * @return Returns the array size
     */
    public int size() {
        return this.array().length;
    }

    /**
     *
     * @param element
     * @return Returns element index, if not found returns -1
     */
    public int indexOF(String element) {
        String[] array = array();

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @param array Array of elements
     * @param element element within the array
     * @return Returns element index, if not found returns -1
     */
    public static int indexOF(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @param number String contains a numbers
     * @return Returns true if it contains numbers only, else returns false
     */
    public static boolean isDigit(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!(number.charAt(i) >= '0' && number.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

        /**
     *
     * @param string String contains a chars
     * @return Returns true if it contains characters or white spaces only, else returns false
     */
    public static boolean isAlpha(String string) {
        String loweredString = string.toLowerCase();

        for (int i = 0; i < loweredString.length(); i++) {
            if (!(loweredString.charAt(i) == ' ' || (loweredString.charAt(i) >= 'a' && loweredString.charAt(i) <= 'z'))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param element
     * @return Returns true if element is within the array, false otherwise
     */
    public boolean exists(String element) {
        return indexOF(element) != -1;
    }

    private String removeBreakLine(String string) {
        return string.replaceAll("(\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029])$", "");
    }
}
