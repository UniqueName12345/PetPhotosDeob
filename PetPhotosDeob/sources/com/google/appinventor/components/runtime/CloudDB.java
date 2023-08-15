package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.CloudDBJedisListener;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kawa.lang.SyntaxForms;
import org.json.JSONArray;
import org.json.JSONException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.exceptions.JedisNoScriptException;

@UsesPermissions({"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
@DesignerComponent(category = ComponentCategory.STORAGE, description = "Non-visible component allowing you to store data on a Internet connected database server (using Redis software). This allows the users of your App to share data with each other. By default data will be stored in a server maintained by MIT, however you can setup and run your own server. Set the \"RedisServer\" property and \"RedisPort\" Property to access your own server.", designerHelpDescription = "Non-visible component that communicates with CloudDB server to store and retrieve information.", iconName = "images/cloudDB.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@UsesLibraries(libraries = "jedis.jar")
/* loaded from: classes.dex */
public class CloudDB extends AndroidNonvisibleComponent implements Component, OnClearListener, OnDestroyListener, ObservableDataSource<String, Future<YailList>> {
    private static final String APPEND_SCRIPT = "local key = KEYS[1];local toAppend = cjson.decode(ARGV[1]);local project = ARGV[2];local currentValue = redis.call('get', project .. \":\" .. key);local newTable;local subTable = {};local subTable1 = {};if (currentValue == false) then   newTable = {};else   newTable = cjson.decode(currentValue);  if not (type(newTable) == 'table') then     return error('You can only append to a list');  end end table.insert(newTable, toAppend);local newValue = cjson.encode(newTable);redis.call('set', project .. \":\" .. key, newValue);table.insert(subTable1, newValue);table.insert(subTable, key);table.insert(subTable, subTable1);redis.call(\"publish\", project, cjson.encode(subTable));return newValue;";
    private static final String APPEND_SCRIPT_SHA1 = "d6cc0f65b29878589f00564d52c8654967e9bcf8";
    private static final String COMODO_ROOT = "-----BEGIN CERTIFICATE-----\nMIIENjCCAx6gAwIBAgIBATANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJTRTEU\nMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFkZFRydXN0IEV4dGVybmFs\nIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBFeHRlcm5hbCBDQSBSb290\nMB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFowbzELMAkGA1UEBhMCU0Ux\nFDASBgNVBAoTC0FkZFRydXN0IEFCMSYwJAYDVQQLEx1BZGRUcnVzdCBFeHRlcm5h\nbCBUVFAgTmV0d29yazEiMCAGA1UEAxMZQWRkVHJ1c3QgRXh0ZXJuYWwgQ0EgUm9v\ndDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALf3GjPm8gAELTngTlvt\nH7xsD821+iO2zt6bETOXpClMfZOfvUq8k+0DGuOPz+VtUFrWlymUWoCwSXrbLpX9\nuMq/NzgtHj6RQa1wVsfwTz/oMp50ysiQVOnGXw94nZpAPA6sYapeFI+eh6FqUNzX\nmk6vBbOmcZSccbNQYArHE504B4YCqOmoaSYYkKtMsE8jqzpPhNjfzp/haW+710LX\na0Tkx63ubUFfclpxCDezeWWkWaCUN/cALw3CknLa0Dhy2xSoRcRdKn23tNbE7qzN\nE0S3ySvdQwAl+mG5aWpYIxG3pzOPVnVZ9c0p10a3CitlttNCbxWyuHv77+ldU9U0\nWicCAwEAAaOB3DCB2TAdBgNVHQ4EFgQUrb2YejS0Jvf6xCZU7wO94CTLVBowCwYD\nVR0PBAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wgZkGA1UdIwSBkTCBjoAUrb2YejS0\nJvf6xCZU7wO94CTLVBqhc6RxMG8xCzAJBgNVBAYTAlNFMRQwEgYDVQQKEwtBZGRU\ncnVzdCBBQjEmMCQGA1UECxMdQWRkVHJ1c3QgRXh0ZXJuYWwgVFRQIE5ldHdvcmsx\nIjAgBgNVBAMTGUFkZFRydXN0IEV4dGVybmFsIENBIFJvb3SCAQEwDQYJKoZIhvcN\nAQEFBQADggEBALCb4IUlwtYj4g+WBpKdQZic2YR5gdkeWxQHIzZlj7DYd7usQWxH\nYINRsPkyPef89iYTx4AWpb9a/IfPeHmJIZriTAcKhjW88t5RxNKWt9x+Tu5w/Rw5\n6wwCURQtjr0W4MHfRnXnJK3s9EK0hZNwEGe6nQY1ShjTK3rMUUKhemPR5ruhxSvC\nNr4TDea9Y355e6cJDUCrat2PisP29owaQgVR1EX1n6diIWgVIEM8med8vSTYqZEX\nc4g/VhsxOBi0cQ+azcgOno4uG+GMmIPLHzHxREzGBHNJdmAPx/i9F4BrLunMTA5a\nmnkPIAou1Z5jJh5VkpTYghdae9C8x49OhgQ=\n-----END CERTIFICATE-----\n";
    private static final String COMODO_USRTRUST = "-----BEGIN CERTIFICATE-----\nMIIFdzCCBF+gAwIBAgIQE+oocFv07O0MNmMJgGFDNjANBgkqhkiG9w0BAQwFADBv\nMQswCQYDVQQGEwJTRTEUMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFk\nZFRydXN0IEV4dGVybmFsIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBF\neHRlcm5hbCBDQSBSb290MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFow\ngYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5MRQwEgYDVQQHEwtK\nZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBOZXR3b3JrMS4wLAYD\nVQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIICIjAN\nBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sIs9CsVw127c0n00yt\nUINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnGvDoZtF+mvX2do2NC\ntnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQIjy8/hPwhxR79uQf\njtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfbIWax1Jt4A8BQOujM\n8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0tyA9yn8iNK5+O2hm\nAUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97Exwzf4TKuzJM7UXiV\nZ4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNVicQNwZNUMBkTrNN9\nN6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5D9kCnusSTJV882sF\nqV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJWBp/kjbmUZIO8yZ9\nHE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ5lhCLkMaTLTwJUdZ\n+gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzGKAgEJTm4Diup8kyX\nHAc/DVL17e8vgg8CAwEAAaOB9DCB8TAfBgNVHSMEGDAWgBStvZh6NLQm9/rEJlTv\nA73gJMtUGjAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rIDZsswDgYDVR0PAQH/\nBAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAGBgRVHSAAMEQGA1Ud\nHwQ9MDswOaA3oDWGM2h0dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9BZGRUcnVzdEV4\ndGVybmFsQ0FSb290LmNybDA1BggrBgEFBQcBAQQpMCcwJQYIKwYBBQUHMAGGGWh0\ndHA6Ly9vY3NwLnVzZXJ0cnVzdC5jb20wDQYJKoZIhvcNAQEMBQADggEBAJNl9jeD\nlQ9ew4IcH9Z35zyKwKoJ8OkLJvHgwmp1ocd5yblSYMgpEg7wrQPWCcR23+WmgZWn\nRtqCV6mVksW2jwMibDN3wXsyF24HzloUQToFJBv2FAY7qCUkDrvMKnXduXBBP3zQ\nYzYhBx9G/2CkkeFnvN4ffhkUyWNnkepnB2u0j4vAbkN9w6GAbLIevFOFfdyQoaS8\nLe9Gclc1Bb+7RrtubTeZtv8jkpHGbkD4jylW6l/VXxRTrPBPYer3IsynVgviuDQf\nJtl7GQVoP7o81DgGotPmjw7jtHFtQELFhLRAlSv0ZaBIefYdgWOWnU914Ph85I6p\n0fKtirOMxyHNwu8=\n-----END CERTIFICATE-----\n";
    private static final boolean DEBUG = false;
    private static final String DST_ROOT_X3 = "-----BEGIN CERTIFICATE-----\nMIIDSjCCAjKgAwIBAgIQRK+wgNajJ7qJMDmGLvhAazANBgkqhkiG9w0BAQUFADA/\nMSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\nDkRTVCBSb290IENBIFgzMB4XDTAwMDkzMDIxMTIxOVoXDTIxMDkzMDE0MDExNVow\nPzEkMCIGA1UEChMbRGlnaXRhbCBTaWduYXR1cmUgVHJ1c3QgQ28uMRcwFQYDVQQD\nEw5EU1QgUm9vdCBDQSBYMzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\nAN+v6ZdQCINXtMxiZfaQguzH0yxrMMpb7NnDfcdAwRgUi+DoM3ZJKuM/IUmTrE4O\nrz5Iy2Xu/NMhD2XSKtkyj4zl93ewEnu1lcCJo6m67XMuegwGMoOifooUMM0RoOEq\nOLl5CjH9UL2AZd+3UWODyOKIYepLYYHsUmu5ouJLGiifSKOeDNoJjj4XLh7dIN9b\nxiqKqy69cK3FCxolkHRyxXtqqzTWMIn/5WgTe1QLyNau7Fqckh49ZLOMxt+/yUFw\n7BZy1SbsOFU5Q9D8/RhcQPGX69Wam40dutolucbY38EVAjqr2m7xPi71XAicPNaD\naeQQmxkqtilX4+U9m5/wAl0CAwEAAaNCMEAwDwYDVR0TAQH/BAUwAwEB/zAOBgNV\nHQ8BAf8EBAMCAQYwHQYDVR0OBBYEFMSnsaR7LHH62+FLkHX/xBVghYkQMA0GCSqG\nSIb3DQEBBQUAA4IBAQCjGiybFwBcqR7uKGY3Or+Dxz9LwwmglSBd49lZRNI+DT69\nikugdB/OEIKcdBodfpga3csTS7MgROSR6cz8faXbauX+5v3gTt23ADq1cEmv8uXr\nAvHRAosZy5Q6XkjEGB5YGV8eAlrwDPGxrancWYaLbumR9YbK+rlmM6pZW87ipxZz\nR8srzJmwN0jP41ZL9c8PDHIyh8bwRLtTcm1D9SZImlJnt1ir/md2cXjbDaJWFBM5\nJDGFoqgCWjBH4d1QB7wCCZAA62RjYJsWvIjJEubSfZGL+T0yjWW06XyxV3bqxbYo\nOb8VZRzI9neWagqNdwvYkQsEjgfbKbYK7p2CNTUQ\n-----END CERTIFICATE-----\n";
    private static final String LOG_TAG = "CloudDB";
    private static final String MIT_CA = "-----BEGIN CERTIFICATE-----\nMIIFXjCCBEagAwIBAgIJAMLfrRWIaHLbMA0GCSqGSIb3DQEBCwUAMIHPMQswCQYD\nVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJyaWRnZTEuMCwGA1UE\nChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5vbG9neTEZMBcGA1UE\nCxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWREQiBDZXJ0aWZpY2F0\nZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkqhkiG9w0BCQEWC2pp\nc0BtaXQuZWR1MB4XDTE3MTIyMjIyMzkyOVoXDTI3MTIyMDIyMzkyOVowgc8xCzAJ\nBgNVBAYTAlVTMQswCQYDVQQIEwJNQTESMBAGA1UEBxMJQ2FtYnJpZGdlMS4wLAYD\nVQQKEyVNYXNzYWNodXNldHRzIEluc3RpdHV0ZSBvZiBUZWNobm9sb2d5MRkwFwYD\nVQQLExBNSVQgQXBwIEludmVudG9yMSYwJAYDVQQDEx1DbG91ZERCIENlcnRpZmlj\nYXRlIEF1dGhvcml0eTEQMA4GA1UEKRMHRWFzeVJTQTEaMBgGCSqGSIb3DQEJARYL\namlzQG1pdC5lZHUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDHzI3D\nFobNDv2HTWlDdedmbxZIJYSqWlzdRJC3oVJgCubdAs46WJRqUxDRWft9UpYGMKkw\nmYN8mdPby2m5OJagdVIZgnguB71zIQkC8yMzd94FC3gldX5m7R014D/0fkpzvsSt\n6fsNectJT0k7gPELOH6t4u6AUbvIsEX0nNyRWsmA/ucXCsDBwXyBJxfOKIQ9tDI4\n/WfcKk9JDpeMF7RP0CIOtlAPotKIaPoY1W3eMIi/0riOt5vTFsB8pxhxAVy0cfGX\niHukdrAkAJixTgkyS7wzk22xOeXVnRIzAMGK5xHMDw/HRQGTrUGfIXHENV3u+3Ae\nL5/ZoQwyZTixmQNzAgMBAAGjggE5MIIBNTAdBgNVHQ4EFgQUZfMKQXqtC5UJGFrZ\ngZE1nmlx+t8wggEEBgNVHSMEgfwwgfmAFGXzCkF6rQuVCRha2YGRNZ5pcfrfoYHV\npIHSMIHPMQswCQYDVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJy\naWRnZTEuMCwGA1UEChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5v\nbG9neTEZMBcGA1UECxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWRE\nQiBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkq\nhkiG9w0BCQEWC2ppc0BtaXQuZWR1ggkAwt+tFYhoctswDAYDVR0TBAUwAwEB/zAN\nBgkqhkiG9w0BAQsFAAOCAQEAIkKr3eIvwZO6a1Jsh3qXwveVnrqwxYvLw2IhTwNT\n/P6C5jbRnzUuDuzg5sEIpbBo/Bp3qIp7G5cdVOkIrqO7uCp6Kyc7d9lPsEe/cbF4\naNwNmdWroRN1y0tuMU6+z7frd5pOeAZP9E/DM/0Uaz4yVzwnlvZUttaLymyMhH54\nisGQKbAqHDFtKZvb6DxsHzrO2YgeaBAtjeVhPWiv8BhzbOo9+hhZvYHYtoM2W+Ze\nDHuvv0v+qouphftDKVBp16N8Pk5WgabTXzV6VcNee92iwbWYDEv06+S3AF/q2TBe\nxxXtAa5ywbp6IRF37QuQChcYnOx7zIylYI1PIENfQFC2BA==\n-----END CERTIFICATE-----\n";
    private static final String POP_FIRST_SCRIPT = "local key = KEYS[1];local project = ARGV[1];local currentValue = redis.call('get', project .. \":\" .. key);local decodedValue = cjson.decode(currentValue);local subTable = {};local subTable1 = {};if (type(decodedValue) == 'table') then   local removedValue = table.remove(decodedValue, 1);  local newValue = cjson.encode(decodedValue);  if (newValue == \"{}\") then     newValue = \"[]\"   end   redis.call('set', project .. \":\" .. key, newValue);  table.insert(subTable, key);  table.insert(subTable1, newValue);  table.insert(subTable, subTable1);  redis.call(\"publish\", project, cjson.encode(subTable));  return cjson.encode(removedValue);else   return error('You can only remove elements from a list');end";
    private static final String POP_FIRST_SCRIPT_SHA1 = "68a7576e7dc283a8162d01e3e7c2d5c4ab3ff7a5";
    private static final String SET_SUB_SCRIPT = "local key = KEYS[1];local value = ARGV[1];local topublish = cjson.decode(ARGV[2]);local project = ARGV[3];local newtable = {};table.insert(newtable, key);table.insert(newtable, topublish);redis.call(\"publish\", project, cjson.encode(newtable));return redis.call('set', project .. \":\" .. key, value);";
    private static final String SET_SUB_SCRIPT_SHA1 = "765978e4c340012f50733280368a0ccc4a14dfb7";
    private Jedis INSTANCE;
    private SSLSocketFactory SslSockFactory;
    private final Activity activity;
    private Handler androidUIHandler;
    private volatile ExecutorService background;
    private ConnectivityManager cm;
    private volatile CloudDBJedisListener currentListener;
    private HashSet<DataSourceChangeListener> dataSourceObservers;
    private volatile boolean dead;
    private String defaultRedisServer;
    private boolean importProject;
    private boolean isPublic;
    private volatile boolean listenerRunning;
    private String projectID;
    private volatile int redisPort;
    private volatile String redisServer;
    private volatile boolean shutdown;
    private final List<storedValue> storeQueue;
    private String token;
    private boolean useDefault;
    private volatile boolean useSSL;

    /* loaded from: classes.dex */
    private static class storedValue {
        private String tag;
        private JSONArray valueList;

        storedValue(String tag, JSONArray valueList) {
            this.tag = tag;
            this.valueList = valueList;
        }

        public String getTag() {
            return this.tag;
        }

        public JSONArray getValueList() {
            return this.valueList;
        }
    }

    public CloudDB(ComponentContainer container) {
        super(container.$form());
        this.importProject = false;
        this.projectID = "";
        this.token = "";
        this.isPublic = false;
        this.dead = false;
        this.defaultRedisServer = null;
        this.useDefault = true;
        this.INSTANCE = null;
        this.redisServer = "DEFAULT";
        this.useSSL = true;
        this.shutdown = false;
        this.SslSockFactory = null;
        this.listenerRunning = false;
        this.background = Executors.newSingleThreadExecutor();
        this.storeQueue = Collections.synchronizedList(new ArrayList());
        this.dataSourceObservers = new HashSet<>();
        this.androidUIHandler = new Handler();
        this.activity = container.$context();
        this.projectID = "";
        this.token = "";
        this.redisPort = 6381;
        this.cm = (ConnectivityManager) this.form.$context().getSystemService("connectivity");
    }

    public void Initialize() {
        if (this.currentListener == null) {
            startListener();
        }
        this.form.registerForOnClear(this);
        this.form.registerForOnDestroy(this);
    }

    private void stopListener() {
        if (this.currentListener != null) {
            this.currentListener.terminate();
            this.currentListener = null;
            this.listenerRunning = false;
        }
    }

    @Override // com.google.appinventor.components.runtime.OnClearListener
    public void onClear() {
        this.shutdown = true;
        flushJedis(false);
    }

    @Override // com.google.appinventor.components.runtime.OnDestroyListener
    public void onDestroy() {
        onClear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void startListener() {
        if (!this.listenerRunning) {
            this.listenerRunning = true;
            Thread t = new Thread() { // from class: com.google.appinventor.components.runtime.CloudDB.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Jedis jedis = CloudDB.this.getJedis(true);
                    if (jedis != null) {
                        try {
                            CloudDB.this.currentListener = new CloudDBJedisListener(CloudDB.this);
                            jedis.subscribe(CloudDB.this.currentListener, new String[]{CloudDB.this.projectID});
                        } catch (Exception e) {
                            Log.e(CloudDB.LOG_TAG, "Error in listener thread", e);
                            try {
                                jedis.close();
                            } catch (Exception e2) {
                            }
                            try {
                                Thread.sleep(3000L);
                            } catch (InterruptedException e3) {
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(3000L);
                        } catch (InterruptedException e4) {
                        }
                    }
                    CloudDB.this.listenerRunning = false;
                    if (!CloudDB.this.dead && !CloudDB.this.shutdown) {
                        CloudDB.this.startListener();
                    }
                }
            };
            t.start();
        }
    }

    @DesignerProperty(defaultValue = "DEFAULT", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void RedisServer(String servername) {
        if (servername.equals("DEFAULT")) {
            if (!this.useDefault) {
                this.useDefault = true;
                if (this.defaultRedisServer != null) {
                    this.redisServer = this.defaultRedisServer;
                }
                flushJedis(true);
                return;
            }
            return;
        }
        this.useDefault = false;
        if (!servername.equals(this.redisServer)) {
            this.redisServer = servername;
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Redis Server to use to store data. A setting of \"DEFAULT\" means that the MIT server will be used.")
    public String RedisServer() {
        return this.redisServer.equals(this.defaultRedisServer) ? "DEFAULT" : this.redisServer;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Default Redis Server to use.", userVisible = false)
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void DefaultRedisServer(String server) {
        this.defaultRedisServer = server;
        if (this.useDefault) {
            this.redisServer = server;
        }
    }

    @DesignerProperty(defaultValue = "6381", editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER)
    public void RedisPort(int port) {
        if (port != this.redisPort) {
            this.redisPort = port;
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The Redis Server port to use. Defaults to 6381")
    public int RedisPort() {
        return this.redisPort;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets the ProjectID for this CloudDB project.")
    public String ProjectID() {
        checkProjectIDNotBlank();
        return this.projectID;
    }

    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ProjectID(String id) {
        if (!this.projectID.equals(id)) {
            this.projectID = id;
        }
        if (this.projectID.equals("")) {
            throw new RuntimeException("CloudDB ProjectID property cannot be blank.");
        }
    }

    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Token(String authToken) {
        if (!this.token.equals(authToken)) {
            this.token = authToken;
        }
        if (this.token.equals("")) {
            throw new RuntimeException("CloudDB Token property cannot be blank.");
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This field contains the authentication token used to login to the backed Redis server. For the \"DEFAULT\" server, do not edit this value, the system will fill it in for you. A system administrator may also provide a special value to you which can be used to share data between multiple projects from multiple people. If using your own Redis server, set a password in the server's config and enter it here.", userVisible = false)
    public String Token() {
        checkProjectIDNotBlank();
        return this.token;
    }

    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void UseSSL(boolean useSSL) {
        if (this.useSSL != useSSL) {
            this.useSSL = useSSL;
            flushJedis(true);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set to true to use SSL to talk to CloudDB/Redis server. This should be set to True for the \"DEFAULT\" server.", userVisible = false)
    public boolean UseSSL() {
        return this.useSSL;
    }

    @SimpleFunction(description = "Store a value at a tag.")
    public void StoreValue(String tag, Object valueToStore) {
        String value;
        boolean isConnected = false;
        checkProjectIDNotBlank();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isConnected = true;
        }
        if (valueToStore != null) {
            try {
                String strval = valueToStore.toString();
                if (strval.startsWith("file:///") || strval.startsWith("/storage")) {
                    value = JsonUtil.getJsonRepresentation(readFile(strval));
                } else {
                    value = JsonUtil.getJsonRepresentation(valueToStore);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        } else {
            value = "";
        }
        if (isConnected) {
            synchronized (this.storeQueue) {
                boolean kickit = false;
                if (this.storeQueue.size() == 0) {
                    kickit = true;
                }
                JSONArray valueList = new JSONArray();
                try {
                    valueList.put(0, value);
                    storedValue work = new storedValue(tag, valueList);
                    this.storeQueue.add(work);
                    if (kickit) {
                        this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.2
                            @Override // java.lang.Runnable
                            public void run() {
                                storedValue work2;
                                JSONArray pendingValueList = null;
                                String pendingTag = null;
                                String pendingValue = null;
                                while (true) {
                                    try {
                                        synchronized (CloudDB.this.storeQueue) {
                                            int size = CloudDB.this.storeQueue.size();
                                            if (size != 0) {
                                                work2 = (storedValue) CloudDB.this.storeQueue.remove(0);
                                            } else {
                                                work2 = null;
                                            }
                                        }
                                        if (work2 == null) {
                                            break;
                                        }
                                        String tag2 = work2.getTag();
                                        JSONArray valueList2 = work2.getValueList();
                                        if (tag2 == null || valueList2 == null) {
                                        }
                                        if (pendingTag == null) {
                                            pendingTag = tag2;
                                            pendingValueList = valueList2;
                                            pendingValue = valueList2.getString(0);
                                        } else if (pendingTag.equals(tag2)) {
                                            pendingValue = valueList2.getString(0);
                                            pendingValueList.put(pendingValue);
                                        } else {
                                            try {
                                                String jsonValueList = pendingValueList.toString();
                                                CloudDB.this.jEval(CloudDB.SET_SUB_SCRIPT, CloudDB.SET_SUB_SCRIPT_SHA1, 1, pendingTag, pendingValue, jsonValueList, CloudDB.this.projectID);
                                                pendingTag = tag2;
                                                pendingValueList = valueList2;
                                                pendingValue = valueList2.getString(0);
                                            } catch (JedisException e2) {
                                                CloudDB.this.CloudDBError(e2.getMessage());
                                                CloudDB.this.flushJedis(true);
                                                CloudDB.this.storeQueue.clear();
                                                return;
                                            }
                                        }
                                    } catch (Exception e3) {
                                        Log.e(CloudDB.LOG_TAG, "Exception in store worker!", e3);
                                        return;
                                    }
                                    Log.e(CloudDB.LOG_TAG, "Exception in store worker!", e3);
                                    return;
                                }
                                if (pendingTag != null) {
                                    try {
                                        String jsonValueList2 = pendingValueList.toString();
                                        CloudDB.this.jEval(CloudDB.SET_SUB_SCRIPT, CloudDB.SET_SUB_SCRIPT_SHA1, 1, pendingTag, pendingValue, jsonValueList2, CloudDB.this.projectID);
                                        CloudDB.this.UpdateDone(pendingTag, "StoreValue");
                                    } catch (JedisException e4) {
                                        CloudDB.this.CloudDBError(e4.getMessage());
                                        CloudDB.this.flushJedis(true);
                                    }
                                }
                            }
                        });
                    }
                } catch (JSONException e2) {
                    throw new YailRuntimeError("JSON Error putting value.", "value is not convertable");
                }
            }
            return;
        }
        CloudDBError("Cannot store values off-line.");
    }

    @SimpleFunction(description = "Get the Value for a tag, doesn't return the value but will cause a GotValue event to fire when the value is looked up.")
    public void GetValue(final String tag, final Object valueIfTagNotThere) {
        checkProjectIDNotBlank();
        new AtomicReference();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (isConnected) {
            this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.3
                @Override // java.lang.Runnable
                public void run() {
                    final AtomicReference<Object> value = CloudDB.this.getValueByTag(tag, valueIfTagNotThere);
                    if (value.get() != null) {
                        CloudDB.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CloudDB.this.GotValue(tag, value.get());
                            }
                        });
                    }
                }
            });
        } else {
            CloudDBError("Cannot fetch variables while off-line.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AtomicReference<Object> getValueByTag(String tag, Object valueIfTagNotThere) {
        AtomicReference<Object> value = new AtomicReference<>();
        Jedis jedis = getJedis();
        try {
            String returnValue = jedis.get(this.projectID + ":" + tag);
            if (returnValue != null) {
                String val = JsonUtil.getJsonRepresentationIfValueFileName(this.form, returnValue);
                if (val != null) {
                    value.set(val);
                } else {
                    value.set(returnValue);
                }
            } else {
                value.set(JsonUtil.getJsonRepresentation(valueIfTagNotThere));
            }
        } catch (NullPointerException e) {
            CloudDBError("System Error getting tag " + tag);
            flushJedis(true);
            value.set(null);
        } catch (JSONException e2) {
            CloudDBError("JSON conversion error for " + tag);
            value.set(null);
        } catch (JedisException e3) {
            Log.e(LOG_TAG, "Exception in GetValue", e3);
            CloudDBError(e3.getMessage());
            flushJedis(true);
            value.set(null);
        }
        return value;
    }

    @SimpleFunction(description = "returns True if we are on the network and will likely be able to connect to the CloudDB server.")
    public boolean CloudConnected() {
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @SimpleEvent(description = "Event triggered by the \"RemoveFirstFromList\" function. The argument \"value\" is the object that was the first in the list, and which is now removed.")
    public void FirstRemoved(Object value) {
        checkProjectIDNotBlank();
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value, true);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "error while converting to JSON...", e);
                return;
            }
        }
        final Object sValue = value;
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.4
            @Override // java.lang.Runnable
            public void run() {
                EventDispatcher.dispatchEvent(CloudDB.this, "FirstRemoved", sValue);
            }
        });
    }

    @SimpleFunction(description = "Return the first element of a list and atomically remove it. If two devices use this function simultaneously, one will get the first element and the the other will get the second element, or an error if there is no available element. When the element is available, the \"FirstRemoved\" event will be triggered.")
    public void RemoveFirstFromList(final String tag) {
        checkProjectIDNotBlank();
        this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.5
            @Override // java.lang.Runnable
            public void run() {
                CloudDB.this.getJedis();
                try {
                    CloudDB.this.FirstRemoved(CloudDB.this.jEval(CloudDB.POP_FIRST_SCRIPT, CloudDB.POP_FIRST_SCRIPT_SHA1, 1, tag, CloudDB.this.projectID));
                } catch (JedisException e) {
                    CloudDB.this.CloudDBError(e.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleFunction(description = "Append a value to the end of a list atomically. If two devices use this function simultaneously, both will be appended and no data lost.")
    public void AppendValueToList(final String tag, Object itemToAdd) {
        checkProjectIDNotBlank();
        Object itemObject = new Object();
        if (itemToAdd != null) {
            try {
                itemObject = JsonUtil.getJsonRepresentation(itemToAdd);
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
            }
        }
        final String item = (String) itemObject;
        this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.6
            @Override // java.lang.Runnable
            public void run() {
                CloudDB.this.getJedis();
                try {
                    CloudDB.this.jEval(CloudDB.APPEND_SCRIPT, CloudDB.APPEND_SCRIPT_SHA1, 1, tag, item, CloudDB.this.projectID);
                    CloudDB.this.UpdateDone(tag, "AppendValueToList");
                } catch (JedisException e2) {
                    CloudDB.this.CloudDBError(e2.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleEvent
    public void GotValue(String tag, Object value) {
        checkProjectIDNotBlank();
        if (value == null) {
            CloudDBError("Trouble getting " + tag + " from the server.");
            return;
        }
        if (value != null) {
            try {
                if (value instanceof String) {
                    value = JsonUtil.getObjectFromJson((String) value, true);
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        notifyDataObservers(tag, value);
        EventDispatcher.dispatchEvent(this, "GotValue", tag, value);
    }

    @SimpleFunction(description = "Remove the tag from CloudDB.")
    public void ClearTag(final String tag) {
        checkProjectIDNotBlank();
        this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Jedis jedis = CloudDB.this.getJedis();
                    jedis.del(CloudDB.this.projectID + ":" + tag);
                    CloudDB.this.notifyDataObservers(tag, (Object) null);
                    CloudDB.this.UpdateDone(tag, "ClearTag");
                } catch (Exception e) {
                    CloudDB.this.CloudDBError(e.getMessage());
                    CloudDB.this.flushJedis(true);
                }
            }
        });
    }

    @SimpleEvent
    public void UpdateDone(final String tag, final String operation) {
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.8
            @Override // java.lang.Runnable
            public void run() {
                EventDispatcher.dispatchEvent(CloudDB.this, "UpdateDone", tag, operation);
            }
        });
    }

    @SimpleFunction(description = "Get the list of tags for this application. When complete a \"TagList\" event will be triggered with the list of known tags.")
    public void GetTagList() {
        checkProjectIDNotBlank();
        NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        if (isConnected) {
            this.background.submit(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.9
                @Override // java.lang.Runnable
                public void run() {
                    Jedis jedis = CloudDB.this.getJedis();
                    try {
                        Set<String> value = jedis.keys(CloudDB.this.projectID + ":*");
                        final List<String> listValue = new ArrayList<>(value);
                        for (int i = 0; i < listValue.size(); i++) {
                            listValue.set(i, listValue.get(i).substring((CloudDB.this.projectID + ":").length()));
                        }
                        CloudDB.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.9.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CloudDB.this.TagList(listValue);
                            }
                        });
                    } catch (JedisException e) {
                        CloudDB.this.CloudDBError(e.getMessage());
                        CloudDB.this.flushJedis(true);
                    }
                }
            });
        } else {
            CloudDBError("Not connected to the Internet, cannot list tags");
        }
    }

    @SimpleEvent(description = "Event triggered when we have received the list of known tags. Used with the \"GetTagList\" Function.")
    public void TagList(List<String> value) {
        checkProjectIDNotBlank();
        EventDispatcher.dispatchEvent(this, "TagList", value);
    }

    @SimpleEvent(description = "Event indicating that CloudDB data has changed for the given tag and value.")
    public void DataChanged(final String tag, Object value) {
        final Object finalTagValue;
        if (value != null) {
            try {
                if (value instanceof String) {
                    Object tagValue = JsonUtil.getObjectFromJson((String) value, true);
                    finalTagValue = tagValue;
                    notifyDataObservers(tag, finalTagValue);
                    this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.10
                        @Override // java.lang.Runnable
                        public void run() {
                            EventDispatcher.dispatchEvent(CloudDB.this, "DataChanged", tag, finalTagValue);
                        }
                    });
                }
            } catch (JSONException e) {
                throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Retrieval Error.");
            }
        }
        finalTagValue = "";
        notifyDataObservers(tag, finalTagValue);
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.10
            @Override // java.lang.Runnable
            public void run() {
                EventDispatcher.dispatchEvent(CloudDB.this, "DataChanged", tag, finalTagValue);
            }
        });
    }

    @SimpleEvent(description = "Indicates that an error occurred while communicating with the CloudDB Redis server.")
    public void CloudDBError(final String message) {
        Log.e(LOG_TAG, message);
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.11
            @Override // java.lang.Runnable
            public void run() {
                boolean dispatched = EventDispatcher.dispatchEvent(CloudDB.this, "CloudDBError", message);
                if (!dispatched) {
                    new Notifier(CloudDB.this.form).ShowAlert("CloudDBError: " + message);
                }
            }
        });
    }

    private void checkProjectIDNotBlank() {
        if (this.projectID.equals("")) {
            throw new RuntimeException("CloudDB ProjectID property cannot be blank.");
        }
        if (this.token.equals("")) {
            throw new RuntimeException("CloudDB Token property cannot be blank");
        }
    }

    public Form getForm() {
        return this.form;
    }

    public Jedis getJedis(boolean createNew) {
        String jToken;
        if (this.dead) {
            return null;
        }
        try {
            if (this.token != null && !this.token.equals("") && this.token.substring(0, 1).equals("%")) {
                jToken = this.token.substring(1);
            } else {
                jToken = this.token;
            }
            if (this.useSSL) {
                ensureSslSockFactory();
                JedisShardInfo jedisinfo = new JedisShardInfo(this.redisServer, this.redisPort, 20000, true, this.SslSockFactory, (SSLParameters) null, (HostnameVerifier) null);
                jedisinfo.setPassword(jToken);
                return new Jedis(jedisinfo);
            }
            JedisShardInfo jedisinfo2 = new JedisShardInfo(this.redisServer, this.redisPort, 20000);
            jedisinfo2.setPassword(jToken);
            return new Jedis(jedisinfo2);
        } catch (JedisConnectionException e) {
            Log.e(LOG_TAG, "in getJedis()", e);
            CloudDBError(e.getMessage());
            return null;
        } catch (JedisDataException e2) {
            Log.e(LOG_TAG, "in getJedis()", e2);
            CloudDBError(e2.getMessage() + " CloudDB disabled, restart to re-enable.");
            this.dead = true;
            return null;
        }
    }

    public synchronized Jedis getJedis() {
        if (this.INSTANCE == null) {
            this.INSTANCE = getJedis(true);
        }
        return this.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushJedis(boolean restartListener) {
        if (this.INSTANCE != null) {
            try {
                this.INSTANCE.close();
            } catch (Exception e) {
            }
            this.INSTANCE = null;
            this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.CloudDB.12
                @Override // java.lang.Runnable
                public void run() {
                    CloudDB.this.background.shutdownNow();
                    CloudDB.this.background = Executors.newSingleThreadExecutor();
                }
            });
            stopListener();
            if (restartListener) {
                startListener();
            }
        }
    }

    private YailList readFile(String fileName) {
        try {
            if (fileName.startsWith("file://")) {
                fileName = fileName.substring(7);
            }
            if (!fileName.startsWith("/")) {
                throw new YailRuntimeError("Invalid fileName, was " + fileName, "ReadFrom");
            }
            String extension = getFileExtension(fileName);
            byte[] content = FileUtil.readFile(this.form, fileName);
            String encodedContent = Base64.encodeToString(content, 0);
            Object[] results = {"." + extension, encodedContent};
            return YailList.makeList(results);
        } catch (FileNotFoundException e) {
            throw new YailRuntimeError(e.getMessage(), "Read");
        } catch (IOException e2) {
            throw new YailRuntimeError(e2.getMessage(), "Read");
        }
    }

    private String getFileExtension(String fullName) {
        String fileName = new java.io.File(fullName).getName();
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public ExecutorService getBackground() {
        return this.background;
    }

    public Object jEval(String script, String scriptsha1, int argcount, String... args) throws JedisException {
        Jedis jedis = getJedis();
        try {
            return jedis.evalsha(scriptsha1, argcount, args);
        } catch (JedisNoScriptException e) {
            return jedis.eval(script, argcount, args);
        }
    }

    private synchronized void ensureSslSockFactory() {
        X509Certificate[] systemCertificates;
        if (this.SslSockFactory == null) {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream caInput = new ByteArrayInputStream(COMODO_ROOT.getBytes("UTF-8"));
                Certificate ca = cf.generateCertificate(caInput);
                caInput.close();
                ByteArrayInputStream caInput2 = new ByteArrayInputStream(COMODO_USRTRUST.getBytes("UTF-8"));
                Certificate inter = cf.generateCertificate(caInput2);
                caInput2.close();
                ByteArrayInputStream caInput3 = new ByteArrayInputStream(DST_ROOT_X3.getBytes("UTF-8"));
                Certificate dstx3 = cf.generateCertificate(caInput3);
                caInput3.close();
                ByteArrayInputStream caInput4 = new ByteArrayInputStream(MIT_CA.getBytes("UTF-8"));
                Certificate mitca = cf.generateCertificate(caInput4);
                caInput4.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                int count = 1;
                for (X509Certificate cert : getSystemCertificates()) {
                    keyStore.setCertificateEntry("root" + count, cert);
                    count++;
                }
                keyStore.setCertificateEntry("comodo", ca);
                keyStore.setCertificateEntry("inter", inter);
                keyStore.setCertificateEntry("dstx3", dstx3);
                keyStore.setCertificateEntry("mitca", mitca);
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, tmf.getTrustManagers(), null);
                this.SslSockFactory = ctx.getSocketFactory();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not setup SSL Trust Store for CloudDB", e);
                throw new YailRuntimeError("Could Not setup SSL Trust Store for CloudDB: ", e.getMessage());
            }
        }
    }

    private X509Certificate[] getSystemCertificates() {
        try {
            TrustManagerFactory otmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            otmf.init((KeyStore) null);
            X509TrustManager otm = (X509TrustManager) otmf.getTrustManagers()[0];
            return otm.getAcceptedIssuers();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Getting System Certificates", e);
            return new X509Certificate[0];
        }
    }

    @Override // com.google.appinventor.components.runtime.DataSource
    public Future<YailList> getDataValue(final String key) {
        return this.background.submit(new Callable<YailList>() { // from class: com.google.appinventor.components.runtime.CloudDB.13
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public YailList call() {
                AtomicReference<Object> valueReference = CloudDB.this.getValueByTag(key, new YailList());
                String valueString = (String) valueReference.get();
                Object value = JsonUtil.getObjectFromJson(valueString);
                return value instanceof YailList ? (YailList) value : YailList.makeEmptyList();
            }
        });
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void addDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.add(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void notifyDataObservers(String key, Object newValue) {
        Iterator<DataSourceChangeListener> it = this.dataSourceObservers.iterator();
        while (it.hasNext()) {
            DataSourceChangeListener dataComponent = it.next();
            dataComponent.onDataSourceValueChange(this, key, newValue);
        }
    }
}
