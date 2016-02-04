package com.mrcrayfish.modelcreator;

import com.mrcrayfish.modelcreator.dialog.WelcomeDialog;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AlternateApp {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> createAndShowGUI());
        }

        public static void createAndShowGUI(){
//            JFrame frame = new JFrame("AlternateApp");
//            frame.setPreferredSize(new Dimension(200, 200));
//            frame.pack();
//            frame.setVisible(true);
            JFrame frame = new WindowFrame();

            URL stickerUrl = ModelCreator.class.getResource("sticker.png");
            WelcomeDialog.show(frame, stickerUrl);

        }



}