import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Project 4
 *
 * @author Abdul Saleh, Chris Qiu, Dayoon Suh, Daniel Sowers
 * @version 11/15/2021
 */

public class Comment {
    private String comment;
    private ArrayList<String> replies;
    private String grade = "Not graded yet";

    public Comment(String comment) { //Comment constructor to create a comment object.
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);

        this.comment = comment + "                      " + timestamp;
        this.replies = new ArrayList<String>();

    }

    public String getComment() {
        return comment;
    }

    public void setGrade(String givenGrade) { // Sets a grade for the comment.
        this.grade = givenGrade;
    }

    public ArrayList<String> getReplies() {
        return replies;
    }


    public String toString() { // Returns a comment in a specified format.

        String reply = "";
        for (String rep : replies) {
            reply += "\n        -> " + rep;

        }

        return this.comment + "             Grade: " + grade + reply;
    }
}
