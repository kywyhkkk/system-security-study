import javax.crypto.KeyAgreement;
import javax.crypto.KeyPair;
import javax.crypto.KeyPairGenerator;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class DiffieHellman {

    private static final String ALGORITHM = "DH"; // Diffie-Hellman algorithm

    private KeyPairGenerator keyPairGenerator;
    private KeyAgreement keyAgreement;

    public DiffieHellman() throws Exception {

        keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);  // 2048 位的 DH 密钥
        keyAgreement = KeyAgreement.getInstance(ALGORITHM);
    }


    public KeyPair generateKeyPair() throws Exception {
        return keyPairGenerator.generateKeyPair();
    }


    public byte[] generateSharedSecret(PublicKey publicKey, PrivateKey privateKey) throws Exception {
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true); // 执行 Diffie-Hellman 密钥交换
        return keyAgreement.generateSecret();  // 返回共享的秘密密钥
    }


    public PublicKey getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic();
    }
}
