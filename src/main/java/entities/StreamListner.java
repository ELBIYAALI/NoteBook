package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamListner extends Thread {
	StringBuilder strBuilder;
	InputStream is;
	boolean stop = false;

	public void setStrBuilder(StringBuilder strBuilder) {
		this.strBuilder = strBuilder;
	}

	public StringBuilder getStrBuilder() {
		return strBuilder;
	}

	public StreamListner(InputStream input, StringBuilder stringBuilder) {
		is = input;
		strBuilder = stringBuilder;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);

				String line = "";

				while (br.ready() && (line = br.readLine()) != null) {
					strBuilder.append(line);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
				break;
			}

		}
	}
}