package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by keyangzheng on 9/1/16.
 */
public class ButtonSection extends JPanel implements ActionListener{
    protected GridLayout layout = new GridLayout(0, 4, 10, 10);

    //number button group
    protected JButton numBtnOne = new JButton("1");
    protected JButton numBtnTwo = new JButton("2");
    protected JButton numBtnThree = new JButton("3");
    protected JButton numBtnFour = new JButton("4");
    protected JButton numBtnFive = new JButton("5");
    protected JButton numBtnSix = new JButton("6");
    protected JButton numBtnSeven = new JButton("7");
    protected JButton numBtnEight = new JButton("8");
    protected JButton numBtnNine = new JButton("9");
    protected JButton numBtnZero = new JButton("0");
    protected JButton numBtnBlank = new JButton();

    //operation button group
    protected JButton optBtnPlus = new JButton("+");
    protected JButton optBtnMinus = new JButton("-");
    protected JButton optBtnMultiply = new JButton("*");
    protected JButton optBtnDivision = new JButton("/");
    protected JButton optBtnDecimalPoint = new JButton(".");
    protected JButton optBtnEqual = new JButton("=");

    //panels for layout
    protected JPanel panelOperation = new JPanel();
    protected JPanel panelEqual = new JPanel();

    //
    private List<ButtonInputObserver> _listener = new ArrayList<ButtonInputObserver>();


    public ButtonSection(){
        super();
        this.setPreferredSize(new Dimension(500, 400));

        numBtnBlank.setVisible(false);

        panelOperation.setLayout(layout);
        panelOperation.setPreferredSize(new Dimension(500, 300));

        panelOperation.add(numBtnOne);
        panelOperation.add(numBtnTwo);
        panelOperation.add(numBtnThree);
        panelOperation.add(optBtnPlus);
        panelOperation.add(numBtnFour);
        panelOperation.add(numBtnFive);
        panelOperation.add(numBtnSix);
        panelOperation.add(optBtnMinus);
        panelOperation.add(numBtnSeven);
        panelOperation.add(numBtnEight);
        panelOperation.add(numBtnNine);
        panelOperation.add(optBtnMultiply);
        panelOperation.add(numBtnBlank);
        panelOperation.add(numBtnZero);
        panelOperation.add(optBtnDecimalPoint);
        panelOperation.add(optBtnDivision);

        panelEqual.setLayout(new GridLayout(1, 1, 10, 10));
        panelEqual.setPreferredSize(new Dimension(500, 75));
        panelEqual.add(optBtnEqual);

        this.setLayout(new BorderLayout(0,10));

        this.add(panelOperation, BorderLayout.NORTH);
        this.add(panelEqual, BorderLayout.SOUTH);

        numBtnOne.addActionListener(this);
        numBtnTwo.addActionListener(this);
        numBtnThree.addActionListener(this);
        numBtnFour.addActionListener(this);
        numBtnFive.addActionListener(this);
        numBtnSix.addActionListener(this);
        numBtnSeven.addActionListener(this);
        numBtnEight.addActionListener(this);
        numBtnNine.addActionListener(this);
        numBtnZero.addActionListener(this);
        optBtnPlus.addActionListener(this);
        optBtnMinus.addActionListener(this);
        optBtnMultiply.addActionListener(this);
        optBtnDivision.addActionListener(this);
        optBtnDecimalPoint.addActionListener(this);
        optBtnEqual.addActionListener(this);

    }

    public void registerListener(ButtonInputObserver observer){

        _listener.add(observer);
    }

    protected void transferInputEvent(String btnInput){
        for (ButtonInputObserver ob: _listener){
            ob.inputHappened(btnInput);
        }
    }

    public void actionPerformed(ActionEvent e){
        transferInputEvent(((JButton)e.getSource()).getText());
    }

}
