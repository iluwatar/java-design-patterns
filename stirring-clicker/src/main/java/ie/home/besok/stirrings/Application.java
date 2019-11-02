package ie.home.besok.stirrings;

import java.awt.*;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        FileStorage storage = new FileStorage();

        EventQueue.invokeLater(() -> {
            Gui gui = new Gui(storage);
            gui.setVisible(true);
        });
    }
}
