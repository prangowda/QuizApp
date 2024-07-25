/*
// Name  : Pranam KG
// Regno : 230970108
//Class&Section : MCA -"B"

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.quizapp;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.swing.*;
import org.bson.Document;

/**
 *
 * @author Pranam
 */

// this codes creates  quetion page with  question 
public class QuestionsPanel extends javax.swing.JPanel {
    private List<Document> questions;
    private JPanel contentPanel; // Panel to hold question panels
    private List<ButtonGroup> buttonGroups = new ArrayList<>(); // Track ButtonGroups for each question

    /**
     * Creates new form Questions
     */
    public QuestionsPanel(QuizAPP mainFrame) {
        //initComponents();
        setLayout(new BorderLayout()); // Use BorderLayout for the panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertical BoxLayout
        JScrollPane scrollPane = new JScrollPane(contentPanel,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Adjust this value scrolling
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); 
        JLabel title = new JLabel("Questions");
        title.setFont(new Font("Arial",Font.BOLD,20));
        title.setForeground(Color.RED);
        contentPanel.add(title);
        loadQuestionsFromDatabase();
        initializeComponents();
        displayQuestions();

        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the center

    }

    private void loadQuestionsFromDatabase() {
        try {
            final MongoDatabase db = MongoDB.getDatabase();
            MongoCollection<Document> collection = db.getCollection("quiz");
            questions = collection.find().into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeComponents() {
        if (questions.isEmpty()) {
            System.out.println("No questions found in the database");
            return;
        }
        
        for (Document question : questions) {
            JPanel questionPanel = createQuestionPanel(question);
            contentPanel.add(questionPanel);
        }

        JButton submitButton = new JButton("Submit Answers");
        submitButton.setFont(new Font("Arial",Font.BOLD,20));
        submitButton.setBackground(Color.GREEN);

        submitButton.addActionListener(e -> handleSubmit());
        contentPanel.add(submitButton); 
    }

    private JPanel createQuestionPanel(Document question) {
    JPanel questionPanel = new JPanel();
    questionPanel.setLayout(new BorderLayout());
    questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // adds margin around each question panel

    JLabel questionLabel = new JLabel(question.getString("question"));
    questionLabel.setFont(new Font("Arial",Font.BOLD,16));

    questionPanel.add(questionLabel, BorderLayout.NORTH);

    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
    ButtonGroup group = new ButtonGroup();
    List<String> options = (List<String>) question.get("opts");
    for (String option : options) {
        JRadioButton optionButton = new JRadioButton(option);
        optionButton.setActionCommand(option);
        group.add(optionButton);
        optionButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // spacing between radio buttons
        optionsPanel.add(optionButton);
    }
    questionPanel.add(optionsPanel, BorderLayout.CENTER);
    buttonGroups.add(group);

    return questionPanel;
}

    private void displayQuestions() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void handleSubmit() {
        int correctCount = 0;
        boolean allAnswered = true;
        for (ButtonGroup bg : buttonGroups) {
            if (bg.getSelection() == null) {
                allAnswered = false;
                break;
            }
        }
    
        if (!allAnswered) {
            JOptionPane.showMessageDialog(this, "Please attempt all questions!", "Alert", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Calculate correct answers
        for (int i = 0; i < questions.size(); i++) {
            ButtonGroup bg = buttonGroups.get(i);
            ButtonModel selectedModel = bg.getSelection();
            String selectedAnswer = null;
    
            // Find which button is selected by comparing the model with each button's model
            Enumeration<AbstractButton> buttons = bg.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton button = buttons.nextElement();
                if (button.getModel() == selectedModel) {
                    selectedAnswer = button.getActionCommand();
                    break;
                }
            }
    
            // Check if the selected answer is correct
            Document questionDoc = questions.get(i);
            String correctAnswer = questionDoc.getString("ans");
            if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
                correctCount++;
            }
        }
   
        //upload result
        uploadResult(correctCount);
        // Show results and exit
        JOptionPane.showMessageDialog(this, "You got " + correctCount + " out of " + questions.size() + " correct!", "Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    private void uploadResult(double score) {
        try {
            final MongoDatabase db = MongoDB.getDatabase();
            MongoCollection<Document> collection = db.getCollection("results");
    
            String username = Session.getUser(); // Assuming Session.getUser() returns the username of the currently logged-in user
            Date currentDate = new Date(); // Gets the current system date
    
            Document updateDocument = new Document()
                .append("$set", new Document("score", score).append("date", currentDate));
    
            // Update options to perform an upsert (update existing or insert new if not exists)
            UpdateOptions options = new UpdateOptions().upsert(true);
    
            // Update or insert the document based on the user
            collection.updateOne(Filters.eq("user", username), updateDocument, options);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to upload results: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("     Questions");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(278, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
