import android.content.Context;
import java.security.KeyPair;

public class MainActivity {

    public static void main(String[] args) throws Exception {

        Context context = ...; 


        DiffieHellman diffieHellman = new DiffieHellman();


        KeyPair keyPair = diffieHellman.generateKeyPair();


        PublicKey publicKey = diffieHellman.getPublicKey(keyPair);


        PublicKey otherPublicKey = ...;


        byte[] sharedSecret = diffieHellman.generateSharedSecret(otherPublicKey, keyPair.getPrivate());


        SharedPreferencesUtil.saveSharedSecret(context, sharedSecret);


        byte[] retrievedSecret = SharedPreferencesUtil.getSharedSecret(context);
    }
}
