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
public class DBTable extends DataBase {

    public DBTable(File file) throws IOException {
        super(file);
    }
    
    public void setHeaders(String... args) throws IOException{
        if(this.readLines() == null){
            this.write(true, String.join(this.delimeter, args));
        }
    }
}
