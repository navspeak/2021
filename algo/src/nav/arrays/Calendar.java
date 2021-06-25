package nav.arrays;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Calendar {
    private static Map<Double, String> timingMap = new HashMap<>();
    public static void main(String[] args) {

        String s = "9:45";
        Double d = getTimeAsDouble(s); // Double has precision of upto 15-16
        System.out.println(d);
        System.out.println(getTimeAsString(d));

        BigDecimal bd = new BigDecimal("9.777777777777777777779123");
        System.out.println(bd.intValue());
        System.out.println(bd.remainder(BigDecimal.ONE));

        List<StringMeeting> calendar1 = new ArrayList<StringMeeting>();
        calendar1.add(new StringMeeting("9:00", "10:30"));
        calendar1.add(new StringMeeting("12:00", "13:00"));
        calendar1.add(new StringMeeting("16:00", "18:00"));


        StringMeeting dailyBounds1 = new StringMeeting("9:00", "20:00");

        List<StringMeeting> calendar2 = new ArrayList<StringMeeting>();
        calendar2.add(new StringMeeting("10:00", "11:30"));
        calendar2.add(new StringMeeting("12:30", "14:30"));
        calendar2.add(new StringMeeting("14:30", "15:00"));
        calendar2.add(new StringMeeting("16:00", "17:00"));

        StringMeeting dailyBounds2 = new StringMeeting("10:00", "18:30");

        int meetingDuration = 30;

        List<StringMeeting> expected = new ArrayList<StringMeeting>();
        expected.add(new StringMeeting("11:30", "12:00"));
        expected.add(new StringMeeting("15:00", "16:00"));
        expected.add(new StringMeeting("18:00", "18:30"));

        List<StringMeeting> actual =
                calendarMatching(calendar1, dailyBounds1, calendar2, dailyBounds2, meetingDuration);
        actual.forEach(System.out::println);

    }

    static Double getTimeAsDouble(String time){
        return Double.parseDouble(time.split(":")[0]) + Double.parseDouble(time.split(":")[1])/60;
    }

    static String getTimeAsString(Double time){
        String asString = String.valueOf(time);
        String[] parts = asString.split("\\.");
        Integer hh = Integer.parseInt(parts[0]);
        Double mm = Double.parseDouble(parts[1]) * 0.60;
        String mmAsString = String.valueOf(mm).split("\\.")[0];
        mmAsString = mmAsString.length() == 1? mmAsString+"0" : mmAsString;
        return hh+":"+mmAsString;
    }


    public static List<StringMeeting> calendarMatching(
            List<StringMeeting> calendar1,
            StringMeeting dailyBounds1,
            List<StringMeeting> calendar2,
            StringMeeting dailyBounds2,
            int meetingDuration) {

        calendar1.add(0, new StringMeeting("00:00", dailyBounds1.start));
        calendar1.add(new StringMeeting(dailyBounds1.end, "24:00"));
        calendar2.add(0, new StringMeeting("00:00", dailyBounds2.start));
        calendar2.add(new StringMeeting(dailyBounds2.end, "24:00"));
        List<StringMeeting> combinedCal = new ArrayList<>();
        combinedCal.addAll(calendar1);
        combinedCal.addAll(calendar2);

        combinedCal.sort((x,y) -> {
            if (!x.range[0].equals(y.range[0])) // Double value comparision
                return Double.compare(x.range[0], y.range[0]);
            else
                return Double.compare(x.range[1], y.range[1]);
        });

        List<StringMeeting> freeSlots = new ArrayList<>();
        Double endTimeOfPrev = null;
        Double startTimeOfNext, endTimeOfNext;
        Double duration = meetingDuration / 60.0;
        for (StringMeeting meeting: combinedCal){
            if (endTimeOfPrev == null){
                endTimeOfPrev = meeting.range[1];
                continue;
            }

            startTimeOfNext = meeting.range[0];
            endTimeOfNext = meeting.range[1];

            if (endTimeOfPrev <= startTimeOfNext) {
                if (startTimeOfNext - endTimeOfPrev >= duration){
                    Double t = endTimeOfPrev + (startTimeOfNext - endTimeOfPrev) ;
                    freeSlots.add(new StringMeeting(getTimeAsString(endTimeOfPrev), getTimeAsString(t)));
                }
                endTimeOfPrev = endTimeOfNext;
            } else if (endTimeOfPrev < meeting.range[1] ){
                endTimeOfPrev = endTimeOfNext;
            } else{
                continue;
            }

        }
        return freeSlots;

    }



    static class StringMeeting {
        @Override
        public String toString() {
            return "StringMeeting{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }

        public String start;
        public String end;
        Double[] range = new Double[2];

        public StringMeeting(String start, String end) {
            this.start = start;
            this.end = end;
            this.range[0] = getTimeAsDouble(start);
            this.range[1] = getTimeAsDouble(end);
        }
    }
}
