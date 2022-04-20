import javax.swing.*;
import java.awt.event.*;

public class CallFriend extends JDialog {
    public JPanel contentPane;
    private JButton андрейButton;
    private JButton ваняButton;
    private JButton романButton;
    private JButton николайButton;
    private JButton евгенияButton;

    public CallFriend(String RightAnswer) {
        init();



        андрейButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "Андрей: Я думаю, что это ответ \"" +RightAnswer + "\"");
                dispose();

            }
        });
        ваняButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "Ваня: Хмм, мне кажется, что правельный ответ \"" +RightAnswer + "\"");
                dispose();
            }
        });
        романButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "Роман: Вроде бы это ответ \"" +RightAnswer + "\"");
                dispose();
            }
        });
        николайButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "Николай: Я скажу наугад. Это - \"" +RightAnswer + "\"");
                dispose();
            }
        });
        евгенияButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(contentPane, "Евгения: Думаю, что ответ на вопрос такой -\"" +RightAnswer + "\"");
                closeFrame(e);
            }
        });

    }
    public void init() {
        this.setContentPane(contentPane);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(250,300);
        this.setLocationRelativeTo(null);
    }
    public void closeFrame(ActionEvent e) {
        this.dispose();
    }


}
