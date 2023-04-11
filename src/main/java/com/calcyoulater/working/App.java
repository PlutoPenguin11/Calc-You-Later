package com.calcyoulater.working;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.calcyoulater.gui.Calculator;

public class App {
    public static void main(String[] args) throws IOException {
        Calculator c = Calculator.instance();
        App.setIconImage(c,"/calculator_icon.png");
    }

    public static void setIconImage(Calculator frame, String imageName) throws IOException {
        InputStream is = App.class.getResourceAsStream(imageName);
        Image image = ImageIO.read(is);
        frame.setIconImage(image);
    }
}
