package by.epam.task1.entities;

import java.util.Comparator;

/**
 * Comparator for Sphere class.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * @see Sphere
 */
public enum SphereComparator implements Comparator<Sphere> {
    /**
     * Compares sphere by their name.
     */
    NAME {
        @Override
        public int compare(final Sphere o1, final Sphere o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    },

    /**
     * Compares sphere by their radius.
     */
    RADIUS {
        @Override
        public int compare(final Sphere o1, final Sphere o2) {
            return Double.compare(o1.getRadius(), o2.getRadius());
        }
    },

    /**
     * Compares sphere by their x-axis value.
     */
    X_AXIS {
        @Override
        public int compare(final Sphere o1, final Sphere o2) {
            return Double.compare(
                    o1.getCentrePoint().getX(),
                    o2.getCentrePoint().getX());
        }
    },

    /**
     * Compares sphere by their y-axis value.
     */
    Y_AXIS {
        @Override
        public int compare(final Sphere o1, final Sphere o2) {
            return Double.compare(
                    o1.getCentrePoint().getY(),
                    o2.getCentrePoint().getY());
        }
    },

    /**
     * Compares sphere by their z-axis value.
     */
    Z_AXIS {
        @Override
        public int compare(final Sphere o1, final Sphere o2) {
            return Double.compare(
                    o1.getCentrePoint().getZ(),
                    o2.getCentrePoint().getZ());
        }
    }
}
