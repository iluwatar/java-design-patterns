package ie.home.besok.stirrings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Gui extends JFrame {

    private FileStorage storage;

    public Gui(FileStorage storage) {
        this.storage = storage;
        try {
            createUI(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createUI(FileStorage storage) throws IOException {
        setTitle("Stirring counter");
        setSize(300, 300);
        setLocationRelativeTo(null);


        JButton button = createButton(storage);
        JButton graphick = new JButton();
        graphick.setIcon(getIcon("3.jpg"));


        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(
                gl.createSequentialGroup().addComponent(button).addComponent(graphick)
        );

        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(button).addComponent(graphick));


        button.addActionListener((event) -> {
            storage.plus();
            try {
                JOptionPane.showMessageDialog(null,"","",JOptionPane.INFORMATION_MESSAGE, getIcon("2.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JButton createButton(FileStorage storage) throws IOException {
        ImageIcon babyIcon = getIcon("1.png");

        JButton button = new JButton();

        button.setIcon(babyIcon);
        return button;
    }

    private ImageIcon getIcon(String name) throws IOException {
        URL file = this.getClass().getClassLoader().getResource(name);
        return new ImageIcon(ImageIO.read(file));
    }


}
