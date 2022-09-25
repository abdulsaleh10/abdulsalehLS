
import javax.swing.*;
import java.sql.Timestamp;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 5 Server
 *
 * @author Chris Qiu, Abdul Saleh, Daniel Sowers, and Dayoon Suh
 * @version 11/30/2021
 */

public class P5Server {

    public static ArrayList<String> usernamesTeacher = new ArrayList<String>();
    public static ArrayList<String> passwordsTeacher = new ArrayList<String>();
    public static ArrayList<String> usernamesStudent = new ArrayList<String>();
    public static ArrayList<String> passwordsStudent = new ArrayList<String>();
    public static ArrayList<DiscussionForum> discussionForum = new ArrayList<DiscussionForum>();
    public static ArrayList<String> contentOfForum = new ArrayList<>();


    public static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    public static ArrayList<Student> students = new ArrayList<Student>();
    ;
    private static String success = "success";

    public static void giveGrade(Student toBeGraded, int index, String score) {
        toBeGraded.studentComments.get(index).setGrade(score);
    }

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(2021);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());


            String registerOrLogin = reader.readLine();

            if (registerOrLogin.equals("register")) {

                String rtOrRs = reader.readLine();

                if (rtOrRs.equals("rt")) {

                    String[] data = reader.readLine().split(" ");
                    String username = data[0];
                    String password = data[1];

                    usernamesTeacher.add(username);
                    passwordsTeacher.add(password);

                    Teacher teacher = new Teacher(username, password);
                    teachers.add(teacher);

                    registerOrLogin = reader.readLine();

                    if (registerOrLogin.equals("login")) {
                        String ltOrLs = reader.readLine();
                        if (ltOrLs.equals("lt")) {
                            String[] dataLogin = reader.readLine().split(" ");
                            String usernameLogin = dataLogin[0];
                            String passwordLogin = dataLogin[1];

                            boolean loginSuccessful = false;

                            int infoIndex = -1;

                            if (teachers.size() > 0) {
                                for (String u : usernamesTeacher) {
                                    if (usernameLogin.equals(u)) {
                                        infoIndex = usernamesTeacher.indexOf(u);
                                        if (passwordLogin.equals(passwordsTeacher.get(infoIndex))) {
                                            loginSuccessful = true;
                                            break;
                                        } else {
                                            loginSuccessful = false;
                                        }
                                    } else {
                                        loginSuccessful = false;
                                    }
                                }
                            }
                            if (loginSuccessful) {
                                writer.write(success);
                                writer.println();
                                writer.flush();

                                Teacher currentUser = teachers.get(infoIndex);

                                String menu = reader.readLine();

                                if (menu.equals("createDiscussionTeacher")) {
                                    String title = reader.readLine();
                                    String content = reader.readLine();
                                    System.out.println();

                                    discussionForum.add(new DiscussionForum
                                            (title, content));
                                    String contentOfForumToUse = title + ":  " + content;
                                    contentOfForum.add(contentOfForumToUse);

                                    writer.write(contentOfForumToUse);
                                    writer.println();
                                    writer.flush();

                                }

                                if (menu.equals("editDiscussionTeacher")) {
                                    int index = Integer.parseInt(reader.readLine());
                                    String newTitle = reader.readLine();
                                    String newContent = reader.readLine();
                                    currentUser.editDiscussionForum(index, newTitle, newContent);

                                }

                                if (menu.equals("deleteDiscussionTeacher")) {
                                    int forumNum = Integer.parseInt(reader.readLine());
                                    currentUser.deleteDiscussionForum(forumNum);
                                }

                                if (menu.equals("replyTeacher")) {
                                    Long datetime = System.currentTimeMillis();
                                    Timestamp timestamp = new Timestamp(datetime);
                                    discussionForum.get(Integer.parseInt(reader.readLine())).getCommentList()
                                            .get(Integer.parseInt(reader.readLine())).getReplies().add(0,
                                                    currentUser + ": " + reader.readLine() + "          " +
                                                            timestamp);

                                }

                                if (menu.equals("addGradeTeacher")) {
                                    String gradeUsername = reader.readLine();
                                    for (int i = 0; i < students.size(); i++) {
                                        if (gradeUsername == students.get(i).getUsername()) {
                                            giveGrade(students.get(i), Integer.parseInt(reader.readLine()), reader.readLine());
                                        }
                                    }
                                }

                                if (menu.equals("editAccountTeacher")) {
                                    String newPassword = reader.readLine();
                                    currentUser.setPassword(newPassword);
                                }

                                if (menu.equals("deleteAccountTeacher")) {
                                    teachers.remove(currentUser);
                                }

                            } else {
                                writer.write("tryAgain");
                                writer.flush();
                            }

                        }

                        if (ltOrLs.equals("ls")) {
                            String[] dataLogin = reader.readLine().split(" ");
                            String usernameLogin = dataLogin[0];
                            String passwordLogin = dataLogin[1];

                            boolean loginSuccessful = false;

                            int infoIndex = -1;

                            if (students.size() > 0) {
                                for (String u : usernamesStudent) {
                                    if (usernameLogin.equals(u)) {
                                        infoIndex = usernamesStudent.indexOf(u);
                                        if (passwordLogin.equals(passwordsStudent.get(infoIndex))) {
                                            loginSuccessful = true;
                                            break;
                                        } else {
                                            loginSuccessful = false;
                                        }
                                    } else {
                                        loginSuccessful = false;
                                    }
                                }
                            }

                            if (loginSuccessful) {
                                writer.write(success);
                                writer.println();
                                writer.flush();

                                Student currentUser = students.get(infoIndex);

                                String menu = reader.readLine();

                                if (menu.equals("commentStudent")) {
                                    int forumNum = Integer.parseInt(reader.readLine());
                                    String comment = reader.readLine();
                                    currentUser.createComment(forumNum, comment);

                                }

                                if (menu.equals("replyStudent")) {
                                    int forumNum = Integer.parseInt(reader.readLine());
                                    int commentNum = Integer.parseInt(reader.readLine());
                                    String reply = reader.readLine();
                                    currentUser.createReply(forumNum, commentNum, reply);

                                }

                                if (menu.equals("editAccountStudent")) {
                                    String newPassword = reader.readLine();
                                    currentUser.setPassword(newPassword);

                                }

                                if (menu.equals("deleteAccountStudent")) {
                                    students.remove(currentUser);
                                }

                            } else {
                                writer.write("fail");
                                writer.flush();
                            }
                        }
                    }
                }

                if (rtOrRs.equals("rs")) {
//                    String info = reader.readLine();
//                    String[] data = info.split(" ");

                    String[] data = reader.readLine().split(" ");
                    String username = data[0];
                    String password = data[1];

                    usernamesStudent.add(username);
                    passwordsStudent.add(password);

                    Student student = new Student(username, password);
                    students.add(student);

                    registerOrLogin = reader.readLine();


                    if (registerOrLogin.equals("login")) {
                        System.out.println(success);

                        String ltOrLs = reader.readLine();
                        if (ltOrLs.equals("lt")) {
                            String[] dataLogin = reader.readLine().split(" ");
                            String usernameLogin = dataLogin[0];
                            String passwordLogin = dataLogin[1];

                            boolean loginSuccessful = false;

                            int infoIndex = -1;

                            if (teachers.size() > 0) {
                                for (String u : usernamesTeacher) {
                                    if (usernameLogin.equals(u)) {
                                        infoIndex = usernamesTeacher.indexOf(u);
                                        if (passwordLogin.equals(passwordsTeacher.get(infoIndex))) {
                                            loginSuccessful = true;
                                            break;
                                        } else {
                                            loginSuccessful = false;
                                        }
                                    } else {
                                        loginSuccessful = false;
                                    }
                                }
                            }
                            if (loginSuccessful) {
                                writer.write(success);
                                writer.println();
                                writer.flush();

                                Teacher currentUser = teachers.get(infoIndex);

                                String menu = reader.readLine();

                                if (menu.equals("createDiscussionTeacher")) {
                                    String title = reader.readLine();
                                    String content = reader.readLine();

                                    discussionForum.add(new DiscussionForum
                                            (title, content));
                                    System.out.println(title + " " + content);
                                    String contentOfForumToUse = title + "\n   " + content;
                                    contentOfForum.add(contentOfForumToUse);

                                    writer.write(contentOfForumToUse);
                                    writer.println();
                                    writer.flush();
                                }

                                if (menu.equals("editDiscussionTeacher")) {
                                    int index = Integer.parseInt(reader.readLine());
                                    String newTitle = reader.readLine();
                                    String newContent = reader.readLine();
                                    currentUser.editDiscussionForum(index, newTitle, newContent);

                                }

                                if (menu.equals("deleteDiscussionTeacher")) {
                                    int forumNum = Integer.parseInt(reader.readLine());
                                    currentUser.deleteDiscussionForum(forumNum);
                                }

                                if (menu.equals("replyTeacher")) {
                                    int forumNum = Integer.parseInt(reader.readLine());
                                    int commentNum = Integer.parseInt(reader.readLine());
                                    String reply = reader.readLine();
                                    currentUser.createReply(forumNum, commentNum, reply);

                                }

                                if (menu.equals("addGradeTeacher")) {
                                    String gradeUsername = reader.readLine();
                                    for (int i = 0; i < students.size(); i++) {
                                        if (gradeUsername == students.get(i).getUsername()) {
                                            giveGrade(students.get(i), Integer.parseInt(reader.readLine()),
                                                    reader.readLine());
                                        }
                                    }
                                }

                                if (menu.equals("editAccountTeacher")) {
                                    String newPassword = reader.readLine();
                                    currentUser.setPassword(newPassword);
                                }

                                if (menu.equals("deleteAccountTeacher")) {
                                    teachers.remove(currentUser);
                                }

                            } else {
                                writer.write("tryAgain");
                                writer.flush();
                            }

                        }

                        if (ltOrLs.equals("ls")) {
                            String[] dataLogin = reader.readLine().split(" ");
                            String usernameLogin = dataLogin[0];
                            String passwordLogin = dataLogin[1];

                            boolean loginSuccessful = false;

                            int infoIndex = -1;

                            if (students.size() > 0) {

                                for (int i = 0; i < students.size(); i++) {
                                    if (students.get(i).getUsername().equals(usernameLogin)) {
                                        infoIndex = usernamesStudent.indexOf(students.get(i).getUsername());
                                        if (passwordLogin.equals(passwordsStudent.get(infoIndex))) {
                                            loginSuccessful = true;
                                            break;
                                        } else {
                                            loginSuccessful = false;
                                        }
                                    } else {
                                        loginSuccessful = false;
                                    }
                                }

                                if (loginSuccessful) {


                                    writer.write(success);
                                    writer.println();
                                    writer.flush();

                                    Student currentUser = students.get(infoIndex);

                                    String menu = reader.readLine();

                                    if (menu.equals("commentStudent")) {
                                        int forumNum = Integer.parseInt(reader.readLine());
                                        String comment = reader.readLine();
                                        currentUser.createComment(forumNum, comment);

                                    }

                                    if (menu.equals("replyStudent")) {
                                        int forumNum = Integer.parseInt(reader.readLine());
                                        int commentNum = Integer.parseInt(reader.readLine());
                                        String reply = reader.readLine();
                                        currentUser.createReply(forumNum, commentNum, reply);

                                    }

                                    if (menu.equals("editAccountStudent")) {
                                        String newPassword = reader.readLine();
                                        currentUser.setPassword(newPassword);

                                    }

                                    if (menu.equals("deleteAccountStudent")) {
                                        students.remove(currentUser);
                                    }

                                } else {
                                    writer.write("Fail");
                                    writer.flush();
                                }

                            }
                        }

                    }
                }

                if (registerOrLogin.equals("login")) {
                    String ltOrLs = reader.readLine();
                    if (ltOrLs.equals("lt")) {
                        String[] dataLogin = reader.readLine().split(" ");
                        String usernameLogin = dataLogin[0];
                        String passwordLogin = dataLogin[1];

                        boolean loginSuccessful = false;

                        int infoIndex = -1;

                        if (teachers.size() > 0) {
                            for (String u : usernamesTeacher) {
                                if (usernameLogin.equals(u)) {
                                    infoIndex = usernamesTeacher.indexOf(u);
                                    if (passwordLogin.equals(passwordsTeacher.get(infoIndex))) {
                                        loginSuccessful = true;
                                        break;
                                    } else {
                                        loginSuccessful = false;
                                    }
                                } else {
                                    loginSuccessful = false;
                                }
                            }
                        }
                        if (loginSuccessful) {
                            writer.write(success);
                            writer.println();
                            writer.flush();

                            Teacher currentUser = teachers.get(infoIndex);

                            String menu = reader.readLine();

                            if (menu.equals("createDiscussionTeacher")) {
                                String title = reader.readLine();
                                String content = reader.readLine();

                                discussionForum.add(new DiscussionForum
                                        (title, content));
                                String contentOfForumToUse = title + "\n   " + content;
                                contentOfForum.add(contentOfForumToUse);

                                writer.write(contentOfForumToUse);
                                writer.println();
                                writer.flush();
                            }

                            if (menu.equals("editDiscussionTeacher")) {
                                int index = Integer.parseInt(reader.readLine());
                                String newTitle = reader.readLine();
                                String newContent = reader.readLine();
                                currentUser.editDiscussionForum(index, newTitle, newContent);

                            }

                            if (menu.equals("deleteDiscussionTeacher")) {
                                int forumNum = Integer.parseInt(reader.readLine());
                                currentUser.deleteDiscussionForum(forumNum);
                            }

                            if (menu.equals("replyTeacher")) {
                                int forumNum = Integer.parseInt(reader.readLine());
                                int commentNum = Integer.parseInt(reader.readLine());
                                String reply = reader.readLine();
                                currentUser.createReply(forumNum, commentNum, reply);

                            }

                            if (menu.equals("addGradeTeacher")) {
                                String gradeUsername = reader.readLine();
                                for (int i = 0; i < students.size(); i++) {
                                    if (gradeUsername == students.get(i).getUsername()) {
                                        giveGrade(students.get(i), Integer.parseInt(reader.readLine()),
                                                reader.readLine());
                                    }
                                }
                            }

                            if (menu.equals("editAccountTeacher")) {
                                String newPassword = reader.readLine();
                                currentUser.setPassword(newPassword);
                            }

                            if (menu.equals("deleteAccountTeacher")) {
                                teachers.remove(currentUser);
                            }

                        } else {
                            writer.write("tryAgain");
                            writer.flush();
                        }

                    }

                    if (ltOrLs.equals("ls")) {
                        String[] data = reader.readLine().split(" ");
                        String username = data[0];
                        String password = data[1];

                        boolean loginSuccessful = false;

                        int infoIndex = -1;

                        if (students.size() > 0) {
                            for (String u : usernamesStudent) {
                                if (username.equals(u)) {
                                    infoIndex = usernamesStudent.indexOf(u);
                                    if (password.equals(passwordsStudent.get(infoIndex))) {
                                        loginSuccessful = true;
                                        break;
                                    } else {
                                        loginSuccessful = false;
                                    }
                                } else {
                                    loginSuccessful = false;
                                }
                            }
                        }

                        if (loginSuccessful) {
                            writer.write(success);
                            writer.println();
                            writer.flush();

                            Student currentUser = students.get(infoIndex);

                            String menu = reader.readLine();

                            if (menu.equals("commentStudent")) {
                                int forumNum = Integer.parseInt(reader.readLine());
                                String comment = reader.readLine();
                                currentUser.createComment(forumNum, comment);

                            }

                            if (menu.equals("replyStudent")) {
                                int forumNum = Integer.parseInt(reader.readLine());
                                int commentNum = Integer.parseInt(reader.readLine());
                                String reply = reader.readLine();
                                currentUser.createReply(forumNum, commentNum, reply);

                            }

                            if (menu.equals("editAccountStudent")) {
                                String newPassword = reader.readLine();
                                currentUser.setPassword(newPassword);

                            }

                            if (menu.equals("deleteAccountStudent")) {
                                students.remove(currentUser);
                            }

                        } else {
                            writer.write("fail");
                            writer.flush();
                        }

                    }
                }
            }
            if (registerOrLogin.equals("login")) {
                String ltOrLs = reader.readLine();
                if (ltOrLs.equals("lt")) {
                    String[] dataLogin = reader.readLine().split(" ");
                    String usernameLogin = dataLogin[0];
                    String passwordLogin = dataLogin[1];

                    boolean loginSuccessful = false;

                    int infoIndex = -1;

                    if (teachers.size() > 0) {
                        for (String u : usernamesTeacher) {
                            if (usernameLogin.equals(u)) {
                                infoIndex = usernamesTeacher.indexOf(u);
                                if (passwordLogin.equals(passwordsTeacher.get(infoIndex))) {
                                    loginSuccessful = true;
                                    break;
                                } else {
                                    loginSuccessful = false;
                                }
                            } else {
                                loginSuccessful = false;
                            }
                        }
                    }
                    if (loginSuccessful) {
                        writer.write(success);
                        writer.println();
                        writer.flush();

                        Teacher currentUser = teachers.get(infoIndex);

                        String menu = reader.readLine();

                        if (menu.equals("createDiscussionTeacher")) {
                            String title = reader.readLine();
                            String content = reader.readLine();

                            discussionForum.add(new DiscussionForum
                                    (title, content));
                            System.out.println(title + " " + content);
                            String contentOfForumToUse = title + "\n   " + content;
                            contentOfForum.add(contentOfForumToUse);

                            writer.write(contentOfForumToUse);
                            writer.println();
                            writer.flush();
                        }

                        if (menu.equals("editDiscussionTeacher")) {
                            int index = Integer.parseInt(reader.readLine());
                            String newTitle = reader.readLine();
                            String newContent = reader.readLine();
                            currentUser.editDiscussionForum(index, newTitle, newContent);

                        }

                        if (menu.equals("deleteDiscussionTeacher")) {
                            int forumNum = Integer.parseInt(reader.readLine());
                            currentUser.deleteDiscussionForum(forumNum);
                        }

                        if (menu.equals("replyTeacher")) {
                            int forumNum = Integer.parseInt(reader.readLine());
                            int commentNum = Integer.parseInt(reader.readLine());
                            String reply = reader.readLine();
                            currentUser.createReply(forumNum, commentNum, reply);

                        }

                        if (menu.equals("addGradeTeacher")) {
                            String gradeUsername = reader.readLine();
                            for (int i = 0; i < students.size(); i++) {
                                if (gradeUsername == students.get(i).getUsername()) {
                                    giveGrade(students.get(i), Integer.parseInt(reader.readLine()),
                                            reader.readLine());
                                }
                            }
                        }

                        if (menu.equals("editAccountTeacher")) {
                            String newPassword = reader.readLine();
                            currentUser.setPassword(newPassword);
                        }

                        if (menu.equals("deleteAccountTeacher")) {
                            teachers.remove(currentUser);
                        }

                    } else {
                        writer.write("tryAgain");
                        writer.flush();
                    }

                }

                if (ltOrLs.equals("ls")) {
                    String[] data = reader.readLine().split(" ");
                    String username = data[0];
                    String password = data[1];

                    boolean loginSuccessful = false;

                    int infoIndex = -1;

                    if (students.size() > 0) {
                        for (String u : usernamesStudent) {
                            if (username.equals(u)) {
                                infoIndex = usernamesStudent.indexOf(u);
                                if (password.equals(passwordsStudent.get(infoIndex))) {
                                    loginSuccessful = true;
                                    break;
                                } else {
                                    loginSuccessful = false;
                                }
                            } else {
                                loginSuccessful = false;
                            }
                        }
                    }

                    if (loginSuccessful) {
                        writer.write(success);
                        writer.println();
                        writer.flush();

                        Student currentUser = students.get(infoIndex);

                        String menu = reader.readLine();

                        if (menu.equals("commentStudent")) {
                            int forumNum = Integer.parseInt(reader.readLine());
                            String comment = reader.readLine();
                            currentUser.createComment(forumNum, comment);

                        }

                        if (menu.equals("replyStudent")) {
                            int forumNum = Integer.parseInt(reader.readLine());
                            int commentNum = Integer.parseInt(reader.readLine());
                            String reply = reader.readLine();
                            currentUser.createReply(forumNum, commentNum, reply);

                        }

                        if (menu.equals("editAccountStudent")) {
                            String newPassword = reader.readLine();
                            currentUser.setPassword(newPassword);

                        }

                        if (menu.equals("deleteAccountStudent")) {
                            students.remove(currentUser);
                        }

                    } else {
                        writer.write("fail");
                        writer.println();
                        writer.flush();
                    }
                }
            }

        }

    }
}
