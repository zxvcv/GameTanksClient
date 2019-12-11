package app.abstractObjects;

import javafx.scene.Group;

public interface Drawable{
    void display(Group group);
    void undisplay(Group group);
    boolean isDisplay();
}
