package com.esgi;


import com.esgi.Utils.XMLToMongoDB;

/**
 * Created by Karthi on 09/10/2016.
 */
public class MainApplication {

    public static void main (String[] args){

        XMLToMongoDB x1 = new XMLToMongoDB();
        x1.Parser(System.getProperty("user.dir")+"/file_xml.xml");


    }



}
