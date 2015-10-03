package type;

import java.util.ArrayList;
import java.util.List;

/**
 * �û���
 * @author Geurney
 *
 */
public class User {
	
	/**
	 * �û��� 
	 */
	private String name;
	
	/**
	 * ������ҳ
	 */
	private String url;

	/**
	 * �û�����·�� 
	 */
	private String path;
	
	/**
	 * ���±���Ŀ¼
	 */
	private String blogFolder;
	
	/**
	 * ������Ϣ(���ʡ����������֡�ԭ����ת�ء����ĺ�����)
	 */
	private List<String> blogInfo;

	/**
	 * ͷ��ͼƬ��ַ 
	 */
	private String profileImage;
	
	/**
	 * ���·���
	 */
	private List<Category> categoreis;
	
	/**
	 * User���캯��
	 */
	public User() {
		blogInfo = new ArrayList<String>();
		categoreis = new ArrayList<Category>();
	}
	
	/**
	 * ��ȡ�û���
	 * @return �û���
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * �����û���
	 * @param name �û���
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * ��ȡ�û�����·��
	 * @return �û�����·��
	 */
	public String getPath() {
		return path;
	}

	/**
	 * �����û�����·��
	 * @param path �û�����·��
	 */
	public void setPath(String path) {
		this.path = path;
		if (name != null) {
			setBlogFolder(path + "\\" + "blog");
		}
	}

	/**
	 * ��ȡ�û�����Ŀ¼
	 * @return �û�����Ŀ¼
	 */
	public String getBlogFolder() {
		return blogFolder;
	}

	/**��
	 * �����û�����Ŀ¼
	 * @param blogFolder �û�����Ŀ¼
	 */
	public void setBlogFolder(String blogFolder) {
		this.blogFolder = blogFolder;
	}

	/**
	 * ��ȡ�û�������Ϣ
	 * @return �û�������Ϣ
	 */
	public List<String> getBlogInfo() {
		return blogInfo;
	}

	/**
	 * �����û�������Ϣ
	 * @param blogInfo �û�������Ϣ
	 */
	public void setBlogInfo(List<String> blogInfo) {
		this.blogInfo = blogInfo;
	}

	/**
	 * ��ȡ�û�ͷ�񱣴�·��
	 * @return �û�ͷ�񱣴�·��
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * �����û�ͷ�񱣴�·��
	 * @param profileImage �û�ͷ�񱣴�·��
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * ��ȡ�û����·���
	 * @return �û����·���
	 */
	public List<Category> getCategoreis() {
		return categoreis;
	}

	/**
	 * �����û����·���
	 * @param categoreis �û����·���
	 */
	public void setCategoreis(List<Category> categoreis) {
		this.categoreis = categoreis;
	}
	
	/**
	 * ��ȡ�û�������ҳurl
	 * @return �û�������ҳurl
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * �����û�������ҳurl
	 * @param url �û�������ҳurl
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
