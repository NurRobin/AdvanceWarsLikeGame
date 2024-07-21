package de.nurrobin.logic;

import de.nurrobin.enums.TerrainType;
import de.nurrobin.enums.UnitType;
import de.nurrobin.model.Unit;
import de.nurrobin.persistor.TilePersistor;
import de.nurrobin.util.Logger;

import static de.nurrobin.enums.ResourceType.ENTITY;
import static de.nurrobin.enums.UnderlayingResourceType.PROPERTIESFILE;
import static de.nurrobin.util.ResourceURLBuilder.buildURL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        int IA = 1;
        int BD = getBaseDamage(attackerType, defenderType, isSecondaryWeapon);
        double AN = 1.1;
        int ID = 1;
        int DN = 1;
        int AH = attacker.getHealth();
        int TC = defenderTerrain.getDefenseBonus();
        int DH = defender.getHealth();
        
        double calculatedDamage =( ( ( ( ( ( ( IA * BD ) * AN ) * ID ) * DN ) * AH) * (( 100 - TC * DH ) / 100) )/100);
        int damage = (int) Math.round(calculatedDamage);

        logger.log("Damage calculated: " + damage);
        logger.log("Calculation: ((" + IA + " * " + BD + " * " + AN + " * " + ID + " * " + DN + " * " + AH + ") * ((100 - " + TC + " * " + DH + ") / 100)) = " + damage);
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
        return -1;
    }

    
}