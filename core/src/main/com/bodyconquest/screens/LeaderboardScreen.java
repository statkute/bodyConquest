package main.com.bodyconquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.rendering.BodyConquest;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * The type Leaderboard screen.
 */
public class LeaderboardScreen extends AbstractGameScreen implements Screen {


    private HashMap<String, Integer> leaderboard;
    private LinkedHashMap sorted;
    private Texture header;
    private int place;

    /**
     * Instantiates a new Leaderboard Screen.
     *
     * @param game the game
     */
    public LeaderboardScreen(BodyConquest game) {
        super(game);
        leaderboard = new HashMap<>();
        leaderboard.put("Alexandru", 20);
        leaderboard.put("Augustas", 16);
        leaderboard.put("Brandon", 30);
        leaderboard.put("Gintare", 15);
        leaderboard.put("Paul", 14);
        leaderboard.put("Anton", 14);
        leaderboard.put("Speed", 13);
        leaderboard.put("Tim", 12);
        leaderboard.put("Jack", 11);
        leaderboard.put("Rose", 10);
        loadAssets();
        getAssets();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        sortLeaderboard();
        game.batch.begin();
        game.batch.draw(header, BodyConquest.V_WIDTH / 2.0f - header.getWidth() / 2.0f, 450);
        game.usernameFont.getData().setScale(1.0f, 1.0f);
        drawLeaderboard();
        drawNumbers();
        game.batch.end();

    }

    @Override
    public void loadAssets() {
        super.loadAssets();
        manager.load(Assets.headerLeaderboard, Texture.class);
        manager.finishLoading();
    }

    @Override
    public void getAssets() {
        super.getAssets();
        header = manager.get(Assets.headerLeaderboard, Texture.class);
    }

    /**
     * Get leaderboard hash map .
     *
     * @return the hash map of leaderboard
     */
    public HashMap<String, Integer> getLeaderboard() {
        return leaderboard;
    }


    /**
     * Receive leaderboard from the server.
     *
     * @param leaderboard the leaderboard
     */
    public void receiveLeaderboard(HashMap<String, Integer> leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Sort leaderboard.
     */
    public void sortLeaderboard() {
        this.sorted = leaderboard
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    /**
     * Draw leaderboard.
     */
    public void drawLeaderboard() {

        for (Object s : sorted.keySet()) {
            place++;
            switch (place) {
                case 1:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 400.0f);
                    break;
                case 2:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 360.0f);
                    break;
                case 3:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 320.0f);
                    break;
                case 4:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 280.0f);
                    break;
                case 5:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 240.0f);
                    break;
                case 6:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 200.0f);
                    break;
                case 7:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 160.0f);
                    break;
                case 8:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 120.0f);
                    break;
                case 9:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".  \t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 80.0f);
                    break;
                case 10:
                    game.usernameFont.draw(game.batch, Integer.toString(place) + ".\t " + s, BodyConquest.V_WIDTH / 2.0f - 150.0f, 40.0f);
                    break;
            }
        }
        place = 0;


    }

    /**
     * Draw scores.
     */
    public void drawNumbers() {

        for (Object s : sorted.keySet()) {
            place++;
            switch (place) {
                case 1:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 400.0f);
                    break;
                case 2:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 360.0f);
                    break;
                case 3:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 320.0f);
                    break;
                case 4:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 280.0f);
                    break;
                case 5:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 240.0f);
                    break;
                case 6:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 200.0f);
                    break;
                case 7:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 160.0f);
                    break;
                case 8:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 120.0f);
                    break;
                case 9:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 80.0f);
                    break;
                case 10:
                    game.usernameFont.draw(game.batch, Integer.toString(leaderboard.get(s)), BodyConquest.V_WIDTH / 2.0f + 180, 40.0f);
                    break;
            }

        }
        place = 0;

    }
}
