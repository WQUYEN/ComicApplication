package quyenvvph20946.fpl.supercomicapplication.model;

import java.util.Date;

public class Comment {
    private String _id;
    private String comicId;
    private User userId;
    private String content;
    private Date createdAt;

    // Getter và setter cho các trường dữ liệu


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Comment() {
    }

    public Comment(String comicId, User userId, String content) {

        this.comicId = comicId;
        this.userId = userId;
        this.content = content;
    }

    public static class User {
        private String _id;
        private String username;

        // Getter và setter cho các trường dữ liệu


        public User() {
        }

        public User(String _id, String username) {
            this._id = _id;
            this.username = username;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
