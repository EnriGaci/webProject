package pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Category")
public class Category implements Serializable {
	
	private String Name;

	@XmlElement(name="Category")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
