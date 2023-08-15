package com.google.appinventor.components.runtime.util;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import gnu.lists.FString;
import gnu.math.IntFraction;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class JsonUtil {
    private static final String BINFILE_DIR = "/AppInventorBinaries";
    private static final String LOG_TAG = "JsonUtil";

    private JsonUtil() {
    }

    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList.add(val);
        }
        return returnList;
    }

    @Deprecated
    public static List<Object> getListFromJsonArray(JSONArray jsonArray) throws JSONException {
        return getListFromJsonArray(jsonArray, false);
    }

    public static List<Object> getListFromJsonArray(JSONArray jsonArray, boolean useDicts) throws JSONException {
        List<Object> returnList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            returnList.add(convertJsonItem(jsonArray.get(i), useDicts));
        }
        return returnList;
    }

    public static List<Object> getListFromJsonObject(JSONObject jObject) throws JSONException {
        List<Object> returnList = new ArrayList<>();
        Iterator<String> keys = jObject.keys();
        List<String> keysList = new ArrayList<>();
        while (keys.hasNext()) {
            keysList.add(keys.next());
        }
        Collections.sort(keysList);
        for (String key : keysList) {
            List<Object> nestedList = new ArrayList<>();
            nestedList.add(key);
            nestedList.add(convertJsonItem(jObject.get(key), false));
            returnList.add(nestedList);
        }
        return returnList;
    }

    public static YailDictionary getDictionaryFromJsonObject(JSONObject jsonObject) throws JSONException {
        YailDictionary result = new YailDictionary();
        TreeSet<String> keys = new TreeSet<>();
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            keys.add(it.next());
        }
        Iterator<String> it2 = keys.iterator();
        while (it2.hasNext()) {
            String key = it2.next();
            result.put(key, convertJsonItem(jsonObject.get(key), true));
        }
        return result;
    }

    @Deprecated
    public static Object convertJsonItem(Object o) throws JSONException {
        return convertJsonItem(o, false);
    }

    public static Object convertJsonItem(Object o, boolean useDicts) throws JSONException {
        if (o == null) {
            return "null";
        }
        if (o instanceof JSONObject) {
            if (useDicts) {
                return getDictionaryFromJsonObject((JSONObject) o);
            }
            return getListFromJsonObject((JSONObject) o);
        } else if (o instanceof JSONArray) {
            List<Object> array = getListFromJsonArray((JSONArray) o, useDicts);
            if (useDicts) {
                return YailList.makeList((List) array);
            }
            return array;
        } else if (o.equals(Boolean.FALSE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("false"))) {
            return false;
        } else {
            if (o.equals(Boolean.TRUE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("true"))) {
                return true;
            }
            return o instanceof Number ? o : o.toString();
        }
    }

    public static String getJsonRepresentation(Object value) throws JSONException {
        Object[] objArr;
        if (value == null || value.equals(null)) {
            return "null";
        }
        if (value instanceof FString) {
            return JSONObject.quote(value.toString());
        }
        if (value instanceof YailList) {
            return ((YailList) value).toJSONString();
        }
        if (value instanceof IntFraction) {
            return JSONObject.numberToString(Double.valueOf(((IntFraction) value).doubleValue()));
        }
        if (value instanceof Number) {
            return JSONObject.numberToString((Number) value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof List) {
            value = ((List) value).toArray();
        }
        if (value instanceof YailDictionary) {
            StringBuilder sb = new StringBuilder();
            YailDictionary dict = (YailDictionary) value;
            String sep = "";
            sb.append('{');
            for (Map.Entry<Object, Object> entry : dict.entrySet()) {
                sb.append(sep);
                sb.append(JSONObject.quote(entry.getKey().toString()));
                sb.append(':');
                sb.append(getJsonRepresentation(entry.getValue()));
                sep = ",";
            }
            sb.append('}');
            return sb.toString();
        } else if (value.getClass().isArray()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[");
            String separator = "";
            for (Object o : (Object[]) value) {
                sb2.append(separator).append(getJsonRepresentation(o));
                separator = ",";
            }
            sb2.append("]");
            return sb2.toString();
        } else {
            return JSONObject.quote(value.toString());
        }
    }

    @Deprecated
    public static Object getObjectFromJson(String jsonString) throws JSONException {
        return getObjectFromJson(jsonString, false);
    }

    public static Object getObjectFromJson(String jsonString, boolean useDicts) throws JSONException {
        if (jsonString == null || jsonString.equals("")) {
            return "";
        }
        Object value = new JSONTokener(jsonString).nextValue();
        if (value == null || value.equals(JSONObject.NULL)) {
            return null;
        }
        if (!(value instanceof String) && !(value instanceof Number) && !(value instanceof Boolean)) {
            if (value instanceof JSONArray) {
                return getListFromJsonArray((JSONArray) value, useDicts);
            }
            if (value instanceof JSONObject) {
                if (useDicts) {
                    return getDictionaryFromJsonObject((JSONObject) value);
                }
                return getListFromJsonObject((JSONObject) value);
            }
            throw new JSONException("Invalid JSON string.");
        }
        return value;
    }

    @Deprecated
    public static String getJsonRepresentationIfValueFileName(Object value) {
        Log.w(LOG_TAG, "Calling deprecated function getJsonRepresentationIfValueFileName", new IllegalAccessException());
        return getJsonRepresentationIfValueFileName(Form.getActiveForm(), value);
    }

    public static String getJsonRepresentationIfValueFileName(Form context, Object value) {
        List<String> valueList;
        try {
            if (value instanceof String) {
                JSONArray valueJsonList = new JSONArray((String) value);
                valueList = getStringListFromJsonArray(valueJsonList);
            } else if (value instanceof List) {
                valueList = (List) value;
            } else {
                throw new YailRuntimeError("getJsonRepresentationIfValueFileName called on unknown type", value.getClass().getName());
            }
            if (valueList.size() == 2 && valueList.get(0).startsWith(".")) {
                String filename = writeFile(context, valueList.get(1), valueList.get(0).substring(1));
                System.out.println("Filename Written: " + filename);
                return getJsonRepresentation(filename.replace("file:/", "file:///"));
            }
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
            return null;
        }
    }

    public static YailList getColumnsFromJson(String json) throws JSONException {
        Object jsonObject = getObjectFromJson(json);
        List<YailList> resultColumns = new ArrayList<>();
        if (jsonObject instanceof List) {
            List<?> jsonList = (List) jsonObject;
            for (Object entry : jsonList) {
                List<String> columnElements = new ArrayList<>();
                if (entry instanceof List) {
                    List<?> listEntry = (List) entry;
                    columnElements.add(listEntry.get(0).toString());
                    Object jsonValue = listEntry.get(1);
                    if (jsonValue instanceof List) {
                        List<?> jsonValueList = (List) jsonValue;
                        for (Object jsonValueListEntry : jsonValueList) {
                            columnElements.add(jsonValueListEntry.toString());
                        }
                    } else {
                        columnElements.add(jsonValue.toString());
                    }
                }
                resultColumns.add(YailList.makeList((List) columnElements));
            }
        }
        return YailList.makeList((List) resultColumns);
    }

    private static String writeFile(Form context, final String input, String fileExtension) {
        String fullDirName = context.getDefaultPath(BINFILE_DIR);
        String preAmble = Uri.parse(context.getDefaultPath("")).getPath();
        File destDirectory = new File(Uri.parse(fullDirName).getPath());
        if (!destDirectory.isDirectory() && !destDirectory.mkdirs()) {
            throw new YailRuntimeError("Unable to create " + destDirectory, "Write");
        }
        final Synchronizer<Boolean> result = new Synchronizer<>();
        try {
            File dest = File.createTempFile("BinFile", "." + fileExtension, destDirectory);
            new FileWriteOperation(context, context, "Write", dest.getAbsolutePath().replace(preAmble, ""), context.DefaultFileScope(), false, true) { // from class: com.google.appinventor.components.runtime.util.JsonUtil.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.appinventor.components.runtime.util.FileWriteOperation, com.google.appinventor.components.runtime.util.FileStreamOperation
                public boolean process(OutputStream stream) throws IOException {
                    try {
                        stream.write(Base64.decode(input, 0));
                        result.wakeup(true);
                    } catch (Exception e) {
                        result.caught(e);
                    }
                    return true;
                }
            }.run();
            result.waitfor();
            if (result.getThrowable() != null) {
                Throwable t = result.getThrowable();
                Log.e(LOG_TAG, "Error writing content", t);
                if (t instanceof RuntimeException) {
                    throw ((RuntimeException) t);
                }
                if (t instanceof IOException) {
                    throw ((IOException) t);
                }
                throw new YailRuntimeError(t.getMessage(), "Write");
            }
            trimDirectory(20, destDirectory);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            throw new YailRuntimeError(e.getMessage() + " destDirectory: " + destDirectory, "Write");
        }
    }

    private static void trimDirectory(int maxSavedFiles, File directory) {
        File[] files = directory.listFiles();
        Arrays.sort(files, new Comparator<File>() { // from class: com.google.appinventor.components.runtime.util.JsonUtil.2
            @Override // java.util.Comparator
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
            }
        });
        int excess = files.length - maxSavedFiles;
        for (int i = 0; i < excess; i++) {
            files[i].delete();
        }
    }

    public static String encodeJsonObject(Object jsonObject) throws IllegalArgumentException {
        try {
            return getJsonRepresentation(jsonObject);
        } catch (JSONException e) {
            throw new IllegalArgumentException("jsonObject is not a legal JSON object");
        }
    }
}
