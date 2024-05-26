package PEC;

import java.util.PriorityQueue;
import java.util.*; 

public class PEC {
    private PriorityQueue<Event> pec;

    public PEC(){
        pec = new PriorityQueue<Event>();
    }

    public boolean addEvent(Event newEvent){
        return pec.add(newEvent);
    }

    public boolean removeEvent(Event oldEvent){
        return pec.remove(oldEvent);
    }

    public Event getNextEvent(){
        return pec.poll();
    }

    public void clearPEC(){
        pec.clear();
    }

    public Iterator<Event> getIterator(){
        return pec.iterator();
    }
}
