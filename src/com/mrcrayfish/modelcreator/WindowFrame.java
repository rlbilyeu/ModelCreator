package com.mrcrayfish.modelcreator;

import com.mrcrayfish.modelcreator.panels.SidebarPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class WindowFrame extends JFrame {
    private Canvas canvas;
    private JScrollPane scrollable;


    public WindowFrame() {
        JFrame frame = new JFrame("AlternateApp");
        initWindowFrame(frame);

        canvas = new Canvas();
        initCanvas(frame);

        scrollable = new ScrollPane(frame);
        frame.pack();


    }


    private JFrame initWindowFrame(JFrame frame) {
        frame.setPreferredSize(new Dimension(1200, 815));
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setLayout(new BorderLayout(10, 0));
        frame.setIconImages(getIcons());
//		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;





    }

    private java.util.List<Image> getIcons() {
        List<Image> icons = new ArrayList<>();
        icons.add(Toolkit.getDefaultToolkit().getImage("res/icons/set/icon_16x.png"));
        icons.add(Toolkit.getDefaultToolkit().getImage("res/icons/set/icon_32x.png"));
        icons.add(Toolkit.getDefaultToolkit().getImage("res/icons/set/icon_64x.png"));
        icons.add(Toolkit.getDefaultToolkit().getImage("res/icons/set/icon_128x.png"));
        return icons;
    }

    private Canvas initCanvas(JFrame frame) {
        canvas.setPreferredSize(new Dimension(1000, 790));
        frame.add(canvas, BorderLayout.CENTER);
        canvas.setFocusable(true);
        canvas.setVisible(true);
        canvas.requestFocus();
        return canvas;




 /*
from forums
http://wiki.lwjgl.org/wiki/Using_a_Resizeable_AWT_Frame_with_LWJGL
    Create an empty canvas (AWT).

    Set the size of the canvas to be equal to the LWJGL Display.

    Add the canvas to the Java Swing form. (There's a reason for this).

    Set the canvas as parent of the LWJGL Display using Display.setParent(canvasName);. (Remember you might also need to allow the display to be resizable.)

    The reason for adding the canvas to the form before setting it as parent of the Display is that, the canvas needs to be drawable so it can be a parent of the the LWGL Display. Otherwise your program will crash with an error that no OpenGL context was found.
*/



    }

}



 class  ScrollPane extends JScrollPane {
     JScrollPane scrollPane;
     JPanel sidePanel;


     public ScrollPane(Frame frame){
         sidePanel = new SidebarPanel(frame);

//         sidePanel = new JPanel();
//         setLayout(layout = new SpringLayout());
//         sidePanel.setPreferredSize(new Dimension(200, 760));

         sidePanel.setBackground(Color.BLUE);


         scrollPane = new JScrollPane(sidePanel);
         scrollPane.setName("scrollpane");
         scrollPane.setBorder(BorderFactory.createEmptyBorder());
         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         frame.add(scrollPane, BorderLayout.EAST);
     }


 }


