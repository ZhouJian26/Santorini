package it.polimi.ingsw.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Build extends Action implements RawObj {
    private final String block;
    private final List<Integer> position;
    private final Boolean status;

    public Build(String block, List<Integer> position, Boolean status) {
        this.block = block;
        this.position = position;
        this.status = status;
    }

    @Override
    public List<String> getRawData() {
        return new ArrayList<>(Arrays.asList("Build", "Type Block: " + block,
                "Position: [" + (position.get(0) * 5 + position.get(1)) + "]"));
    }

    public String getBlock() {
        return block;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Integer> getPosition() {
        return position.stream().map(e -> e).collect(Collectors.toList());
    }

}