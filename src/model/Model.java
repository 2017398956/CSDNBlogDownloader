package model;

import javax.swing.SwingWorker;

/**
 * Model �ӿ�
 * @author Geurney
 *
 */
public abstract class Model extends SwingWorker<Void, Void> {

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
	 * ������ϢFlag
	 */
	public static final String STATE_PROGRESS = "STATE_PROGRESS";
	
	
	/**
	 * �û�ģʽ�����ϢFlag
	 */
	public static final String USER_STATE_DONE = "USER_STATE_DONE";

	
	/**
	 * ����ģʽ�����ϢFlag
	 */
	public static final String CATEGORY_STATE_DONE = "CATEGORY_STATE_DONE";
	
	/**
	 * ����ģʽ�����ϢFlag
	 */
	public static final String URL_STATE_DONE = "URL_STATE_DONE";
	
	/**
	 * ����״̬��True �����С�False δ������
	 */
	protected boolean status;

	/**
	 * ��ȡ����״̬
	 * @return True �����С�False δ������
	 */
	public boolean isBusy() {
		return status;
	}
	
	/**
	 * ��ʼʱ��
	 */
	protected long startTime;
	
	/**
	 * ���ʱ��
	 */
	protected long endTime;
	
	
}
