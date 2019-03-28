package main.com.bodyconquest.entities.resources;

import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Spawnable;
import main.com.bodyconquest.game_logic.utils.Timer;
import main.com.bodyconquest.networking.Server;
import main.com.bodyconquest.networking.ServerSender;
import main.com.bodyconquest.networking.utilities.MessageMaker;

/**
 * The Resources class.
 */
public class Resources extends Thread {

    private final int MAX_RESOURCE = 100;
    private final long UPDATE_COOLDOWN = 1000;

    private int lipids;
    private int sugars;
    private int proteins;
    private int regenerationLipids;
    private int regenerationSugars;
    private int regenerationProteins;
    private PlayerType player;

    private long updateTimer;

    /**
     * Instantiates a new Resources class.
     *
     * @param player the player whose resources these are
     */
    public Resources(PlayerType player) {
        this.player = player;
        lipids = 100;
        sugars = 100;
        proteins = 100;
        regenerationLipids = 7;
        regenerationSugars = 5;
        regenerationProteins = 6;
    }

    /**
     * Method called to update the current values of the resources.
     */
    public void update() {
        if (updateTimer < System.currentTimeMillis()) {

            if (lipids < MAX_RESOURCE) {
                if (lipids + regenerationLipids > MAX_RESOURCE) {
                    lipids = MAX_RESOURCE;
                } else {
                    lipids += regenerationLipids;
                }
            }

            if (sugars < MAX_RESOURCE) {
                if (sugars + regenerationSugars > MAX_RESOURCE) {
                    sugars = MAX_RESOURCE;
                } else {
                    sugars += regenerationSugars;
                }
            }

            if (proteins < MAX_RESOURCE) {
                if (proteins + regenerationProteins > MAX_RESOURCE) {
                    proteins = MAX_RESOURCE;
                } else {
                    proteins += regenerationProteins;
                }
            }
            updateTimer = System.currentTimeMillis() + UPDATE_COOLDOWN;
        }
    }

    /**
     * Gets an update message to be sent to the server/client.
     *
     * @return the update message
     */
    public String getUpdateMessage() {
        return MessageMaker.resourceUpdate(lipids, sugars, proteins, player);
    }

    /**
     * Method to check whether an object can be spawned
     *
     * @param spawnableObject the spawnable object
     * @return true whether the resources are enough to spawn that object; false otherwise
     */
    public boolean canAfford(Spawnable spawnableObject) {
        return canAfford(spawnableObject.getLipidCost(), spawnableObject.getSugarCost(), spawnableObject.getProteinCost());
    }

    /**
     * Method to check whether the current resources are more than the parameters
     *
     * @param priceLipids   the price lipids
     * @param priceSugars   the price sugars
     * @param priceProteins the price proteins
     * @return true if current resources are greater or equal to the parameters; false otherwise
     */
    public boolean canAfford(int priceLipids, int priceSugars, int priceProteins) {
        return lipids >= priceLipids && sugars >= priceSugars && proteins >= priceProteins;
    }

    /**
     * Method called when resources were used to buy something
     *
     * @param spawnable the spawnable
     */
    public void buy(Spawnable spawnable) {
        buy(spawnable.getLipidCost(), spawnable.getSugarCost(), spawnable.getProteinCost());
    }

    /**
     * Method called when resources were used to buy something
     *
     * @param priceLipids   the price lipids
     * @param priceSugars   the price sugars
     * @param priceProteins the price proteins
     */
    public void buy(int priceLipids, int priceSugars, int priceProteins) {
        lipids -= priceLipids;
        sugars -= priceSugars;
        proteins -= priceProteins;
    }

    /**
     * Increase lipid regeneration.
     *
     * @param increase the increase
     */
    public void increaseLipidRegeneration(int increase) {
        regenerationLipids += increase;
    }

    /**
     * Increase sugar regeneration.
     *
     * @param increase the increase
     */
    public void increaseSugarRegeneration(int increase) {
        regenerationSugars += increase;
    }

    /**
     * Increase protein regeneration.
     *
     * @param increase the increase
     */
    public void increaseProteinRegeneration(int increase) {
        regenerationProteins += increase;
    }

    /**
     * Gets lipids.
     *
     * @return the lipids
     */
    public int getLipids() {
        return lipids;
    }

    /**
     * Gets proteins.
     *
     * @return the proteins
     */
    public int getProteins() {
        return proteins;
    }

    /**
     * Gets sugars.
     *
     * @return the sugars
     */
    public int getSugars() {
        return sugars;
    }
}
