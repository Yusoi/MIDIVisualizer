package image_handlers;

import data_structures.Dimension;
import data_structures.TrackArray;

import java.util.ArrayList;

public class ImageDetailsCalculator {

    public static final int DEFAULT_BAR_HEIGHT = 500;
    public static final int DEFAULT_WIDTH_PER_TICK = 1;

    public static Dimension calculateDimensions(ArrayList<TrackArray> trackList){
        int x = 0;
        int y = 0;

        int maxChordsArray[] = new int[trackList.size()];

        int i = 0;
        int maxChord = 0;
        for(TrackArray trackArray : trackList) {

            int maxNotesInChord = trackArray.getMaxNotesInChord();
            maxChordsArray[i] = maxNotesInChord;

            //x calculation
            long maxTick = trackArray.getMaxTick();
            if (maxTick > x) {
                x = (int) maxTick;
            }

            i++;
        }

        //y calculation
        y = MathWrapper.leastCommonMultiple(maxChordsArray);

        System.out.println("Dimensions : "+x+"x"+y);

        return new Dimension(x*DEFAULT_WIDTH_PER_TICK,y*DEFAULT_BAR_HEIGHT*trackList.size());
    }
}
