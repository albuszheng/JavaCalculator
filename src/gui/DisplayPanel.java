package gui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

/**
 * Created by keyangzheng on 9/1/16.
 *
 * Todo display layout & html + css
 */
public class DisplayPanel extends JPanel {
    protected String upperSubDisplay = "150 +";
    protected String mainDisplay = "200";
    protected JEditorPane displayPane = new JEditorPane();
    protected String cssRule = "div {margin: 2px;border: thin solid #333;Ω}p, h1 {text-align: right;margin: 0;padding: 5px 10px 0 10px;font: sans-serif;}p {font-size: 1.05em;line-height:.8;font-weight:100;}h1 {font-size: 2.em;line-height: 1.0;}";
//    protected String sampleHTML = "<p>1504<span>&nbsp;&nbsp;+&nbsp;</span></p>	<h1>100</h1>";
    protected final String defaultHTML = "<div><p> </p><h1>0</h1></div>";

    public DisplayPanel(){
        super();

        this.setPreferredSize(new Dimension(500, 100));

        displayPane.setPreferredSize(new Dimension(500, 100));
        displayPane.setContentType("text/html");

        displayPane.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        displayPane.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(cssRule);

        Document doc = kit.createDefaultDocument();
        displayPane.setDocument(doc);
        displayPane.setText(defaultHTML);

        this.add(displayPane);

    }

    public void updateText(String mainDisplay, String upperSubDisplay){


        updateUI();
    }

    public void updateDisplay(String newInput){

    }

}
