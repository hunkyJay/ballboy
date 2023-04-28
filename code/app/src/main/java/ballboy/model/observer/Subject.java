package ballboy.model.observer;

/**
 * The base interface for subjects in Observer design pattern.
 */
public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}
