package by.epam.task2.tubes.states;

import by.epam.task2.trains.Train;

import java.util.Optional;


/**
 * This interface is used to make different tasks if a state of tube changes.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Train
 */
public interface TubeState {
    /**
     * This method is responsible for finding an appropriate train in queues
     * (depends on tube state).
     * @return Train
     * */
    Optional<Train> findTrain();
}
