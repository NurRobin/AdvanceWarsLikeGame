The playable map should consist of three layers

- The background layer (Layer 0) is the lowest layer and should contain the background of the map. Like the background tiles of the map. E.g. plains or sea.
- The object layer (Layer 1) is the middle layer and should contain all objects that are not directly interactable. Like the buildings, woods, or mountains.
- The interactive layer (Layer 2) is the top layer and should contain all objects that are interactable. Like the units.

The map should be a grid of tiles. Each tile should have a size of 32x32 pixels. The map should be a square. The size of the map and its contents should be read from the map file. The map file should be a JSON like file.