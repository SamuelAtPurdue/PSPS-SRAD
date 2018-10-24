import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Timeline extends ArrayList<JSONObject>{
	
	public Timeline() {
		super();
	}
	
	public void generateTimeline(List<File> fileList) {
		JSONParser jsonParser = new JSONParser();
		for(int i = 0; i < fileList.size(); i++) {
			try{
				System.out.println(fileList.get(i).getPath());
				FileReader reader = new FileReader(fileList.get(0).getPath());
				Object obj = jsonParser.parse(reader);
				JSONObject jsonObject = (JSONObject) obj;
				this.add(jsonObject);
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
	
	public void getClosestMoment() {
		
	}
	

	
}
