import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class GameFrame extends JFrame {
    ArrayList<Question> questions = new ArrayList<Question>();
    private Random  rnd = new Random();
    private boolean errorCanBeen = false;
    int Level =0;
    int helpButtons = 4;
    String nonSumm;
    int nonSummIndex;
    Question currentQuestion;

    private JPanel panel1;
    private JPanel controlPanel1;
    private JPanel questionAndAnswersPanel;
    private JList lstLevel;
    private JButton answersButton1;
    private JButton answersButton3;
    private JButton answersButton2;
    private JButton answersButton4;
    private JLabel questionLabel1;
    private JButton button1;
    private JButton помощьЗалаButton;
    private JButton звонокДругуButton;
    private JButton правоНаОшибкуButton;
    private JButton заменаВопросаButton;
    private JButton забратьДеньгиButton;
    private JLabel nonSummLabel;


    private ListSelectionListener nonSummListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            nonSumm = lstLevel.getSelectedValue().toString();
            nonSummIndex = lstLevel.getSelectedIndex();
            nonSummLabel.setText(nonSumm);
            lstLevel.removeListSelectionListener(nonSummListener);
            //ReadFile();
            startGame();
        }
    };

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    private  void readFileDB() {
        try{
            Class.forName("org.sqlite.JDBC");
            //Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Vasilev\\IdeaProjects\\sqlite\\question.db");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\src\\question.db");

            Statement statmt = conn.createStatement();
            String query = "select quest, one, two, three, four, right, level from quest";
            //String query = "select count(*) from q";
            ResultSet resSet = statmt.executeQuery(query);

            String[] s = new String[8];
            while (resSet.next()) {
                for (int i=1; i<8; i++) {
                     s[i-1] = new String(resSet.getString(i).getBytes(StandardCharsets.UTF_8));
                }
                questions.add(new Question(s));
            }

            resSet.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
    private void ReadFile()
    {
        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\Vasilev\\IdeaProjects\\WhoWantsToBeAMillionaire\\out\\production\\WhoWantsToBeAMillionaire\\question.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] s = strLine.split("\t");
                questions.add(new Question(s));
            }
        } catch (IOException e) {
            System.out.println("Ошибка");
        }
    }

    private void ShowQuestion(Question q)
    {
        questionLabel1.setText(q.Text);
        answersButton1.setText(q.Answers[0]);
        answersButton3.setText(q.Answers[1]);
        answersButton2.setText(q.Answers[2]);
        answersButton4.setText(q.Answers[3]);
    }
    private Question GetQuestion(int level) {
        List<Question> list =
                questions.stream().filter(q -> q.Level == level).collect(Collectors.toList());
        return list.get(rnd.nextInt(list.size()));
    }
    private void NextStep()
    {
        JButton[] btns = new JButton[]{answersButton1, answersButton3,
                answersButton2, answersButton4};

        for(JButton btn: btns)
            btn.setEnabled(true);

        Level++;
        currentQuestion = GetQuestion(Level);
        ShowQuestion(currentQuestion);
        lstLevel.setSelectedIndex(lstLevel.getModel().getSize()-Level);
    }

    private void startGame()
    {
        Level = 0;
        JButton[] btns = new JButton[]{ button1,помощьЗалаButton, звонокДругуButton,
                правоНаОшибкуButton,заменаВопросаButton, забратьДеньгиButton };

        for(JButton btn: btns)
            btn.setEnabled(true);

        helpButtons =4;
        NextStep();
    }
    private void pauseGame() {
        questionLabel1.setText("Выберите несгораемую сумму");
        nonSummLabel.setText("0");
        lstLevel.setSelectedIndex(15);
        lstLevel.addListSelectionListener(nonSummListener);
        JButton[] btns = new JButton[]{ button1,помощьЗалаButton, звонокДругуButton,
                правоНаОшибкуButton,заменаВопросаButton, забратьДеньгиButton,answersButton1, answersButton3,
                answersButton2, answersButton4 };

        for(JButton btn: btns)
            btn.setEnabled(false);
    }



    public GameFrame() {


        lstLevel.setSelectedIndex(15);
        readFileDB();
        //ReadFile();
        //startGame();

        answersButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntAnswerPerformed(e);
            }
        });
        answersButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntAnswerPerformed(e);
            }
        });
        answersButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntAnswerPerformed(e);
            }
        });
        answersButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bntAnswerPerformed(e);
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JButton[] btns = new JButton[]{answersButton1, answersButton3,
                        answersButton2, answersButton4};

                int count = 0;
                while (count<2)
                {
                    int n = rnd.nextInt(4);
                    String ac = btns[n].getActionCommand();

                    if (!ac.equals(currentQuestion.RightAnswer) && btns[n].isEnabled())
                    {
                        btns[n].setEnabled(false);
                        count++;
                    }
                }
                button1.setEnabled(false);
                checkHelpButtons();
            }
        });
        помощьЗалаButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHelpPeople(e);
                помощьЗалаButton.setEnabled(false);
                checkHelpButtons();
            }
        });
        звонокДругуButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rightAnswer = currentQuestion.Answers[Integer.parseInt(currentQuestion.RightAnswer)-1]; //ответ для правильного варианта

                //JDialog frame = new JDialog(GameFrame.this,"CallFriend");
                //frame.setContentPane(new CallFriend(rightAnswer).contentPane);
                //frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                //frame.setVisible(true);
                //frame.setSize(250,300);
                //frame.setLocationRelativeTo(null);
                //new JDialog(GameFrame.this,"CallFriend");
                new CallFriend(rightAnswer);
                звонокДругуButton.setEnabled(false);
                checkHelpButtons();
            }
        });
        правоНаОшибкуButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GameFrame.this, "Теперь вы имеете право на ошибку!");
                errorCanBeen = true;
                правоНаОшибкуButton.setEnabled(false);
                checkHelpButtons();
            }
        });
        заменаВопросаButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestion = GetQuestion(Level);
                ShowQuestion(currentQuestion);

                JButton[] btns = new JButton[]{answersButton1, answersButton3,
                        answersButton2, answersButton4};

                for(JButton btn: btns)
                    btn.setEnabled(true);
                заменаВопросаButton.setEnabled(false);
                checkHelpButtons();
            }
        });
        забратьДеньгиButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object index = lstLevel.getSelectedValue();
                JOptionPane.showMessageDialog(GameFrame.this, "Вы молодцы! Вы выйграли " + index.toString());
                pauseGame();
            }
        });
        questionLabel1.setText("Выберите несгораемую сумму");
        lstLevel.addListSelectionListener(nonSummListener);
    }


    private void btnHelpPeople(java.awt.event.ActionEvent evt) {
        String rightQuest = currentQuestion.RightAnswer;
        int rightAnswer = 40+ rnd.nextInt(10) *3;
        int nonRight = ((100 - rightAnswer) /3) -1;
        String answer = "";
        for (int i=1; i<5; i++) {
            if (Integer.parseInt(rightQuest) == i) {
                answer += rightQuest + " - " + rightAnswer + "%\n";
            } else
            {
                answer += i + " - " + nonRight+ "%\n";
                nonRight++;
            }

        }
        JOptionPane.showMessageDialog(this, ""+answer);
    }
    private void bntAnswerPerformed(java.awt.event.ActionEvent evt) {


        if (currentQuestion.RightAnswer.equals(evt.getActionCommand()))
            if (currentQuestion.Level==15)
                JOptionPane.showMessageDialog(this, "Поздравляем вы стали миллионером! Вы забираете с собой 3 млн. рублей!");
            else
                NextStep();
        else
        {
            if (!errorCanBeen) {
                String message="Неверный ответ! ";
                if (16 - currentQuestion.Level <= nonSummIndex) {
                    message += "Ваш выйгрыш составил несгораемую сумму - " + nonSumm;
                } else {
                    message += "Вы проиграли";
                }
                JOptionPane.showMessageDialog(this, message);
                pauseGame();
                //startGame();
            }
            errorCanBeen = false;
        }

    }

    private void checkHelpButtons () {

        if (helpButtons!=1) {
            helpButtons--;
        } else {
            button1.setEnabled(false);
            помощьЗалаButton.setEnabled(false);
            звонокДругуButton.setEnabled(false);
            правоНаОшибкуButton.setEnabled(false);
            заменаВопросаButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameFrame");
        frame.setContentPane(new GameFrame().panel1);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
