package clientApp;

import javafx.scene.canvas.GraphicsContext;

public interface Sprite {
    public void update(double time);
    public void render(GraphicsContext gc);
}
