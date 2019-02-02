package by.epam.task1.repository.implementation;

import by.epam.task1.entities.Sphere;
import by.epam.task1.recorders.SphereRecorder;

/**
 * This class stores pairs that contain
 * Sphere entity and Sphere recorder classes.
 * Outer class use this class in List to store both objects.
 */
public class Pair {
    /**
     * Pair identification number.
     * */
    private int id;
    /**
     * Sphere.
     */
    private Sphere sphere;
    /**
     * Sphere recorder.
     */
    private SphereRecorder sphereRecorder;

    /**
     * Constructor.
     * Initialises new sphere recorder for particular sphere.
     *
     * @param newId id
     * @param newSphere sphere
     */
    Pair(final int newId, final Sphere newSphere) {
        this.id = newId;
        this.sphere = newSphere;
        this.sphereRecorder = new SphereRecorder();
        this.sphere.addObserver(sphereRecorder);
    }

    /**
     * @return id
     * */
    public Integer getId() {
        return id;
    }

    /**
     * @param newId id to set
     * */
    public void setId(final int newId) {
        this.id = newId;
    }

    /**
     * @return sphere
     */
    public Sphere getSphere() {
        return sphere;
    }

    /**
     * @param newSphere sphere to set
     */
    public void setSphere(final Sphere newSphere) {
        this.sphere = newSphere;
    }

    /**
     * @return sphere recorder
     */
    public SphereRecorder getSphereRecorder() {
        return sphereRecorder;
    }

    /**
     * @param newSphereRecorder sphere recorder to set
     */
    public void setSphereRecorder(final SphereRecorder newSphereRecorder) {
        this.sphereRecorder = newSphereRecorder;
    }
}
