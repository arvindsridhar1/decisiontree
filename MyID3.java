package decisiontree;

import support.decisiontree.Attribute;
import support.decisiontree.DecisionTreeData;
import support.decisiontree.DecisionTreeNode;
import support.decisiontree.ID3;

import java.util.ArrayList;
import java.util.Set;

/**
  * This class is where your ID3 algorithm should be implemented.
  */
public class MyID3 implements ID3 {

    String[] _classifications;
    String _classification;
    DecisionTreeData _new_data;
    /**
     * Constructor. You don't need to edit this.
     */
    public MyID3() {
    }

    /**
     * This is the trigger method that actually runs the algorithm.
     * This will be called by the visualizer when you click 'train'.
     */
    @Override
    public DecisionTreeNode id3Trigger(DecisionTreeData data) {
        // TODO run the algorithm, return the root of the tree
        _new_data = data;
        _classifications = data.getClassifications();
        ArrayList<Attribute> attributes = data.getAttributeList();
        return this.myID3Algorithm(_new_data, data, attributes);
    }

    /*
     * TODO implement the algorithm - this is one possible method signature, feel free to change!
     */
    private DecisionTreeNode myID3Algorithm(DecisionTreeData data, DecisionTreeData parentData,  ArrayList<Attribute> attributes) {
        DecisionTreeNode node = new DecisionTreeNode();
        if(checkDataEmpty(data)) {
            node.setElement(findMostFrequentClassification(parentData));
            return node;
        }
        else if(sameClassificationCheck(data)){
            node.setElement(_classification);
            return node;
        }
        else if(checkAttributesEmpty(attributes)){
            node.setElement(findMostFrequentClassification(data));
            return node;
        }
        else{
            Attribute maxInfoAttribute = this.calculateMaxInfoAttribute(data, attributes);
            System.out.println(maxInfoAttribute.getName());
            //System.out.println(this.calculateInformationGain(data, maxInfoAttribute));
            node.setElement(maxInfoAttribute.getName());
            Set<String> values = maxInfoAttribute.getValues();
            for (String value: values){
                DecisionTreeData new_data = this.newDataInitializer(data, maxInfoAttribute, value, attributes);
                //System.out.println(value);
                //Check here if there is attribute bug, maybe remove the if statement
                if(attributes.contains(maxInfoAttribute)) {
                    attributes.remove(this.maxInfoAttributeIndex(data, attributes));
                }
                DecisionTreeNode subTree = this.myID3Algorithm(new_data, data, attributes);
                node.addChild(value, subTree);
            }
            return node;
        }
    }

    public String findMostFrequentClassification(DecisionTreeData data){
        String[][] examples = data.getExamples();
        int lastCol = examples[0].length - 1;
        int numFirst = 0;
        int numSecond = 0;
        for (int i = 0; i < examples.length; i++){
            if(examples[i][lastCol].equals(_classifications[0])){
                numFirst += 1;
            }
            if(examples[i][lastCol].equals(_classifications[1])){
                numSecond += 1;
            }
        }
        if(numFirst > numSecond){
            return _classifications[0];
        }
        return _classifications[1];
    }


    public boolean checkDataEmpty(DecisionTreeData data){
        String[][] examples = data.getExamples();
        if(examples.length == 0){
            return true;
        }
        return false;
    }

