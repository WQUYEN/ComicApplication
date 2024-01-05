package quyenvvph20946.fpl.supercomicapplication.model;

public class ListCommentModel {
    String fullname;
    String content;

    public ListCommentModel() {
    }

    public ListCommentModel(String fullname, String content) {
        this.fullname = fullname;

        this.content = content;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
