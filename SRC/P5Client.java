import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Project 5 Server
 *
 * @author Chris Qiu, Abdul Saleh, Daniel Sowers, and Dayoon Suh
 * @version 11/7/2021
 */

public class P5Client extends JComponent implements Runnable {

    JButton login; //login button
    JButton register; //register button
    JButton backToMain;
    JButton backToStuOrTeacherLogin;
    JButton backToStuOrTeacherRegister;
    JButton loginAsTeacher; //log in as teacher
    JButton loginAsStudent; //log in as student
    JButton registerAsTeacher; //register as student
    JButton registerAsStudent; //register as student
    JButton createDiscussionTeacher; //create discussion button
    JButton editDiscussionTeacher; //edit discussion button
    JButton deleteDiscussionTeacher; //delete discussion button
    JButton replyTeacher; //reply as teacher button
    JButton addGradeTeacher; //add grade button
    JButton editAccountTeacher; //edit account button
    JButton deleteAccountTeacher; //delete account button
    JButton logoutTeacher; //log out button
    JButton commentStudent; // Student comment button
    JButton replyStudent; // Student reply button
    JButton editAccountStudent; // Student edit account button
    JButton deleteAccountStudent; // Student delete account button
    JButton logoutStudent; // Student logout button
    JButton studentLoginEnter;
    JButton teacherLoginEnter;
    JButton studentRegisterEnter;
    JButton teacherRegisterEnter;
    JTextArea discussionTextArea;
    P5Client studentScreen;
    P5Client teacherScreen;
    JLabel dta;
    JTextField discussionTitle;
    JTextField text;
    JTextField index;
    JTextField editedTitle;
    JTextField editedForum;
    JTextField whichDisDelete;
    JTextField whichDis;
    JTextField whichComment;
    JTextField replyContent;
    JTextField whichStu;
    JTextField stuWhichDis;
    JTextField score;
    JTextField studentUsernameL;
    JTextField studentPasswordL;
    JTextField teacherUsernameL;
    JTextField teacherPasswordL;
    JTextField studentUsernameR;
    JTextField studentPasswordR;
    JTextField teacherUsernameR;
    JTextField teacherPasswordR;
    JTextField commentTextField;
    JTextField replyTextField;
    static Socket socket;
    static BufferedReader reader;
    static PrintWriter writer;
    String StuInfoLogin;
    String TeachInfoLogin;
    String StuInfoReg;
    String TeachInfoReg;
    JButton createDisConfirm;
    String title;
    String textBody;
    JButton confirmGrade;
    String stuName;
    String whichDiscussion;
    String stuScore;
    JButton editDisConfirm;
    String num;
    String newT;
    String newC;
    JButton deleteDisConfirm;
    String deleteIndex;
    JButton replyConfirm;
    String discussionIndex;
    String commentIndex;
    String replyBody;
    JButton uploadCommentButton;
    JButton uploadReplyButton;
    JTextField forumNumberTextField;
    String uploadComment;
    String uploadReply;
    String textDiscussion = "";


    public static void main(String[] args) throws IOException {
        String host = "localhost";
        String port = "2021";
        socket = new Socket(host, Integer.parseInt(port));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        if (socket.isConnected())
            JOptionPane.showMessageDialog(null, "Connection established",
                    "Discussion Board", JOptionPane.INFORMATION_MESSAGE);
        else
            return;

        SwingUtilities.invokeLater(new P5Client());
    }

