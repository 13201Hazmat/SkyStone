package org.firstinspires.ftc.teamcode.autoRes.commands;

import java.util.ArrayDeque;

public class CommandList implements ICommand {
    /**
     * Fields
     */
    // Commands will be implemented as a queue
    public ArrayDeque<ICommand> commands;

    /**
     * @param commands a queue implementation of an ArrayDeque filled with commands
     *                 (first in, first out)
     */
    public CommandList(ArrayDeque<ICommand> commands) {
        this.commands = commands;
    }

    /**
     * This method should be called after initilization and only once to start the
     * commands
     */
    public void run() {
        if (commands.peek().isDone()) {
            commands.poll();
            if (!commands.isEmpty()) {
                commands.peek().run();
            }
        }
    }

    /**
     * Returns if all of the commands are done
     * 
     * @return returns true if all of the commands all done or false if not
     */

    public boolean isDone() {
        boolean isDone = commands.isEmpty();
        if (isDone) {
            run();
        }
        return isDone;
    }
}
