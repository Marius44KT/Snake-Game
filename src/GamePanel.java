import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private static final int screen_width=600;
    private static final int screen_height=600;
    private static final int unit_size=25;
    private static final int game_units=(screen_width*screen_height)/unit_size;
    private static final int delay=60;
    private final int x[]=new int[game_units];
    private final int y[]=new int[game_units];
    private int bodyParts=10;
    private int applesEaten=0;
    private int appleX;
    private int appleY;
    private char direction='R';
    private boolean running=false;
    private boolean restart_game=false;
    private Timer timer;
    private Random random;


    public GamePanel()
    {
        random=new Random();
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame()
    {
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g)
    {
        if(running)
        {
            /*
            for(int i=0; i<screen_width/unit_size; i++)
            {
                g.drawLine(i*unit_size,0,i*unit_size,screen_height);
                g.drawLine(0,i*unit_size,screen_height,i*unit_size);
            }
             */
            //color the apple
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,unit_size,unit_size);
            //color the snake
            for(int i=0; i<bodyParts; i++)
            {
                if(i==0)    //snake head
                {
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
                else
                {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
            }
            g.setColor(new Color(255,100,0));
            g.setFont(new Font("Ink Free",Font.BOLD,50));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(screen_width-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else
            gameOver(g);
    }


    public void newApple()
    {
        appleX=random.nextInt((int)screen_width/unit_size)*unit_size;
        appleY=random.nextInt((int)screen_height/unit_size)*unit_size;
    }


    public void move()
    {
        for(int i=bodyParts; i>0; i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction)
        {
            case 'U':
                y[0]=y[0]-unit_size;
                break;
            case 'D':
                y[0]=y[0]+unit_size;
                break;
            case 'L':
                x[0]=x[0]-unit_size;
                break;
            case 'R':
                x[0]=x[0]+unit_size;
                break;
        }
    }


    public void checkApple()
    {
        if(x[0]==appleX && y[0]==appleY)
        {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }


    public void checkCollisions()
    {
        //snake head collides with its own body
        for(int i=bodyParts; i>0; i--)
        {
            if((x[0]==x[i]) && (y[0]==y[i]))
            {
                running=false;
            }
        }
        //avoid collisions with borders
        if(x[0]>screen_width)
            x[0]=0;
        if(x[0]<0)
            x[0]=screen_width;
        if(y[0]>screen_height)
            y[0]=0;
        if(y[0]<0)
            y[0]=screen_height;
        //allow snake to collide with borders(end of the game)
        /*
        //check if head touches left border
        if(x[0]<0)
            running=false;
        //check if head touches right border
        if(x[0]>screen_width)
            running=false;
        //check if head touches up border
        if(y[0]<0)
            running=false;
        //check if head touches down border
        if(y[0]>screen_height)
            running=false;
        */
        if(!running)
            timer.stop();
    }


    public void gameOver(Graphics g)
    {
        //Score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screen_width-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game over",(screen_width-metrics2.stringWidth("Game Over"))/2,screen_height/2);

        g.setColor(new Color(25,255,0));
        g.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics metrics3=getFontMetrics(g.getFont());
        g.drawString("Press Space to restart the game",(screen_width-metrics3.stringWidth("Press Space to restart the game"))/2,screen_height-g.getFont().getSize());
        restart_game=true;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(running)
        {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                    {
                        if(y[0]>=0 && y[0]<screen_height)
                            direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                    {
                        if(y[0]>=0 && y[0]<screen_height)
                            direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D')
                    {
                        if(x[0]>=0 && x[0]<screen_height)
                            direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                    {
                        if(x[0]>=0 && x[0]<screen_height)
                            direction='D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if(restart_game)
                    {
                        restart_game=false;
                        Main.GameOver();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}