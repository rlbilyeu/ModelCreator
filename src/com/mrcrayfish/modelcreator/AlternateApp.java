package com.mrcrayfish.modelcreator;

import javax.swing.JFrame;

public class AlternateApp {

    public static void main(String[] args) {
        //Schedule as job for the event-dispatching thread:
        javax.swing.SwingUtilities.invokeLater(AlternateApp::createAndShowGUI);
    }


    private static void createAndShowGUI() {
        JFrame frame = new WindowFrame();
    }



}