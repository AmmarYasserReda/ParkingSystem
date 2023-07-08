/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProjectManagementSystemProject.Controller;

import java.io.*;

/**
 *
 * @author ahmed maher
 */
public class DataBase {

    protected File file;
    protected BufferedReader read;
    protected BufferedWriter write;

    protected final String delimeter = "\t";
    protected final String LineSeprator = System.lineSeparator();

    public DataBase(File file) throws IOException {
        this.file = file;
        this.createFile();
    }

    protected final void createFile() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     *
     * @param file The file on which the DataBase operates on
     */
    public void setFile(File file) throws IOException {
        this.file = file;
        this.createFile();
    }

    /**
     *
     * @return Returns the whole file content as an array of strings. In case of
     * file reading issue ,or file is empty file the method returns null
     * @throws java.io.IOException
     */
    public String[] readLines() throws IOException {
        read = new BufferedReader(new FileReader(file));
        if (file.canRead() && file.length() != 0) {
            StringBuilder data = new StringBuilder();

            while (read.ready()) {
                data.append(read.readLine()).append(LineSeprator);
            }

            String[] lines = data.toString().split(LineSeprator);
            lines[lines.length - 1] = DataBase.removeBreakLine(lines[lines.length - 1]);
            read.close();

            return lines;
        }

        return null;
    }

    /**
     *
     * @param offset The field position to match 'searchIndex' with
     * @param searchIndex value to compare with the specified field
     * @return Returns null in case no record found, else returns an array of
     * two values the file record, and the record index. In case of duplicates
     * the first instance is returned
     * @throws java.io.IOException
     */
    public String[] getRecordWhere(int offset, String searchIndex) throws IOException {
        String[] lines = this.readLines();

        if (lines == null) {
            return null;
        }

        for (int i = 0; i < lines.length; i++) {
            String[] word = lines[i].split(delimeter);
            if (searchIndex.equals(word[offset])) {
                String[] values = {DataBase.removeBreakLine(lines[i]), i + ""};
                return values;
            }
        }

        return null;
    }

    /**
     *
     * @param offset The field position to match 'searchIndex' with
     * @param searchIndex value to compare with the specified field.
     * @return Returns null in case no record found, else returns an array of
     * two values the file record, and the record index. In case of duplicates
     * the all duplicates are returned
     * @throws java.io.IOException
     */
    public String[] getMatchedRecordsWhere(int offset, String searchIndex) throws IOException {
        String[] lines = this.readLines();

        if (lines == null) {
            return null;
        }

        boolean flag = false;
        StringBuilder arrayString = new StringBuilder();

        for (String line : lines) {
            String[] words = line.split(delimeter);

            if (searchIndex.equals(words[offset])) {
                flag = true;
                arrayString.append(line).append(LineSeprator);
            }
        }

        return flag ? DataBase.removeBreakLine(arrayString.toString()).split(LineSeprator) : null;
    }

    /**
     *
     * @param offset The field position to match 'searchIndex' with
     * @param searchIndex value to compare with the specified field
     * @return returns record if found, else returns null
     * @throws java.io.IOException
     */
    public boolean recordExistsWhere(int offset, String searchIndex) throws IOException {
        String[] record = this.getRecordWhere(offset, searchIndex);

        return record != null;
    }

    /**
     *
     * @param truncate Truncate specifies whether to truncate existing data
     * @param stringsArgs Fields to be written as a record within the file
     * @return Writes a new line to file and return true on completion. Returns
     * false if the file is read only
     * @throws java.io.IOException
     */
    public boolean write(boolean truncate, String... stringsArgs) throws IOException {
        String record = String.join(this.delimeter, stringsArgs);

        if (file.canWrite()) {
            boolean fileExists = file.exists() && file.length() != 0 && !truncate;
            write = new BufferedWriter(new FileWriter(file, fileExists));
            String newLine = fileExists ? LineSeprator + record : record;

            write.append(newLine);
            write.close();

            return true;
        }

        return false;
    }

    /**
     *
     * @param truncate Truncate specifies whether to truncate existing data
     * @param line String to be written as a record within the file
     * @return Writes a new line to file and return true on completion. Returns
     * false if the file is read only
     * @throws java.io.IOException
     */
    protected boolean write(boolean truncate, String line) throws IOException {
        if (file.canWrite()) {
            boolean fileExists = file.exists() && file.length() != 0 && !truncate;
            write = new BufferedWriter(new FileWriter(file, fileExists));
            String newLine = fileExists ? LineSeprator + line : line;

            write.append(newLine);
            write.close();

            return true;
        }

        return false;
    }

    /**
     *
     * @param offset The field position to match 'searchIndex' with
     * @param searchIndex The PK of the record to be updated
     * @param fieldIndex The field position to be updated.
     * @param replacement Value to be replaced with a specific field (offset)
     * within a record in file
     * @return Returns false if the file is read only, and there's reading
     * issue, else returns true on completion
     * @throws java.io.IOException
     *
     */
    public boolean updateRecordWhere(int offset, String searchIndex, int fieldIndex, String replacement) throws IOException {
        if (file.canRead() && file.canWrite()) {
            String[] record = this.getRecordWhere(offset, searchIndex);

            if (record == null) {
                return false;
            }

            String[] lines = this.readLines();
            String[] words = record[0].split(this.delimeter);

            words[fieldIndex] = replacement;
            lines[Integer.parseInt(record[1])] = String.join(this.delimeter, words);

            for (int iterator = 0; iterator < lines.length; iterator++) {
                this.write(iterator == 0, lines[iterator]);
            }

            return true;
        }

        return false;
    }

    /**
     *
     * @param offset The field position to match 'searchIndex' with
     * @param searchIndex Value to compare with the specified field
     * @return Removes a specific record in file, and returns true on
     * completion. Returns false if the file is read only
     * @throws java.io.IOException
     */
    public boolean deleteRecordWhere(int offset, String searchIndex) throws IOException {
        if (file.canWrite()) {
            String[] record = this.getRecordWhere(offset, searchIndex);

            if (record == null) {
                return false;
            }

            String[] fileContents = this.readLines();
            String[] newContents = this.splice(fileContents, Integer.parseInt(record[1]));
            for (int i = 0; i < newContents.length; i++) {
                this.write(i == 0, newContents[i]);
            }

            return true;
        }

        return false;
    }

    /**
     *
     * @param strings Array of values
     * @param index Index of an element within the array
     * @return Returns a new array where the element at the specified index is
     * removed.
     */
    public String[] splice(String[] strings, int index) {
        if (strings == null) {
            return strings;
        }
        
        int stringsLength = strings.length;
        int newStringsLength = stringsLength - 1;
        String[] newStrings = new String[newStringsLength];

        for (int iterator = 0; iterator < stringsLength; iterator++) {
            if (iterator == index) {
                continue;
            }

            newStrings[(iterator >= index) ? iterator - 1 : iterator] = strings[iterator];
        }

        return newStrings;
    }

    /**
     *
     * @param record Returned by the DataBase class
     * @return Returns array of strings ( fields ).
     */
    public String[] toFields(String record) {
        return record.split(this.delimeter);
    }

    /**
     *
     * @param string Any String
     * @return Returns a record without a break line at the end
     */
    public static String removeBreakLine(String string) {
        return string.replaceAll("(\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029])$", "");
    }
}
