package com.iluwatar.model.view.presenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Every instance of this class represents the Model component in the
 * Model-View-Presenter architectural pattern.
 * <p>
 * It is responsible for reading and loading the contents of a given file.
 */
public class FileLoader {

	/**
	 * Indicates if the file is loaded or not.
	 */
	private boolean loaded = false;

	/**
	 * The name of the file that we want to load.
	 */
	private String fileName;

	/**
	 * Loads the data of the file specified.
	 */
	public String loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					this.fileName)));
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}

			this.loaded = true;
			br.close();

			return sb.toString();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Sets the path of the file to be loaded, to the given value.
	 * @param fileName The path of the file to be loaded.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return fileName The path of the file to be loaded.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * @return True, if the file given exists, false otherwise.
	 */
	public boolean fileExists() {
		return new File(this.fileName).exists();
	}

	/**
	 * @return True, if the file is loaded, false otherwise.
	 */
	public boolean isLoaded() {
		return this.loaded;
	}
}
