package data_structures;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TrackArray {
    private ArrayList<Note> track;
    private long maxTick;
    private int maxNotesInChord;

    public TrackArray(ArrayList<Note> track, long maxTick, int maxNotesInChord){
        this.track = new ArrayList<>(track);
        this.maxTick = maxTick;
        this.maxNotesInChord = maxNotesInChord;
    }

    public ArrayList<Note> getTrack() {
        return track;
    }

    public long getMaxTick() {
        return maxTick;
    }

    public int getMaxNotesInChord() {
        return maxNotesInChord;
    }

    public ArrayList<Note> getNotesInTick(long tick){
        ArrayList<Note> result = new ArrayList<Note>();

        for(Note note : track){
            if(note.getBeginTick() <= tick && note.getEndTick() >= tick){
                result.add(note);
            }
        }

        return result;
    }
}
