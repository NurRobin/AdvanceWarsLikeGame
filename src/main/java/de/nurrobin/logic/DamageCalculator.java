package de.nurrobin.logic;

import de.nurrobin.enums.TerrainType;
import de.nurrobin.enums.UnitType;
import de.nurrobin.model.GameMap;
import de.nurrobin.model.Unit;
import de.nurrobin.persistor.TilePersistor;
import de.nurrobin.util.Logger;

import static de.nurrobin.enums.ResourceType.ENTITY;
import static de.nurrobin.enums.UnderlayingResourceType.PROPERTIESFILE;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * DamageCalculator
 */
public class DamageCalculator {

    private static final Logger logger = new Logger(DamageCalculator.class);

    public static int calculateDamage(Unit attacker, Unit defender, boolean isSecondaryWeapon) {
        UnitType attackerType = attacker.getUnitType();
        UnitType defenderType = defender.getUnitType();
        TerrainType defenderTerrain = TilePersistor.getInstance().getTileAtPosition(defender.getX(), defender.getY()).getTerrain().getType();
        int BaseDamage = getBaseDamage(attackerType, defenderType, isSecondaryWeapon);
        int AttackingHP = attacker.getHealth();
        int DefendingHP = defender.getHealth();
        int TerrainCover = defenderTerrain.getDefenseBonus();
        
        int damage = ((BaseDamage * AttackingHP) * ((100 - TerrainCover * DefendingHP) / 100));
        return damage;
    }

    private static int getBaseDamage(UnitType attackerType, UnitType defenderType, boolean isSecondaryWeapon) {
        String key = attackerType + "." + defenderType + "." + (isSecondaryWeapon ? 2 : 1);
        String propertiesFileURL = buildURL(ENTITY, PROPERTIESFILE, "damage");
        Properties properties = new Properties();
        try (InputStream input = DamageCalculator.class.getResourceAsStream(propertiesFileURL)) {
            if (input == null) {
                System.out.println("Sorry, unable to find damage.properties");
                throw new FileNotFoundException("damage.properties not found with filepath: " + propertiesFileURL);
            }
            properties.load(input);
            String value = properties.getProperty(key);
            logger.log("Damage value for " + key + " is " + value);
            if (value != null && !value.equals("-")) {
                return Integer.parseInt(value);
            }
            else {
                return 0;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return -1; // Return -1 or throw an exception if the damage value is not found or on error
    }

    
}