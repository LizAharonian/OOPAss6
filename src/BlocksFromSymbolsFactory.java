import java.util.HashMap;
import java.util.Map;

/**
 * BlocksFromSymbolsFactory class.
 * in charge of mapping from symbols to spaces and blocks.
 */
public class BlocksFromSymbolsFactory {
    //members
    private Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
    private Map<String, Integer> spacerHeight = new HashMap<String, Integer>();
    private Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();;

    /**
     * isSpaceSymbol function.
     * @param s - string symbol.
     * @return true if 's' is a valid space symbol., false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * isBlockSymbol function.
     * @param s - string symbol.
     * @return  true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    /**
     * getSpaceWidth function.
     * @param s - string symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * getSpaceHeight function.
     * @param s - string symbol.
     * @return the height in pixels associated with the given spacer-symbol.
     */
    public int getSpaceHeight(String s) {
        return this.spacerHeight.get(s);
    }

    /**
     * getBlock function.
     * @param s - string symbol.
     * @param x - the block x upperLeftPoint.
     * @param y - the block y upperLeftPoint.
     * @return  a block according to the definitions associated with symbol s.
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * addBlockCreator function.
     * @param symbol - string symbol.
     * @param blockCreator - block creator obj.
     */
    public void addBlockCreator(String symbol, BlockCreator blockCreator) {
        this.blockCreators.put(symbol, blockCreator);
    }

    /**
     * addspacerWidths function.
     * @param symbol - string symbol.
     * @param width - width of spacer.
     */
    public void addspacerWidths(String symbol, Integer width) {
        this.spacerWidths.put(symbol, width);
    }

    /**
     * addspacerHeight function.
     * @param symbol - string symbol.
     * @param height - height of spacer.
     */
    public void addspacerHeight(String symbol, Integer height) {
        this.spacerHeight.put(symbol, height);
    }
}
