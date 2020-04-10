package com.blaydens;

import org.rspeer.runetek.api.commons.predicate.Predicates;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Task {

    public static enum ACTIONS {
        INTERACT,
        CLICK,
        WALK,
        USE_ON,
        TALK,
        TYPE
    }

    public static final Map<ACTIONS, String> ACTION_NAMES = new HashMap<ACTIONS, String>(){{
        put(ACTIONS.TALK, "Talk-to");
        put(ACTIONS.USE_ON, "Use");
    }};

    private String name;
    private ACTIONS action;
    private String actionParam;
    private Focus focus;
    private Predicate condition;
    private int performedCount;

    public Task(Task task){
        this(task.getName(), task.getAction(), task.getActionParam(), task.getFocus(), task.getCondition());
    }

    public Task(String name, ACTIONS action, Focus focus, Predicate condition){
        this(name, action, "", focus, condition);
    }

    public Task(String name, ACTIONS action, String actionParam, Focus focus, Predicate condition){
        this.name = name;
        this.action = action;
        this.actionParam = actionParam;
        this.focus = focus;
        this.condition = condition;
    }

    public static Task getTaskByName(String taskName){
        Task foundTask = null;
        for(Task task : Data.TASKS){
            if(task.getName().equals(taskName)){
                foundTask = task;
                break;
            }
        }
        return foundTask;
    }

    public static int getPerformedCount(String taskName){
        return getTaskByName(taskName).getPerformedCount();
    }

    public boolean shouldPerform(){
        return this.condition.test(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ACTIONS getAction() {
        return action;
    }

    public void setAction(ACTIONS action) {
        this.action = action;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public Focus getFocus() {
        return focus;
    }

    public void setFocus(Focus focus) {
        this.focus = focus;
    }

    public Predicate getCondition() {
        return condition;
    }

    public void setCondition(Predicate condition) {
        this.condition = condition;
    }

    public int getPerformedCount() {
        return performedCount;
    }

    public void incrementPerformedCount() {
        this.performedCount++;
    }

}
