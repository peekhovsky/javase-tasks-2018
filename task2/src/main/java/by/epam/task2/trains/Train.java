package by.epam.task2.trains;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * This is an entity class that describes train.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@SuppressWarnings("WeakerAccess")
public class Train implements Callable<String> {
    /**
     * Possible train directions.
     */
    public enum Directions {
        /***
         * Left direction.
         */
        LEFT,
        /***
         * Right direction.
         */
        RIGHT
    }


    /**
     * Default sleep time.
     */
    private static final int SLEEP_TIME_DEF = 4;
    /**
     * Name.
     */
    private String name;

    /**
     * Leaving time.
     */
    private int leavingTimeInSeconds;

    /**
     * Sleep time in tube.
     */
    private int sleepInSeconds = SLEEP_TIME_DEF;

    /**
     * Train current direction.
     */
    private Directions direction;

    /**
     * No args constructor.
     */
    public Train() {
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param nameNew to set
     */
    public void setName(final String nameNew) {
        this.name = nameNew;
    }

    /**
     * @return leaving time
     */
    public int getLeavingTimeInSeconds() {
        return leavingTimeInSeconds;
    }

    /**
     * @param leavingTimeInSecondsNew leaving time to set
     */
    public void setLeavingTimeInSeconds(final int leavingTimeInSecondsNew) {
        this.leavingTimeInSeconds = leavingTimeInSecondsNew;
    }

    /**
     * @return sleep time
     */
    public int getSleepInSeconds() {
        return sleepInSeconds;
    }

    /**
     * @param sleepInSecondsNew sleep time
     */
    public void setSleepInSeconds(final int sleepInSecondsNew) {
        this.sleepInSeconds = sleepInSecondsNew;
    }

    /**
     * @return direction
     */
    public Directions getDirection() {
        return direction;
    }

    /**
     * @param directionNew direction to set
     */
    public void setDirection(final Directions directionNew) {
        this.direction = directionNew;
    }

    /**
     * This method works in its own thread. In simulates a time train spend on
     * road in tube.
     *
     * @return a name of this train.
     */
    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(sleepInSeconds);
        return this.name;
    }

    /**
     * @return name of the train
     */
    @Override
    public String toString() {
        final String res;
        if (name == null) {
            res = "No name";
        } else {
            res = name;
        }
        return res;
    }
}
