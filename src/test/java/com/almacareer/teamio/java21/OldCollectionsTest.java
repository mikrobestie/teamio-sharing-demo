package com.almacareer.teamio.java21;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Examples of old collections "sequenced" usage.
 */
class OldCollectionsTest {

    @Test
    void testOldList() {
        List<String> list = new ArrayList<>(List.of("b", "c"));
        System.out.println("Initial: " + list);

        list.add(0, "a");
        System.out.println("With added 'a' as first: " + list);

        list.add("d");
        System.out.println("With added 'd' as last: " + list);

        System.out.println("First: " + list.get(0));
        System.out.println("Last: " + list.get(list.size() - 1));

        list.remove(0);
        System.out.println("With first removed: " + list);

        list.remove(list.size() - 1);
        System.out.println("With last removed: " + list);

        List<String> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        System.out.println("Reversed: " + reversed);
    }

    @Test
    void testOldLinkedHasSet() {
        LinkedHashSet<String> set = new LinkedHashSet<>(List.of("a", "b", "c"));
        System.out.println("Initial: " + set);

        // no way to add element as first!

        set.add("d");
        System.out.println("With added 'd' as last: " + set);

        System.out.println("First: " + set.iterator().next());
        System.out.println("Last: " + set.stream().reduce((first, second) -> second).orElse(null));

        set.remove(set.iterator().next());
        System.out.println("With first removed: " + set);

        set.remove(set.stream().reduce((first, second) -> second).orElse(null));
        System.out.println("With last removed: " + set);

        // no way to reverse, or... convert to list and reverse
        List<String> reversedList = new ArrayList<>(set);
        Collections.reverse(reversedList);
        System.out.println("Reversed: " + reversedList);
    }

    @Test
    void testOldSortedSet() {
        SortedSet<String> set = new ConcurrentSkipListSet<>(List.of("a", "b", "c", "d"));
        System.out.println("Initial: " + set);

        System.out.println("First: " + set.first());
        System.out.println("Last: " + set.last());

        set.remove(set.first());
        System.out.println("With first removed: " + set);

        set.remove(set.last());
        System.out.println("With last removed: " + set);
    }

    @Test
    void testOldMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println("Initial: " + map);

        // no way to add element as first!

        map.put("d", 4);
        System.out.println("With added 'd' as last: " + map);

        System.out.println("First: " + map.entrySet().iterator().next());
        System.out.println("Last: " + map.entrySet().stream().reduce((first, second) -> second).orElse(null));

        map.remove("a");
        System.out.println("With first removed: " + map);

        map.remove("d");
        System.out.println("With last removed: " + map);

        // no way to reverse, or... convert to list and reverse
        List<Map.Entry<String, Integer>> reversedList = new ArrayList<>(map.entrySet());
        Collections.reverse(reversedList);
        System.out.println("Reversed: " + reversedList);
    }
}
