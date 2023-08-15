package com.google.appinventor.common.version;

/* loaded from: classes.dex */
public final class GitBuildId {
    public static final String ACRA_URI = "${acra.uri}";
    public static final String ANT_BUILD_DATE = "June 17 2023";
    public static final String GIT_BUILD_FINGERPRINT = "02a57c75eea79a2169bd3ed1f2604a7a27db3c6b";
    public static final String GIT_BUILD_VERSION = "nb193";

    private GitBuildId() {
    }

    public static String getVersion() {
        if (GIT_BUILD_VERSION != "" && !GIT_BUILD_VERSION.contains(" ")) {
            return GIT_BUILD_VERSION;
        }
        return "none";
    }

    public static String getFingerprint() {
        return GIT_BUILD_FINGERPRINT;
    }

    public static String getDate() {
        return ANT_BUILD_DATE;
    }

    public static String getAcraUri() {
        return ACRA_URI.equals(ACRA_URI) ? "" : ACRA_URI.trim();
    }
}
