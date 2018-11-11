package bostonhacks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * @author Smit Rao
 * 
 * A client to post to Bose SoundLink 10 based on mp3 url, at specified volume.
 * 
 */
public class BosePoster {
	
	/*
	 * @author Smit Rao
	 * 
	 * Post request made to connected Bose SoundLink 10 speaker at specified volume using mp3
	 * file located at specified url.
	 * 
	 * @param String mp3 - the url where the mp3 resource is located
	 * @param int volume - the volume to play the mp3 file at
	 * 
	 */
	private void postIt(String mp3, int volume) throws MalformedURLException, IOException {
		String url = "http://192.168.1.174:8090/speaker";
		String charset = "UTF-8";

		String query = String.format(
				  "<play_info>"
				+ "<app_key>"
				+ 	"AcRhBy6G7ohA5C55KHJ0Wo5FZecRq3GY"
				+ "</app_key>"
				+ "<url>"
				+ 	mp3
				+ "</url>"
				+ "<service>"
				+ 	"Translation"
				+ "</service>"
				+ "<reason>"
				+ 	"Help reduce language barriers!"
				+ "</reason>"
				+ "<message>"
				+ 	"Don't get lost in translation ;)"
				+ "</message>"
				+ "<volume>"
				+ 	volume
				+ "</volume>"
				+ "</play_info>"
				);

		URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.setUseCaches(false);
		urlConnection.setDoOutput(true); // Triggers POST.
		urlConnection.setRequestProperty("accept-charset", charset);
		urlConnection.setRequestProperty("content-type", "text/xml");

		OutputStreamWriter writer = null;
		try {
		    writer = new OutputStreamWriter(urlConnection.getOutputStream(), charset);
		    writer.write(query); // Write POST query string (if any needed).
		} finally {
		    if (writer != null) try { writer.close(); } catch (IOException logOrIgnore) {}
		}

		InputStream result = urlConnection.getInputStream();
		System.out.println("Finished execution to SoundLink 10: " + result);
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		BosePoster poster = new BosePoster();
		
		poster.postIt("https://www.8notes.com/school/mp32/piano/beethoven_fur_elise_orig.mp3", 25);
	}
	
}
