package com.blaydens;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Tab;
import org.rspeer.runetek.api.scene.HintArrow;
import org.rspeer.runetek.api.scene.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {

    public static final ArrayList<Task> TASKS = new ArrayList<Task>(){{
        //Example task to add (should be in the order they are to be performed)
        //May be out of order, but must be in order of priority, last overrides first if conditions met
        /*
        add(new Task(
                "Type in name",                 FRIENDLY TASK NAME
                Task.ACTIONS.TYPE,              ACTION TYPE
                Util.generateRandomString(11),  [ACTION PARAM]
                new Focus(                      ACTION FOCUS ENTITY
                        Focus.TYPES.INTERFACE,  TYPE OF ENTITY
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.NAME, "Gielinor Guide");
                        }}                      ATTRIBUTES OF ENTITY
                ),
                o -> {
                    return true;
                }                               CONDITION TO DETERMINE IF ACTION SHOULD BE PERFORMED CURRENTLY
                                                ALL ACTIONS AFTER THIS ONE MUST RETURN FALSE TO EVEN PERFORM THIS CHECK
        ));
        */
        add(new Task(
                "Go to hint",
                Task.ACTIONS.WALK,
                new Focus(
                        Focus.TYPES.HINT,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.TEXT, "POSITION");
                        }}
                ),
                task -> {
                    return HintArrow.isPresent() && !Dialog.isOpen();
                }
        ));
        add(new Task(
                "Interact with hint target",
                Task.ACTIONS.INTERACT,
                new Focus(
                        Focus.TYPES.HINT,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.TEXT, "TARGET");
                        }}
                ),
                task -> {
                    return HintArrow.isPresent() && HintArrow.getTarget() != null && !Dialog.isOpen();
                }
        ));
        add(new Task(
                "Type in name",
                Task.ACTIONS.TYPE,
                Util.generateRandomString(11),
                new Focus(
                        Focus.TYPES.INTERFACE,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.GROUP, "558");
                            put(Focus.ATTRIBUTE_TYPES.ID, "7");
                        }}
                ),
                task -> {
                    return Interfaces.get(ic -> ic.isVisible() && ic.getToolTip().equalsIgnoreCase("set name")).length > 0;
                }
        ));
        add(new Task(
                "Change appearance",
                Task.ACTIONS.INTERACT,
                "24",
                new Focus(
                        Focus.TYPES.INTERFACE,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.GROUP, "269");
                            put(Focus.ATTRIBUTE_TYPES.ID, "113");
                        }}
                ),
                task -> {
                    InterfaceComponent headArrow = Interfaces.getComponent(269, 113);
                    if(
                            headArrow != null
                            && headArrow.isVisible()
                    ) {
                        ((Task) task).setFocus(
                                new Focus(
                                        Focus.TYPES.INTERFACE,
                                        new HashMap<Focus.ATTRIBUTE_TYPES, String>() {{
                                            put(Focus.ATTRIBUTE_TYPES.GROUP, "269");
                                            put(
                                                    Focus.ATTRIBUTE_TYPES.ID,
                                                    String.valueOf((new int[]{
                                                            113,114,115,116,117,118,119,121,127,129,130,131
                                                    })[Random.nextInt(0,12)])
                                            );
                                        }}
                                )
                        );
                        CLog.log("Changing appearance. Have done this " + ((Task) task).getPerformedCount() + " times.");
                        return true;
                    } else {
                        return false;
                    }
                }
        ));
        add(new Task(
                "Accept appearance",
                Task.ACTIONS.CLICK,
                Util.generateRandomString(11),
                new Focus(
                        Focus.TYPES.INTERFACE,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.TEXT, "Accept");
                        }}
                ),
                task -> {
                    return (
                            Interfaces.get(ic -> ic.isVisible() && ic.getToolTip().equals("Accept")).length > 0
                            && Task.getPerformedCount("Change appearance") >= 15
                    );
                }
        ));
        add(new Task(
                "Talk to Guide",
                Task.ACTIONS.TALK,
                new Focus(
                        Focus.TYPES.NPC,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.NAME, "Gielinor Guide");
                        }}
                ),
                task -> {
                    return (
                            Interfaces.get(ic -> ic.isVisible() && ic.getToolTip().equalsIgnoreCase("set name")).length == 0
                            && Interfaces.get(ic -> ic.isVisible() && ic.getToolTip().equalsIgnoreCase("Accept")).length == 0
                            && !Dialog.isOpen()
                            && ((Task)task).getPerformedCount() <= 2
                    );
                }
        ));
        add(TaskTemplate.click_dialog_containing("experienced player"));
        add(TaskTemplate.click_tab_matching(Tab.OPTIONS));
        add(TaskTemplate.click_tab_matching(Tab.INVENTORY));
        add(TaskTemplate.click_tab_matching(Tab.SKILLS));
        add(TaskTemplate.click_tab_matching(Tab.QUEST_LIST));
        add(TaskTemplate.click_tab_matching(Tab.EQUIPMENT));
        add(TaskTemplate.click_tab_matching(Tab.COMBAT));
        add(TaskTemplate.click_tab_matching(Tab.ACCOUNT_MANAGEMENT));
        add(TaskTemplate.click_tab_matching(Tab.PRAYER));
        add(TaskTemplate.click_tab_matching(Tab.FRIENDS_LIST));
        add(TaskTemplate.click_tab_matching(Tab.MAGIC));
        add(new Task(
                "Continue dialog",
                Task.ACTIONS.CLICK,
                new Focus(
                        Focus.TYPES.DIALOG,
                        new HashMap<Focus.ATTRIBUTE_TYPES, String>(){{
                            put(Focus.ATTRIBUTE_TYPES.TEXT, "ontinue");
                        }}
                ),
                task -> {
                    return Dialog.canContinue() && Dialog.getChatOptions().length == 0;
                }
        ));
    }};

    //CONFIG
    public static final int MIN_LOOP_WAIT_TIME = 360;
    public static final int MAX_LOOP_WAIT_TIME = 650;


    //REFERENCE VARS
    public static Player me;

    public static void updateData(){
        me = Players.getLocal();
    }
}
