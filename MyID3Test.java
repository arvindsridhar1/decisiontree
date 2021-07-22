package decisiontree;

import org.junit.Test;
import support.decisiontree.Attribute;
import support.decisiontree.DataReader;
import support.decisiontree.DecisionTreeData;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

/**
 * This class can be used to test the functionality of your MyID3 implementation.
 * Use the Heap stencil and your heap tests as a guide!
 * 
 */

public class MyID3Test {
	
	@Test
	public void simpleTest() {
	    
	    MyID3 id3 = new MyID3();

	    // This creates a DecisionTreeData object that you can use for testing.
	    //DecisionTreeData shortData = DataReader.readFile("/course/cs0160/lib/decisiontree-data/short-data-training.csv");
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
	    
	}
	
	/**
	 * TODO: add your tests below!
	 */

	@Test
	public void checkMaxAttribute(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");

		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		assertTrue(id3.calculateMaxInfoAttribute(shortData, shortDataList).getName().equals(" Pat"));
	}

	@Test
	public void checkMostFrequentClassificiation(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.findMostFrequentClassification(shortData).equals(" true"));
	}

	@Test
	public void checkDataEmpty(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.checkDataEmpty(shortData) == false);
	}

	@Test
	public void checkAttributesEmpty(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		assertTrue(id3.checkAttributesEmpty(shortDataList) == false);
	}

	@Test
	public void checkSameClassification(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.sameClassificationCheck(shortData) == false);
	}

	@Test
	public void checkEntropy(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.calculateEntropy(shortData) == 0.9544340029249649);
	}

	@Test
	public void checkInformation(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		Attribute pat = id3.calculateMaxInfoAttribute(shortData, shortDataList);
		assertTrue(id3.calculateRemainder(shortData, pat) == 0.5);
	}

	@Test
	public void checkNewDataInitializer(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		Attribute pat = id3.calculateMaxInfoAttribute(shortData, shortDataList);
		DecisionTreeData testData = id3.newDataInitializer(shortData, pat, " None", shortDataList);
		assertTrue(testData.getExamples().length == 1);

		DecisionTreeData newTestData = id3.newDataInitializer(shortData, pat, " Full", shortDataList);
		assertTrue(newTestData.getExamples().length == 4);

	}


}