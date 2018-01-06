import java.awt.Color;

/**
 * ColorsParser class.
 * converts string to Color obj.
 */
public class ColorsParser {

    /**
     * colorFromString function.
     * parse color definition and return the specified color.
     * @param s - string for conversion.
     * @return new Color obj.
     */
    public java.awt.Color colorFromString(String s) {
        if (s == null) {
            return null;
        }
        s = s.replace("color(", "");
        s = s.replace(")", "");
        switch (s) {

            case "black":
                return Color.black;

            case "blue":
                return Color.blue;

            case "cyan":
                return Color.cyan;

            case "gray":
                return Color.gray;

            case "lightGray":
                return Color.lightGray;

            case "green":
                return Color.green;

            case "orange":
                return Color.orange;

            case "pink":
                return Color.pink;

            case "red":
                return Color.red;

            case "white":
                return Color.white;

            case "yellow":
                return Color.yellow;
            default:
                String a = "default";
        }
        if (s.startsWith("RGB")) {
            s = s.replace("RGB(", "");
            s =  s.replace(")", "");
            String[] rgb = s.split(",");
            if (rgb.length == 3) {
                try {
                    int x = Integer.parseInt(rgb[0]);
                    int y = Integer.parseInt(rgb[1]);
                    int z = Integer.parseInt(rgb[2]);
                    Color color = new Color(x, y, z);
                    return color;
                } catch (Exception ex) {
                    System.out.println(ex);
                    System.exit(1);
                }

            }
        }

        return null;

    }
}
