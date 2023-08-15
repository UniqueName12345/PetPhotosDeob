package com.google.appinventor.components.runtime.repackaged.org.json;

import java.util.Iterator;

/* loaded from: classes2.dex */
public class JSONML {
    private static Object parse(XMLTokener x, boolean arrayForm, JSONArray ja) throws JSONException {
        loop0: while (x.more()) {
            Object token = x.nextContent();
            if (token == XML.LT) {
                Object token2 = x.nextToken();
                if (token2 instanceof Character) {
                    if (token2 == XML.SLASH) {
                        Object token3 = x.nextToken();
                        if (!(token3 instanceof String)) {
                            throw new JSONException(new StringBuffer().append("Expected a closing name instead of '").append(token3).append("'.").toString());
                        }
                        if (x.nextToken() != XML.GT) {
                            throw x.syntaxError("Misshaped close tag");
                        }
                        return token3;
                    } else if (token2 == XML.BANG) {
                        char c = x.next();
                        if (c == '-') {
                            if (x.next() == '-') {
                                x.skipPast("-->");
                            } else {
                                x.back();
                            }
                        } else if (c == '[') {
                            if (x.nextToken().equals("CDATA") && x.next() == '[') {
                                if (ja != null) {
                                    ja.put(x.nextCDATA());
                                }
                            } else {
                                throw x.syntaxError("Expected 'CDATA['");
                            }
                        } else {
                            int i = 1;
                            do {
                                Object token4 = x.nextMeta();
                                if (token4 == null) {
                                    throw x.syntaxError("Missing '>' after '<!'.");
                                }
                                if (token4 == XML.LT) {
                                    i++;
                                    continue;
                                } else if (token4 == XML.GT) {
                                    i--;
                                    continue;
                                } else {
                                    continue;
                                }
                            } while (i > 0);
                        }
                    } else if (token2 == XML.QUEST) {
                        x.skipPast("?>");
                    } else {
                        throw x.syntaxError("Misshaped tag");
                    }
                } else if (!(token2 instanceof String)) {
                    throw x.syntaxError(new StringBuffer().append("Bad tagName '").append(token2).append("'.").toString());
                } else {
                    String tagName = (String) token2;
                    JSONArray newja = new JSONArray();
                    JSONObject newjo = new JSONObject();
                    if (arrayForm) {
                        newja.put(tagName);
                        if (ja != null) {
                            ja.put(newja);
                        }
                    } else {
                        newjo.put("tagName", tagName);
                        if (ja != null) {
                            ja.put(newjo);
                        }
                    }
                    Object token5 = null;
                    while (true) {
                        Object nextToken = token5 == null ? x.nextToken() : token5;
                        if (nextToken == null) {
                            throw x.syntaxError("Misshaped tag");
                        }
                        if (nextToken instanceof String) {
                            String attribute = (String) nextToken;
                            if (arrayForm || (!"tagName".equals(attribute) && !"childNode".equals(attribute))) {
                                token5 = x.nextToken();
                                if (token5 == XML.EQ) {
                                    Object token6 = x.nextToken();
                                    if (!(token6 instanceof String)) {
                                        throw x.syntaxError("Missing value");
                                    }
                                    newjo.accumulate(attribute, XML.stringToValue((String) token6));
                                    token5 = null;
                                } else {
                                    newjo.accumulate(attribute, "");
                                }
                            }
                        } else {
                            if (arrayForm && newjo.length() > 0) {
                                newja.put(newjo);
                            }
                            if (nextToken == XML.SLASH) {
                                if (x.nextToken() != XML.GT) {
                                    throw x.syntaxError("Misshaped tag");
                                }
                                if (ja == null) {
                                    return arrayForm ? newja : newjo;
                                }
                            } else if (nextToken != XML.GT) {
                                throw x.syntaxError("Misshaped tag");
                            } else {
                                String closeTag = (String) parse(x, arrayForm, newja);
                                if (closeTag == null) {
                                    continue;
                                } else if (!closeTag.equals(tagName)) {
                                    throw x.syntaxError(new StringBuffer().append("Mismatched '").append(tagName).append("' and '").append(closeTag).append("'").toString());
                                } else {
                                    if (!arrayForm && newja.length() > 0) {
                                        newjo.put("childNodes", newja);
                                    }
                                    if (ja == null) {
                                        return arrayForm ? newja : newjo;
                                    }
                                }
                            }
                        }
                    }
                    throw x.syntaxError("Reserved attribute.");
                }
            } else if (ja != null) {
                if (token instanceof String) {
                    token = XML.stringToValue((String) token);
                }
                ja.put(token);
            }
        }
        throw x.syntaxError("Bad XML");
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return toJSONArray(new XMLTokener(string));
    }

    public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
        return (JSONArray) parse(x, true, null);
    }

    public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
        return (JSONObject) parse(x, false, null);
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return toJSONObject(new XMLTokener(string));
    }

    public static String toString(JSONArray ja) throws JSONException {
        int i;
        StringBuffer sb = new StringBuffer();
        String tagName = ja.getString(0);
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        Object object = ja.opt(1);
        if (object instanceof JSONObject) {
            i = 2;
            JSONObject jo = (JSONObject) object;
            Iterator keys = jo.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                XML.noSpace(key);
                String value = jo.optString(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value));
                    sb.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = ja.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                Object object2 = ja.get(i);
                i++;
                if (object2 != null) {
                    if (object2 instanceof String) {
                        sb.append(XML.escape(object2.toString()));
                        continue;
                    } else if (object2 instanceof JSONObject) {
                        sb.append(toString((JSONObject) object2));
                        continue;
                    } else if (object2 instanceof JSONArray) {
                        sb.append(toString((JSONArray) object2));
                        continue;
                    } else {
                        continue;
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String toString(JSONObject jo) throws JSONException {
        StringBuffer sb = new StringBuffer();
        String tagName = jo.optString("tagName");
        if (tagName == null) {
            return XML.escape(jo.toString());
        }
        XML.noSpace(tagName);
        String tagName2 = XML.escape(tagName);
        sb.append('<');
        sb.append(tagName2);
        Iterator keys = jo.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            if (!"tagName".equals(key) && !"childNodes".equals(key)) {
                XML.noSpace(key);
                String value = jo.optString(key);
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(key));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value));
                    sb.append('\"');
                }
            }
        }
        JSONArray ja = jo.optJSONArray("childNodes");
        if (ja == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            int length = ja.length();
            for (int i = 0; i < length; i++) {
                Object object = ja.get(i);
                if (object != null) {
                    if (object instanceof String) {
                        sb.append(XML.escape(object.toString()));
                    } else if (object instanceof JSONObject) {
                        sb.append(toString((JSONObject) object));
                    } else if (object instanceof JSONArray) {
                        sb.append(toString((JSONArray) object));
                    } else {
                        sb.append(object.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(tagName2);
            sb.append('>');
        }
        return sb.toString();
    }
}
