/**
 * Launches the JavaFX application without directly extending Application.
 * This avoids JavaFX runtime detection issues in some IDE configurations.
 */
public class Launcher {

    public static void main(String[] args) {
        JavaFX.main(args);
    }
}
