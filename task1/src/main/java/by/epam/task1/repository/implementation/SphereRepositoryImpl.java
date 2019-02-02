package by.epam.task1.repository.implementation;

import by.epam.task1.actions.SphereAction;
import by.epam.task1.entities.Sphere;
import by.epam.task1.repository.SphereRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is an implementation of repository to store pairs.
 * This is a model of real repository. It does not store data anywhere else.
 * Realises singleton pattern.
 *
 * @author Rostislav Pekhovsky
 * @version 0.1
 * @see Sphere
 * @see SphereRepository
 */
@SuppressWarnings("WeakerAccess")
public final class SphereRepositoryImpl implements SphereRepository {

    /**
     * A list with sphere entities.
     */
    private List<Pair> pairs = new ArrayList<>();

    /**
     * Current last id in the list.
     */
    private Integer currentId = 0;

    /**
     * Object that is needed for calculations connected with sphere.
     */
    private SphereAction sphereAction = new SphereAction();

    /**
     * Private no args constructor.
     */
    private SphereRepositoryImpl() {
    }

    /**
     * Singleton holder. It initialises sphere repository only once when
     * is the first appeal to getInstance() method (lazy initialization
     * solution).
     */
    private static SphereRepositoryImpl instance;

    /**
     * This method is to get an instance of that class.
     * Any outer object can get an instance of that class using only this
     * method. If there is no copy of them yet it will create new, otherwise
     * returns old version. There is possible to make only one copy of that
     * class.
     *
     * @return an instance
     */
    public static SphereRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new SphereRepositoryImpl();
        }
        return instance;
    }

    /**
     * This method finds pair by its id.
     *
     * @param id pair id
     * @return present optional if pair is found, otherwise empty optional.
     */
    public Optional<Pair> findPairById(final Integer id) {
        return pairs.stream()
                .filter(sphereRecorder -> sphereRecorder.getId().equals(id))
                .findFirst();
    }

    @Override
    public int add(final Sphere sphere) {
        pairs.add(new Pair(currentId, sphere));
        currentId++;
        return currentId - 1;
    }

    @Override
    public void addAll(final List<Sphere> newSpheres) {
        newSpheres.forEach(this::add);
    }

    @Override
    public void delete(final Integer id) {
        findPairById(id).ifPresent(sphere -> pairs.remove(sphere));
    }

    @Override
    public void deleteAll() {
        pairs.clear();
    }

    @Override
    public void update(final Integer id, final Sphere newSphere) {
        findPairById(id).ifPresent(pair -> {
            pairs.remove(pair);
            Pair newPair = new Pair(pair.getId(), newSphere);
            pairs.add(newPair);
        });
    }

    @Override
    public Optional<Sphere> findById(final Integer id) {
        Optional<Pair> pairOptional = findPairById(id);
        return pairOptional.map(Pair::getSphere);
    }

    @Override
    public List<Sphere> findByName(final String name) {
        return pairs.stream()
                .map(Pair::getSphere)
                .filter(sphere -> sphere.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sphere> findAll() {
        return pairs.stream()
                .map(Pair::getSphere)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sphere> findAllInSphere(final Sphere outerSphere) {
        if (outerSphere == null) {
            return new ArrayList<>();
        }
        return pairs.stream()
                .map(Pair::getSphere)
                .filter(innerSphere ->
                        sphereAction.isSphereInSphere(innerSphere, outerSphere))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sphere> findAllInRange(final double range) {
        return pairs.stream()
                .map(Pair::getSphere)
                .filter(sphere ->
                        sphereAction.isSphereInRange(sphere, range))
                .collect(Collectors.toList());
    }

    @Override
    public void sortByName() {
        pairs.sort(PairComparator.NAME);
    }

    @Override
    public void sortByCentrePointX() {
        pairs.sort(PairComparator.X_AXIS);
    }

    @Override
    public void sortByCentrePointY() {
        pairs.sort(PairComparator.Y_AXIS);
    }

    @Override
    public void sortByCentrePointZ() {
        pairs.sort(PairComparator.Z_AXIS);
    }

    @Override
    public void sortByRadius() {
        pairs.sort(PairComparator.RADIUS);
    }
}


