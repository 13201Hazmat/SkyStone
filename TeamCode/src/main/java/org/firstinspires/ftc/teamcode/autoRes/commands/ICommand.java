package org.firstinspires.ftc.teamcode.autoRes.commands;

public interface ICommand {
    /**
     * Returns if the command has been run or not
     * 
     * @return will return true if the command has been run, false in not
     */
    public boolean hasRun();

    /**
     * Will start running the command, should only be run once after initilazation
     */
    public void run();

    /**
     * Returns if the has finished within its error
     * 
     * @return returns true if the command is done or false if not
     */
    public boolean isDone();
}