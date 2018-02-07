
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ashwini Shankar Narayan
 */
public class Main {
	public static List<Integer> getInt(int datlen) throws IOException {
		
		List<Integer> to_return = new ArrayList<Integer>();
		URL url = new URL("https://www.random.org/integers/?num=" + datlen
				+ "&min=0&max=10000&col=1&base=10&format=plain&rnd=new");
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(http.getInputStream()));
		String line;
		int pr = 0;

		line = rd.readLine();

		while (line != null) {
			pr = Integer.parseInt(line);
			to_return.add(pr);
			line = rd.readLine();
		}

		rd.close();
		return to_return;
	}

	public static void main(String[] args) throws IOException {

		List<Integer> randomIds = new ArrayList<Integer>();
		int lim = 128 * 128;
		int X = 10000;
		// Extracting height X width number of random numbers to generate a random Image
		// in chunks of 10000 as its the limit
		while (randomIds.size() < 128 * 128) {
			if (lim > X) {
				randomIds.addAll(getInt(X));
				lim = lim - X;
			} else {
				randomIds.addAll(getInt(lim));
				lim = lim - lim;
			}
		}

		//System.out.println("Random integers   Size   " + randomIds.size());

		int width = 128;
		int height = 128;

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		File f = null;
		int k = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// each number is within 0-10000 in value and we generate 3 different values
				// from one random value by using different divisors
				int r = (int) (Math.floor(randomIds.get(k) / 2)) % 255;
				int g = (int) (Math.floor(randomIds.get(k) / 3)) % 255;
				int b = (int) (Math.floor(randomIds.get(k) / 4)) % 255;
				k = k + 1;
				// Shifting RGB values to create 24bit value for Buffer Image
				int p = (r << 16 | g << 8 | b);

				img.setRGB(j, i, p);
			}
		}
		// Saving the random Image to file
		try {
			f = new File("/Users/ashwinivishwas/Output.png");
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}

	}
}
