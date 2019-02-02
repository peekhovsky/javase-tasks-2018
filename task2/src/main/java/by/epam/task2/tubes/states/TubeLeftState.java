package by.epam.task2.tubes.states;

import by.epam.task2.trains.Train;

import java.util.Optional;
import java.util.Queue;

/**
 * This class implements TubeState interface.
 * In overrides method for left state condition of a tube.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Train
 * @see TubeState
 */
public final class TubeLeftState implements TubeState {
    /**
     * This is a left queue from tube system.
     */
    private Queue<Train> trainQueueLeft;

    /**
     * Constructor.
     *
     * @param trainQueueLeftNew left queue from tube system
     */
    public TubeLeftState(final Queue<Train> trainQueueLeftNew) {
        this.trainQueueLeft = trainQueueLeftNew;
    }

    @Override
    public Optional<Train> findTrain() {
        return Optional.ofNullable(trainQueueLeft.poll());
    }

    @Override
    public String toString() {
        return "LeftState";
    }
}
