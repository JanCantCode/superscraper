package tk.jandev.superscraper.scraper;

public class TickDataPoint {
    public double playerX;
    public double playerY;
    public double playerZ;

    public double yaw;
    public double pitch;
    public double attackTime;

    public double enemyX;
    public double enemyY;
    public double enemyZ;

    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;
    public boolean sprint;
    public boolean hit;
    public boolean jump;

    public String toString() {
        return playerX + " " + playerY + " " + playerZ + " " + yaw + " " + pitch + " " + attackTime + " " + enemyX + " " + enemyY +" " + enemyZ+" "+w+" "+a+" "+s+" "+d+" "+sprint+" "+hit+" "+jump;
    }
}
