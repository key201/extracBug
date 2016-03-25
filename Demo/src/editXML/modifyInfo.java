package editXML;

import java.util.List;

public class modifyInfo {
	
	private String modifyRevision;
	private String modifyAuthor;
	private String modifyDate;
	private String modifyMessage;
	private List<codeModInfos> lsmodifyPath;
	private String bugInformation;
	private String bugId;
	private String commitTime;
	
	public String getCommitTime(){
		return this.commitTime;
	}
	
	public String getmodifyRevision() {
		return this.modifyRevision;
	}
	
	public List<codeModInfos> getmodifyPathList() {
		return this.lsmodifyPath;
	}
	
	public String getmodifyAuthor() {
		return this.modifyAuthor;
	}
	public String getmodifyDate() {
		return this.modifyDate;
	}
	public String getmodifyMessage() {
		return this.modifyMessage;
	}	
	public String getBugInformation() {
		return this.bugInformation;
	}
	public String getBugId(){
		return bugId;
	}
	public void setmodifyRevision(String modifyRevision) {
		this.modifyRevision = modifyRevision;
	}
	
	public void setmodifyAuthor(String modifyAuthor) {
		 this.modifyAuthor = modifyAuthor;
	}
	
	public void setmodifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public void setmodifyMessage(String modifyMessage) {
		this.modifyMessage = modifyMessage;
	}
	public void setmodifyMessage(List<codeModInfos> lsmodifyPath) {
		this.lsmodifyPath = lsmodifyPath;
	}
	public void setBugInformation(String bugInformation) {
		this.bugInformation = bugInformation;
	}
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	public void setCommitTime(String commitTime){
		this.commitTime = commitTime;
	}
}
