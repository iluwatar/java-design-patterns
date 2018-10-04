public class Rect {
    int x; int y; int width; int height;

    //(x,y) - centre of rectangle

    public Rect(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean contains(Point p) {
        return (p.x>=this.x-this.width/2 && p.x<=this.x+this.width/2 && p.y>=this.y-this.height/2 && p.y<=this.y+this.height/2);
    }

    boolean intersects(Rect other) {
        return !(this.x+this.width/2<=other.x-other.width/2 || this.x-this.width/2>=other.x+other.width/2 ||
                this.y+this.height/2<=other.y - other.height/2 || this.y-this.height/2>=other.y+other.height/2);
    }
}
