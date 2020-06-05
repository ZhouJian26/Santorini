package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Build extends Action implements RawObj {
    public final String block;
    public final List<Integer> position;
    public final Boolean status;
    public final String god;

    public Build(String block, List<Integer> position, Boolean status, String god) {
        this.block = block;
        this.position = position;
        this.status = status;
        this.god = god;
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList(god, "Build", "Type Block: " + block,
                "Position: #" + (position.get(0) * 5 + position.get(1))));
    }

}