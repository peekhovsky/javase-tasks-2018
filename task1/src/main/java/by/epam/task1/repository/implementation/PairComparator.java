package by.epam.task1.repository.implementation;

import by.epam.task1.entities.Sphere;
import by.epam.task1.entities.SphereComparator;

import java.util.Comparator;

/**
 * Use this enum to compare Pair-class objects that is used
 * in SphereRepositoryImpl.
 * Almost all comparators use comparators from SphereComparator class.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 */
public enum PairComparator implements Comparator<Pair> {
    /**
     * Compares pairs by their ids.
     * */
    ID {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            return Integer.compare(o1.getId(), o2.getId());
        }
    },

    /**
     * Compares pairs by their sphere name.
     */
    NAME {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            Sphere s1 = o1.getSphere();
            Sphere s2 = o2.getSphere();
            return SphereComparator.NAME.compare(s1, s2);
        }
    },

    /**
     * Compares pairs by their sphere radius.
     */
    RADIUS {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            Sphere s1 = o1.getSphere();
            Sphere s2 = o2.getSphere();
            return SphereComparator.RADIUS.compare(s1, s2);
        }
    },

    /**
     * Compares pairs by their x-axis value.
     */
    X_AXIS {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            Sphere s1 = o1.getSphere();
            Sphere s2 = o2.getSphere();
            return SphereComparator.X_AXIS.compare(s1, s2);
        }
    },

    /**
     * Compares pairs by their y-axis value.
     */
    Y_AXIS {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            Sphere s1 = o1.getSphere();
            Sphere s2 = o2.getSphere();
            return SphereComparator.Y_AXIS.compare(s1, s2);
        }
    },

    /**
     * Compares pairs by their z-axis value.
     */
    Z_AXIS {
        @Override
        public int compare(final Pair o1, final Pair o2) {
            Sphere s1 = o1.getSphere();
            Sphere s2 = o2.getSphere();
            return SphereComparator.Z_AXIS.compare(s1, s2);
        }
    }
}
