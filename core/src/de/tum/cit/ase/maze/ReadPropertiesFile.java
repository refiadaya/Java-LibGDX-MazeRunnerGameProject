package de.tum.cit.ase.maze;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The ReadPropertiesFile class is responsible for parsing maze configurations from properties files.
 * It reads a properties file corresponding to a specific maze level and creates a list of MazeObjects based on the configuration.
 */
public class ReadPropertiesFile {

    String fileName;

    /**
     * Parses a maze configuration from a properties file for a specific level.
     *
     * @param level The level for which the maze configuration should be parsed.
     * @return An ArrayList of MazeObjects representing the maze configuration.
     */
    public ArrayList<MazeObject> parseMaze(int level, String filePath) {
        ArrayList<MazeObject> mazeObjectList = new ArrayList<>();
        if (level == 6){
            fileName = filePath;
        } else{
            fileName = "maps/level-%d.properties";

            fileName = String.format(fileName, level);
        }

        try (FileReader reader = new FileReader(fileName)) {
            Properties properties = new Properties();
            properties.load(reader);
            for (String key : properties.stringPropertyNames()) {
                String[] equal = key.split("=");
                String[] coordinates = equal[0].split(",");
                float x = Integer.parseInt(coordinates[0]);
                float y = Integer.parseInt(coordinates[1]);
                int objectValue = Integer.parseInt(properties.getProperty(key));

                MazeObject mazeObject = createMazeObject(objectValue, x * 64, y * 64);

                mazeObjectList.add(mazeObject);

            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return mazeObjectList;
    }

    /**
     * Creates a MazeObject based on the provided object value and coordinates.
     *
     * @param objectValue The numeric representation of the MazeObject type.
     * @param x           The x-coordinate of the MazeObject.
     * @param y           The y-coordinate of the MazeObject.
     * @return A MazeObject corresponding to the provided parameters.
     */
    public static MazeObject createMazeObject(int objectValue, float x, float y) {
        return switch (objectValue) {
            case 0 -> new Wall(x, y);
            case 1 -> new Entry(x, y);
            case 2 -> new Exit(x, y);
            case 3 -> new Trap(x, y);
            case 4 -> new Enemy(x, y);
            case 5 -> new Key(x, y);
            default -> null;
        };
    }
}