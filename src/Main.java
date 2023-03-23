public class Main {
    private static GameFrame frame;
    public static void main(String[] args) {
        frame=new GameFrame();
    }

    public static void GameOver()
    {
        frame.dispose();
        frame=new GameFrame();
    }
}
