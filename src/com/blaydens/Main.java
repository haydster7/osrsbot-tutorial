package com.blaydens;

import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(developer = "haydster7", desc = "Completes tutorial island", name = "TutorialBot3000")
public class Main extends Script {

    private Player me;

    @Override
    public void onStart() {
        CLog.log("Welcome to tutorial bot", CLog.Level.USER);
    }

    @Override
    public int loop() {
        Util.printDebugInfo();
        Task task = Decider.chooseTask();
        if(task != null){
            Performer.perform(task);
        }

        return Random.nextInt(Data.MIN_LOOP_WAIT_TIME, Data.MAX_LOOP_WAIT_TIME);
    }

    @Override
    public void onStop() {
        CLog.log("Goodbye. Thanks for using tutorial bot", CLog.Level.USER);
    }


}
