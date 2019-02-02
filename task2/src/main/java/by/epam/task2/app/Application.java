package by.epam.task2.app;

import by.epam.task2.exceptions.TaskException;
import by.epam.task2.readers.Reader;
import by.epam.task2.trains.Train;
import by.epam.task2.tubes.TubeSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Application class is responsible for performing of full program (in console).
 * A start point of app is here.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class Application {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TubeSystem.class);

    /**
     * Max count of trains on one road.
     */
    private static final int MAX_COUNT_OF_TRAINS_ON_ONE_ROAD = 4;

    /**
     * Max count of trains past one dir.
     */
    private static final int MAX_NUMBER_OF_TRAINS_PAST_ONE_DIR = 4;

    /**
     * Private constructor.
     */
    private Application() {
    }

    /**
     * A start point of the app.
     * @param args program arguments
     */
    public static void main(final String[] args) {

        final long startTime = System.currentTimeMillis();

        final Reader reader = new Reader("src/main/resources/data.txt");
        List<Train> trains;
        try {
            trains = reader.readTrains();
        } catch (TaskException e) {
            LOGGER.fatal(e.getMessage());
            return;
        }
        trains.sort(Comparator.comparingInt(Train::getLeavingTimeInSeconds));

        //set up tube system
        final TubeSystem tubeSystem = TubeSystem.getInstance();
        tubeSystem.setMaxNumberOfTrainsPastOneDir(
                MAX_NUMBER_OF_TRAINS_PAST_ONE_DIR);
        tubeSystem.setMaxNumOfTrainsOnOneRoad(
                MAX_COUNT_OF_TRAINS_ON_ONE_ROAD);
        tubeSystem.startTubes();

        for (final Train train : trains) {
            final int time = train.getLeavingTimeInSeconds();
            while (true) {
                final long timeSeconds
                        = TimeUnit.MILLISECONDS.toSeconds(
                        System.currentTimeMillis() - startTime);
                if (timeSeconds > time) {
                    if (train.getDirection().equals(Train.Directions.LEFT)) {
                        tubeSystem.addTrainToLeftQueue(train);
                    } else {
                        tubeSystem.addTrainToRightQueue(train);
                    }
                    break;
                }
            }
        }

        LOGGER.info("Main thread is finished.");
        try {
            tubeSystem.stop();
        } catch (TaskException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
