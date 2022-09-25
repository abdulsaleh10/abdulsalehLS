import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Project 4
 *
 * @author Abdul Saleh, Chris Qiu, Dayoon Suh, Daniel Sowers
 * @version 11/15/2021
 */

public class DiscussionForum {
    private String title;
    private String content;
    private ArrayList<Comment> comments;

    //Discussion forum constructor
    public DiscussionForum(String title, String content) {
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);

        this.title = title;
        this.content = content + "                      " + timestamp;
        this.comments = new ArrayList<Comment>(); // 0 comments at first
    }

    //All the comments in a discussion forum.
    public ArrayList<Comment> getCommentList() {
        return comments;
    }

    //if teacher wants to change the title.
    public void setTitle(String title) {
        this.title = title;
    }

    //if teacher wants to change the content.
    public void setContent(String content) {
        this.content = content;
    }

    //String representation of the discussion forum.
    public String toString() {
        String post = this.title + "\n-----------------\n" + this.content
                + "\n*******************************\n";
        String comment = "";

        int index = 1;

        for (Comment com : this.comments) {
            comment += "    " + String.valueOf(index) + ") " + com.toString() + "\n";
            index++;
        }
        return post + "\n" + comment;
    }
}
