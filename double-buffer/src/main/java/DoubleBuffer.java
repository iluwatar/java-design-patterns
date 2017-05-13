import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Drawing in applets is almost always done with double-buffering.
 * This means that drawing is first done to an off-screen image, and when all
 * is done, the off-screen image is drawn on the screen.
 * This reduces the nasty flickering applets otherwise have.
 *
 * */
public class DoubleBuffer extends Applet implements Runnable, MouseListener {

    private static final int FPS = 30;

    private int frameNumber = -1;
    private int delay;
    private Thread animatorThread;
    private boolean frozen = false;

    private boolean fillColumnTop = true;

    private Dimension offDimension;
    private Image offImage;
    private Graphics offGraphics;

    @Override
    public void init() {
        delay = 1000 / FPS;
        addMouseListener(this);
    }

    @Override
    public void start() {
        if (!frozen) {
            //Start animating!
            if (animatorThread == null) {
                animatorThread = new Thread(this);
            }
            animatorThread.start();
        }
    }

    @Override
    public void stop() {
        //Stop the animating thread.
        animatorThread = null;

        //Get rid of the objects necessary for double buffering.
        offGraphics = null;
        offImage = null;
    }

    @Override
    public void run() {
        //Remember the starting time.
        long startTime = System.currentTimeMillis();

        //Animation loop.
        while (Thread.currentThread() == animatorThread) {
            //Advance the animation frame.
            frameNumber++;
            repaint();
            //Delay depending on how far we are behind.
            try {
                startTime += delay;
                Thread.sleep(Math.max(0,
                        startTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        Dimension d = getSize();
        boolean fillSquare;
        boolean fillNextFrame;
        int rowWidth = 0;
        int x = 0, y = 0;
        int w, h;
        int tmp;

        //Create the off-screen graphics context, if no good one exists.
        if ((offGraphics == null)
                || (d.width != offDimension.width)
                || (d.height != offDimension.height)) {
            offDimension = d;
            offImage = createImage(d.width, d.height);
            offGraphics = offImage.getGraphics();
        }

        //Erase the previous image.
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, d.width, d.height);
        offGraphics.setColor(Color.black);

        //Set width of first "square". Decide whether to fill it.
        fillSquare = fillColumnTop;
        fillColumnTop = !fillColumnTop;
        int squareSize = 20;
        tmp = frameNumber % squareSize;
        if (tmp == 0) {
            w = squareSize;
            fillNextFrame = !fillSquare;
        } else {
            w = tmp;
            fillNextFrame = fillSquare;
        }

        //Draw from left to right.
        while (x < d.width) {
            int colHeight = 0;

            //Draw the column.
            while (y < d.height) {
                colHeight += squareSize;

                //If we don't have room for a full square, cut if off.
                if (colHeight > d.height) {
                    h = d.height - y;
                } else {
                    h = squareSize;
                }

                //Draw the rectangle if necessary.
                if (fillSquare) {
                    offGraphics.fillRect(x, y, w, h);
                    fillSquare = false;
                } else {
                    fillSquare = true;
                }

                y += h;
            }

            //Determine x, y, and w for the next go around.
            x += w;
            y = 0;
            w = squareSize;
            rowWidth += w;
            if (rowWidth > d.width) {
                w = d.width - x;
            }
            fillSquare = fillColumnTop;
            fillColumnTop = !fillColumnTop;
        }
        fillColumnTop = fillNextFrame;

        //Paint the image onto the screen.
        g.drawImage(offImage, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (frozen) {
            frozen = false;
            start();
        } else {
            frozen = true;

            //Instead of calling stop(), which destroys the
            //back buffer, just stop the animating thread.
            animatorThread = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
