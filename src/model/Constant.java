package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import view.Cat;


public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);
    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.pink);
        put("⚪",Color.magenta);
        put("▲",Color.green);
        put("🔶",Color.orange);
    }};
    public static Map<String, Integer> idMap2 = new HashMap<>(){{
        put("💎",1);
        put("⚪",2);
        put("▲",3);
        put("🔶",4);
    }};
    public static Map<Integer,String> idMap3 = new HashMap<>(){{
        put(1,"💎");
        put(2,"⚪");
        put(3,"▲");
        put(4,"🔶");
    }};

}
