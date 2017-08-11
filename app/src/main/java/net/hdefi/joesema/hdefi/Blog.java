package net.hdefi.joesema.hdefi;

/**
 * Created by JoeSema on 8/10/17.
 */

public class Blog {

    private String title;
    private String description;

    public Blog() {

    }

    public Blog(String title, String description) {
        this.title = title;
        this.description = description;
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
}
