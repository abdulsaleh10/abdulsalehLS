import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Project 4
 *
 * @author Abdul Saleh, Chris Qiu, Dayoon Suh, Daniel Sowers
 * @version 11/15/2021
 */

public class Student {

    private String username;
    private String password;
    //    public String filename;
    ArrayList<Comment> studentComments = new ArrayList<>();

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Creating a comments
    public void createComment(int forumNum, String comment) {
        Comment newComment = new Comment(getUsername() + ": " + comment);
        studentComments.add(newComment);
        DiscussionBoard.discussionForum.get(forumNum).getCommentList().add(newComment);
    }

    //Creating a reply
    public void createReply(int disIndex, int comIndex, String reply) {
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        DiscussionBoard.discussionForum.get(disIndex).getCommentList().get(comIndex).getReplies().add(0,
                reply + "          " + timestamp);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    //Print all the comments specific student made.
    public void printStudentComments() {
        for (Comment c : studentComments)
            System.out.println(c.getComment());
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
