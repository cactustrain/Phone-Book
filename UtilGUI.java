// Phone Book - Advanced Programming Assignment
// Michael Russell; 21st December 2005
// BSc Computing with Software Engineering, Bradford College

// This code is taken from the class notes "Centering a main window"

import java.awt.*;

public class UtilGUI 
{
    /** Centre a Window, Frame, JFrame, Dialog, etc. */
    public static void centre(Window w) 
    {
        // After packing a Frame or Dialog, centre it on the screen.
        Dimension us = w.getSize( ), 
            them = Toolkit.getDefaultToolkit( ).getScreenSize( );
        int newX = (them.width - us.width) / 2;
        int newY = (them.height- us.height)/ 2;
        w.setLocation(newX, newY);
    }
}