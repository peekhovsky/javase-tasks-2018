package by.epam.task1.actions;

import by.epam.task1.entities.Plane;
import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import by.epam.task1.validators.FigureValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Action class for Sphere entity. Calculates surface area, volume, ect.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * @see Sphere
 * @see Plane
 */
@SuppressWarnings("WeakerAccess")
public class SphereAction {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(SphereAction.class);

    /**
     * This method is responsible for calculating surface area.
     * Formula:
     * <p>
     * S = 4*PI*R^2
     * </p>
     * PI - pi number, R - sphere radius.
     *
     * @param sphere a sphere
     * @return surface area of the sphere
     * @throws FigureException if data is not valid
     */
    public double calcSurfaceArea(final Sphere sphere)
            throws FigureException {
        final int formulaArg = 4;
        if (sphere.getRadius() < 0) {
            throw new FigureException("Radius cannot be lower than 0");
        }
        double res = formulaArg * Math.PI * Math.pow(sphere.getRadius(), 2);
        LOGGER.debug("Result of calcSurfaceArea(): " + res);
        if (!FigureValidator.isDoubleValid(res)) {
            throw new FigureException(
                    FigureException.ErrorCodes.NUMBER_IS_TOO_BIG);
        }
        return res;
    }

    /**
     * That method calculates sphere volume using the formula below.
     * <p>
     * V = (4/3)*PI*R^3
     * </p>
     * PI - pi number, R - sphere radius.
     *
     * @param sphere a sphere
     * @return a hash code of the plane
     * @throws FigureException if data is not valid
     */
    public double calcVolume(final Sphere sphere)
            throws FigureException {
        if (!FigureValidator.isSphereValid(sphere)) {
            throw new FigureException("Figure values is not valid.");
        }

        final double formulaArgument = 4d / 3d;
        final int cubePower = 3;
        double res = formulaArgument * Math.PI
                * Math.pow(sphere.getRadius(), cubePower);
        LOGGER.debug("Result of calcVolume(): " + res);
        if (!FigureValidator.isDoubleValid(res)) {
            throw new FigureException(
                    FigureException.ErrorCodes.NUMBER_IS_TOO_BIG);
        }

        return res;
    }

    /**
     * This method calculates volumes ratio of two parts of sphere divided
     * by plane.
     * Segment volume formula:
     * <p>
     * V = PI*H*(R - (1/3)*H)
     * </p>
     *
     * @param sphere a sphere
     * @param plane  a plane that divides sphere
     * @return ratio if thr plane crosses the sphere, otherwise 0
     * @throws FigureException if a plane does not cross a sphere
     */
    public double calcRatioOfVolumes(final Sphere sphere,
                                     final Plane plane)
            throws FigureException {
        double dis = calcDistance(sphere.getCentrePoint(), plane);

        if (!FigureValidator.isSphereValid(sphere)
                || FigureValidator.isPlaneValid(plane)) {
            throw new FigureException(FigureException
                    .ErrorCodes.FIGURE_IS_NOT_VALID);
        }

        if (dis >= sphere.getRadius()) {
            LOGGER.warn("The plane does not cross the sphere.");
            throw new FigureException("The plane does not cross the sphere.");
        }

        final double formulaArg = 1d / 3d;
        double h = sphere.getRadius() - dis;
        double seg1Volume = Math.PI * h * (sphere.getRadius() - formulaArg * h);
        double res = Math.abs(seg1Volume / (calcVolume(sphere) - seg1Volume));
        LOGGER.debug("Result of calcRatioOfVolumes(): " + res);
        if (!FigureValidator.isDoubleValid(res)) {
            throw new FigureException(
                    FigureException.ErrorCodes.NUMBER_IS_TOO_BIG);
        }
        return res;
    }

    /**
     * This method determines that sphere is tangent to plane.
     * <p>
     * A sphere is tangent to a plane when the distance between the centre
     * of the sphere and the plane equals to the radius of the sphere.
     *
     * @param sphere a sphere
     * @param plane  a plane
     * @return true if the sphere is tangent to the plane, otherwise false.
     * @throws FigureException if data is not valid
     */
    public boolean isSphereTangentToPlane(final Sphere sphere,
                                          final Plane plane)
            throws FigureException {
        if (!FigureValidator.isSphereValid(sphere)) {
            throw new FigureException(
                    FigureException.ErrorCodes.SPHERE_IS_NOT_VALID);
        }
        if (FigureValidator.isPlaneValid(plane)) {
            throw new FigureException(
                    FigureException.ErrorCodes.PLANE_IS_NOT_VALID);
        }
        boolean res = calcDistance(sphere.getCentrePoint(), plane)
                == sphere.getRadius();
        LOGGER.debug("Result of isSphereTangentToPlane(): " + res);
        return res;
    }