    //ActionListeners
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                String log = "login";
                try {
                    send(log);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                logInStudentOrTeacher();
            }
            if (e.getSource() == register) {
                String reg = "register";
                try {
                    send(reg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registerStudentOrTeacher();
            }
            if (e.getSource() == backToMain) {
                loginRegisterScreen();
            }
            if (e.getSource() == backToStuOrTeacherLogin) {
                logInStudentOrTeacher();
            }
            if (e.getSource() == backToStuOrTeacherRegister) {
                registerStudentOrTeacher();
            }
            if (e.getSource() == loginAsStudent) {
                String logStu = "ls";
                try {
                    send(logStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                loginStudent();
            }
            if (e.getSource() == loginAsTeacher) {
                String logTeach = "lt";
                try {
                    send(logTeach);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                loginTeacher();
            }
            if (e.getSource() == registerAsStudent) {
                String registerStu = "rs";
                try {
                    send(registerStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registerStudent();
            }
            if (e.getSource() == registerAsTeacher) {
                String registerTeach = "rt";
                try {
                    send(registerTeach);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registerTeacher();
            }
            if (e.getSource() == studentLoginEnter) {
                StuInfoLogin = studentUsernameL.getText() + " " + studentPasswordL.getText();
                System.out.println(StuInfoLogin);
                try {
                    send(StuInfoLogin);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String success = "";
                try {
                    success = reader.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (success.equals("success")) {
                    studentScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "The username or password might be " +
                            "incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    loginStudent();
                }
            }
            if (e.getSource() == studentRegisterEnter) {
                StuInfoReg = studentUsernameR.getText() + " " + studentPasswordR.getText();
                try {
                    send(StuInfoReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Account Created",
                        "", JOptionPane.INFORMATION_MESSAGE);
                loginRegisterScreen();
            }
            if (e.getSource() == teacherLoginEnter) {
                TeachInfoLogin = teacherUsernameL.getText() + " " + teacherPasswordL.getText();
                try {
                    send(TeachInfoLogin);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String success = "";
                try {
                    success = reader.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (success.equals("success")) {
                    teacherScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "The username or password might be " +
                            "incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    loginTeacher();
                }
            }
            if (e.getSource() == teacherRegisterEnter) {
                TeachInfoReg = teacherUsernameR.getText() + " " + teacherPasswordR.getText();
                try {
                    send(TeachInfoReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Account Created",
                        "", JOptionPane.INFORMATION_MESSAGE);
                loginRegisterScreen();
            }
            if (e.getSource() == createDiscussionTeacher) {
                String createDis = "createDiscussionTeacher";
                try {
                    send(createDis);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                createDiscussion();
            }
            if (e.getSource() == editDiscussionTeacher) {
                String editDis = "editDiscussionTeacher";
                try {
                    send(editDis);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                editDiscussion();
            }
            if (e.getSource() == deleteDiscussionTeacher) {
                String deleteDis = "deleteDiscussionTeacher";
                try {
                    send(deleteDis);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                deleteDiscussion();
            }
            if (e.getSource() == replyTeacher) {
                String replyTeach = "replyTeacher";
                try {
                    send(replyTeach);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                createReply();
            }
            if (e.getSource() == addGradeTeacher) {
                String addGrade = "addGradeTeacher";
                try {
                    send(addGrade);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                assignGrade();
            }
            if (e.getSource() == editAccountTeacher) {
                String editAccTeach = "editAccountTeacher";
                try {
                    send(editAccTeach);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                editAccountTeacher();
            }
            if (e.getSource() == deleteAccountTeacher) {
                String deleteAccTeach = "deleteAccountTeacher";
                try {
                    send(deleteAccTeach);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                deleteAccountTeacher();
            }
            if (e.getSource() == logoutTeacher) {
                loginRegisterScreen();
            }

            if (e.getSource() == commentStudent) {
                String commentStu = "commentStudent";
                try {
                    send(commentStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentCommentScreen();
            }

            if (e.getSource() == replyStudent) {
                String replyStu = "replyStudent";
                try {
                    send(replyStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentReplyScreen();
            }

            if (e.getSource() == editAccountStudent) {
                String editAccStu = "editAccountStudent";
                try {
                    send(editAccStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentEditAccountScreen();
            }
            if (e.getSource() == deleteAccountStudent) {
                String deleteAccStu = "deleteAccountStudent";
                try {
                    send(deleteAccStu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentDeleteAccountScreen();
            }
            if (e.getSource() == logoutStudent) {
                loginRegisterScreen();
            }
            if (e.getSource() == createDisConfirm) {
                title = discussionTitle.getText();
                try {
                    send(title);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                textBody = text.getText();
                try {
                    send(textBody);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    textDiscussion += reader.readLine() + "\n";
                    System.out.println(textDiscussion);
                } catch (IOException xe) {
                    xe.printStackTrace();
                }
                teacherScreen();
            }
            if (e.getSource() == confirmGrade) {
                stuName = whichStu.getText();
                try {
                    send(stuName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                whichDiscussion = stuWhichDis.getText();
                try {
                    send(whichDiscussion);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                stuScore = score.getText();
                try {
                    send(stuScore);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                teacherScreen();
            }
            if (e.getSource() == editDisConfirm) {
                num = index.getText();
                String contentReceived = "";
                try {
                    send(num);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                newT = editedTitle.getText();
                try {
                    send(newT);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                newC = editedForum.getText();
                try {
                    send(newC);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    while ((contentReceived = reader.readLine()) != null) {
                        contentReceived += reader.readLine();
                    }
                    System.out.println(contentReceived);
                    teacherScreen();
                } catch (IOException es) {
                    es.printStackTrace();
                }
            }
            if (e.getSource() == deleteDisConfirm) {
                deleteIndex = whichDisDelete.getText();
                try {
                    send(deleteIndex);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                teacherScreen();
            }
            if (e.getSource() == replyConfirm) {
                discussionIndex = whichDis.getText();
                try {
                    send(discussionIndex);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                commentIndex = whichComment.getText();
                try {
                    send(commentIndex);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                replyBody = replyContent.getText();
                try {
                    send(replyBody);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                teacherScreen();
            }
            if (e.getSource() == uploadCommentButton) {
                try {
                    send(forumNumberTextField.getText());
                    send(commentTextField.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentScreen();
            }
            if (e.getSource() == uploadReplyButton) {
                try {
                    send(forumNumberTextField.getText());
                    send(whichComment.getText());
                    send(replyTextField.getText());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentScreen();
            }

        }
    };

    //Login or Register Window
    public void loginRegisterScreen() {

        JFrame frame = new JFrame("Discussion Board");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == login) {
                    frame.setVisible(false);
                }
                if (e.getSource() == register) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(100, 100);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        BoxLayout bl = (new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setLayout(bl);
        panel.setBorder(new EmptyBorder(new Insets(90, 100, 90, 100)));

        login = new JButton("Log In");
        login.addActionListener(actionListener);
        login.addActionListener(aListener);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);


        register = new JButton("Register");
        register.addActionListener(actionListener);
        register.addActionListener(aListener);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(login);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(register);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    //Student or Teacher Window
    public void logInStudentOrTeacher() {
        JFrame frame = new JFrame("Discussion Board");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loginAsStudent) {
                    frame.setVisible(false);
                }
                if (e.getSource() == loginAsTeacher) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToMain) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(100, 100);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        BoxLayout bl = (new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setLayout(bl);
        panel.setBorder(new EmptyBorder(new Insets(90, 100, 90, 100)));

        loginAsStudent = new JButton("Student");
        loginAsStudent.addActionListener(actionListener);
        loginAsStudent.addActionListener(aListener);
        loginAsStudent.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginAsTeacher = new JButton("Teacher");
        loginAsTeacher.addActionListener(actionListener);
        loginAsTeacher.addActionListener(aListener);
        loginAsTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        backToMain = new JButton("Back");
        backToMain.addActionListener(actionListener);
        backToMain.addActionListener(aListener);
        backToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(loginAsStudent);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(loginAsTeacher);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(backToMain);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    //registering teacher or student window
    public void registerStudentOrTeacher() {
        JFrame frame = new JFrame("Discussion Board");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == registerAsStudent) {
                    frame.setVisible(false);
                }
                if (e.getSource() == registerAsTeacher) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToMain) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(100, 100);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        BoxLayout bl = (new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setLayout(bl);
        panel.setBorder(new EmptyBorder(new Insets(90, 100, 90, 100)));

        registerAsStudent = new JButton("Student");
        registerAsStudent.addActionListener(actionListener);
        registerAsStudent.addActionListener(aListener);
        registerAsStudent.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerAsTeacher = new JButton("Teacher");
        registerAsTeacher.addActionListener(actionListener);
        registerAsTeacher.addActionListener(aListener);
        registerAsTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        backToMain = new JButton("Back");
        backToMain.addActionListener(actionListener);
        backToMain.addActionListener(aListener);
        backToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(registerAsStudent);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(registerAsTeacher);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(backToMain);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    //student logging in screen
    public void loginStudent() {
        JFrame frame = new JFrame("Student login");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == studentLoginEnter) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToStuOrTeacherLogin) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);
        JLabel usernameLabel = new JLabel("Enter username: ");
        studentUsernameL = new JTextField("", 10);
        panel.add(usernameLabel);
        panel.add(studentUsernameL);

        JLabel passwordLabel = new JLabel("Enter password: ");
        studentPasswordL = new JTextField("", 10);
        panel.add(passwordLabel);
        panel.add(studentPasswordL);

        JPanel enterBackPanel = new JPanel();
        content.add(enterBackPanel, BorderLayout.SOUTH);
        studentLoginEnter = new JButton("Enter");
        studentLoginEnter.addActionListener(actionListener);
        studentLoginEnter.addActionListener(aListener);
        enterBackPanel.add(studentLoginEnter);

        backToStuOrTeacherLogin = new JButton("Back");
        backToStuOrTeacherLogin.addActionListener(actionListener);
        backToStuOrTeacherLogin.addActionListener(aListener);
        enterBackPanel.add(backToStuOrTeacherLogin);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //logging in teacher
    public void loginTeacher() {
        JFrame frame = new JFrame("Teacher login");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == teacherLoginEnter) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToStuOrTeacherLogin) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);
        JLabel usernameLabel = new JLabel("Enter username: ");
        teacherUsernameL = new JTextField("", 10);
        panel.add(usernameLabel);
        panel.add(teacherUsernameL);

        JLabel passwordLabel = new JLabel("Enter password: ");
        teacherPasswordL = new JTextField("", 10);
        panel.add(passwordLabel);
        panel.add(teacherPasswordL);

        JPanel enterBackPanel = new JPanel();
        content.add(enterBackPanel, BorderLayout.SOUTH);
        teacherLoginEnter = new JButton("Enter");
        teacherLoginEnter.addActionListener(actionListener);
        teacherLoginEnter.addActionListener(aListener);
        enterBackPanel.add(teacherLoginEnter);

        backToStuOrTeacherLogin = new JButton("Back");
        backToStuOrTeacherLogin.addActionListener(actionListener);
        backToStuOrTeacherLogin.addActionListener(aListener);
        enterBackPanel.add(backToStuOrTeacherLogin);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //student registering
    public void registerStudent() {
        JFrame frame = new JFrame("Student register");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == studentRegisterEnter) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToStuOrTeacherRegister) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);
        JLabel usernameLabel = new JLabel("Enter username: ");
        studentUsernameR = new JTextField("", 10);
        panel.add(usernameLabel);
        panel.add(studentUsernameR);

        JLabel passwordLabel = new JLabel("Enter password: ");
        studentPasswordR = new JTextField("", 10);
        panel.add(passwordLabel);
        panel.add(studentPasswordR);

        JPanel enterBackPanel = new JPanel();
        content.add(enterBackPanel, BorderLayout.SOUTH);
        studentRegisterEnter = new JButton("Enter");
        studentRegisterEnter.addActionListener(actionListener);
        studentRegisterEnter.addActionListener(aListener);
        enterBackPanel.add(studentRegisterEnter);

        backToStuOrTeacherRegister = new JButton("Back");
        backToStuOrTeacherRegister.addActionListener(actionListener);
        backToStuOrTeacherRegister.addActionListener(aListener);
        enterBackPanel.add(backToStuOrTeacherRegister);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //teacher registering
    public void registerTeacher() {

        JFrame frame = new JFrame("Teacher register");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == teacherRegisterEnter) {
                    frame.setVisible(false);
                }
                if (e.getSource() == backToStuOrTeacherRegister) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);
        JLabel usernameLabel = new JLabel("Enter username: ");
        teacherUsernameR = new JTextField("", 10);
        panel.add(usernameLabel);
        panel.add(teacherUsernameR);

        JLabel passwordLabel = new JLabel("Enter password: ");
        teacherPasswordR = new JTextField("", 10);
        panel.add(passwordLabel);
        panel.add(teacherPasswordR);

        JPanel enterBackPanel = new JPanel();
        content.add(enterBackPanel, BorderLayout.SOUTH);
        teacherRegisterEnter = new JButton("Enter");
        teacherRegisterEnter.addActionListener(actionListener);
        teacherRegisterEnter.addActionListener(aListener);
        enterBackPanel.add(teacherRegisterEnter);

        backToStuOrTeacherRegister = new JButton("Back");
        backToStuOrTeacherRegister.addActionListener(actionListener);
        backToStuOrTeacherRegister.addActionListener(aListener);
        enterBackPanel.add(backToStuOrTeacherRegister);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public String printForum() {
        String ret = "";
        int index = 1;
       /* for (DiscussionForum df : discussionForum) {
            ret += "\n*******************************\n" + String.valueOf(index) + ". " + df.toString() + "\n";
            index++;
        }

        */

        return ret;
    }

    //teacher abilities
    public void teacherScreen() {


        JFrame teacherFrame = new JFrame("Teacher");
        teacherFrame.revalidate();
        teacherFrame.repaint();

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == logoutTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == createDiscussionTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == editDiscussionTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == deleteDiscussionTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == replyTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == editAccountTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == deleteAccountTeacher) {
                    teacherFrame.setVisible(false);
                }
                if (e.getSource() == addGradeTeacher) {
                    teacherFrame.setVisible(false);
                }
            }
        };

        Container teacherContent = teacherFrame.getContentPane();
        teacherContent.setLayout((new BorderLayout()));
        teacherScreen = new P5Client();
        teacherContent.add(teacherScreen, BorderLayout.CENTER);

        //String discussion = printForum();
        dta = new JLabel(textDiscussion);
        teacherContent.add(dta);



        teacherFrame.setSize(1000, 400);
        teacherFrame.setLocationRelativeTo(null);
        teacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teacherFrame.setVisible(true);


        createDiscussionTeacher = new JButton("Create Discussion");
        createDiscussionTeacher.addActionListener(actionListener);
        createDiscussionTeacher.addActionListener(aListener);
        createDiscussionTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        editDiscussionTeacher = new JButton("Edit Discussion");
        editDiscussionTeacher.addActionListener(actionListener);
        editDiscussionTeacher.addActionListener(aListener);
        editDiscussionTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteDiscussionTeacher = new JButton("Delete Discussion");
        deleteDiscussionTeacher.addActionListener(actionListener);
        deleteDiscussionTeacher.addActionListener(aListener);
        deleteDiscussionTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        replyTeacher = new JButton("Reply");
        replyTeacher.addActionListener(actionListener);
        replyTeacher.addActionListener(aListener);
        replyTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        addGradeTeacher = new JButton("Add Grade");
        addGradeTeacher.addActionListener(actionListener);
        addGradeTeacher.addActionListener(aListener);
        addGradeTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        editAccountTeacher = new JButton("Edit Account");
        editAccountTeacher.addActionListener(actionListener);
        editAccountTeacher.addActionListener(aListener);
        editAccountTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteAccountTeacher = new JButton("Delete Account");
        deleteAccountTeacher.addActionListener(actionListener);
        deleteAccountTeacher.addActionListener(aListener);
        deleteAccountTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutTeacher = new JButton("Log Out");
        logoutTeacher.addActionListener(actionListener);
        logoutTeacher.addActionListener(aListener);
        logoutTeacher.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelTeacher = new JPanel();
        panelTeacher.add(createDiscussionTeacher);
        panelTeacher.add(editDiscussionTeacher);
        panelTeacher.add(deleteDiscussionTeacher);
        panelTeacher.add(replyTeacher);
        panelTeacher.add(addGradeTeacher);
        panelTeacher.add(editAccountTeacher);
        panelTeacher.add(deleteAccountTeacher);
        panelTeacher.add(logoutTeacher);

        JScrollPane scrollBarTeacher = new JScrollPane(dta);
        teacherFrame.add(scrollBarTeacher);


        teacherContent.add(panelTeacher, BorderLayout.SOUTH);
    }


    public void createDiscussion() {
        JFrame frame = new JFrame("Create Discussion");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == createDisConfirm) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);

        JLabel disTitle = new JLabel("Enter Discussion Title: ");
        discussionTitle = new JTextField("", 10);
        panel.add(disTitle);
        panel.add(discussionTitle);

        JLabel body = new JLabel("Enter content: ");
        text = new JTextField("", 10);
        panel.add(body);
        panel.add(text);

        JPanel confirm = new JPanel();
        content.add(confirm, BorderLayout.SOUTH);
        createDisConfirm = new JButton("Confirm");
        createDisConfirm.addActionListener(actionListener);
        createDisConfirm.addActionListener(aListener);
        confirm.add(createDisConfirm);
    }

    public void assignGrade() {
        JFrame frame = new JFrame("Assign Grade");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == confirmGrade) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);

        JLabel stu = new JLabel("Which student do you want to grade?");
        whichStu = new JTextField("", 10);
        panel.add(stu);
        panel.add(whichStu);

        JLabel whichReply = new JLabel("Which reply?");
        stuWhichDis = new JTextField("", 10);
        panel.add(whichReply);
        panel.add(stuWhichDis);

        JLabel scoreTxt = new JLabel("What score?");
        score = new JTextField("", 10);
        panel.add(scoreTxt);
        panel.add(score);

        JPanel confirm = new JPanel();
        content.add(confirm, BorderLayout.SOUTH);
        confirmGrade = new JButton("Confirm");
        confirmGrade.addActionListener(actionListener);
        confirmGrade.addActionListener(aListener);
        confirm.add(confirmGrade);
    }

    public void editDiscussion() {
        JFrame frame = new JFrame("Edit Discussion");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == editDisConfirm) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);

        JLabel whichDis = new JLabel("Which discussion do you want to edit?");
        index = new JTextField("", 10);
        panel.add(whichDis);
        panel.add(index);

        JLabel newTitle = new JLabel("What do you want to change the title to?");
        editedTitle = new JTextField("", 10);
        panel.add(newTitle);
        panel.add(editedTitle);

        JLabel newForum = new JLabel("What do you want to change the content to?");
        editedForum = new JTextField("", 10);
        panel.add(newForum);
        panel.add(editedForum);

        JPanel confirm = new JPanel();
        content.add(confirm, BorderLayout.SOUTH);
        editDisConfirm = new JButton("Confirm");
        editDisConfirm.addActionListener(actionListener);
        editDisConfirm.addActionListener(aListener);
        confirm.add(editDisConfirm);
    }

    public void deleteDiscussion() {
        JFrame frame = new JFrame("Delete Discussion");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteDisConfirm) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);

        JLabel whichDis = new JLabel("Which discussion do you want to delete?");
        whichDisDelete = new JTextField("", 10);
        panel.add(whichDis);
        panel.add(whichDisDelete);

        JPanel confirm = new JPanel();
        content.add(confirm, BorderLayout.SOUTH);
        deleteDisConfirm = new JButton("Confirm");
        deleteDisConfirm.addActionListener(actionListener);
        deleteDisConfirm.addActionListener(aListener);
        confirm.add(deleteDisConfirm);
    }

    public void createReply() {
        JFrame frame = new JFrame("Create Reply");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == replyConfirm) {
                    frame.setVisible(false);
                }
            }
        };

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        content.add(panel, BorderLayout.CENTER);

        JLabel disIndex = new JLabel("Which discussion do you want to respond to?");
        whichDis = new JTextField("", 10);
        panel.add(disIndex);
        panel.add(whichDis);

        JLabel comIndex = new JLabel("Which comment do you want to reply to?");
        whichComment = new JTextField("", 10);
        panel.add(comIndex);
        panel.add(whichComment);

        JLabel reply = new JLabel("Enter your comment: ");
        replyContent = new JTextField("", 10);
        panel.add(reply);
        panel.add(replyContent);

        JPanel confirm = new JPanel();
        content.add(confirm, BorderLayout.SOUTH);
        replyConfirm = new JButton("Confirm");
        replyConfirm.addActionListener(actionListener);
        replyConfirm.addActionListener(aListener);
        confirm.add(replyConfirm);
    }

    public void editAccountTeacher() {
        int edit = JOptionPane.showConfirmDialog(null, "Do you want to edit your account?",
                "Edit Account", JOptionPane.YES_NO_OPTION);
        if (edit == 0) {
            String newID = JOptionPane.showInputDialog(null, "Enter new ID");
            String newPassword = JOptionPane.showInputDialog(null, "Enter new password");
//            Teacher.setUsername(newID);
//            Teacher.setPassword(newID);
            JOptionPane.showMessageDialog(null, "Account edited!",
                    "Edit Account", JOptionPane.PLAIN_MESSAGE);
            // work on progress

        }
    }

    public void deleteAccountTeacher() {
        int delete = JOptionPane.showConfirmDialog(null, "Do you want to delete your account?",
                "Delete Account", JOptionPane.YES_NO_OPTION);
        if (delete == 0) {
            JOptionPane.showMessageDialog(null, "Account Deleted!",
                    "Delete Account", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // Student Main Screen
    public void studentScreen() {
        JFrame studentFrame = new JFrame("Student");

        studentFrame.revalidate();
        studentFrame.repaint();

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == commentStudent) {
                    studentFrame.setVisible(false);
                }
                if (e.getSource() == replyStudent) {
                    studentFrame.setVisible(false);
                }
                if (e.getSource() == logoutStudent) {
                    studentFrame.setVisible(false);
                }
                if (e.getSource() == deleteAccountStudent) {
                    studentFrame.setVisible(false);
                }
            }
        };

        Container studentContent = studentFrame.getContentPane();
        studentContent.setLayout((new BorderLayout()));
        studentScreen = new P5Client();
        studentContent.add(studentScreen, BorderLayout.CENTER);

        dta = new JLabel(textDiscussion);
        studentContent.add(dta);



        studentFrame.setSize(600, 400);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentFrame.setVisible(true);

        commentStudent = new JButton("Comment");
        commentStudent.addActionListener(actionListener);
        commentStudent.addActionListener(aListener);
        replyStudent = new JButton("Reply");
        replyStudent.addActionListener(actionListener);
        replyStudent.addActionListener(aListener);
        editAccountStudent = new JButton("Edit Account");
        editAccountStudent.addActionListener(actionListener);
        deleteAccountStudent = new JButton("Delete Account");
        deleteAccountStudent.addActionListener(actionListener);
        deleteAccountStudent.addActionListener(aListener);
        logoutStudent = new JButton("Log Out");
        logoutStudent.addActionListener(actionListener);
        logoutStudent.addActionListener(aListener);


        JPanel panelStudent = new JPanel();
        panelStudent.add(commentStudent);
        panelStudent.add(replyStudent);
        panelStudent.add(editAccountStudent);
        panelStudent.add(deleteAccountStudent);
        panelStudent.add(logoutStudent);

        JScrollPane scrollBarStudent = new JScrollPane(discussionTextArea);
        studentFrame.add(scrollBarStudent);


        studentContent.add(panelStudent, BorderLayout.SOUTH);


    }

    public void studentCommentScreen() {
        JFrame commentFrame = new JFrame("Comment");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == uploadCommentButton) {
                    commentFrame.setVisible(false);
                }
            }
        };

        Container commentContent = commentFrame.getContentPane();
        commentContent.setLayout(new BorderLayout());

        commentFrame.setSize(400, 100);
        commentFrame.setLocationRelativeTo(null);
        commentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        commentFrame.setVisible(true);

        JLabel forumNum = new JLabel("Which forum?");
        forumNumberTextField = new JTextField("", 10);
        JLabel com = new JLabel("Enter your comment: ");
        commentTextField = new JTextField("", 20);
        uploadCommentButton = new JButton("Comment");
        uploadCommentButton.addActionListener(actionListener);
        uploadCommentButton.addActionListener(aListener);


        JPanel commentPanel = new JPanel();
        commentPanel.add(forumNum);
        commentPanel.add(forumNumberTextField);
        commentPanel.add(com);
        commentPanel.add(commentTextField);
        commentPanel.add(uploadCommentButton);
        commentContent.add(commentPanel, BorderLayout.CENTER);



    }

    public void studentReplyScreen() {
        JFrame replyFrame = new JFrame("Reply");

        ActionListener aListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == uploadReplyButton) {
                    replyFrame.setVisible(false);
                }
            }
        };

        Container replyContent = replyFrame.getContentPane();
        replyContent.setLayout(new BorderLayout());

        replyFrame.setSize(400, 150);
        replyFrame.setLocationRelativeTo(null);
        replyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        replyFrame.setVisible(true);

        JLabel forumNUM = new JLabel("Which forum?");
        forumNumberTextField = new JTextField("", 10);
        JLabel comment = new JLabel("Which comment?");
        whichComment = new JTextField("", 5);
        JLabel replyField = new JLabel("Enter your reply: ");
        replyTextField = new JTextField("", 20);
        uploadReplyButton = new JButton("Reply");
        uploadReplyButton.addActionListener(actionListener);
        uploadReplyButton.addActionListener(aListener);

        JPanel replyPanel = new JPanel();
        replyPanel.add(forumNUM);
        replyPanel.add(forumNumberTextField);
        replyPanel.add(comment);
        replyPanel.add(whichComment);
        replyPanel.add(replyField);
        replyPanel.add(replyTextField);
        replyPanel.add(uploadReplyButton);
        replyContent.add(replyPanel, BorderLayout.CENTER);

    }

    public void studentEditAccountScreen() {
        int edit = JOptionPane.showConfirmDialog(null, "Do you want to edit your account?",
                "Edit Account", JOptionPane.YES_NO_OPTION);
        if (edit == 0) {
            String newID = JOptionPane.showInputDialog(null, "Enter new ID");
            String newPassword = JOptionPane.showInputDialog(null, "Enter new password");
//            Student.setUsername(newID);
//            Student.setPassword(newID);
            JOptionPane.showMessageDialog(null, "Account edited!",
                    "Edit Account", JOptionPane.PLAIN_MESSAGE);
            // work on progress

        }

    }

    public void studentDeleteAccountScreen() {
        int delete = JOptionPane.showConfirmDialog(null, "Do you want to delete your account?",
                "Delete Account", JOptionPane.YES_NO_OPTION);
        if (delete == 0) {
            JOptionPane.showMessageDialog(null, "Account Deleted!",
                    "Delete Account", JOptionPane.PLAIN_MESSAGE);
        }
    }


    public void send(String out) throws IOException {
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write(out);
        writer.println();
        writer.flush();
    }


    public void run() {
        loginRegisterScreen();

    }
}