    public boolean checkAttributesEmpty(ArrayList<Attribute> attributes){
        if(attributes.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean sameClassificationCheck(DecisionTreeData data){
        String[][] examples = data.getExamples();
        int lastCol = examples[0].length - 1;
        String baseClassification = examples[0][lastCol];
        for (int i = 0; i < examples.length; i++) {
            if(!examples[i][lastCol].equals(baseClassification)){
                return false;
            }
        }
        _classification = baseClassification;
        return true;
    }

    public Attribute calculateMaxInfoAttribute(DecisionTreeData data, ArrayList<Attribute> attributes){
        ArrayList<Double> informationGains = new ArrayList<>();
        for (Attribute attribute: attributes){
            informationGains.add(this.calculateInformationGain(data, attribute));
        }
        double maxInfo = informationGains.get(0);
        int newIndex = 0;
        for(int i = 0; i < informationGains.size(); i++){
                if(informationGains.get(i) > maxInfo){
                    maxInfo = informationGains.get(i);
                    newIndex = i;
                }
        }

        return attributes.get(newIndex);
        /**
        for (Attribute attribute: attributes){
            if(this.calculateInformationGain(data, attribute) == maxInfo){
                //Matches an attribute back to it's value, which has been singled out as the max information
                return attribute;
            }
        }
        return null;
         */
    }

    public double calculateInformationGain(DecisionTreeData data, Attribute attribute){
        double informationGain = this.calculateEntropy(data) - this.calculateRemainder(data, attribute);
        System.out.println(this.calculateEntropy(data) + ", " + this.calculateRemainder(data, attribute));
        return informationGain;
    }

    public double calculateEntropy(DecisionTreeData data){
        String[][] examples = data.getExamples();
        int lastCol = examples[0].length - 1;
        double positive = 0;
        double negative = 0;
        for (int i = 0; i < examples.length; i++){
            if(examples[i][lastCol].equals(_classifications[0])){
                positive += 1;
            }
            if(examples[i][lastCol].equals(_classifications[1])){
                negative += 1;
            }
        }
        return calculateEntropyHelper(positive, negative);
    }

    public double calculateEntropyHelper(double positive, double negative){
        double ratio = positive/(positive + negative);
        double entropy = -1 * ((ratio * this.logBaseTwo(ratio)) +
                ((1-ratio)*(this.logBaseTwo(1-ratio))));
        return entropy;
    }

    public double logBaseTwo(double logNumber){
        if(logNumber == 0){
            return 0;
        }
        return Math.log(logNumber) / Math.log(2);
    }

    public double calculateRemainder(DecisionTreeData data, Attribute attribute){
        double remainder = 0;
        String[][] examples = data.getExamples();
        double numExamples = examples.length;
        int lastCol = examples[0].length - 1;
        Set<String> attributeValues = attribute.getValues();
        int attributeColumn = attribute.getColumn();
        for (String value: attributeValues){
            double positive = 0;
            double negative = 0;
            for (int i = 0; i < numExamples; i++) {
                if(examples[i][attributeColumn].equals(value)){
                    if(examples[i][lastCol].equals(_classifications[0])){
                        positive += 1;
                    }
                    if(examples[i][lastCol].equals(_classifications[1])){
                        negative += 1;
                    }
                }
            }
            if(!(positive + negative == 0)){
                double subEntropy = this.calculateEntropyHelper(positive, negative);
                double weight = (positive+negative)/(numExamples);
                remainder += (subEntropy*weight);
            }
        }
        return remainder;
    }

    public DecisionTreeData newDataInitializer(DecisionTreeData data, Attribute attribute, String value, ArrayList<Attribute> attributes){
        String[][] examples = data.getExamples();
        int numNewRows = 0;
        int attributeColumn = attribute.getColumn();
        for(int row=0; row < examples.length; row++){
           if(examples[row][attributeColumn].equals(value)){
               numNewRows += 1;
           }
        }
        String[][] newExamples = new String[numNewRows][examples[0].length];
        int newRows = 0;
        for(int row=0; row < examples.length; row++){
            if(examples[row][attributeColumn].equals(value)){
                newExamples[newRows] = examples[row];
                newRows += 1;
            }
        }
        DecisionTreeData newData = new DecisionTreeData(newExamples, attributes, _classifications);
        return newData;
    }

    public int maxInfoAttributeIndex(DecisionTreeData data, ArrayList<Attribute> attributes){
        for(int i=0; i < attributes.size(); i++){
            if(attributes.get(i).equals(this.calculateMaxInfoAttribute(data, attributes))){
                return i;
            }
        }
        return 0;
    }
}
