import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        System.out.println(new Date());
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(new Date());


    }
}
