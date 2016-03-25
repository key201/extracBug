package editXML;
import java.util.ArrayList;

//管理代码修改信息的类
public class codeModInfos {	

    // code modify type
    public static final int ITEM_ADDED   = 0; //The item was added.
    public static final int ITEM_DELETE  = 1; //The item was deleted
    public static final int ITEM_MODIFY  = 2; //条目属性改变了，注意开头的空格。
    public static final int ITEM_REPLACE = 3; //The item was replaced by a different one at the same location.

	   
    private String modifyAction;		//上面定义的类型
    private String prop_mods;
    private String fileType;			//dir路径，file为文件
    private String text_mods;
    private String modifyMsg;			//开发者修改代码时，提交的日志信息
    
    public void setModifyAction(String modifyAction) {
    	this.modifyAction = modifyAction;
    }

    public void setProp_mods(String prop_mods) {
    	this.prop_mods = prop_mods;
    }
    
    public void setFileType(String fileType) {
    	this.fileType = fileType;
    }    
    public void setText_mods(String text_mods) {
    	this.text_mods = text_mods;
    }
    public void setModifyMsg(String modifyMsg) {
    	this.modifyMsg = modifyMsg;
    }
    public String getModifyAction(){
    	return this.modifyAction;
    }
    public String getProp_mods(){
    	return this.prop_mods;
    }
    public String getfileType(){
    	return this.fileType;
    }
    public String getText_mods(){
    	return this.text_mods;
    }
    public String getModifyMsg(){
    	return this.modifyMsg;
    }
}
