package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ChartDataSourceUtil {
    private ChartDataSourceUtil() {
    }

    public static int determineMaximumListSize(YailList matrix) {
        int entries = 0;
        if (matrix == null) {
            return 0;
        }
        for (int i = 0; i < matrix.size(); i++) {
            Object row = matrix.getObject(i);
            if (row instanceof List) {
                List<?> list = (List) row;
                if (list.size() > entries) {
                    entries = list.size();
                }
            }
        }
        return entries;
    }

    public static YailList getTranspose(YailList matrix) {
        int entries = determineMaximumListSize(matrix);
        List<YailList> result = new ArrayList<>();
        for (int i = 0; i < entries; i++) {
            YailList listEntries = getTransposeEntry(matrix, i);
            result.add(listEntries);
        }
        return YailList.makeList((List) result);
    }

    private static YailList getTransposeEntry(YailList matrix, int index) {
        List<String> entries = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            List<?> matrixEntry = (List) matrix.getObject(i);
            if (matrixEntry.size() > index) {
                if (matrixEntry instanceof YailList) {
                    entries.add(((YailList) matrixEntry).getString(index));
                } else {
                    entries.add(matrixEntry.get(index).toString());
                }
            } else {
                entries.add("");
            }
        }
        return YailList.makeList((List) entries);
    }
}
