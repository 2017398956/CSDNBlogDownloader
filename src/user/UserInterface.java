package user;

import java.io.IOException;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
/**
 * User�ӿ�
 * @author Geurney
 *
 */
public interface UserInterface {
	/**
	 * host
	 */
	public static final String host = "http://blog.csdn.net/";
	
	/**
	 * ������Ϣ��ϢFlag
	 */
	public static final String STATE_BLOGINFO = "STATE_BLOGINFO";
	
	/**
	 * �û�ͷ����ϢFlag
	 */
	public static final String STATE_PROFILE = "STATE_PROFILE";
	
	/**
	 * ϵͳ��ϢFlag
	 */
	public static final String STATE_SYSTEM = "STATE_SYSTEM";
	
	/**
	 * �����ϢFlag
	 */
	public static final String STATE_DONE = "STATE_DONE";
	
	/**
	 * ������ϢFlag
	 */
	public static final String STATE_PROGRESS = "STATE_PROGRESS";
	
	/**
	 * ��ֹ��ϢFlag
	 */
	public static final String STATE_ABORT = "STATE_ABORT";

	/**
	 * ��ȡ��ҳ������Ϣ���û�ͷ������·��ࡣ
	 * @return True ��ȡ�ɹ���False ��ȡʧ��
	 */
	public boolean profile();
	
	/**
	 * ��ȡ�����µ���������
	 * @param category ��������
	 * @return �����µ�������������
	 */
	public List<SimpleEntry<String, String>> crawlCategory(SimpleEntry<String, String> category);
	
	/**
	 * ��ȡ��������
	 * @param category ��������
	 * @param link ���±����url��ַ
	 * @return True ��ȡ�ɹ���False ��ȡʧ��
	 */
	public boolean crawlBlog(String category, SimpleEntry<String, String> link);
	
	/**
	 * ������������
	 * @throws IOException ��������ʧ��
	 */
	public void createIndex() throws IOException;
	
}
