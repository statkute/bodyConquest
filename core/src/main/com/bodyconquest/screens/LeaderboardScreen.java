package com.cauldron.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.cauldron.bodyconquest.rendering.BodyConquest;

import java.util.HashMap;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class LeaderboardScreen extends AbstractGameScreen implements Screen {


    private HashMap<String,Integer> leaderboard;


    /**
     * Instantiates a new Leaderboard Screen.
     *
     * @param game the game
     */
    public LeaderboardScreen(BodyConquest game) {
        super(game);
        leaderboard.put("Alexandru",20);
        leaderboard.put("Augustas",16);
        leaderboard.put("Brandon",30);
        leaderboard.put("Gintare",15);
        leaderboard.put("Paul",14);
        leaderboard.put("Anton",14);
        leaderboard.put("Speed",13);
        leaderboard.put("Tim",12);
        leaderboard.put("Jack",11);
        leaderboard.put("Rose",10);
    }

    @Override
    public void render(float delta) {
        super.render(delta);


    }

    @Override
    public void loadAssets() {
        super.loadAssets();
    }

    @Override
    public void getAssets() {
        super.getAssets();
    }

    public HashMap<String,Integer> getLeaderboard(){
        return leaderboard;
    }


    public void receiveLeaderboard(HashMap<String,Integer> leaderboard){
        this.leaderboard = leaderboard;
    }

    public void sortLeaderboard(){
        HashMap<String, Integer> sorted = leaderboard
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                HashMap::new));
    }
}
