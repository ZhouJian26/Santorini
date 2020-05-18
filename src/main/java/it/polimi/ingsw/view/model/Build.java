package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Build extends Action implements RawObj {
    public final String block;
    public final ArrayList<Integer> position;
    public final Boolean status;

    public Build(String block, ArrayList<Integer> position, Boolean status) {
        this.block = block;
        this.position = position;
        this.status = status;
    }

    @Override
    public ArrayList<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Build", "Type Block: " + block, "Position: " + position.toString()));
    }

}