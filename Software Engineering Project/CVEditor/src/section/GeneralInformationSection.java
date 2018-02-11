package section;

import java.util.ArrayList;

import input.Field;

public class GeneralInformationSection extends Section{
	private String name;
	private String address;
	private String homeTelephone;
	private String mobileTelephone;
	private String email;
	
	public GeneralInformationSection() {
		super("General Information");
		this.name = "";
		this.address = "";
		this.homeTelephone = "";
		this.mobileTelephone = "";
		this.email = "";
	}

	public void updateAllFields(String name, String address, String homeTelephone, String mobileTelephone, String email){
		this.name = name;
		this.address = address;
		this.homeTelephone = homeTelephone;
		this.mobileTelephone = mobileTelephone;
		this.email = email;
	}
	
	public void addFields() {
		clearSectionFieldList();
		addField("Name:~" + name);
		addField("Address:~" + address);
		addField("Telephone(Home):~" + homeTelephone);
		addField("Telephone(Mobile):~" + mobileTelephone);
		addField("Email:~" + email);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setHomeTelephone(String homeTelephone){
		this.homeTelephone = homeTelephone;
	}
	
	public void setMobileTelephone(String mobileTelephone){
		this.mobileTelephone = mobileTelephone;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getHomeTelephone(){
		return homeTelephone;
	}
	
	public String getMobileTelephone(){
		return mobileTelephone;
	}
	
	public String getEmail(){
		return email;
	}

	public ArrayList<Field> compare(Section comparedSection) {
		ArrayList<Field> fields = new ArrayList<Field>();
		
		fields.add(getTempField("Name", name, name));
		fields.add(getTempField("Address", address, ((GeneralInformationSection)comparedSection).getAddress()));
		fields.add(getTempField("Telephone(home)", homeTelephone, ((GeneralInformationSection)comparedSection).getHomeTelephone()));
		fields.add(getTempField("Telephone(mobile)", mobileTelephone, ((GeneralInformationSection)comparedSection).getMobileTelephone()));
		fields.add(getTempField("Email", email, ((GeneralInformationSection)comparedSection).getEmail()));
		return fields;
	}

	public void updateFromFields(ArrayList<Field> fields) {
		 if (fields.get(0).getComparedFieldValue() != null && fields.get(0).getKeepComparedField()){
			 setName(fields.get(0).getComparedFieldValue());
		 }
		 if (fields.get(1).getComparedFieldValue() != null && fields.get(1).getKeepComparedField()){
			 setAddress(fields.get(1).getComparedFieldValue());
		 }
		 if (fields.get(2).getComparedFieldValue() != null && fields.get(2).getKeepComparedField()){
			 setHomeTelephone(fields.get(2).getComparedFieldValue());
		 }
		 if (fields.get(3).getComparedFieldValue() != null && fields.get(3).getKeepComparedField()){
			 setMobileTelephone(fields.get(3).getComparedFieldValue());
		 }
		 if (fields.get(4).getComparedFieldValue() != null && fields.get(4).getKeepComparedField()){
			 setEmail(fields.get(4).getComparedFieldValue());
		 }
	}
}