    /**
     * This method determines that an object is the sphere.
     *
     * @param o an object
     * @return true if an object is the sphere, otherwise false.
     */
    public boolean isSphere(final Object o) {
        return (o instanceof Sphere)
                && (FigureValidator.isSphereValid((Sphere) o));
    }

    /**
     * This method calculates distance between a plane and a point using
     * the formula below.
     * <p>
     * d = (Ax + By + Cz + D) / sqrt(A^2 + B^2 + C^2)
     * <p>
     * A, B, C, D - plane coefficients.
     * x, y, z - point axis.
     *
     * @param point a point
     * @param plane a plane
     * @return a distance between the plane and the point
     * @throws FigureException if double is too big
     */
    private double calcDistance(final Point point, final Plane plane)
            throws FigureException {
        final int squarePower = 2;
        double res =
                (
                        Math.abs(
                                plane.getA() * point.getX()
                                        + plane.getB() * point.getY()
                                        + plane.getC() * point.getZ()
                                        + plane.getD()
                        )
                )
                        /

                        Math.sqrt(
                                Math.pow(plane.getA(), squarePower)
                                        + Math.pow(plane.getB(), squarePower)
                                        + Math.pow(plane.getC(), squarePower)
                        );

        LOGGER.debug("Result of calcDistance(): " + res);
        if (!FigureValidator.isDoubleValid(res)) {
            throw new FigureException(
                    FigureException.ErrorCodes.NUMBER_IS_TOO_BIG);
        }
        return res;
    }

    /**
     * This method determines if one sphere is located inside another.
     * <p>
     * One sphere is in another if the distance between centres of spheres
     * and inner sphere radius smaller than outer sphere radius.
     *
     * @param outerSphere outer sphere
     * @param innerSphere inner sphere
     * @return true if inner sphere is in outer sphere, otherwise false
     * (returns false even if numbers are too big)
     */
    public boolean isSphereInSphere(final Sphere innerSphere,
                                    final Sphere outerSphere) {
        double distance;
        try {
            distance = calcDistance(
                    innerSphere.getCentrePoint(), outerSphere.getCentrePoint());
        } catch (FigureException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        boolean res
                = distance + innerSphere.getRadius() <= outerSphere.getRadius();
        LOGGER.debug("Inner sphere: " + innerSphere);
        LOGGER.debug("Outer sphere: " + outerSphere);
        LOGGER.debug("Result of isSphereInSphere(): " + res);
        return res;
    }

    /**
     * That method determines if a sphere is inside a range.
     * Range area is a sphere where the center is the origin of axes and a
     * radius is the range.
     *
     * @param sphere sphere to check
     * @param range  range
     * @return true if sphere is in range area, otherwise false
     * (returns false even if numbers are too big)
     */
    public boolean isSphereInRange(final Sphere sphere, final double range) {
        Sphere outerSphere =
                new Sphere(new Point(0, 0, 0), range);
        return isSphereInSphere(sphere, outerSphere);
    }

    /**
     * Calculates distance between two points using the formula below.
     * <p>
     * d = sqrt((xB - xA)^2 + (yB - yA)^2 + (zB - zA)^2)
     * <p>
     * xA, yA, zA - first point axes
     * xB, yB, zB - second point axes
     *
     * @param pointA first point
     * @param pointB second point
     * @return a distance between points
     * @throws FigureException if result is too big
     */
    private double calcDistance(final Point pointA, final Point pointB)
            throws FigureException {
        final int squarePower = 2;
        double res = Math.sqrt(
                Math.pow(pointB.getX() - pointA.getX(), squarePower)
                        + Math.pow(pointB.getY() - pointA.getY(), squarePower)
                        + Math.pow(pointB.getZ() - pointA.getZ(), squarePower));
        LOGGER.debug("Result of calcDistance(): " + res);
        if (!FigureValidator.isDoubleValid(res)) {
            throw new FigureException(
                    FigureException.ErrorCodes.NUMBER_IS_TOO_BIG);
        }
        return res;
    }
}
