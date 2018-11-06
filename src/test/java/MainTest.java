 import com.jeancoder.app.sdk.server.DevServer;

public class MainTest {
	public static void main(String[] args) throws Exception {
		String fp = System.getProperty("user.dir");
		DevServer.start(8096, fp);
	}
}
