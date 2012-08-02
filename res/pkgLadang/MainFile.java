package pkgLadang;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.Display;

public class MainFile extends MIDlet {

	public CvsMain canvas;
	public MainFile() {
		canvas = new CvsMain(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		destroyApp(false);
		notifyDestroyed();
		//
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		Display display = Display.getDisplay(this);
		display.setCurrent(canvas);
		canvas.mulai();

	}

}
