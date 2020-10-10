package Chap06_Context.event_driven_simulation;

import Chap02_Sorting.Chap02_04.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import org.jetbrains.annotations.NotNull;

/**
 * @author zhaochengming
 * @date 2020/10/10 16:03
 */
public class CollisionSystem {
    private static final double HZ = 0.5;
    private MinPQ<Event> pq;
    private double t = 0.0;
    private final Particle[] particles;

    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();
    }

    private void predict(Particle a, double limit) {
        if(a == null) return;
        for (Particle particle : this.particles) {
            double dt = a.timeToHit(particle);
            if(this.t + dt <= limit)
                this.pq.push(new Event(this.t + dt, a, particle));
        }

        double verticalHitTime = a.timeToHitVerticalWall();
        if(this.t + verticalHitTime <= limit)
            this.pq.push(new Event(this.t + verticalHitTime, a, null));

        double horizontalHitTime = a.timeToHitHorizontalWall();
        if(this.t + horizontalHitTime <= limit)
            this.pq.push(new Event(this.t + horizontalHitTime, null, a));
    }

    private void reDraw(double limit) {
        StdDraw.clear();
        for(Particle particle: this.particles)
            particle.draw();
        StdDraw.show();
        StdDraw.pause(20);
        if(this.t < limit)
            this.pq.push(new Event(this.t + 1.0 / HZ, null, null));
    }

    public void simulate(double limit) {
        this.pq = new MinPQ<>();
        for(Particle particle: this.particles)
            this.predict(particle, limit);
        this.pq.push(new Event(0, null, null));

        while(!this.pq.isEmpty()) {
            Event e = this.pq.pop();
            if(!e.isValid()) continue;
            Particle a = e.a;
            Particle b = e.b;

            // physical collision, so update positions, and then simulation clock
            for (Particle particle: this.particles)
                particle.move(e.time - this.t);
            this.t = e.time;

            // process event
            if      (a != null && b != null)        a.bounceOff(b);                     // p-p collision
            else if (a != null)                     a.bounceOffVerticalWall();          // vertical collision
            else if (b != null)                     b.bounceOffHorizontalWall();        // horizontal collision
            else                                    reDraw(limit);                      // redraw event

            this.predict(a, limit);
            this.predict(b, limit);
        }
    }

    private static class Event implements Comparable<Event> {
        private final double time;
        private final Particle a, b;
        private final int countA, countB;

        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            this.countA = a == null ? -1 : a.getCount();
            this.countB = b == null ? -1 : b.getCount();
        }

        @Override
        public int compareTo(@NotNull CollisionSystem.Event that) {
            return Double.compare(this.time, that.time);
        }

        public boolean isValid() {
            if(this.a != null && a.getCount() != this.countA) return false;
            return this.b == null || b.getCount() == this.countB;
        }
    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.enableDoubleBuffering();
        int n = 5;
        Particle[] particles = new Particle[n];
        for(int i = 0; i < n; i ++)
            particles[i] = new Particle();

        CollisionSystem sys = new CollisionSystem(particles);
        sys.simulate(10000);
    }
}
