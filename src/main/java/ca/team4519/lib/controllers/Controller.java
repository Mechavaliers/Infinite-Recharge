package ca.team4519.lib.controllers;

public abstract class Controller {
    protected boolean isEnabled = false;
    public abstract void reset();
    public abstract boolean onTarget();
}