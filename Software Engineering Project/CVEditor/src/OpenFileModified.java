import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OpenFileModified {
	private String lastModifiedDate;
	private boolean correctOpen;
	
	public void setLastModifiedDate(File file){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		this.lastModifiedDate = sdf.format(file.lastModified());
	}
	
	public void setCorrectOpen(boolean correctOpen){
		this.correctOpen = correctOpen;
	}
	
	public boolean getCorrectOpen(){
		return(correctOpen);
	}
	
	public String getLastModifiedDate(){
		return(lastModifiedDate);
	}
}
