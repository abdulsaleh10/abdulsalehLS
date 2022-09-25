import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")

/**
 * Project 4
 *
 * @author Abdul Saleh, Chris Qiu, Dayoon Suh, Daniel Sowers
 * @version 11/15/2021
 */

public class DiscussionBoard {

    public static ArrayList<Teacher> teacher = new ArrayList<Teacher>();
    public static ArrayList<Student> student = new ArrayList<Student>();
    public static ArrayList<DiscussionForum> discussionForum = new ArrayList<DiscussionForum>();

    //Prints the discussion forums.
    public static void printForum() {
        String ret = "";
        int index = 1;
        for (DiscussionForum df : discussionForum) {
            ret += "\n*******************************\n" + String.valueOf(index) + ". " + df.toString() + "\n";
            index++;
        }
        System.out.println(ret);
    }

    //Ask whether you want to login or register.
    public static void loginOrRegister() {
        System.out.println("Do you want to login or register?");
        System.out.println("1.Login \n2.Register");

    }

    //Ask whether you want to login as a teacher, student, or back.
    public static void loginStudentOrTeacher() {
        System.out.println("Student or Teacher?");
        System.out.println("1.Student \n2.Teacher \n3. Back");
    }

    //Method for if they want to login as a student.
    public static void loginStudent() {
        Scanner sc = new Scanner(System.in);

        if (student.size() > 0) {

            System.out.println("What is your username?");
            String username = sc.nextLine();
            System.out.println(username);
            System.out.println("What is your password?");
            String password = sc.nextLine();
            System.out.println(password);

            //METHOD TO CHECK IF STUDENT EXIST

            boolean loginSuccessful = false;

            for (Student st : student) {
                String id = st.getUsername();
                String pw = st.getPassword();

                if (username.equals(id) && password.equals(pw)) {
                    loginSuccessful = true;
                    System.out.println("Login successful!");

                    Student curStudent = st;

                    while (loginSuccessful) {
                        teacher.add((Teacher) (read("teacher.txt")));
                        student.add((Student) (read("student.txt")));
                        discussionForum.add((DiscussionForum) (read("discussion.txt")));
                        printForum();
                        System.out.println("1.Comment\n2.Reply\n3.Edit Account\n4.Delete Account\n5.Log Out");
                        String menu = sc.nextLine();
                        if (menu.equals("1")) {
                            System.out.println("Which discussion forum do you want to comment to?");
                            int disNum = sc.nextInt() - 1;
                            sc.nextLine();
                            while (true) {
                                System.out.println("Do you want to import a comment?");
                                System.out.println("1.Yes \n2.No");
                                int ans = sc.nextInt();
                                sc.nextLine();
                                if (ans == 1) {
                                    System.out.println("Enter filename");
                                    String filename = sc.nextLine();
                                    try {
                                        sc = (new Scanner(new FileReader(filename)));
                                        while (sc.hasNextLine()) {
                                            for (int i = 0; i < filename.length(); i++) {
                                                String line = sc.nextLine();
                                                curStudent.createComment(disNum, line);
                                            }
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                } else if (ans == 2) {
                                    System.out.println("Enter comment:");
                                    String comment = sc.nextLine();
                                    curStudent.createComment(disNum, comment);
                                    break;
                                } else
                                    System.out.println("Invalid choice");
                            }
                        } else if (menu.equals("2")) {
                            System.out.println("Which discussion forum do you want to reply to?");
                            int disNum = sc.nextInt() - 1;
                            System.out.println("Which comment do you want to reply to?");
                            int commentNum = sc.nextInt() - 1;
                            sc.nextLine();
                            System.out.println("Add your reply");
                            String reply = sc.nextLine();
                            curStudent.createReply(disNum, commentNum, reply);
                        } else if (menu.equals("3")) {
                            System.out.println("Enter new username:");
                            String newUsername = sc.nextLine();
                            curStudent.setUsername(newUsername);
                            System.out.println("Enter new password:");
                            String newPassword = sc.nextLine();
                            curStudent.setPassword(newPassword);

                        } else if (menu.equals("4")) {
                            student.remove(curStudent);
                            System.out.println("Account Deleted.");
                        } else if (menu.equals("5")) {
                            break;
                        }
                    }
                }

            }
            if (!loginSuccessful) {
                System.out.println("Account does not exist!");
            }
        } else {
            System.out.println("No accounts");
        }
    }

    //Method for if the user wants to login as a teacher.
    public static void loginTeacher() {
        Scanner sc = new Scanner(System.in);

        if (teacher.size() > 0) {
            System.out.println("What is your username?");
            String username = sc.nextLine();
            System.out.println("What is your password?");
            String password = sc.nextLine();

            //METHOD TO CHECK IF TEACHER EXIST GOES HERE

            boolean loginSuccessful = false;

            for (Teacher tc : teacher) {
                String id = tc.getUsername();
                String pw = tc.getPassword();

                if (username.equals(id) && password.equals(pw)) {
                    loginSuccessful = true;
                    System.out.println("Login successful!");

                    Teacher curTeacher = tc;

                    while (true) {

                        printForum();
                        System.out.println("1.Create Discussion\n2.Edit Discussion\n3.Delete " +
                                "Discussion\n4.Reply\n5.Add grade \n6.Edit Account\n7.Delete Account\n8.Log Out");
                        String menu = sc.nextLine();

                        if (menu.equals("1")) {
                            curTeacher.createDiscussionForum();
                        } else if (menu.equals("2")) {
                            System.out.println("Which discussion forum do you want to edit?");
                            int disNum = sc.nextInt() - 1;
                            sc.nextLine();
                            System.out.println("What do you want to change the title to?");
                            String newTitle = sc.nextLine();
                            System.out.println("Enter new forum:");
                            String newForum = sc.nextLine();
                            curTeacher.editDiscussionForum(disNum, newTitle, newForum);
                        } else if (menu.equals("3")) {
                            System.out.println("Which discussion forum do you want to delete?");
                            int delete = sc.nextInt() - 1;
                            sc.nextLine();
                            curTeacher.deleteDiscussionForum(delete);
                            System.out.println("Discussion deleted.");
                        } else if (menu.equals("4")) {
                            System.out.println("Which discussion forum do you want to reply to?");
                            int disNum = sc.nextInt() - 1;
                            System.out.println("Which comment do you want to reply to?");
                            int commentNum = sc.nextInt() - 1;
                            sc.nextLine();
                            System.out.println("Add your reply");
                            String reply = sc.nextLine();
                            curTeacher.createReply(disNum, commentNum, reply);
                        } else if (menu.equals("5")) {
                            System.out.println("Enter student username");
                            String user = sc.nextLine();
                            Student toBeGraded = null;
                            for (Student st : student) {
                                if (st.getUsername().equals(user)) {
                                    toBeGraded = st;
                                    st.printStudentComments();
                                }
                            }
                            System.out.println("Which comment would you like to grade? (Enter number)");
                            int commentNum = sc.nextInt() - 1;
                            sc.nextLine();
                            System.out.println("Enter grade");
                            String grade = sc.nextLine();
                            curTeacher.giveGrade(toBeGraded, commentNum, grade);
                            System.out.println("Graded!");

                        } else if (menu.equals("6")) {
                            System.out.println("Enter new username:");
                            String newUsername = sc.nextLine();
                            curTeacher.setUsername(newUsername);
                            System.out.println("Enter new password:");
                            String newPassword = sc.nextLine();
                            curTeacher.setPassword(newPassword);
                        } else if (menu.equals("7")) {
                            teacher.remove(curTeacher);
                            System.out.println("Account Deleted.");
                        } else if (menu.equals("8")) {
                            break;
                        } else {
                            System.out.println("Invalid input! please try again.");
                        }
                    }
                } else
                    System.out.println("Account does not exist!");

            }
        } else {
            System.out.println("No accounts");
        }
    }

    public static Object read(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!");
        } catch (IOException e) {
            System.out.println("error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(String fileName, Object o) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file!");
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    //Method for if you want to register as a student.
    public static void registeringStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Create a username");
        String username = sc.nextLine();
        System.out.println("Create a password");
        String password = sc.nextLine();

        boolean noProblemWithAccount = true;

        for (Student st : student) {
            String info = st.getUsername() + "," + st.getPassword();
            if (info.equals(username + "," + password)) {
                System.out.println("This account exists, please log in!");
                noProblemWithAccount = false;
            } else if (st.getUsername().equals(username)) {
                System.out.println("Username already exists");
                noProblemWithAccount = false;
            }
        }

        if (noProblemWithAccount) {
            Student s = new Student(username, password);
            student.add(s);
            System.out.println("Account created!");
        }
    }

    //Method for if you want to register as a teacher.
    public static void registeringTeacher() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Create a username");
        String username = sc.nextLine();
        System.out.println("Create a password");
        String password = sc.nextLine();

        boolean noProblemWithAccount = true;

        for (Teacher tc : teacher) {
            String info = tc.getUsername() + "," + tc.getPassword();
            if (info.equals(username + "," + password)) {
                System.out.println("This account exists, please log in!");
                noProblemWithAccount = false;
            } else if (tc.getUsername().equals(username)) {
                System.out.println("Username already exists");
                noProblemWithAccount = false;
            }
        }

        if (noProblemWithAccount) {
            Teacher t = new Teacher(username, password);
            teacher.add(t);
            System.out.println("Account created!");
        }

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            loginOrRegister();
            String choice = sc.nextLine();
            // LOG IN CHOICE
            if (choice.equals("1")) {
                while (true) {
                    loginStudentOrTeacher();
                    String SoT = sc.nextLine();
                    if (SoT.equals("1")) {
                        loginStudent();
                        write("discussion.txt", discussionForum);
                        break;
                    } else if (SoT.equals("2")) {
                        loginTeacher();
                        write("discussion.txt", discussionForum);
                        break;
                    } else {
                        System.out.println("Invalid choice!");
                    }
                }
                //REGISTER ACCOUNT CHOICE
            } else if (choice.equals("2")) {
                while (true) {
                    loginStudentOrTeacher();
                    String SoT = sc.nextLine();
                    //REGISTERING STUDENT ACCOUNT
                    if (SoT.equals("1")) {
                        registeringStudent();
                        write("student.txt", student);
                        break;
                    } else if (SoT.equals("2")) {
                        registeringTeacher();
                        write("teacher.txt", teacher);
                        break;
                    } else if (SoT.equals("3"))
                        break;
                    else {
                        System.out.println("Invalid choice!");
                    }
                }
            } else
                System.out.println("Invalid choice!");
        }
    }
}
