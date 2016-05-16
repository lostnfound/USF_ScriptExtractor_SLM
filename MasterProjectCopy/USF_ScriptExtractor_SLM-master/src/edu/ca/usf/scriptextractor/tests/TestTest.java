package edu.ca.usf.scriptextractor.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import edu.ca.usf.scriptextractor.AlphanumericUniformProbabilityStrategy;
import edu.ca.usf.scriptextractor.CharacterKeywordTransform;
import edu.ca.usf.scriptextractor.CharacterTransform;
import edu.ca.usf.scriptextractor.CompositeWordTransform;
import edu.ca.usf.scriptextractor.InputTransform;
import edu.ca.usf.scriptextractor.SLM;
import edu.ca.usf.scriptextractor.TermSequence;
import org.apache.commons.*;
import org.apache.commons.lang3.*;
public class TestTest {

	public static void main(String[] args) throws IOException {

		InputTransform benignTransform = new CharacterTransform();
		InputTransform maliciousTransform = new CharacterTransform();
		
		//CompositeWordTransform benignTransform = new CompositeWordTransform();
		//CompositeWordTransform maliciousTransform = new CompositeWordTransform();
		//InputTransform benignTransform = new CharacterKeywordTransform();
		 
		//InputTransform maliciousTransform = new CharacterKeywordTransform();
		
		

		// System.out.println("Benign N-Gram Size, Malicious N-Gram Size, Benign Train Count, Benign Test Count, Malicious ")
		for (int i = 3; i <= 4; i++) {
			//for (int k = 1; k <= 10; k++) {
				// for (int j = 0; j <= 5; j++) {
				run(i, i, 0.8f, 100, benignTransform, maliciousTransform);
			//}
			// }
		}
	}

	private static void run(int benignNGram, int maliciousNGram,
			float trainPercent, int fileLimit, InputTransform benignTransform,
			InputTransform maliciousTransform) throws IOException {
		
		File benignDir = new File("data/scripts/benign/");
		// File benignDir = new File("testData/benign/");
		File maliciousDir = new File("data/scripts/sub/");

		File[] benignFiles = benignDir.listFiles();
		File[] maliciousFiles = maliciousDir.listFiles();
		
		System.out.println(maliciousFiles.length);

		SLM benignModel = new SLM(benignNGram);
		SLM maliciousModel = new SLM(maliciousNGram);
		maliciousModel
				.setQueryStrategry(new AlphanumericUniformProbabilityStrategy());

		List<File> benignTestScripts = new ArrayList<File>();
		List<File> maliciousTestScripts = new ArrayList<File>();

		
		int maliciousTrainCounter = 0;
		for (int i = 2; i < 100 && (i < fileLimit); i++) {
			System.out.println(fileToString(maliciousFiles[i]));
			System.out.println("************************************");
			
		}

		
	}

	public static String fileToString(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		StringBuffer script = new StringBuffer();
		int curChar;
		while ((curChar = fis.read()) != -1) {
			script.append((char) curChar);
		}
		String scriptString = script.toString().replaceAll(
				"<\\/?(script|SCRIPT)[^>]*?>", "");
		System.out.println(scriptString);
		return scriptString;
		// System.out.println(scriptString);
		// return script.toString();
	}

	public static void train(SLM model, File inFile,
			InputTransform inputTransform) throws IOException {
		// WordClassTransform t = new WordClassTransform();
		// CompositeWordTransform t = new CompositeWordTransform();
		// List<WordClass> words = t.transform(inFile);
		// System.out.println(words);
		try {
			model.add(inputTransform.transform(fileToString(inFile)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /model.add(fileToString(inFile));
		// model.add(t.transform(fileToString(inFile)));
	}
}
