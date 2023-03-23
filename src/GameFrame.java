import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame()
    {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();  //takes JFrame and fits it snugly around all of the components
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
