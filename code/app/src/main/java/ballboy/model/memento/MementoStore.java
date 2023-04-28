package ballboy.model.memento;

/**
 * This a caretaker class used to store memento.
 */
public class MementoStore {
    private GameMemento memento;

    public void setMemento(GameMemento memento){
        this.memento = memento;
    }
    public GameMemento getMemento(){
        return memento;
    }
}
