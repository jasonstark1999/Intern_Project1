import java.io.*;
import java.util.*;
import com.github.javafaker.Faker;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/*
 * this project is for data anonymization, we use distinct names to replace 
 * customer name, address, policy number and phone number
 * So we could protect our users' privacy.
 * Also, we utilize OpenCSV jar and Faker jar to read CSV files and generate random name.
 */
public class Main {
	
	public static List<String[]> csvBody;
	/*
	 * this function is to set csv's row with the same policy number having the normalized customer name
	 * @param csvBody 
	 * @return void
	 */
	public static void normalizer(List<String[]> body, int size){
		HashMap<String, String> policyCustomMap = new HashMap<>();
		for(int i=0; i<size; i++) {
			String policy = body.get(i)[1];
			String cumstomer = body.get(i)[5];
			if(policy!=null && policy!="") {
				if(!policyCustomMap.containsKey(policy)) {
					policyCustomMap.put(policy, cumstomer);
				}
				else {
					body.get(i)[5] = policyCustomMap.get(policy);
				}
			}
		}
		return;
		
	}
	
	/*
	 * this function is to generate fake full name for each customer name, and store them into name map.
	 * @param csvBody 
	 * @return void
	 */
	private static void fakeCustomer(List<String[]> csvBody, int size) {
		for (int i = 1; i < size; i++) {
			HashMap<String, String> nameMap = new HashMap<>();
			String customer = csvBody.get(i)[5];
			Faker fake = new Faker();
			String fakeName = fake.name().lastName()+", "+fake.name().firstName();
			if(customer!="" && customer!=null) {
				
				if(!nameMap.containsKey(customer)){
					nameMap.put(customer, fakeName);
					csvBody.get(i)[5] =fakeName;
				}
				else {
					csvBody.get(i)[5] = nameMap.get(customer);
				}
			}
			
			System.out.println(csvBody.get(i)[5]);
			
		}
		return;
	}
	
	
	/*
	 * this function is to generate fake policy number for each policy number, and store them into policy map.
	 * @param csvBody 
	 * @return void
	 */
	private static void fakePolicy(List<String[]> csvBody, int size) {
		HashMap<String, String> policyMap = new HashMap<>();
		for (int i = 1; i < size; i++) {
			String policy = csvBody.get(i)[1];
			Faker fake1 = new Faker();
			
			if(policy!="" && policy!=null) {
				String fakePolicy = fake1.number().digits(policy.length());
				if(!policyMap.containsKey(policy)){
					policyMap.put(policy, fakePolicy);
					csvBody.get(i)[1] = fakePolicy;
				}
				else{
					csvBody.get(i)[1] = policyMap.get(policy);
				}
				
			}
			System.out.println(csvBody.get(i)[1]);
		}
		return;
		
	}
	/*
	 * this function is to generate fake agent name for each agent name, and store them into agent map.
	 * @param csvBody 
	 * @return void
	 */
	private static void fakeAgent(List<String[]> csvBody, int size) {
		HashMap<String, String> agentMap = new HashMap<>();
		for (int i = 1; i < size; i++) {
			String agent = csvBody.get(i)[6];
			Faker fake2 = new Faker();
			String fakeAgent = fake2.company().name();
			if(agent!="" && agent!=null) {
				
				if(!agentMap.containsKey(agent)){
					agentMap.put(agent,fakeAgent);
					csvBody.get(i)[6] = fakeAgent;
				}
				else{
					csvBody.get(i)[6] = agentMap.get(agent);
				}
				
			}
			System.out.println(csvBody.get(i)[6]);
		}
		return;
	}
	
	/*
	 * this function is to generate fake phone number for each phone number, and store them into phone map.
	 * @param csvBody 
	 * @return void
	 */
	
	private static void fakePhone(List<String[]> csvBody, int size) {
		HashMap<String, String> phoneMap = new HashMap<>();
		for (int i = 1; i < size; i++) {
			//System.out.println(csvBody.get(i)[31]);
			String phone = csvBody.get(i)[30];
			Faker fake3 = new Faker();
			String fakePhone = "("+fake3.number().digits(3)+")"+fake3.number().digits(7)+" Ext:";
			if(phone!="" && phone!=null) {
				if(!phoneMap.containsKey(phone)) {
					phoneMap.put(phone, fakePhone);
					csvBody.get(i)[30] = fakePhone;
				}
				else {
					csvBody.get(i)[30] = phoneMap.get(phone);
				}
			}
			System.out.println(csvBody.get(i)[30]);
		}
		
	}
	
	public static void main(String[] args) {

				
		//HashMap<String, String> addressMap = new HashMap<>();
		
		
		try {
			//read the report by CSV reader
			CSVReader reader = new CSVReader(new FileReader("Report2020.csv"), ',');
			//String[] nextLine;
			//put all data in to a list of string array
			csvBody = reader.readAll();
			System.out.println(csvBody.size());
			System.out.println(csvBody.get(0).length);
			reader.close();
			
			//set all row with same policy number to the normalized customer name
			normalizer(csvBody,30);
			
			System.out.println("!!!FAKE customer name!!!");
			//generate fake full name for each customer name, and store them into name map.
			fakeCustomer(csvBody,30);
			
			
			//generate fake policy number to replace the original ones, store them into policy map.
			System.out.println("!!!FAKE policy number!!!");
			fakePolicy(csvBody,30);
			
			
			//generate fake agent name to replace the original ones, store them into agent map.
			System.out.println("!!!FAKE agent!!!");
			fakeAgent(csvBody,30);
			
			
			//generate fake phone number to replace the original ones, store them into phone map.
			System.out.println("!!!FAKE phone!!!");
			fakePhone(csvBody,30);
			
			//write the edited report into a new csv file
			CSVWriter writer = new CSVWriter(new FileWriter("outputReport1.csv"), ',');
			writer.writeAll(csvBody);
			writer.flush();
			writer.close(); 
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
				
		
	}

	

}
