package Views.Controls;

import Primitives.MessageRecord;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageControl extends ControlBase {
    private JLabel lbl_timestamp;
    private JLabel lbl_message;
    private JLabel lbl_sender;
    private JPanel basePanel;

    public MessageControl(MessageRecord message) {
        super();
        Font font = this.lbl_timestamp.getFont();
        this.lbl_timestamp.setFont(new Font(font.getFontName(), Font.PLAIN, 10));
        this.lbl_timestamp.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        this.lbl_sender.setFont(new Font(font.getFontName(), Font.PLAIN, 9));
        this.lbl_sender.setText(message.user());
        this.lbl_message.setText(lineBreak(message.message()));
        this.lbl_timestamp.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.timestamp()));
    }
    @Override
    public JPanel getControl() {
        return this.basePanel;
    }
    private String lineBreak(String s) {
        if(s.length() < 90)
            return s;
        String word = "";
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != ' ')
                word += s.charAt(i);
            else {
                words.add(word);
                word = "";
            }
        }
        StringBuilder txt = new StringBuilder();
        int numLinebreaks = 0;
        for (String w : words) {
            if(txt.length() + w.length() < 90 + numLinebreaks * 90) {
                if(w.equals(words.get(words.size() - 1)))
                    txt.append(w);
                else
                    txt.append(w).append(' ');
            }
            else {
                numLinebreaks++;
                txt.append("<br/>");
                if(w.equals(words.get(words.size() - 1)))
                    txt.append(w);
                else
                    txt.append(w).append(' ');
            }
        }
        return "<html><p>" + txt + "</p></html>";
    }
}
