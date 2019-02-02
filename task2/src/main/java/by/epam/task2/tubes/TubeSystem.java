package by.epam.task2.tubes;

import by.epam.task2.exceptions.TaskException;
import by.epam.task2.trains.Train;
import by.epam.task2.tubes.states.TubeLeftState;
import by.epam.task2.tubes.states.TubeRightState;
import by.epam.task2.tubes.states.TubeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This class is singleton that manages traffic thought the tubes. It has
 * its own class named Tube that works in own thread.
 * Once you put train to left or right queue (trainQueueLeft, trainQueueRight)
 * tubes try to get it and push it on the railroad.
 * <p>
 * It has restrictions like max number of trains that can go through one
 * tubes in one direction (maxNumOfTrainsOnOneRoad) and max amount of trains
 * in one tubes (maxNumberOfTrainsPastOneDir).
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public final class TubeSystem {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TubeSystem.class);
    /**
     * An instance of this class. It is used to make singleton.
     */
    private static TubeSystem instance;
    /**
     * A lock that is used to lock method getInstance() for thread-safe
     * singleton realization.
     */
    private static Lock lock = new ReentrantLock();
    /**
     * Default value for max number of trains that can go thought one
     * tubes in one direction (maxNumOfTrainsOnOneRoad).
     */
    private static final int MAX_NUM_OF_TRAINS_ON_ONE_ROAD_DEF = 2;
    /**
     * Default value for max amount of trains
     * * in one tubes (maxNumberOfTrainsPastOneDir).
     */
    private static final int MAX_NUM_OF_TRAINS_PAST_ONE_DIRECTION_DEF = 2;
    /**
     * Max number of trains that can go thought one
     * tubes in one direction (maxNumOfTrainsOnOneRoad).
     */
    private int maxNumOfTrainsOnOneRoad;
    /**
     * Max amount of trains in one tubes (maxNumberOfTrainsPastOneDir).
     */
    private int maxNumberOfTrainsPastOneDir;
    /**
     * This value is to close all threads when all trains in gone.
     */
    private final AtomicBoolean isEnabled = new AtomicBoolean(true);
    /**
     * Train queue that are going to go to left direction of tubes.
     */
    private final Queue<Train> trainQueueLeft = new ConcurrentLinkedQueue<>();
    /**
     * Train queue that are going to go to right direction of tubes.
     */
    private final Queue<Train> trainQueueRight = new ConcurrentLinkedQueue<>();
    /**
     * A thread pool executor service for tubes threads.
     */
    private final ExecutorService tubeExecutorService
            = Executors.newFixedThreadPool(2);
    /**
     * Number of trains that are gone through tubes 1.
     */
    private Future<Integer> numberOfTrainsTube1;
    /**
     * Number of trains that are gone through tubes 2.
     */
    private Future<Integer> numberOfTrainsTube2;

    /**
     * @return instance of singleton
     */
    public static TubeSystem getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new TubeSystem();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    /**
     * This method adds train to left queue.
     *
     * @param train train to add to left queue
     */
    public void addTrainToLeftQueue(final Train train) {
        trainQueueLeft.add(train);
        LOGGER.info("Train " + train + " is in left queue");
    }

    /**
     * This method adds train to right queue.
     *
     * @param train train to add to left queue
     */
    public void addTrainToRightQueue(final Train train) {
        trainQueueRight.add(train);
        LOGGER.info("Train " + train + " is in right queue");
    }

    /**
     * This method starts performing of both tubes.
     */
    public void startTubes() {
        final Tube tube1 = new Tube("Tube 1", true);
        final Tube tube2 = new Tube("Tube 2", false);
        numberOfTrainsTube1 = tubeExecutorService.submit(tube1);
        numberOfTrainsTube2 = tubeExecutorService.submit(tube2);
    }

    /**
     * This method stops tubes threads. Threads will not stop until queues have
     * trains or trains are in tubes. Is uses isEnabled variable to note
     * threads that it is time to stop.
     *
     * @throws TaskException if there is an error in computation in future get()
     *                       method.
     */
    public void stop() throws TaskException {
        try {
            while (!trainQueueRight.isEmpty() || !trainQueueLeft.isEmpty()) {

                TimeUnit.SECONDS.sleep(1);
            }
            isEnabled.set(false);
            tubeExecutorService.shutdown();
            while (!tubeExecutorService.isShutdown()) {
                TimeUnit.SECONDS.sleep(1);
            }
            while (!numberOfTrainsTube1.isDone()
                    || !numberOfTrainsTube2.isDone()) {
                TimeUnit.SECONDS.sleep(1);
            }
            LOGGER.info("Number of trains are gone through tube 1: "
                        + numberOfTrainsTube1.get());
            LOGGER.info("Number of trains are gone through tube 2: "
                        + numberOfTrainsTube2.get());

        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!");
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new TaskException(e.getMessage(), e);
        }
    }

    /**
     * @param maxNumOfTrainsOnOneRoadNew max number of trains that can go
     *                                   thought one tubes in one direction
     *                                   (maxNumOfTrainsOnOneRoad) to set
     */
    public void setMaxNumOfTrainsOnOneRoad(
            final int maxNumOfTrainsOnOneRoadNew) {
        this.maxNumOfTrainsOnOneRoad = maxNumOfTrainsOnOneRoadNew;
    }

    /**
     * @param maxNumberOfTrainsPastOneDirNew Max amount of trains in one tubes
     *                                       (maxNumberOfTrainsPastOneDir) to
     *                                       set.
     */
    public void setMaxNumberOfTrainsPastOneDir(
            final int maxNumberOfTrainsPastOneDirNew) {
        this.maxNumberOfTrainsPastOneDir = maxNumberOfTrainsPastOneDirNew;
    }

    /**
     * Private constructor (public constructor is not enabled because this
     * class is singleton.
     */
    private TubeSystem() {
        maxNumOfTrainsOnOneRoad = MAX_NUM_OF_TRAINS_ON_ONE_ROAD_DEF;
        maxNumberOfTrainsPastOneDir = MAX_NUM_OF_TRAINS_PAST_ONE_DIRECTION_DEF;
    }

    /**
     * Inner tubes class that is used to process trains in queue.
     */
    private final class Tube implements Callable<Integer> {
        /**
         * A number of trains that are gone through this tubes.
         */
        private int numberOfTrains;
        /**
         * A name of this tubes.
         */
        private String name;
        /**
         * A left state class of tubes (when direction is left).
         */
        private final TubeState tubeLeftState
                = new TubeLeftState(trainQueueLeft);
        /**
         * A right state class of tubes (when direction is right).
         */
        private final TubeState tubeRightState
                = new TubeRightState(trainQueueRight);
        /**
         * A state of tubes (left or right).
         */
        private TubeState tubeState;
        /**
         * Current number of trains in tubes.
         */
        private Integer trainsInTube = 0;
        /**
         * Queue with future values returned by trains when they arrive.
         */
        private final Queue<Future<String>> futures
                = new ConcurrentLinkedQueue<>();
        /**
         * A thread pool executor service for train threads.
         */
        private final ExecutorService trainExecutorService
                = Executors.newFixedThreadPool(maxNumOfTrainsOnOneRoad);

        /**
         * Private constructor (available in outer class only).
         *
         * @param nameNew     a name of
         * @param isLeftState if it is true, method will set default left state
         *                    for tubes, otherwise right.
         */
        private Tube(final String nameNew, final boolean isLeftState) {
            this.name = nameNew;
            if (isLeftState) {
                tubeState = tubeLeftState;
            } else {
                tubeState = tubeRightState;
            }
        }

        /**
         * This method pushes train to tubes (starts train thread).
         * @return true if train is available
         */
        private boolean pushTrainToTube() {
            boolean res = false;
            final Optional<Train> trainOptional = tubeState.findTrain();
            if (trainOptional.isPresent()) {
                final Train train = trainOptional.get();
                futures.add(trainExecutorService.submit(train));
                trainsInTube++;
                numberOfTrains++;
                LOGGER.info("Train " + train + " is in the " + this.name);
                res = true;
            }
            return res;
        }

        /**
         * This method c train to tubes (starts train thread).
         */
        private void changeDirection() {
            if (tubeState.getClass().equals(TubeLeftState.class)) {
                tubeState = tubeRightState;
            } else {
                tubeState = tubeLeftState;
            }
        }

        /**
         * This method checks if there is max number of trains are gone through
         * tubes in one direction in sequence.
         * If not, it calls pushTrainToTube() to push train.
         * If there is no train in tubes, it changes direction of tubes.
         */
        private void checkTrains() {
            if (trainsInTube < maxNumberOfTrainsPastOneDir) {
                if (pushTrainToTube()) {
                    checkTrains();
                }
            }
            if (trainsInTube == 0) {
                changeDirection();
            }
        }

        /**
         * This method checks exits of tubes. Once train is arrived, it displays
         * a message about train arriving (and tubes state)
         *
         * @return optional of string (present if train is arrived, otherwise
         * empty
         * @throws TaskException if the computation threw an exception in
         *                       get() method
         */
        private Optional<String> checkExit() throws TaskException {
            String res = null;
            try {
                final Future<String> future = futures.peek();
                if (future != null) {
                    futures.poll();
                    LOGGER.info(this.name + " - trains: " + trainsInTube);
                    LOGGER.info(this.name + " - state:  " + tubeState);
                    trainsInTube--;
                    res = future.get();
                }
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted exception!");
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw new TaskException(e.getMessage(), e);
            }
            return Optional.ofNullable(res);
        }

        /**
         * This method overrides method that will execute in its own thread.
         * It checks isEnabled var and number of trains in the tubes.
         * If there is legal number of trains in the tubes, it calls
         * checkTrains() method to check trains in queues, and push trains to
         * tubes if all conditions are good.
         * If isEnabled var is false, it will service all trains that are left
         * and shutdown.
         *
         * @return a number of trains past through the tubes
         * @throws TaskException if there is an error in checkExit() method.
         */
        @Override
        public Integer call() throws TaskException {
            LOGGER.debug(this.name + " has been started to work.");
            while (isEnabled.get() || trainsInTube > 0) {
                if (trainsInTube < maxNumOfTrainsOnOneRoad) {
                    checkTrains();
                }
                checkExit().ifPresent(messageFromTrain
                        -> LOGGER.info(messageFromTrain + " is arrived."));
            }
            trainExecutorService.shutdown();
            return numberOfTrains;
        }
    }
}
