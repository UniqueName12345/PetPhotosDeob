package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class JavaStringUtils {
    private static final boolean DEBUG = false;
    public static final String LOG_TAG_JOIN_STRINGS = "JavaJoinListOfStrings";
    private static final MappingOrder mappingOrderDictionary = new MappingOrder();
    private static final MappingOrder mappingOrderLongestStringFirst = new MappingLongestStringFirstOrder();
    private static final MappingOrder mappingOrderEarliestOccurrence = new MappingEarliestOccurrenceFirstOrder();
    private static final Comparator<Range> rangeComparator = new RangeComparator();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class MappingOrder {
        private MappingOrder() {
        }

        public void changeOrder(List<String> keys, String text) {
        }
    }

    /* loaded from: classes.dex */
    private static class MappingLongestStringFirstOrder extends MappingOrder {
        private MappingLongestStringFirstOrder() {
            super();
        }

        @Override // com.google.appinventor.components.runtime.util.JavaStringUtils.MappingOrder
        public void changeOrder(List<String> keys, String text) {
            Collections.sort(keys, new Comparator<String>() { // from class: com.google.appinventor.components.runtime.util.JavaStringUtils.MappingLongestStringFirstOrder.1
                @Override // java.util.Comparator
                public int compare(String s, String t1) {
                    return Integer.compare(t1.length(), s.length());
                }
            });
        }
    }

    /* loaded from: classes.dex */
    private static class MappingEarliestOccurrenceFirstOrder extends MappingOrder {
        private MappingEarliestOccurrenceFirstOrder() {
            super();
        }

        @Override // com.google.appinventor.components.runtime.util.JavaStringUtils.MappingOrder
        public void changeOrder(List<String> keys, String text) {
            final Map<String, Integer> occurrenceIndices = new HashMap<>();
            for (String key : keys) {
                int firstIndex = text.indexOf(key);
                if (firstIndex == -1) {
                    firstIndex = text.length() + occurrenceIndices.size();
                }
                occurrenceIndices.put(key, Integer.valueOf(firstIndex));
            }
            Collections.sort(keys, new Comparator<String>() { // from class: com.google.appinventor.components.runtime.util.JavaStringUtils.MappingEarliestOccurrenceFirstOrder.1
                @Override // java.util.Comparator
                public int compare(String s, String t1) {
                    int id1 = ((Integer) occurrenceIndices.get(s)).intValue();
                    int id2 = ((Integer) occurrenceIndices.get(t1)).intValue();
                    return id1 == id2 ? Integer.compare(t1.length(), s.length()) : Integer.compare(id1, id2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Range {
        int end;
        int start;
        String text;

        public Range(int start, int end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }
    }

    /* loaded from: classes.dex */
    private static class RangeComparator implements Comparator<Range> {
        private RangeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Range r1, Range r2) {
            int maxStart = Math.max(r1.start, r2.start);
            int minEnd = Math.min(r1.end, r2.end);
            if (maxStart < minEnd) {
                return 0;
            }
            return Integer.compare(r2.end, r1.end);
        }
    }

    public static String joinStrings(List<Object> listOfStrings, String separator) {
        return join(listOfStrings, separator);
    }

    public static YailList split(String text, String at) {
        List<String> parts = new ArrayList<>();
        Collections.addAll(parts, text.split(at));
        if (Pattern.quote("").equals(at) && parts.get(0).equals("")) {
            parts.remove(0);
        }
        return YailList.makeList((List) parts);
    }

    private static String join(List<Object> list, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object item : list) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(item.toString());
        }
        return sb.toString();
    }

    public static String replaceAllMappingsDictionaryOrder(String text, Map<Object, Object> mappings) {
        return replaceAllMappings(text, mappings, mappingOrderDictionary);
    }

    public static String replaceAllMappingsLongestStringOrder(String text, Map<Object, Object> mappings) {
        return replaceAllMappings(text, mappings, mappingOrderLongestStringFirst);
    }

    public static String replaceAllMappingsEarliestOccurrenceOrder(String text, Map<Object, Object> mappings) {
        return replaceAllMappings(text, mappings, mappingOrderEarliestOccurrence);
    }

    public static String replaceAllMappings(String text, Map<Object, Object> mappings, MappingOrder order) {
        Map<String, String> stringMappings = new HashMap<>();
        List<String> keys = new ArrayList<>();
        for (Map.Entry<Object, Object> current : mappings.entrySet()) {
            String key = current.getKey().toString();
            String value = current.getValue().toString();
            if (!stringMappings.containsKey(key)) {
                keys.add(key);
            }
            stringMappings.put(key, value);
        }
        order.changeOrder(keys, text);
        return applyMappings(text, stringMappings, keys);
    }

    private static String applyMappings(String text, Map<String, String> mappings, List<String> keys) {
        TreeSet<Range> ranges = new TreeSet<>(rangeComparator);
        for (String key : keys) {
            Pattern keyPattern = Pattern.compile(Pattern.quote(key));
            Matcher matcher = keyPattern.matcher(text);
            String replacement = mappings.get(key);
            while (matcher.find()) {
                int startId = matcher.start();
                int endId = matcher.end();
                ranges.add(new Range(startId, endId, replacement));
            }
        }
        Iterator<Range> it = ranges.iterator();
        while (it.hasNext()) {
            Range range = it.next();
            String left = text.substring(0, range.start);
            String middle = range.text;
            String end = text.substring(range.end);
            text = left + middle + end;
        }
        return text;
    }
}
