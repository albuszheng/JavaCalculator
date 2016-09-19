package gui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import calculating.*;

/**
 * Created by keyangzheng on 9/1/16.
 *
 * Todo display error handling and state transfer
 */
public class DisplayPanel extends JPanel {
    /**
     * display related variable
     *
     * times: &times; &#215;
     * division: &divide; &#247;
     */
    protected String upperSubDisplay = "";
    protected String mainDisplay = "0";
    protected JEditorPane displayPane = new JEditorPane();
    protected String cssRule = "div {margin: 2px;border: thin solid #333;Ω}p, h1 {text-align: right;margin: 0;padding: 5px 10px 0 10px;font: sans-serif;}p {font-size: 1.05em;line-height:.8;font-weight:100;}h1 {font-size: 2.em;line-height: 1.0;}";
    protected final String defaultHTML = "<div><p> </p><h1>0</h1></div>";
    protected String displayHTML = "";

    /**
     * state transfer indicator
     */
    private boolean decimalPointFlag = false;
    private State state = State.READY_FIRSTNUM;
    private double firstNum = 0;
    private double secondNum = 0;
    private Operator inputtedOperator = Operator._Default;
    private double computedResult = 0;


    public DisplayPanel(){
        super();

        this.setPreferredSize(new Dimension(500, 110));

        displayPane.setPreferredSize(new Dimension(500, 110));
        displayPane.setContentType("text/html");

        displayPane.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        displayPane.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(cssRule);

        Document doc = kit.createDefaultDocument();
        displayPane.setDocument(doc);
        displayHTML = defaultHTML;
        displayPane.setText(displayHTML);

        this.add(displayPane);

    }

    public void updateText(String mainDisplay, String upperSubDisplay){


        updateUI();
    }

    public void updateDisplay(String newInput){
        firstNum = firstNum*10 + Double.parseDouble(newInput);
        if (firstNum - (int)firstNum == 0.0){
            mainDisplay = Integer.toString((int)firstNum);
        }else {
            mainDisplay = Double.toString(firstNum);
        }
        displayHTML = "<div><p> </p><h1>"+mainDisplay+"</h1></div>";
        displayPane.setText(displayHTML);
        updateUI();
    }


    //state change control
    private void stateCheck(){

    }

    private void reset() {
        firstNum = 0;
        inputtedOperator = Operator._Default;
        secondNum = 0;
        computedResult = 0;
        state = State.READY_FIRSTNUM;

        mainDisplay = "0";
        upperSubDisplay = "";
    }

    private boolean isDigit(String s){
        return false;
    }

}
