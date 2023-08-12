package org.example;

/**
 * try JEP 361: Switch Expressions (added to JAVA 14)
 *
 * @author Mohamed Elsawy
 */
public class SwitchExpressions 
{
    enum Day{ SATURDAY, SUNDAY, MONDAY, TUESDAY, WENESDAY, THURSDAY, FRIDAY }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Day today = Day.MONDAY;

        System.out.println(calcWorkingHoursOld(today));

        System.out.println(calcWorkingHours14(today));

    }


    public static int calcWorkingHoursOld(Day day){
        int workingHours;
        switch (day){
            case FRIDAY:
            case SATURDAY:
                workingHours = 0;
                break;
            case SUNDAY:
            case MONDAY:
            case TUESDAY:
            case WENESDAY:
                workingHours = 8;
                break;
            case THURSDAY:
                workingHours = 6;
                break;
            case null:
                throw new NullPointerException();
        }
        return workingHours;
    }

    public static int calcWorkingHours14(Day day){
        return switch (day){
            case FRIDAY, SATURDAY -> 0;
            case SUNDAY, MONDAY, TUESDAY, WENESDAY -> 8;
            case THURSDAY -> 6;
        };
    }
}
