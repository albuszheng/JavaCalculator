package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keyangzheng on 9/9/16.
 */
public class WrapperPanel extends JPanel implements ButtonInputObserver {

    private DisplayPanel displaySection;
    private ButtonSection buttonSection;


    public WrapperPanel(){

        super();

        this.setPreferredSize(new Dimension(500, 450));

        displaySection = new DisplayPanel();
        buttonSection = new ButtonSection();

        this.add(displaySection);
        this.add(buttonSection);

        buttonSection.registerListener(this);
    }

    @Override
    public void inputHappened(String _eventInput){
        displaySection.updateDisplay(_eventInput);
    }

}
