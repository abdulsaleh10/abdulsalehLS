import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Scanner;

/**
 * Project 4
 *
 * @author Abdul Saleh, Chris Qiu, Dayoon Suh, Daniel Sowers
 * @version 11/15/2021
 */

public class Teacher {

    private String username;
    private String password;

    //Teacher constructor.
    public Teacher(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Method used to create a discussion forum.
    public void createDiscussionForum() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the title of your discussion forum?");
        String title = scan.nextLine();
        while (true) {
            System.out.println("Do you want to import a file?");
            System.out.println("1.Yes \n2.No");
            int file = scan.nextInt();
            scan.nextLine();
            if (file == 1) {
                System.out.println("Enter filename");
                String filename = scan.nextLine();
                try {
                    Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
                    while (sc.hasNextLine()) {
                        for (int i = 0; i < filename.length(); i++) {
                            String line = sc.nextLine();
                            DiscussionBoard.discussionForum.add(new DiscussionForum(title, line));
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            } else if (file == 2) {
                System.out.println("What is the content of your discussion forum?");
                String content = scan.nextLine();
                DiscussionBoard.discussionForum.add(new DiscussionForum(title, content));
                break;
            } else
                System.out.println("Invalid choice");
        }
    }

    //Method to edit a dicussion forum.
    public void editDiscussionForum(int index, String editedTitle, String editedForum) {
        DiscussionBoard.discussionForum.get(index).setTitle(editedTitle);
        DiscussionBoard.discussionForum.get(index).setContent(editedForum);
    }

    //delete a discussion forum
    public void deleteDiscussionForum(int index) {
        DiscussionBoard.discussionForum.remove(index);
    }

    //get the username of the teacher
    public String getUsername() {
        return username;
    }

    //setter method for the teacher.
    public void setUsername(String username) {
        this.username = username;
    }

    //get the password of the teacher
    public String getPassword() {
        return password;
    }

    //set the password of the teacher
    public void setPassword(String password) {
        this.password = password;
    }

    //create a reply to a comment(teacher)
    public void createReply(int disIndex, int comIndex, String reply) {
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        DiscussionBoard.discussionForum.get(disIndex).getCommentList().get(comIndex).getReplies().add(0,
                getUsername() + ": " + reply + "          " + timestamp);
    }

    //grade a students work.
    public void giveGrade(Student toBeGraded, int index, String score) {
        toBeGraded.studentComments.get(index).setGrade(score);
    }
}
