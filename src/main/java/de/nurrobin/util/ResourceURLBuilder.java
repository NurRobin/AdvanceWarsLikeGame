package de.nurrobin.util;

import de.nurrobin.enums.ResourceType;
import de.nurrobin.enums.UnderlayingResourceType;

public class ResourceURLBuilder {
    public static String buildURL(ResourceType resourceType, UnderlayingResourceType underlayingResourceType, String resourceName) {
        StringBuilder url = new StringBuilder();
        switch (resourceType) {
            case BUILDING: url.append("/buildings/");
            case ENTITY: url.append("/entities/");
            case MAP: url.append("/maps/");
            case TILE: url.append("/tiles/");
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + resourceType);
        }
        switch (underlayingResourceType) {
            case TEXTURESFILE: url.append("textures/").append(resourceName).append(".png");
            case DATAFILE: url.append("data/").append(resourceName).append(".json");
            case MAPFILE: url.append(resourceName).append(".map");
            case TILESFILE: url.append("/tiles/").append(resourceName).append(".tiles");
            case UNITSFILE: url.append("/units/").append(resourceName).append(".units");
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + underlayingResourceType);
        }
        String urlAsString = url.toString();
        // Check if the resource exists
        if (ResourceURLBuilder.class.getResource(url.toString()) == null) {
            throw new IllegalArgumentException("Resource not found: " + urlAsString);
        }
        return urlAsString;
    };
}
