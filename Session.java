// Name  : Pranam KG
// Regno : 230970108
//Class&Section : MCA -"B"

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quizapp;

/**
 *
 * @author Pranam
 */
// this function  to store user name to  next result page 
public class Session {   
    public static String username;
    
    public static void setUser(String user){
        username=user;
    }
    
    public static String getUser(){
        return username;
    }
}
