package gui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.util.regex.Pattern;

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
        state = State.READY_FIRSTNUM;

    }

    public void updateDisplay(String newInput){
        if (decimalPointFlag) {

        } else {
            if (Pattern.matches(newInput, "[0-9]")){
                switch (state){
                    case READY_FIRSTNUM: {
                        firstNum = firstNum*10 + Double.parseDouble(newInput);
                        if (firstNum - (int)firstNum == 0.0){
                            mainDisplay = Integer.toString((int)firstNum);
                        }else {
                            mainDisplay = Double.toString(firstNum);
                        }
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        state = State.IN_FIRSTNUM;
                        updateUI();
                        break;
                    }
                    case READY_SECONDNUM: {
                        secondNum = secondNum*10 + Double.parseDouble(newInput);
                        if (secondNum - (int)secondNum == 0.0){
                            mainDisplay = Integer.toString((int)secondNum);
                        }else {
                            mainDisplay = Double.toString(secondNum);
                        }
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        state = State.IN_SECONDNUM;
                        updateUI();
                        break;
                    }
                    case IN_FIRSTNUM: {
                        firstNum = firstNum*10 + Double.parseDouble(newInput);
                        if (firstNum - (int)firstNum == 0.0){
                            mainDisplay = Integer.toString((int)firstNum);
                        }else {
                            mainDisplay = Double.toString(firstNum);
                        }
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case IN_SECONDNUM: {
                        secondNum = secondNum*10 + Double.parseDouble(newInput);
                        if (secondNum - (int)secondNum == 0.0){
                            mainDisplay = Integer.toString((int)secondNum);
                        }else {
                            mainDisplay = Double.toString(secondNum);
                        }
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                }
            } else if (newInput.equals(".")){
                //input as decimal point
                decimalPointFlag = true;
            } else if (newInput.equals("=")){
                //input as equal
                switch (state){
                    case IN_SECONDNUM:{
                        //normal procedure
                        computedResult = Calculator.operation(firstNum, secondNum, inputtedOperator.toString().charAt(0));
                        upperSubDisplay = upperSubDisplay + " =";
                        if (computedResult - (int)computedResult == 0.0){
                            mainDisplay = Integer.toString((int)computedResult);
                        }else {
                            mainDisplay = Double.toString(computedResult);
                        }
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        reset();
                        break;
                    }
                    case IN_FIRSTNUM:{
                        //error: Equal without OP
                        mainDisplay = "We don\'t Have a Operator!";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case READY_FIRSTNUM:{
                        //error: Leading with Equal
                        mainDisplay = "Don\'t Start with = !";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case READY_SECONDNUM:{
                        //error: OP -> Equal
                        mainDisplay = "Where is the 2nd Num?";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                }
            } else {
                // input as operator
                switch (state){
                    case IN_FIRSTNUM:{
                        // normal procedure
                        switch (newInput.charAt(0)){
                            case '+': {
                                inputtedOperator = Operator.PLUS;
                                break;
                            }
                            case '-': {
                                inputtedOperator = Operator.MINUS;
                                break;
                            }
                            case '×': {
                                inputtedOperator = Operator.MULTIPLY;
                                break;
                            }
                            case '÷': {
                                inputtedOperator = Operator.DIVISION;
                                break;
                            }
                        }
                        upperSubDisplay = mainDisplay + " " + newInput;
                        mainDisplay = "";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h1>"+mainDisplay+"</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case READY_FIRSTNUM:{
                        //error: Leading with OP
                        mainDisplay = "Input a num first!";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case READY_SECONDNUM:{
                        //error: OP -> OP
                        mainDisplay = "Num not Operator Please!";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case IN_SECONDNUM:{
                        //error: Multiple OP
                        mainDisplay = "Multiple Op not Supported!";
                        displayHTML = "<div><p>"+ upperSubDisplay +"</p><h3><span style=\"color:blue;\">"+mainDisplay+"</span></h3></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                }
            }//end of operator input
        }

//        firstNum = firstNum*10 + Double.parseDouble(newInput);
//        if (firstNum - (int)firstNum == 0.0){
//            mainDisplay = Integer.toString((int)firstNum);
//        }else {
//            mainDisplay = Double.toString(firstNum);
//        }
//        displayHTML = "<div><p> </p><h1>"+mainDisplay+"</h1></div>";
//        displayPane.setText(displayHTML);
//        updateUI();
    }


    private void reset() {
        firstNum = 0;
        inputtedOperator = Operator._Default;
        secondNum = 0;
        computedResult = 0;
        state = State.READY_FIRSTNUM;
        decimalPointFlag = false;

        mainDisplay = "0";
        upperSubDisplay = "";
    }


}
