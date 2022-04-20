import com.ahlsort.api.IMessageCallback;

public class AhlsortMessageLogger implements IMessageCallback {
	@Override
	public void onMessage(String messageText) {
		System.out.println(messageText);				
	}
}
