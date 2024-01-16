package com.example.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Theme {
    private static Color mainColor = new Color(16, 17, 20);

    public static Color bgColor = mainColor.brighter().brighter();
    public static Color buttonColor = mainColor;

    public static Font font = registerFont();

    private static Font registerFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/UbuntuMono-Regular.ttf"));
            font = font.deriveFont(Font.BOLD, 14f);
        } catch (FontFormatException | IOException e) {
            ((Throwable) e).printStackTrace();
        }

        return font;
    }
}
