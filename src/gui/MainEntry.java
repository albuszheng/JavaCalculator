package gui;
import javax.swing.*;
import java.awt.*;

/**
 * Created by keyangzheng on 9/1/16.
 */
public class MainEntry {

    public static void main(String[] args){
        WrapperPanel wrapper = new WrapperPanel();
        JPanel buttonSection = new ButtonSection();
        JPanel displaySection = new DisplayPanel();

        JFrame frame = new JFrame("Calculator");
        frame.add(wrapper);
        frame.setSize(new Dimension(500, 550));


        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);//display the whole gui

    }

}
