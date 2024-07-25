// Name  : Pranam KG
// Regno : 230970108
//Class&Section : MCA -"B"

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizapp;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
/**
 *
 * @author Pranam
 */
// retun the  connecton object from  the database
public class MongoDB {
    private static final String URI = " "; //connection string here
    private static final MongoClient mongoClient = MongoClients.create(URI);
    private static final MongoDatabase database = mongoClient.getDatabase("quiz");

    public static MongoDatabase getDatabase() {
        return database;
    }
}
