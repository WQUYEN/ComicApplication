package quyenvvph20946.fpl.supercomicapplication.model;

public class User {
    private String _id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String avatarUrl;

    public User() {
    }

    public User(String username, String password, String email, String fullname, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}



