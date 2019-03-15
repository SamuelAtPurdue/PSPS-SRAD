package application;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Timeline{
	
	private static int currentPos;
	private static double minTime;
	private static double maxTime;
	private static ArrayList<Double> timeList = new ArrayList<Double>();
	private static ArrayList<Double> timeDistance = new ArrayList<Double>();
	private static ObservableList<JSONObject> list = FXCollections.observableArrayList();
	
	public Timeline() {

	}

	public JSONObject get(int i){
		return list.get(i);
	}

	public void add(JSONObject j){
		list.add(j);
	}

	public int size(){
		return list.size();
	}



	public void generateTimeline(List<File> fileList) {
		JSONParser jsonParser = new JSONParser();
		for(int i = 0; i < fileList.size(); i++) {
			try{
				System.out.println(fileList.get(i).getPath());
				FileReader reader = new FileReader(fileList.get(i).getPath());
				Object obj = jsonParser.parse(reader);
				JSONObject jsonObject = (JSONObject) obj;
				this.add(jsonObject);
				if (i == 0) {
					maxTime = Double.parseDouble(jsonObject.get("timestamp").toString());
				}else if (i == fileList.size() - 1) {
					maxTime = Double.parseDouble(jsonObject.get("timestamp").toString());
				}
				timeList.add(Double.parseDouble(jsonObject.get("timestamp").toString()));
			}catch(IOException e) {
				System.out.println("File not found");
			}catch(ParseException e) {
				System.out.println("Parse Exception");
			}catch(Exception e) {
				System.out.println("Exception");
			}
		}
	}
	
	public void parseFile(JSONObject jsonFile) {
		JSONObject moment = (JSONObject) jsonFile.get("moment");
		this.add(moment);
	}
	
	public int getClosestMoment(double sliderPos) {
		double minDist = Double.MAX_VALUE;
		int minPos = 0;
		timeDistance.clear();
		for(int i = 0; i < timeList.size(); i++) {
			timeDistance.add(Math.abs(timeList.get(i) - ((maxTime - minTime) * sliderPos + minTime)));
			if (timeDistance.get(i) < minDist){
				minPos = i;
				minDist = timeDistance.get(i);
			}
		}
		currentPos = minPos;
		return minPos;
	}
	
	public void updateTimeline(double sliderPos){
		double currentTime = (maxTime - minTime) * sliderPos + minTime;
	}
	
	public int getCurrentPos() {
		return currentPos;
	}

}
