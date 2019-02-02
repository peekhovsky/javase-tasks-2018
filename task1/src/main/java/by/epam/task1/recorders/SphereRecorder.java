package by.epam.task1.recorders;

import by.epam.task1.actions.SphereAction;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

/**
 * This class stores information like volume and surface area of particular
 * sphere (should add this class to a list of sphere subscribers).
 * When some values has been changed in sphere this values will change.
 * This is realisation of pattern Observer (using default Java 7 interface).
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * @see Observer
 * @see Sphere
 */
public class SphereRecorder implements Observer {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(SphereRecorder.class);
    /**
     * Sphere volume.
     */
    private double volume;
    /**
     * Sphere surface area.
     */
    private double surfaceArea;
    /**
     * Declaration of action class to enable methods to calculate volume
     * and surface area.
     */
    private SphereAction sphereAction = new SphereAction();

    /**
     * No args constructor.
     */
    public SphereRecorder() {
    }
    /**
     * This method appeals when some data has been changed in observable class.
     *
     * @param o   observable object
     * @param arg any param when observable object calls method
     *            notifyObservers()
     */
    @Override
    public void update(final Observable o, final Object arg) {
        if (o instanceof Sphere) {
            try {
                volume = sphereAction.calcVolume((Sphere) o);
                surfaceArea = sphereAction.calcSurfaceArea((Sphere) o);
                LOGGER.info("Updating of sphere recorder: " + this);
            } catch (FigureException e) {
                LOGGER.error("Cannot set new values."
                        + " New object values are incorrect, error code: "
                        + e.getErrorCode());
            }
        }
    }
    /**
     * @return current sphere volume
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @return current sphere surface area
     */
    public double getSurfaceArea() {
        return surfaceArea;
    }

    /**
     * @return string description of circle recorder
     */
    @Override
    public String toString() {
        return "volume = " + volume + ", surface area = " + surfaceArea + ". ";
    }
}
