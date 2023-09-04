package FishingApp;




public class Fisherman {
    public String name = "";
    public int points = 0;
    public int position = 0;
    public Fisherman(String name, int points) {
        this.name = name;
        this.points = points;
    }
    public String getName() {
        return name;
    }
    public int getPoints() {
        return points;
    }
    public String toString() {
        return (name + ": " + points);
    }
    public void setName(String newName) {
        name = newName;
    }
}
