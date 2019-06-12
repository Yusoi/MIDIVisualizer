package image_handlers;

import data_structures.Dimension;
import data_structures.Note;
import data_structures.TrackArray;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class ImageCreator {

    private String songName;
    private ArrayList<TrackArray> trackList;
    private Dimension dim;

    public ImageCreator(String songName, ArrayList<TrackArray> trackList) {
        this.songName = songName;
        this.trackList = new ArrayList<>(trackList);
        dim = ImageDetailsCalculator.calculateDimensions(trackList);
    }

    public BufferedImage generateImage(){

        try {
            File imageFile = File.createTempFile(songName, ".jpg");
        }catch(IOException e){
            e.printStackTrace();
        }

        BufferedImage image = new BufferedImage(dim.getX(),dim.getY(),TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        this.trackList = new ArrayList<>(trackList);

        for(int tick = 0;tick < (dim.getX()/ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK);tick++){
            for(int track = 0;track < trackList.size();track ++){

                ArrayList<Note> chordNotes = trackList.get(track).getNotesInTick(tick);
                if(chordNotes.size() != 0) {
                    int heightPerNote = (dim.getY()/trackList.size()) / chordNotes.size();

                    int count = 0;
                    for (Note n : chordNotes) {

                        //Color c = keyBasedColoring(n);
                        Color c = noteBasedColoring(n);
                        graphics.setPaint(c);
                        graphics.fillRect(tick * ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK,
                                count * heightPerNote + track * dim.getY()/trackList.size(),
                                ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK,
                                heightPerNote);
                        System.out.println("X: " + tick * ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK + " Y: " + count * heightPerNote + " WIDTH: " + ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK + " HEIGHT: " + heightPerNote);
                        count++;
                    }
                }else{
                    Color c = Color.getHSBColor(0f, 0f, 1.0f);
                    graphics.setPaint(c);
                    graphics.fillRect(tick * ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK,
                            dim.getY()/trackList.size() * track,
                            ImageDetailsCalculator.DEFAULT_WIDTH_PER_TICK,
                            dim.getY()/trackList.size());
                }


            }
        }


        return image;
    }

    public Color keyBasedColoring(Note n){
        return Color.getHSBColor(((float) n.getKey() / (float) 255), 1f, 0.8f);
    }

    public Color noteBasedColoring(Note n){
        float hue;
        switch(n.getNote()){
            case "C":
                hue = 0f/255f;
                break;
            case "C#":
                hue = 21.25f/255f;
                break;
            case "D":
                hue = 42.5f/255f;
                break;
            case "D#":
                hue = 63.75f/255f;
                break;
            case "E":
                hue = 85f/255f;
                break;
            case "F":
                hue = 106.25f/255f;
                break;
            case "F#":
                hue = 127.5f/255f;
                break;
            case "G":
                hue = 148.75f/255f;
                break;
            case "G#":
                hue = 170f/255f;
                break;
            case "A":
                hue = 191.25f/255f;
                break;
            case "A#":
                hue = 212.5f/255f;
                break;
            case "B":
                hue = 233.75f/255f;
                break;
            default:
                hue = 0f;
                break;
        }


        return Color.getHSBColor(hue , 1f, 0.8f);
    }

    public void createFile(BufferedImage image){
        File outputfile = new File("image.jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        }catch(IOException e){

        }
    }

}
