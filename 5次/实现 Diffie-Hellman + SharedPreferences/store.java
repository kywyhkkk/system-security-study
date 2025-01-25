import android.content.Context;
import android.content.SharedPreferences;
import javax.crypto.SecretKey;

public class SharedPreferencesUtil {

    private static final String PREFS_NAME = "SMsPrefs";
    private static final String SECRET_KEY = "shared_secret_key";

    // 存储共享的密钥
    public static void saveSharedSecret(Context context, byte[] secretKey) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SECRET_KEY, new String(secretKey));  // 转换为字符串存储
        editor.apply();
    }

    // 获取存储的共享密钥
    public static byte[] getSharedSecret(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String secretKeyStr = preferences.getString(SECRET_KEY, null);
        return secretKeyStr != null ? secretKeyStr.getBytes() : null;
    }
}
