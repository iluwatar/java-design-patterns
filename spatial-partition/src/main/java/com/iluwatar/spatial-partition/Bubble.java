import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Bubble extends Point<Bubble>{

    int radius;

    public Bubble(int x, int y, int id, int radius) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.radius = radius;
    }

    void move() {
        Random rand = new Random();
        //moves by 1 unit in either direction
        this.x += rand.nextInt(3) - 1;
        this.y += rand.nextInt(3) - 1;
    }

    boolean touches(Bubble b) {
        //distance between them is greater than sum of radii (both sides of equation squared)
        return ((this.x-b.x)*(this.x-b.x)+(this.y-b.y)*(this.y-b.y)<=(this.radius+b.radius)*(this.radius+b.radius));
    }

    void pop(Hashtable<Integer, Bubble> allBubbles) {
        System.out.println("Bubble " + this.id + " popped at (" + this.x + "," + this.y + ")!");
        allBubbles.remove(this.id);
    }

    void handleCollision(ArrayList<Point> bubblesToCheck, Hashtable<Integer, Bubble> allBubbles) {
        boolean toBePopped = false; //if any other bubble collides with it, made true
        for(int i=0; i<bubblesToCheck.size(); i++) {
            Integer otherId = bubblesToCheck.get(i).id;
            if (allBubbles.get(otherId) != null && //the bubble hasn't been popped yet
                    this.id != otherId && //the two bubbles are not the same
                    this.touches(allBubbles.get(otherId))) //the bubbles touch
            {
                allBubbles.get(otherId).pop(allBubbles);
                toBePopped = true;
            }
        }
        if(toBePopped) {
            this.pop(allBubbles);
        }
    }
}
