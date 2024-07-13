package de.nurrobin.persistor;

import java.util.ArrayList;
import java.util.List;

import de.nurrobin.model.Tile;
import de.nurrobin.util.Logger;

public class TilePersistor {
    // Save which tiles are on the map
    @SuppressWarnings("unused")
    private static final Logger logger = new Logger(TilePersistor.class);
    private static List<Tile> tiles = new ArrayList<>();
    private static TilePersistor instance;

    private TilePersistor() {
    }

    public static TilePersistor getInstance() {
        if (instance == null) {
            instance = new TilePersistor();
        }
        return instance;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        TilePersistor.tiles = tiles;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    public void clearTiles() {
        tiles.clear();
    }

    public static boolean isTileIDAvailable(String tileID) {
        return tiles.stream().noneMatch(tile -> String.valueOf(tile.getTileCode()).equals(tileID));
    }

    public void updateTile(Tile tile) {
        for (int i = 0; i < tiles.size(); i++) {
            if (Integer.valueOf(tiles.get(i).getTileCode()).equals(tile.getTileCode())) {
                tiles.set(i, tile);
                return;
            }
        }
    }

    public Tile getTileByTileCode(int tileCode) {
        for (Tile tile : tiles) {
            if (tile.getTileCode() == tileCode) {
                return tile;
            }
        }
        return null;
    }

    public Tile getTileAtPosition(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }
}
