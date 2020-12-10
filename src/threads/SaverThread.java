package threads;

import ui.Main;

/**
 * The Class SaverThread.
 */
public class SaverThread extends Thread {

	/** The main. */
	private Main main;
	
	/** The stoper. */
	private boolean stoper = false;
	
	/**
	 * Instantiates a new saver thread.
	 *
	 * @param main the main
	 */
	public SaverThread(Main main) {
		this.main = main;
	}
	
	/**
	 * Sets the stoper.
	 *
	 * @param stoper the new stoper
	 */
	public void setStoper(boolean stoper) {
		this.stoper = stoper;
	}


	/**
	 * Run.
	 */
	@Override
	public void run() {
		while(!stoper) {
			try {
				Thread.sleep(5000);
				main.saveData();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
