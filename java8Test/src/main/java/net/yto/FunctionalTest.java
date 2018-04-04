package net.yto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * @author 00938658-王富国
 * @date 2017/4/11
 */
public class FunctionalTest {

    public static void main(String[] args){

        Function<Instant, String> convert = (date) -> {
            return date.atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        };
        System.out.println(convert.apply(Instant.now()));

    }
}
