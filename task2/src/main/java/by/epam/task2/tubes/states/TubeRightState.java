package by.epam.task2.tubes.states;

import by.epam.task2.trains.Train;

import java.util.Optional;
import java.util.Queue;

/**
 * This class implements TubeState interface.
 * In overrides method for right state condition of a tube.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Train
 * @see TubeState
 */
public final class TubeRightState implements TubeState {
    /**
     * This is a right queue from tube system.
     */
    private Queue<Train> trainQueueRight;

    /**
     * Constructor.
     *
     * @param trainQueueRightNew right queue from tube system
     */
    public TubeRightState(final Queue<Train> trainQueueRightNew) {
        this.trainQueueRight = trainQueueRightNew;
    }

    @Override
    public Optional<Train> findTrain() {
        return Optional.ofNullable(trainQueueRight.poll());
    }

    @Override
    public String toString() {
        return "RightState";
    }
}
