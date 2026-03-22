import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/*
Template Method Pattern:

This class defines the common steps for building a weather scene.
All scenes share the same layout and styling, but the content is different.

So I put the shared structure here and let subclasses decide what to display.
*/
public abstract class WeatherSceneTemplate {
    /*
    Template method:
    This builds the scene in a fixed way:
    1. Create layout
    2. Apply styling
    3. Let subclass add content
    4. Return the scene
    */
    public Scene buildScene(int width, int height, String backgroundColor) {
        VBox root = new VBox();

        // Common layout styling for all scenes
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: " + backgroundColor + ";");

        // Subclass adds its own UI elements here
        addContent(root);

        return new Scene(root, width, height);
    }

    /*
    Subclasses must implement this to add their own content
    (labels, buttons, etc.) into the layout.
    */
    protected abstract void addContent(VBox root);
}