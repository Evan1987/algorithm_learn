package algo4.chap06_context.event_driven_simulation;

import java.awt.*;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author zhaochengming
 * @date 2020/10/10 13:45
 */
public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private double rx, ry;          // position
    private double vx, vy;          // velocity
    private int count;              // num of collisions so far
    private final double radius;    // radius
    private final double mass;      // mass
    private final Color color;      // color

    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
    }

    // generate a random particle
    public Particle() {
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass   = 0.5;
        color  = Color.BLACK;
    }

    public void move(double dt) {
        this.rx += this.vx * dt;
        this.ry += this.vy * dt;
    }

    public void draw() {
        StdDraw.setPenColor(this.color);
        StdDraw.filledCircle(this.rx, this.ry, this.radius);
    }

    /**
     * 与 x = 0 和 x = 1两面墙碰撞的预计时间
     * */
    public double timeToHitVerticalWall() {
        if (this.vx > 0) return (1.0 - this.rx - this.radius) / this.vx;
        if (this.vx < 0) return (this.radius - this.rx) / this.vx;
        return INFINITY;
    }

    /**
     * 与 y = 0 和 y = 1两面墙碰撞的预计时间
     * */
    public double timeToHitHorizontalWall() {
        if (this.vy > 0) return (1.0 - this.ry - this.radius) / this.vy;
        if (this.vy < 0) return (this.radius - this.ry) / this.vy;
        else             return INFINITY;
    }

    /**
     * 与另一个球体碰撞的预计时间
     * */
    public double timeToHit(Particle that) {
        if(this == that) return INFINITY;

        // 以this为参考系，计算that的相对位置和相对速度
        double dx = that.rx - this.rx;
        double dy = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;

        double dvr = dx * dvx + dy * dvy;
        if(dvr > 0) return INFINITY;

        double dv2 = dvx * dvx + dvy * dvy;  // 相对速度的平方
        if(dv2 == 0) return INFINITY;

        double dr2 = dx * dx + dy * dy;
        double sigma = this.radius + that.radius;
        double d = dvr * dvr - dv2 * (dr2 - sigma * sigma);

        if(d < 0) return INFINITY;
        return -(dvr + Math.sqrt(d)) / dv2;
    }

    /**
     * 与 x = 0 或 x = 1 墙碰撞
     * */
    public void bounceOffVerticalWall() {
        this.vx = -this.vx;
        this.count ++;
    }

    /**
     * 与 y = 0 或 y = 1墙碰撞
     * */
    public void bounceOffHorizontalWall() {
        this.vy = -this.vy;
        this.count ++;
    }

    /**
     * 小球间刚体碰撞
     * */
    public void bounceOff(Particle that) {
        // 以this为参考系，计算that的相对位置和相对速度
        double dx = that.rx - this.rx;
        double dy = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;

        double dvr = dx * dvx + dy * dvy;
        double dist = this.radius + that.radius;

        double magnitude = 2 * this.mass * that.mass * dvr / ((this.mass + that.mass) * dist);

        // normal force, and in x and y directions
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    public double kineticEnergy() {
        return 0.5 * this.mass * (this.vx * this.vx + this.vy * this.vy);
    }

    public int getCount() {
        return this.count;
    }
}
