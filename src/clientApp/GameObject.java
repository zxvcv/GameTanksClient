package clientApp;

import clientApp.Drawable;

import java.io.Serializable;

public interface GameObject extends Updateable, Drawable, Destroyable{
    void setThread(int th); //test
}
