package com.example.gui;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    private static Color mainColor = new Color(16, 17, 20);

    public static Color bgColor = mainColor.brighter().brighter();
    public static Color buttonColor = mainColor;

    public static Font font = new Font(null, Font.PLAIN, 14);

}
