package com.mrcrayfish.modelcreator;

import com.mrcrayfish.modelcreator.dialog.WelcomeDialog;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.net.URL;

public class AlternateApp {

        public static void main(String[] args) {
            //schedule as job on event-dispatch thread
            SwingUtilities.invokeLater(() -> createAndShowGUI());
        }

        public static void createAndShowGUI(){
            JFrame frame = new WindowFrame();
            URL stickerUrl = ModelCreator.class.getResource("sticker.png");
            WelcomeDialog.show(frame, stickerUrl);
        }

        public JFrame createSimpleFrame() {
            JFrame frame = new JFrame("AlternateApp");
            frame.setPreferredSize(new Dimension(200, 200));
            frame.pack();
            frame.setVisible(true);
            return frame;
        }



}