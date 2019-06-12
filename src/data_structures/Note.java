package data_structures;

public class Note {
    private long beginTick;
    private long endTick;
    private String note;
    private int key;

    public Note(long beginTick, String note, int key){
        this.beginTick = beginTick;
        this.note = note;
        this.key = key;
    }

    public long getBeginTick() {
        return beginTick;
    }

    public void setBeginTick(long beginTick) {
        this.beginTick = beginTick;
    }

    public long getEndTick() {
        return endTick;
    }

    public void setEndTick(long endTick) {
        this.endTick = endTick;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "["+beginTick+","+endTick+"] -> Key: "+key;
    }
}
