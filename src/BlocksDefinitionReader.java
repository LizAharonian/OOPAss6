import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * BlocksDefinitionReader class.
 * in charge of reading a block-definitions file and returning a BlocksFromSymbolsFactory object.
 */
public class BlocksDefinitionReader {

    /**
     * BlocksFromSymbolsFactory function.
     *
     * @param reader - Reader obj.
     * @return BlocksFromSymbolsFactory obj.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        //create new buffer reader to iterate line by line
        BufferedReader is = null;
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = new BlocksFromSymbolsFactory();
        try {
            is = new BufferedReader(reader);
            Map<String, String> defaultVals = new HashMap<String, String>();
            String line;
            while ((line = is.readLine()) != null) {
                if (line.startsWith("default ")) {
                    line = line.replace("default ", "");
                    defaultVals = blocksDefinitionReader.createMapFromLine(line);
                } else if (line.startsWith("bdef ")) {
                    line = line.replace("bdef ", "");
                    Map<String, String> blockBySymbolParams = blocksDefinitionReader.createMapFromLine(line);
                    Map<String, String> propertiesMap = new HashMap(defaultVals);
                    propertiesMap.putAll(blockBySymbolParams);
                    BlocksDefinitionReader.checkIfAllParamsExsist(propertiesMap);
                    String symbol = propertiesMap.get("symbol");
                    if (symbol.length() != 1) {
                        throw new RuntimeException("symbol is not valid");
                    }
                    int height = Integer.parseInt(propertiesMap.get("height"));
                    int width = Integer.parseInt(propertiesMap.get("width"));
                    int hitPoints = Integer.parseInt(propertiesMap.get("hit_points"));
                    if (height <= 0 || width <= 0 || hitPoints <= 0) {
                        System.out.println("inserted parameter should be positive");
                        System.exit(1);
                    }
                    //blocks colors\image
                    ColorsParser colorsParser = new ColorsParser();
                    Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
                    Map<Integer, Image> imageMap = new HashMap<Integer, Image>();
                    Image defaultImage = null;
                    Color defaultColor = null;
                    if (propertiesMap.containsKey("fill")) {
                        String imageOrColor = propertiesMap.get("fill").trim();
                        //get default if exist
                        if (imageOrColor != null && imageOrColor.contains("color")) {
                            String colorName = imageOrColor.replace("color(", "").replace(")", "");
                            defaultColor = colorsParser.colorFromString(colorName);

                        } else if (imageOrColor != null && imageOrColor.contains("image")) {
                            String imageName = imageOrColor.replace("image(", "").replace(")", "");

                            defaultImage = blocksDefinitionReader.convertImage(imageName);
                        }
                    }

                    for (int i = 0; i < hitPoints; i++) {
                        String fillName = "fill-" + String.valueOf(i + 1);
                        if (propertiesMap.containsKey(fillName)) {
                            if (propertiesMap.get(fillName).contains("color")) {
                                Color uniqueColor = colorsParser.colorFromString(propertiesMap.get(fillName));
                                colorMap.put(i, uniqueColor);

                            } else if (propertiesMap.get(fillName).contains("image")) {
                                Image uniqueImage = blocksDefinitionReader.convertImage(propertiesMap.get(fillName));
                                imageMap.put(i, uniqueImage);
                            }
                        } else {
                            //it must have default values
                            if (defaultColor != null) {
                                colorMap.put(i, defaultColor);
                            } else if (defaultImage != null) {
                                imageMap.put(i, defaultImage);
                            } else {
                                Color firstFill = colorMap.get(0);
                                colorMap.put(i, firstFill);
                            }
                        }
                    }

                    Color stroke = null;
                    if (propertiesMap.containsKey("stroke")) {
                        stroke = colorsParser.colorFromString(propertiesMap.get("stroke"));
                    }
                    CreateBlockByParams blockCreator = new CreateBlockByParams(width,
                            height, hitPoints, colorMap, imageMap, stroke);
                    blocksFromSymbolsFactory.addBlockCreator(symbol, blockCreator);

                } else if (line.startsWith("sdef ")) {
                    line = line.replace("sdef ", "");
                    Map<String, String> seperatorBySymbolParams = blocksDefinitionReader.createMapFromLine(line);
                    BlocksDefinitionReader.checkSpaceParams(seperatorBySymbolParams);
                    String symbol = seperatorBySymbolParams.get("symbol");
                    if (symbol.length() != 1) {
                        throw new RuntimeException("symbol is not valid");
                    }
                    int width = Integer.parseInt(seperatorBySymbolParams.get("width"));
                    if (defaultVals.containsKey("height")) {
                        int height = Integer.parseInt(defaultVals.get("height"));
                        blocksFromSymbolsFactory.addspacerHeight(symbol, height);
                        if (height <= 0 || width <= 0) {
                            System.out.println("inserted parameter should be positive");
                            System.exit(1);
                        }
                    }
                    blocksFromSymbolsFactory.addspacerWidths(symbol, width);
                    if (width <= 0) {
                        System.out.println("inserted parameter should be positive");
                        System.exit(1);
                    }
                }

            }

        } catch (IOException e) {
            System.out.println(" Something went wrong while reading !");
        } finally {
            if (is != null) { // Exception might have happened at constructor
                try {
                    is.close(); // closes FileInputStream too
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }

        return blocksFromSymbolsFactory;

    }

    /**
     * createMapFromLine function.
     *
     * @param line - string line.
     * @return - new map.
     */
    private Map<String, String> createMapFromLine(String line) {
        Map<String, String> map = new HashMap<String, String>();
        String[] splitedBySpace = line.split(" ");
        for (String item : splitedBySpace) {
            String[] splited = item.split(":");
            //line is of format txt:txt
            if (splited.length == 2) {
                map.put(splited[0].trim(), splited[1].trim());
            }
        }
        return map;
    }

    /**
     * convertImage function.
     *
     * @param imageName - string name of image.
     * @return - new Image obj.
     */
    private Image convertImage(String imageName) {
        String imageNameNew = imageName.replace("image(", "").replace(")", "");
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageNameNew);
        try {
            Image image = ImageIO.read(is);
            return image;
        } catch (Exception ex) {
            System.out.println("failed to create image");
            System.exit(1);
            return null;
        }
    }

    /**
     * checkIfAllParamsExsist function.
     *
     * @param params - map of parameters.
     * @throws RuntimeException in case there are missing objects.
     */
    private static void checkIfAllParamsExsist(Map<String, String> params) throws RuntimeException {
        if (!params.containsKey("height") || !params.containsKey("width")
                || !params.containsKey("hit_points") || !params.containsKey("symbol")) {
            throw new RuntimeException("missing params!");
        }
    }

    /**
     * checkSpaceParams function.
     *
     * @param params - map of parameters.
     * @throws RuntimeException in case there are missing objects.
     */
    private static void checkSpaceParams(Map<String, String> params) throws RuntimeException {
        if (!params.containsKey("symbol") || !params.containsKey("width")) {
            throw new RuntimeException("missing params!");
        }
    }
}
