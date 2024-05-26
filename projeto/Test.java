public class Test implements Comparable<Test> {
    private int time;
    private String description;

    public Test(int time, String description) {
        this.time = time;
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    // The compareTo method determines the natural order of Event objects
    @Override
    public int compareTo(Test other) {
        // Compare the 'time' of this event with the 'time' of the other event
        return Integer.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", description='" + description + '\'' +
                '}';
    }
}
