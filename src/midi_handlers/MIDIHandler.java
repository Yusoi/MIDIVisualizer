package midi_handlers;

import data_structures.Note;
import data_structures.TrackArray;
import image_handlers.ImageCreator;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

public class MIDIHandler {

    private Sequence sequence;
    private ArrayList<TrackArray> trackList = new ArrayList<>();
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public MIDIHandler(String filePath){
        try {
            sequence = MidiSystem.getSequence(new File(filePath));
        }catch(InvalidMidiDataException | IOException e){
            e.printStackTrace();
        }
    }

    public void midiParser(){
        Track[] tracks = sequence.getTracks();

        int trackNumber = 0;
        for(Track track : tracks){

            trackNumber++;
            ArrayList<Note> trackNotes = new ArrayList<>();
            HashMap<Integer, Note> currentNotes = new HashMap<>();
            long maxTick = 0;
            int maxNotesInChord = 0;

            for(int i = 0; i < track.size() ; i++){
                MidiEvent event = track.get(i);
                long tick = event.getTick();
                MidiMessage message = event.getMessage();
                if(message instanceof ShortMessage){
                    ShortMessage sm = (ShortMessage) message;
                    int key = sm.getData1();
                    int octave = (key / 12) - 1;
                    int note = key % 12;
                    int velocity = sm.getData2();
                    String noteName = NOTE_NAMES[note];

                    if(sm.getCommand() == NOTE_ON){

                        //Note Starting
                        if(velocity != 0) {

                            //Inserts note into currentNotes
                            currentNotes.put(key,new Note(tick,noteName,key));
                            int currentNotesSize = currentNotes.size();
                            if(maxNotesInChord < currentNotesSize){
                                maxNotesInChord = currentNotesSize;
                            }

                        //Note Ending
                        } else {

                            //Receives correspondent note from currentNotes and removes it
                            Note currentNote = currentNotes.get(key);
                            currentNotes.remove(key);
                            currentNote.setEndTick(tick);
                            trackNotes.add(currentNote);

                            //Updates maxTick
                            if(maxTick < tick){
                                maxTick = tick;
                            }
                        }
                    }

                    //Note Ending
                    if(sm.getCommand() == NOTE_OFF){

                        //Receives correspondent note from currentNotes and removes it if not is off
                        Note currentNote = currentNotes.get(key);
                        currentNotes.remove(key);
                        currentNote.setEndTick(tick);
                        trackNotes.add(currentNote);

                        //Updates maxTick
                        if(maxTick < tick){
                            maxTick = tick;
                        }
                    }
                }
            }

            trackNotes.sort(new BeginningTickComparator());
            TrackArray trackArray = new TrackArray(trackNotes,maxTick,maxNotesInChord);
            trackList.add(trackArray);
        }
    }

    class BeginningTickComparator implements Comparator<Note>{
        @Override
        public int compare(Note a, Note b){
            if(a.getBeginTick() < b.getBeginTick()){
                return -1;
            } else if(a.getBeginTick() > b.getBeginTick()){
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void printTracks(){
        int i = 1;
        for(TrackArray trackArray : trackList){
            System.out.println("[Track "+i+"] Ticks:("+trackArray.getMaxTick()+") Max Notes in a Chord:("+trackArray.getMaxNotesInChord()+")");
            ArrayList<Note> track = trackArray.getTrack();
            for(Note n : track){
                System.out.println("\t"+n.toString());
            }
            i++;
        }
    }

    public static void main(String[] args){
        MIDIHandler midiHandler = new MIDIHandler("D:\\Desktop\\Música Nova.mid");
        midiHandler.midiParser();
        midiHandler.printTracks();
        ImageCreator ic = new ImageCreator("Cenas",midiHandler.trackList);
        ic.createFile(ic.generateImage(false));
        System.out.println("Finished");
    }

}
