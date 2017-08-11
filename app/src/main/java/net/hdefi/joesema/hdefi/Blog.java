package net.hdefi.joesema.hdefi;

/**
 * Created by JoeSema on 8/10/17.
 */

public class Blog {

    private String title;
    private String description;
    private String email;
    private String date;

    public Blog() {

    }

    public Blog(String title, String description, String email, String date) {
        this.title = title;
        this.description = description;
        this.email = email;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
