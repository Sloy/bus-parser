package com.sloydev.busparser.core.command;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.DataSource;

public class MergeCommand {

    private final DataSource input1;
    private final DataSource input2;
    private final DataOutput output;

    public MergeCommand(DataSource input1, DataSource input2, DataOutput output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public void run() {
        //TODO
    }
}
