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

	/**
	 * A test that just makes sure that there are no errors in getting a filepath for data
	 */
	@Test
	public void simpleTest() {
	    MyID3 id3 = new MyID3();
	    // This creates a DecisionTreeData object that you can use for testing.
	    //DecisionTreeData shortData = DataReader.readFile("/course/cs0160/lib/decisiontree-data/short-data-training.csv");
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
	}

	/**
	 * Tests whether the calculateMaxInfoAttribute method works as intended
	 */
	@Test
	public void checkMaxAttribute(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");

		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		assertTrue(id3.calculateMaxInfoAttribute(shortData, shortDataList).getName().equals(" Pat"));
	}

	/**
	 * Tests whether the findMostFrequentClassification method works as intended
	 */
	@Test
	public void checkMostFrequentClassification(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.findMostFrequentClassification(shortData).equals(" true"));
	}

	/**
	 * Tests whether the checkDataEmpty method works as intended
	 */
	@Test
	public void checkDataEmpty(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.checkDataEmpty(shortData) == false);
	}

	/**
	 * Tests whether the checkAttibutesEmpty method works as intended
	 */
	@Test
	public void checkAttributesEmpty(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		assertTrue(id3.checkAttributesEmpty(shortDataList) == false);
	}

	/**
	 * Tests whether the sameClassificationCheck method works as intended
	 */
	@Test
	public void checkSameClassification(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.sameClassificationCheck(shortData) == false);
	}

	/**
	 * Tests whether the entropy calculation method works as intended
	 */
	@Test
	public void checkEntropy(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.calculateEntropy(shortData) == 0.9544340029249649);
	}

	/**
	 * Tests whether the information calculation method works as intended
	 */
	@Test
	public void checkInformation(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		Attribute pat = id3.calculateMaxInfoAttribute(shortData, shortDataList);
		assertTrue(id3.calculateRemainder(shortData, pat) == 0.5);
	}

	/**
	 * Tests whether new data is properly initialized in the newDataInitializer method
	 */
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

	/**
	 * Tests whether the maxInfoAttributeIndex method works as intended
	 */
	@Test
	public void testMaxInfoAttributeIndex(){
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("/Users/Arvind/Desktop/cs16/src/decisiontree/decisiontree-data/short-data-training.csv");
		ArrayList<Attribute> shortDataList = shortData.getAttributeList();
		assertTrue(id3.maxInfoAttributeIndex(shortData, shortDataList) == 4);
	}

}