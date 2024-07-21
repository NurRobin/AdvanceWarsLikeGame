package de.nurrobin.util;

import de.nurrobin.enums.ResourceType;
import de.nurrobin.enums.UnderlayingResourceType;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceURLBuilder {
    static Logger logger = Logger.getLogger(ResourceURLBuilder.class.getName());

    public static String buildURL(ResourceType resourceType, UnderlayingResourceType underlayingResourceType, String resourceName) {
        String basePath = switch (resourceType) {
            case BUILDING -> "/buildings/";
            case ENTITY -> "/entities/";
            case MAP -> "/maps/";
            case TILE -> "/tiles/";
            default -> throw new IllegalStateException("Unexpected value: " + resourceType);
        };

        String filePath = switch (underlayingResourceType) {
            case TEXTURESFILE -> "textures/" + resourceName + ".png";
            case DATAFILE -> "data/" + resourceName + ".json";
            case PROPERTIESFILE -> "data/" + resourceName + ".properties";
            case MAPFILE -> resourceName + ".map";
            case TILESFILE -> "tiles/" + resourceName + ".tiles";
            case UNITSFILE -> "units/" + resourceName + ".units";
            default -> throw new IllegalStateException("Unexpected value: " + underlayingResourceType);
        };

        String urlAsString = basePath + filePath;

        Optional<URL> resource = Optional.ofNullable(ResourceURLBuilder.class.getResource(urlAsString));
        if (resource.isEmpty()) {
            logger.log(Level.SEVERE, "Resource not found: " + urlAsString);
            throw new IllegalArgumentException("Resource not found: " + urlAsString);
        }

        logger.log(Level.FINE, "Resource URL: " + urlAsString);
        return urlAsString;
    }
}
