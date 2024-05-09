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

/**
 * Test for new features of sequenced collections.
 *
 * @see java.util.SequencedCollection
 * @see java.util.SequencedSet
 * @see java.util.SequencedMap
 */
class SequencedCollectionsTest {

    @Test
    void testList() {
        List<String> list = new ArrayList<>(List.of("b", "c"));
        System.out.println("Initial: " + list);

        // formerly list.add(0, "a");
        list.addFirst("a");
        System.out.println("With added 'a' as first: " + list);

        // formerly just list.add("d");
        list.addLast("d");
        System.out.println("With added 'd' as last: " + list);

        System.out.println("First: " + list.getFirst());
        System.out.println("Last: " + list.getLast());

        list.removeFirst();
        System.out.println("With first removed: " + list);

        list.removeLast();
        System.out.println("With last removed: " + list);

        System.out.println("Reversed: " + list.reversed());
    }

    @Test
    void testSet() {
        SequencedSet<String> set = new LinkedHashSet<>(List.of("b", "c"));
        System.out.println("Initial: " + set);

        set.addFirst("a");
        System.out.println("With added 'a' as first: " + set);

        set.addLast("d");
        System.out.println("With added 'd' as last: " + set);

        System.out.println("First: " + set.getFirst());
        System.out.println("Last: " + set.getLast());

        set.removeFirst();
        System.out.println("With first removed: " + set);

        set.removeLast();
        System.out.println("With last removed: " + set);

        System.out.println("Reversed: " + set.reversed());
    }

    @Test
    void testMap() {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("b", 2);
        map.put("c", 3);
        System.out.println("Initial: " + map);

        map.putFirst("a", 1);
        System.out.println("With added 'a' as first: " + map);

        map.putLast("d", 4);
        System.out.println("With added 'd' as last: " + map);

        System.out.println("First: " + map.firstEntry());
        System.out.println("Last: " + map.lastEntry());

        Map.Entry<String, Integer> firstEntry = map.pollFirstEntry();
        System.out.println("With first removed: " + map);

        Map.Entry<String, Integer> lastEntry = map.pollLastEntry();
        System.out.println("With last removed: " + map);

        System.out.println("Reversed: " + map.reversed());

        System.out.println("Sequenced key set: " + map.sequencedKeySet());
        System.out.println("Sequenced values: " + map.sequencedValues());
        System.out.println("Sequenced entry set: " + map.sequencedEntrySet());
    }
}
