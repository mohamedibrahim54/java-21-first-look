package org.example;

import java.util.Date;

/**
 * try JEP 441: Pattern Matching for switch (will be added to JAVA 21)
 *
 * The grammar for switch labels
 * SwitchLabel:
 *   case CaseConstant { , CaseConstant }
 *   case null [, default]
 *   case Pattern [ Guard ]
 *   default
 *
 * @author Mohamed Elsawy
 */

public class PatternMatchingForSwitch {

    // Prior to Java 21 (As of Java 16)
    static String formatter(Object obj) {
        String formatted = "unknown";
        if (obj instanceof Integer i) {
            formatted = String.format("int %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("double %f", d);
        } else if (obj instanceof String s) {
            formatted = String.format("String %s", s);
        }
        return formatted;
    }

    // As of Java 21
    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l    -> String.format("long %d", l);
            case Double d  -> String.format("double %f", d);
            case String s  -> String.format("String %s", s);
            default        -> "unknown";
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Switches and null

    // Prior to Java 21
    // switch statements and expressions throw NullPointerException
    // if the selector expression evaluates to null, so testing for null must be done
    static void testFooBarOld(String s) {
        if (s == null) {
            System.out.println("Oops!");
            return;
        }
        switch (s) {
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }

    // As of Java 21
    // The behavior of the switch when the value of the selector expression is null is always determined by its case labels.
    // With a case null, the switch executes the code associated with that label.
    // without a case null, the switch throws NullPointerException, just as before.
    // (To maintain backward compatibility with the current semantics of switch, the default label does not match a null selector.)
    static void testFooBarNew(String s) {
        switch (s) {
            case null         -> System.out.println("Oops");
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    // Case refinement

    // As of Java 21
    static void testStringOld(String response) {
        switch (response) {
            case null -> { }
            case String s -> {
                if (s.equalsIgnoreCase("YES"))
                    System.out.println("You got it");
                else if (s.equalsIgnoreCase("NO"))
                    System.out.println("Shame");
                else
                    System.out.println("Sorry?");
            }
        }
    }

    // As of Java 21
    // when clauses in switch blocks to specify guards to pattern case labels.
    // We refer to such a case label as a guarded case label, and to the boolean expression as the guard.
    static void testStringNew(String response) {
        switch (response) {
            case null -> { }
            case String s
                when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }

    // As of Java 21
    // case constants, case patterns, and the null label combine to showcase the new power of switch programming
    static void testStringEnhanced(String response) {
        switch (response) {
            case null -> { }
            case "y", "Y" -> {
                System.out.println("You got it");
            }
            case "n", "N" -> {
                System.out.println("Shame");
            }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }
    // -----------------------------------------------------------------------------------------------------------------
    // Switches and enum constants

    // Prior to Java 21
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

    static void testForHearts(Suit s) {
        switch (s) {
            case HEARTS -> System.out.println("It's a heart!");
            default -> System.out.println("Some other suit");
        }
    }

    // As of Java 21
    sealed interface CardClassification permits Suit1, Tarot {}
    public enum Suit1 implements CardClassification { CLUBS, DIAMONDS, HEARTS, SPADES }
    static final class Tarot implements CardClassification {}

    static void exhaustiveSwitchWithoutEnumSupport(CardClassification c) {
        switch (c) {
            case Suit1 s when s == Suit1.CLUBS -> {
                System.out.println("It's clubs");
            }
            case Suit1 s when s == Suit1.DIAMONDS -> {
                System.out.println("It's diamonds");
            }
            case Suit1 s when s == Suit1.HEARTS -> {
                System.out.println("It's hearts");
            }
            case Suit1 s -> {
                System.out.println("It's spades");
            }
            case Tarot t -> {
                System.out.println("It's a tarot");
            }
        }
    }


    // As of Java 21
    // For new code, the treatment of enums:
    // 1. allow qualified names of enum constants to appear as case constants.
    //      These qualified names can be used when switching over an enum type.
    //
    // 2. drop the requirement that the selector expression be of an enum type when the name of one of that enum's constants is used as a case constant.
    //      In that situation we require the name to be qualified and its value to be assignment compatible with the type of the selector expression.
    static void exhaustiveSwitchWithBetterEnumSupport(CardClassification c) {
        switch (c) {
            case Suit1.CLUBS -> {        // Qualified name of enum constant as a label, must be qualified because the type of the selector expression is not enum.
                System.out.println("It's clubs");
            }
            case Suit1.DIAMONDS -> {
                System.out.println("It's diamonds");
            }
            case Suit1.HEARTS -> {
                System.out.println("It's hearts");
            }
            case Suit1.SPADES -> {
                System.out.println("It's spades");
            }
            case Tarot t -> {
                System.out.println("It's a tarot");
            }
        }
    }

    public static void main(String[] args) {
        int i = 10;
        System.out.println(formatter(i));

        long l = 10;
        System.out.println(formatterPatternSwitch(l));

        Date date = new Date();
        System.out.println(formatter(date));
        System.out.println(formatterPatternSwitch(date));


        testFooBarOld("Foo");
        testFooBarOld(null);

        testFooBarNew("bar");
        testFooBarNew(null);


        testStringOld("yes");
        testStringNew("lorem");
        testStringEnhanced("n");

        testForHearts(Suit.HEARTS);
        exhaustiveSwitchWithoutEnumSupport(Suit1.DIAMONDS);
        exhaustiveSwitchWithBetterEnumSupport(Suit1.DIAMONDS);
        Tarot tarot = new Tarot();
        exhaustiveSwitchWithoutEnumSupport(tarot);
        exhaustiveSwitchWithBetterEnumSupport(tarot);

    }
}
