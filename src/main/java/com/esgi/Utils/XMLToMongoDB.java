package com.esgi.Utils;


import com.mongodb.*;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Karthi on 09/10/2016.
 */
public class XMLToMongoDB {


    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public void Parser(String path) {
        String file = null;
        try {

            System.out.println(path);
            file = readFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(file.toString());
            toMongoDb(xmlJSONObj);
            //String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }

    public String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    public void toMongoDb(JSONObject json){
        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("esgi");
            DBCollection collection = db.getCollection("article");

            JSONArray json_main = json.getJSONObject("dblp").getJSONArray("article");


            for(int i = 0 ; i < json_main.length() ; i++){

                JSONObject current_object = json_main.getJSONObject(i);
                DBObject dbObject = (DBObject) JSON.parse(current_object.toString());
                collection.insert(dbObject);

            }


            DBCursor cursorDoc = collection.find();
            while (cursorDoc.hasNext()) {
                System.out.println(cursorDoc.next());
            }

            System.out.println("Done");

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }








}
