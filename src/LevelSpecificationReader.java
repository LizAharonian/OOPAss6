import biuoop.GUI;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * LevelSpecificationReader class.
 */
public class LevelSpecificationReader {
    //members
    private GUI gui;

    /**
     * constructoe method.
     *
     * @param gui - GUI obj.
     */
    public LevelSpecificationReader(GUI gui) {
        this.gui = gui;
    }

    /**
     * fromReader function.
     *
     * @param reader - Reader obj.
     * @return list of levels information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        //create level Information list
        List<LevelInformation> levelInformationList = new ArrayList<LevelInformation>();
        BufferedReader is = null;
        try {
            List<String> blocksOrder = new ArrayList<String>();
            is = new BufferedReader(reader);
            Map<String, String> xml = new HashMap<String, String>();
            String line;
            while ((line = is.readLine()) != null) {
                line = line.trim();
                String[] splited = line.split(":");
                //line is of format txt:txt
                if (splited.length == 2) {
                    xml.put(splited[0].trim(), splited[1].trim());
                }
                if (!line.equals("START_LEVEL") && !line.equals("")
                        && !line.contains(":") && !line.equals("START_BLOCKS")
                        && !line.equals("END_BLOCKS") && !line.equals("END_LEVEL") && !line.startsWith("#")) {
                    blocksOrder.add(line);
                }
                if (line.equals("END_LEVEL")) {
                    try {
                        LevelSpecificationReader.checkIfAllParamsExsist(xml);
                        LevelInformation levelInformation = convertMapToObj(xml, blocksOrder);
                        levelInformationList.add(levelInformation);
                    } catch (RuntimeException ex) {
                        System.out.println(ex);
                        System.exit(1);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(" Something went wrong while reading !");
            System.exit(1);
        } finally {
            if (is != null) { // Exception might have happened at constructor
                try {
                    is.close(); // closes FileInputStream too
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }
        return levelInformationList;
    }

    /**
     * convertMapToObj function.
     *
     * @param xml         - map of obj.
     * @param blocksOrder - list of block order.
     * @return LevelInformation obj.
     * @throws RuntimeException in case something went wrong.
     */
    private LevelInformation convertMapToObj(Map<String, String> xml,
                                             List<String> blocksOrder) throws RuntimeException {
        try {
            //levelName
            String levelName = xml.get("level_name").trim();
            //ballVelocities
            List<Velocity> velocities = new ArrayList<Velocity>();
            String[] ballVelocities = xml.get("ball_velocities").split(" ");
            for (String item : ballVelocities) {
                String[] velocityParts = item.split(",");
                Velocity v = Velocity.fromAngleAndSpeed(Integer.parseInt(velocityParts[0].trim()),
                        Integer.parseInt(velocityParts[1].trim()));
                velocities.add(v);
            }
            //background
            String background = xml.get("background").trim();
            ColorsParser colorsParser = new ColorsParser();
            Color backgroundColor = colorsParser.colorFromString(background);
            Sprite genericBackgroundSprite = null;
            if (backgroundColor == null && background.contains("image")) {
                String imageName = background.replace("image(", "").replace(")", "");
                genericBackgroundSprite = new GenericBackgroundSprite(imageName);

            } else {
                genericBackgroundSprite = new GenericBackgroundSprite(backgroundColor);
            }
            //paddleSpeed
            int paddleSpeed = Integer.parseInt(xml.get("paddle_speed").trim());
            int paddleWidth = Integer.parseInt(xml.get("paddle_width").trim());
            //blocks
            List<Block> blocks = new ArrayList<Block>();
            //blocksStartX
            double blocksStartX = Double.parseDouble(xml.get("blocks_start_x").trim());
            //blocksStartY
            double blocksStartY = Double.parseDouble(xml.get("blocks_start_y").trim());
            Point mostUpperLeftPoint = new Point(blocksStartX, blocksStartY);
            //rowHeight
            Double rowHeight = Double.parseDouble(xml.get("row_height").trim());
            //numBlocks
            int numBlocks = Integer.parseInt(xml.get("num_blocks").trim());
            if (numBlocks < 0 || paddleSpeed <= 0 || paddleWidth <= 0 || rowHeight <= 0) {
                throw new RuntimeException("NumBlocks/paddleSpeed/paddleWidth/rowHeight is not valid");
            }
            String blockDefinitions = xml.get("block_definitions");
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions);
            Reader reader = new InputStreamReader(is);
            BlocksFromSymbolsFactory blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(reader);
            double upperLeftX = blocksStartX;
            double upperLeftY = blocksStartY;
            for (String line : blocksOrder) {
                upperLeftX = blocksStartX;
                for (int i = 0; i < line.length(); i++) {
                    char symbolChar = line.charAt(i);
                    String symbol = String.valueOf(symbolChar);
                    if (blocksFromSymbolsFactory.isSpaceSymbol((symbol)) && line.length() == 1) {
                        String a = "d";
                    } else if (blocksFromSymbolsFactory.isSpaceSymbol((symbol)) && line.length() != 1) {
                        double width = blocksFromSymbolsFactory.getSpaceWidth(symbol);
                        upperLeftX += width;
                    } else if (blocksFromSymbolsFactory.isBlockSymbol((symbol))) {
                        Block block = blocksFromSymbolsFactory.getBlock(symbol, (int) upperLeftX, (int) upperLeftY);
                        blocks.add(block);
                        double width = block.getCollisionRectangle().getWidth();
                        upperLeftX += width;
                    }
                }
                upperLeftY += rowHeight;
            }
            blocksOrder.clear();
            LevelInformation levelInformationObj = new GenericLevelInformation(velocities, paddleSpeed, paddleWidth,
                    levelName, genericBackgroundSprite, blocks);
            return levelInformationObj;
        } catch (Exception ex) {
            System.out.println("level reader: " + ex);
            System.exit(1);
            return null;
        }
    }

    /**
     * convert function.
     * convert string to image.
     * @param imageName - name of image.
     * @return - Image obj.
     */
    private Image convert(String imageName) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
        try {

            Image image = ImageIO.read(is);
            return image;
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(1);
            return null;
        }
    }

    /**
     * checkIfAllParamsExsist function.
     * @param params - params of function.
     * @throws RuntimeException in case there are misiing params.
     */
    private static void checkIfAllParamsExsist(Map<String, String> params) throws RuntimeException {
        if (!params.containsKey("level_name") || !params.containsKey("ball_velocities")
                || !params.containsKey("background") || !params.containsKey("paddle_speed")
                || !params.containsKey("paddle_width") || !params.containsKey("block_definitions")
                || !params.containsKey("blocks_start_x") || !params.containsKey("blocks_start_y")
                || !params.containsKey("row_height") || !params.containsKey("num_blocks")) {
            throw new RuntimeException("missing params!");
        }
    }
}
