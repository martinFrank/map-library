package com.github.martinfrank.maplib;

import java.util.ArrayList;
import java.util.List;

public class MapPrinter {

    private MapPrinter(){

    }

    public static void print(Map map){
        if (map == null) {
            return;
        }
        if (MapStyle.HEX_HORIZONTAL == map.getStyle()){
            printHexHorizontal(map);
        }
        if (MapStyle.HEX_VERTICAL == map.getStyle()){
            printHexVertical(map);
        }
    }

    private static void printHexVertical(Map map) {
        List<String> lines = new ArrayList<>();
        for (int dy = 0; dy < map.getRows(); dy++){
            for (int dx = 0; dx < map.getColumns(); dx++){

            }
        }
    }

    private static void printHexHorizontal(Map map) {
    }

    private static void printSquare(Map map) {
    }
}
