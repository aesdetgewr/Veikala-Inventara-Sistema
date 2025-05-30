package lv.rvt.tools;

// Klase konsoles lietotāja saskarnes veidošanai un noformēšanai
public class ConsoleUI {
    // ANSI krāsu kodi konsoles teksta noformēšanai
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String WHITE = "\u001B[37m";

    // Tabulas noformējuma konstantas
    private static final int MIN_TABLE_WIDTH = 80;
    private static final int DEFAULT_COLUMN_WIDTH = 20;
    
    // Unicode simboli tabulas zīmēšanai
    private static final String TABLE_BORDER = "═";
    private static final String TABLE_VERTICAL = "║";
    private static final String TABLE_TOP_LEFT = "╔";
    private static final String TABLE_TOP_RIGHT = "╗";
    private static final String TABLE_BOTTOM_LEFT = "╚";
    private static final String TABLE_BOTTOM_RIGHT = "╝";
    private static final String TABLE_CROSS = "╬";
    private static final String TABLE_T_DOWN = "╦";
    private static final String TABLE_T_UP = "╩";
    private static final String TABLE_T_RIGHT = "╠";
    private static final String TABLE_T_LEFT = "╣";

    // Centrē tekstu norādītajā platumā
    private static String center(String text, int width) {
        if (text == null) text = "";
        int contentLength = text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int padding = (width - contentLength) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - contentLength - padding));
    }

    // Izvada tekstu ietvertu rāmī ar norādīto krāsu
    private static void printBox(String message, String color) {
        System.out.println("\n" + color + TABLE_TOP_LEFT + TABLE_BORDER.repeat(MIN_TABLE_WIDTH) + TABLE_TOP_RIGHT + RESET);
        System.out.println(color + TABLE_VERTICAL + RESET + center(message, MIN_TABLE_WIDTH) + color + TABLE_VERTICAL + RESET);
        System.out.println(color + TABLE_BOTTOM_LEFT + TABLE_BORDER.repeat(MIN_TABLE_WIDTH) + TABLE_BOTTOM_RIGHT + RESET + "\n");
    }

    // Izvada tabulas galveni ar kolonnām
    public static void printTableHeader(String... headers) {
        if (headers == null || headers.length == 0) return;

        // Tabulas galvenes izvade ar noformējumu
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = Math.max(DEFAULT_COLUMN_WIDTH, headers[i].length() + 2);
        }

        // Top border
        System.out.print(BLUE + TABLE_TOP_LEFT);
        for (int i = 0; i < headers.length; i++) {
            System.out.print(TABLE_BORDER.repeat(columnWidths[i]));
            System.out.print(i < headers.length - 1 ? TABLE_T_DOWN : TABLE_TOP_RIGHT);
        }
        System.out.println(RESET);

        // Headers
        System.out.print(BLUE + TABLE_VERTICAL + RESET);
        for (int i = 0; i < headers.length; i++) {
            String headerText = headers[i];
            System.out.print(center(BLUE + headerText + RESET, columnWidths[i]));
            System.out.print(BLUE + TABLE_VERTICAL + RESET);
        }
        System.out.println();

        // Header-content separator
        System.out.print(BLUE + TABLE_T_RIGHT);
        for (int i = 0; i < headers.length; i++) {
            System.out.print(TABLE_BORDER.repeat(columnWidths[i]));
            System.out.print(i < headers.length - 1 ? TABLE_CROSS : TABLE_T_LEFT);
        }
        System.out.println(RESET);
    }

    // Izvada tabulas rindu ar datiem
    public static void printTableRow(String... columns) {
        if (columns == null || columns.length == 0) return;

        // Tabulas rindas izvade ar noformējumu
        int[] columnWidths = new int[columns.length];
        for (int i = 0; i < columns.length; i++) {
            columnWidths[i] = DEFAULT_COLUMN_WIDTH; 
        }

        // Print row content
        System.out.print(BLUE + TABLE_VERTICAL + RESET);
        for (int i = 0; i < columns.length; i++) {
            String text = columns[i] != null ? columns[i].trim() : "";
            int maxLength = columnWidths[i] - 4;
            if (text.length() > maxLength) {
                text = text.substring(0, maxLength) + "...";
            }
            String paddedText = " " + text;
            int remainingSpace = columnWidths[i] - paddedText.length();
            paddedText += " ".repeat(Math.max(0, remainingSpace));
            System.out.print(paddedText);
            System.out.print(BLUE + TABLE_VERTICAL + RESET);
        }
        System.out.println();
    }

    // Izvada tabulas apakšējo daļu
    public static void printTableFooter(int columnCount) {
        if (columnCount <= 0) return;

        // Tabulas apakšējās daļas izvade
        int columnWidth = DEFAULT_COLUMN_WIDTH;

        System.out.print(BLUE + TABLE_BOTTOM_LEFT);
        for (int i = 0; i < columnCount; i++) {
            System.out.print(TABLE_BORDER.repeat(columnWidth));
            System.out.print(i < columnCount - 1 ? TABLE_T_UP : TABLE_BOTTOM_RIGHT);
        }
        System.out.println(RESET);
    }

    // Izvada virsrakstu
    public static void printHeader(String text) {
        printBox(text, BLUE);
    }

    // Izvada izvēlni ar opcijām
    public static void printMenu(String header, String[] options) {
        printHeader(header);
        for (String option : options) {
            System.out.println("  " + option);
        }
        System.out.println();
    }

    public static void printError(String message) {
        printBox(message, RED);
    }

    public static void printSuccess(String message) {
        printBox(message, BLUE);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}