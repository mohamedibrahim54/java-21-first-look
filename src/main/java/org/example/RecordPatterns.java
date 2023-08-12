package org.example;

/**
 * try JEP 440: Record Patterns (will be added to JAVA 21)
 * <p>
 * The grammar for patterns becomes:
 * <p>
 * Pattern:
 * TypePattern
 * RecordPattern
 * <p>
 * TypePattern:
 * LocalVariableDeclaration
 * <p>
 * RecordPattern:
 * ReferenceType ( [ PatternList ] )
 * <p>
 * PatternList :
 * Pattern { , Pattern }
 *
 * @author Mohamed Elsawy
 */

public class RecordPatterns {
    record Point(int x, int y) {
    }

    enum Color {RED, GREEN, BLUE}

    record ColoredPoint(Point p, Color c) {
    }

    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {
    }

    public static void main(String[] args) {
        Record point = new Point(12, 15);
        print16(point);

        print21(point);
        printSum(point);


        record Pair(Object a, Object b) {
        }

        Pair pair = new Pair(41, 42);

        if (pair instanceof Pair(String s1, String s2)) {
            System.out.println(s1 + ", " + s2);
        } else {
            System.out.println("not a Pair of Strings");
        }
        
        Rectangle rectangle = new Rectangle(new ColoredPoint(new Point(5, 6), Color.GREEN),
                new ColoredPoint(new Point(7, 8), Color.BLUE));

        printXCoordOfUpperLeftPointWithPatterns(rectangle);
    }


    public static void print16(Object o) {
        // As of Java 16
        if (o instanceof Point p) {
            System.out.println("Point: x= " + p.x + ", y=" + p.y);
        }
    }

    public static void print21(Object o) {
        if (o instanceof Point(int x, int y)) {
            System.out.println("Point: x= " + x + ", y=" + y);
        }
    }

    private static void printSum(Object o) {
        if (o instanceof Point(var x, var y)) {
            System.out.println("sum= " + (x + y));
        }
    }

    private static void printXCoordOfUpperLeftPointWithPatterns(Rectangle r) {
        if (r instanceof Rectangle(
                ColoredPoint(Point(var x, var y), var c),
                var lr
        )) {
            System.out.println("Upper-left corner: " + x);
        }
    }
}
