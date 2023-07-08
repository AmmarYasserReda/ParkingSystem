/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.parkingsystem.Controllers;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author ahmed maher
 */
public class Authenication extends DataBase {

    public Authenication(File file) throws IOException {
        super(file);
    }

    // Authenicates an identified user.
    public String[] logIn(String identifier, String authenicator) throws Exception {
        String[] record = this.getRecordWhere(0, identifier);

        if (record != null) {
            String[] fields = record[0].split(this.delimeter);
            return fields[1].equals(authenicator) ? fields : null;
        }

        return null;
    }

    /**
     *
     * @param identifier Identification string
     * @param authenicator Authentication string
     * @param type User type
     * @return Returns false on
     * @throws java.lang.Exception
     */
    public boolean register(String identifier, String authenicator, String type) throws Exception {
        String record = identifier + this.delimeter + authenicator + this.delimeter + type;
        String[] storedRecored = this.getRecordWhere(0, identifier);

        if (storedRecored != null) {
            return false;
        }

        ArrayString roles = new ArrayString();
        roles.intializeArray("Employee", "Manager", "TeamLeader");

        if (!roles.exists(type)) {
            return false;
        }

        this.write(false, identifier, authenicator, type, "0");
        return true;
    }
}
