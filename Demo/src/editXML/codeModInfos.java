package editXML;
import java.util.ArrayList;

//��������޸���Ϣ����
public class codeModInfos {	

    // code modify type
    public static final int ITEM_ADDED   = 0; //The item was added.
    public static final int ITEM_DELETE  = 1; //The item was deleted
    public static final int ITEM_MODIFY  = 2; //��Ŀ���Ըı��ˣ�ע�⿪ͷ�Ŀո�
    public static final int ITEM_REPLACE = 3; //The item was replaced by a different one at the same location.

	   
    private String modifyAction;		//���涨�������
    private String prop_mods;
    private String fileType;			//dir·����fileΪ�ļ�
    private String text_mods;
    private String modifyMsg;			//�������޸Ĵ���ʱ���ύ����־��Ϣ
    
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
