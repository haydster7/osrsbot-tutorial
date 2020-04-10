package com.blaydens;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.input.Keyboard;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;

import java.util.HashMap;
import java.util.Map;

import static org.rspeer.runetek.api.commons.Time.sleep;

public class Performer {
    public static void perform (Task task){
        task.incrementPerformedCount();
        switch(task.getAction()){
            case INTERACT:
                interact (task);
                break;
            case TALK:
                talk (task);
                break;
            case WALK:
                walk (task);
                break;
            case TYPE:
                type (task);
                break;
            case CLICK:
                click (task);
                break;
            default:
                CLog.log("Action (" + task.getAction().name() + ") not implemented");
                break;
        }
    }

    private static void walk (Task task){
        switch(task.getFocus().getType()){
            case HINT:
                walk((Position)task.getFocus().getFocusObject());
                break;
            default:
                walk(task.getFocus().getPosition());
        }
    }

    private static void walk (Position position){
        Movement.walkTo(position);
    }

    private static void talk (Task task){
        Task newTask = new Task(task);
        newTask.setActionParam("Talk-to");
        interact(newTask);
    }

    private static void interact (Task task){
        Object focusObject = task.getFocus().getFocusObject();
        String actionName = Task.ACTION_NAMES.get(task.getAction());
        switch(task.getFocus().getType()){
            case HINT:
                String hintTargetClassName = focusObject.getClass().getSimpleName().toUpperCase();
                Task changeTask = new Task(task);
                Focus.TYPES changeType;
                String changeID;
                if(hintTargetClassName.equals("SCENEOBJECT")){
                    changeType = Focus.TYPES.SCENE_OBJECT;
                    changeID = String.valueOf(((SceneObject) focusObject).getId());
                } else if (hintTargetClassName.equals("NPC")){
                    changeType = Focus.TYPES.valueOf(focusObject.getClass().getSimpleName().toUpperCase());
                    changeID = String.valueOf(((Npc) focusObject).getId());
                } else {
                    changeType = Focus.TYPES.SCENE_OBJECT;
                    changeID = "";
                }
                changeTask.getFocus().setAttributes(
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.ID, changeID);
                        }}
                );
                changeTask.getFocus().setType(changeType);
                interact(changeTask);
            case NPC:
                if(Movement.isInteractable(((Npc) focusObject).getPosition())){
                    ((Npc) focusObject).interact(actionName);
                } else {
                    if(task.getFocus().getPosition() != null){
                        walk(task);
                    } else {
                        CLog.log("Cannot interact with entity, and no position provided in data. Bot is stuck");
                    }
                }
                break;
            case SCENE_OBJECT:
                if(Movement.isInteractable(((SceneObject) focusObject).getPosition())){
                    ((SceneObject) focusObject).interact(actionName);
                } else {
                    if(task.getFocus().getPosition() != null){
                        walk(task);
                    } else {
                        CLog.log("Cannot interact with entity, and no position provided in data. Bot is stuck");
                    }
                }
                break;
            case INTERFACE:
                ((InterfaceComponent) focusObject).interact(Integer.parseInt(task.getActionParam()));
                break;
            default:
                CLog.log("Interaction with entity type (" + task.getFocus().getType().name() + ") not implemented");
        }
    }

    private static void type (Task task){
        //Click on interface element
        click(task);

        sleep(200);

        //Type specified characters
        Keyboard.sendText(task.getActionParam());

        sleep(300);

        //Send enter key
        Keyboard.pressEnter();

        sleep(3000);
        if(task.getName().equals("Type in name")){
            click(Interfaces.getComponent(558, 18));
        }
    }

    private static void click (Task task){
        InterfaceComponent ic = (InterfaceComponent)task.getFocus().getFocusObject();
        click(ic);
    }

    private static void click (InterfaceComponent ic){
        ic.click();
    }
}
