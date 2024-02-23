/**
 * The MessageControl class represents a graphical user interface control for displaying messages.
 * It extends ControlBase and provides functionality to display message details such as timestamp, sender, and message content.
 */
package Views.Controls;

import Primitives.MessageRecord;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * The MessageControl class provides a graphical user interface control for displaying messages.
 * It extends ControlBase and includes features to display message details such as timestamp, sender, and message content.
 */
public class MessageControl extends ControlBase {

    /**
     * Label to display the timestamp of the message.
     */
    private JLabel lbl_timestamp;

    /**
     * Label to display the message content.
     */
    private JLabel lbl_message;

    /**
     * Label to display the sender of the message.
     */
    private JLabel lbl_sender;

    /**
     * Base panel containing all message components.
     */
    private JPanel basePanel;

    /**
     * Constructs a MessageControl object with the provided MessageRecord.
     *
     * @param message The MessageRecord containing message details.
     */
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

    /**
     * Overrides the getControl method to return the base panel containing message components.
     *
     * @return The JPanel containing message components.
     */
    @Override
    public JPanel getControl() {
        return this.basePanel;
    }

    /**
     * Applies line breaks to the message content if it exceeds a certain length.
     *
     * @param s The message content to apply line breaks to.
     * @return The message content with line breaks applied.
     */
    private String lineBreak(String s) {
        if (s.length() < 90)
            return s;
        String word = "";
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ')
                word += s.charAt(i);
            else {
                words.add(word);
                word = "";
            }
        }
        StringBuilder txt = new StringBuilder();
        int numLinebreaks = 0;
        for (String w : words) {
            if (txt.length() + w.length() < 90 + numLinebreaks * 90) {
                if (w.equals(words.get(words.size() - 1)))
                    txt.append(w);
                else
                    txt.append(w).append(' ');
            } else {
                numLinebreaks++;
                txt.append("<br/>");
                if (w.equals(words.get(words.size() - 1)))
                    txt.append(w);
                else
                    txt.append(w).append(' ');
            }
        }
        return "<html><p>" + txt + "</p></html>";
    }
}
