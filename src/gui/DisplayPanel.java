package gui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import calculating.*;

import static calculating.State.*;

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
    protected String cssRule = "div {margin: 2px;border: thin solid #333;}p, h1 {text-align: right;margin: 0;padding: 5px 10px 0 10px;font: sans-serif;}p {font-size: 1.05em;line-height:.8;font-weight:100;}h1 {font-size: 2.em;line-height: 1.0;}";
    protected final String defaultHTML = "<div><p> </p><h1>0</h1></div>";
    protected String displayHTML = "";
    protected final int MAX_DISPLAY_LENGTH = 12;

    /**
     * state transfer indicator
     */
    private boolean decimalPointFlag = false;
    private State state = READY_FIRSTNUM;
    private double firstNum = 0;
    private double secondNum = 0;
    private Operator inputtedOperator = Operator._Default;
    private double computedResult = 0;
    private NumberFormat doubleFormat = null;


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
        state = READY_FIRSTNUM;

        doubleFormat = NumberFormat.getNumberInstance();
        doubleFormat.setMaximumFractionDigits(6);
        doubleFormat.setMaximumIntegerDigits(10);


    }

    public void updateDisplay(String newInput){
        if (Pattern.matches("[0-9]", newInput)) {
            //input as a digit
            if (decimalPointFlag) {
                switch (state) {
                    case IN_FIRSTNUM: {
                        String temp = (mainDisplay + newInput);
                        mainDisplay = temp.substring(0,Math.min(temp.length(), MAX_DISPLAY_LENGTH));
                        firstNum = Double.parseDouble(temp);
                        break;
                    }
                    case IN_SECONDNUM: {
                        String temp = (mainDisplay + newInput);
                        mainDisplay = temp.substring(0,Math.min(temp.length(), MAX_DISPLAY_LENGTH));
                        secondNum = Double.parseDouble(temp);
                        break;
                    }
                }
                displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                displayPane.setText(displayHTML);
                updateUI();
            } else {
                switch (state) {
                    case READY_FIRSTNUM: {
                        firstNum = firstNum * 10 + Double.parseDouble(newInput);
                        mainDisplay = doubleFormat.format(firstNum);
                        displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                        displayPane.setText(displayHTML);
                        state = IN_FIRSTNUM;
                        updateUI();
                        break;
                    }
                    case READY_SECONDNUM: {
                        secondNum = secondNum * 10 + Double.parseDouble(newInput);
                        mainDisplay = doubleFormat.format(secondNum);
                        displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                        displayPane.setText(displayHTML);
                        state = IN_SECONDNUM;
                        updateUI();
                        break;
                    }
                    case IN_FIRSTNUM: {
                        firstNum = firstNum * 10 + Double.parseDouble(newInput);
                        mainDisplay = doubleFormat.format(firstNum);
                        displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                    case IN_SECONDNUM: {
                        secondNum = secondNum * 10 + Double.parseDouble(newInput);
                        mainDisplay = doubleFormat.format(secondNum);
                        displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                        displayPane.setText(displayHTML);
                        updateUI();
                        break;
                    }
                }
            }
        } else if (newInput.equals(".")) {
            //input as decimal point
            if (decimalPointFlag) {
                //error: double decimal point
                switch (state) {
                    case IN_FIRSTNUM: {
                        reverseState();
                        firstNum = 0;
                        break;
                    }
                    case IN_SECONDNUM: {
                        reverseState();
                        secondNum = 0;
                        break;
                    }
                }
                mainDisplay = "Don/'t put the 2nd decimal point into a num! Input the num again";
                displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                displayPane.setText(displayHTML);
                mainDisplay = "0";
                decimalPointFlag = false;
                updateUI();
            } else {
                //normal procedure
                decimalPointFlag = true;
                if ((state == READY_FIRSTNUM) || (state == READY_SECONDNUM)) {
                    nextState();
                }
                mainDisplay += ".";
                displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                displayPane.setText(displayHTML);
                updateUI();
            }
        } else if (newInput.equals("=")) {
            //input as equal
            switch (state) {
                case IN_SECONDNUM: {
                    //normal procedure
                    computedResult = Calculator.operation(firstNum, secondNum, inputtedOperator.toString().charAt(0));    
                    upperSubDisplay = upperSubDisplay + " " + doubleFormat.format(secondNum) + " =";
                    mainDisplay = doubleFormat.format(computedResult);
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    reset();
                    break;
                }
                case IN_FIRSTNUM: {
                    //error: Equal without OP
                    mainDisplay = "We don\'t Have a Operator!";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
                case READY_FIRSTNUM: {
                    //error: Leading with Equal
                    mainDisplay = "Don\'t Start with = !";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
                case READY_SECONDNUM: {
                    //error: OP -> Equal
                    mainDisplay = "Where is the 2nd Num?";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
            }
        } else {
            // input as operator
            switch (state) {
                case IN_FIRSTNUM: {
                    // normal procedure
                    switch (newInput.charAt(0)) {
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
                    mainDisplay = "&nbsp;";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h1>" + mainDisplay + "</h1></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    state = READY_SECONDNUM;
                    decimalPointFlag = false;
                    break;
                }
                case READY_FIRSTNUM: {
                    //error: Leading with OP
                    mainDisplay = "Input a num first!";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
                case READY_SECONDNUM: {
                    //error: OP -> OP
                    mainDisplay = "Num not Operator Please!";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
                case IN_SECONDNUM: {
                    //error: Multiple OP
                    mainDisplay = "Multiple Op not Supported!";
                    displayHTML = "<div><p>" + upperSubDisplay + "</p><h3><span style=\"color:red;\">" + mainDisplay + "</span></h3></div>";
                    displayPane.setText(displayHTML);
                    updateUI();
                    break;
                }
            }
        }//end of operator input

    }


    private void reset() {
        firstNum = 0;
        inputtedOperator = Operator._Default;
        secondNum = 0;
        computedResult = 0;
        state = READY_FIRSTNUM;
        decimalPointFlag = false;

        mainDisplay = "0";
        upperSubDisplay = "";
    }


    private void nextState() {
        switch (state) {
            case READY_FIRSTNUM:  state = IN_FIRSTNUM;break;
            case IN_FIRSTNUM:  state = READY_SECONDNUM;break;
            case READY_SECONDNUM: state = IN_FIRSTNUM;break;
            case IN_SECONDNUM:     state = READY_FIRSTNUM;break;
        }
    }

    private void reverseState() {
        switch (state) {
            case READY_FIRSTNUM:  state = IN_SECONDNUM;break;
            case IN_FIRSTNUM:  state = READY_FIRSTNUM;break;
            case READY_SECONDNUM: state = IN_FIRSTNUM;break;
            case IN_SECONDNUM:     state = READY_SECONDNUM;break;
        }
    }
}
