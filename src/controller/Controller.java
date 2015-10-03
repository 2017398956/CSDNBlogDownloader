package controller;

import gui.GUI;
import model.CategoryModel;
import model.Model;
import model.UrlModel;
import model.UserModel;
/**
 * ������
 * @author Geurney
 *
 */
public class Controller {
	/**
	 * ��ǰʹ�õ�Model
	 */
	private Model model;
	
	/**
	 * �û�����
	 */
	private GUI view;

	/**
	 * ���������캯��
	 */
	public Controller() {
		view = new GUI(this);;
	}
	
	/**
	 * ����������������ͼ�ν���
	 */
	public void run() {
		view.GUIStart();
	}
	
	/**
	 * ���ù���ģʽ
	 * @param model ����ģʽ
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * �������ò˵�
	 */
	public void updateConfigMenu() {
		view.updateConfigMenu();
	}
	
	/**
	 * ��������
	 */
	public void loadConfig() {
		view.loadConfig();
	}
	
	/**
	 * ��������
	 */
	public void saveConfig() {
		view.saveConfig();
	}
	
	/**
	 * ���°���
	 */
	public void updateHelp() {
		view.updateHelp();
	}
	/**
	 * �˳�Ӧ��
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * ִ�й���ģʽ
	 */
	public void execute() {
		if (model != null) {
			model.execute();
		}
	}
	
	/**
	 * ��ֹ����ģʽ
	 */
	public void cancel() {
		if(model != null && model.isBusy()) {
			model.cancel(true);
		}
	}
	
	/**
	 * �Ƿ�æµ
	 * @return True æµ�� False ����
	 */
	public boolean isBusy() {
		if (model == null) {
			return false;
		}
		return model.isBusy();
	}
	
	/**
	 * �û�����ģʽ����
	 * @param name �û���
	 * @param root ����Ŀ¼
	 */
	public void userPanelStart(String name, String root) {
		view.userpanelStart();
		/*ExecutorService backgroundExec = Executors.newCachedThreadPool();
			backgroundExec.execute(new Runnable() {
	    	public void run() {
	    		User user = new User(name, root);
		    	user.run();
		    	start_button.setEnabled(true);
		    	root_button.setEnabled(true);
		    	user_textField.setEditable(true);
		    	root_textField.setEditable(true);
		    	frame.setCursor(null);
	    	}
	    });*/
	    UserModel model = new UserModel();
	    model.setUser(name);
	    model.setRoot(root);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * �����û�ģʽ����
	 */
	public void userpanelReset() {
		view.userpanelReset();
	}

	/**
	 * ���¹���ģʽ����
	 * @param root ����Ŀ¼
	 * @param urls ��������
	 */
	public void urlpanelStart(String root, String urls) {
		view.urlpanelStart();
	    UrlModel model = new UrlModel();
	    model.setRoot(root);
	    model.addUrls(urls);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * ���¹���ģʽ���
	 */
	public void urlpanelClear() {
		view.urlpanelClear();
	}

	/**
	 * ���๤��ģʽ����
	 * @param root ����Ŀ¼
	 * @param categories ��������
	 */
	public void categorypanelStart(String root, String categories) {
		view.categorypanelStart();
	    CategoryModel model = new CategoryModel();
	    model.setRoot(root);
	    model.addCategories(categories);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * ���๤��ģʽ���
	 */
	public void categorypanelClear() {
		view.categorypanelClear();
	}
	
}
