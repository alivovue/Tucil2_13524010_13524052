package frontend.io;

public class Camera {
    private double yaw;
    private double pitch;
    private double zoom;

    public Camera() {
        this.yaw = 0.0;
        this.pitch = 0.0;
        this.zoom = 1.0;
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getZoom() {
        return zoom;
    }

    public void rotate(double yaw, double pitch) {
        this.yaw += yaw;
        this.pitch += pitch;
    }

    public void zoom(double zoom) {
        this.zoom += zoom;
    }

    public void reset() {
        this.yaw = 0.0;
        this.pitch = 0.0;
        this.zoom = 1.0;
    }
}
