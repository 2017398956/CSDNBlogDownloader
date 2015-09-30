package user;


import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import parser.Parser;
import crawler.BlogCrawler;
import crawler.CategoryCrawler;
import crawler.IndexCrawler;

public class User extends SwingWorker<Void, Void> implements UserInterface {
	
	private String name;
	private String url;
	private String root;
	private String blogFolder;
	private List<String> blogInfo;
	private List<SimpleEntry<String, String>> categories;
	private HashMap<String, List<SimpleEntry<String, String>>> index;
	private int numberOfBlogs;

	public User(String name, String root) {
		this.name = name;
		this.root = root + "\\" + name;
		blogFolder = this.root + "\\blog";
		new File(blogFolder).mkdirs();
		url = host + "\\" + name;
		index = new HashMap<String, List<SimpleEntry<String, String>>>();
	}

	@Override
	public boolean profile() {
		IndexCrawler crawler = new IndexCrawler(root);
		firePropertyChange(STATE_SYSTEM, null, "\n��ȡ���ͻ�����Ϣ...");
		if (crawler.crawl(new SimpleEntry<String, String>(name, url))) {
			blogInfo = crawler.blogInfo();
			firePropertyChange(STATE_PROGRESS, null, 5);
			categories = crawler.categories();
			firePropertyChange(STATE_PROGRESS, null, 10);
			for (SimpleEntry<String, String> category : categories) {
				index.put(category.getKey(),
						new ArrayList<SimpleEntry<String, String>>());
				numberOfBlogs += Parser.numberOfLinksParser(category.getKey()); 
			}
			firePropertyChange(STATE_BLOGINFO, null, blogInfo);
			firePropertyChange(STATE_PROFILE, null, root + "\\" + "user.jpg");
			firePropertyChange(STATE_PROGRESS, null, 15);
			firePropertyChange(STATE_SYSTEM, null, "��ɣ�\n");
			return true;
		}
		firePropertyChange(STATE_SYSTEM, null, "ʧ�ܣ�\n");
		return false;
	}
	
	public void crawl() {
		float progress = 15;
		float progressStep = 80.0f / numberOfBlogs;
		for (SimpleEntry<String, String> category : categories) {
			List<SimpleEntry<String, String>> links = crawlCategory(category);
			if (links == null) {
				return;
			}
			for (SimpleEntry<String, String> link : links) {
				if (isCancelled()) {
					return;
				}
				crawlBlog(category.getKey(), link);
				firePropertyChange(STATE_PROGRESS, null, (int) (progress += progressStep));
			}
			firePropertyChange(STATE_SYSTEM, null, "\n");
		}
	}

	@Override
	public List<SimpleEntry<String, String>> crawlCategory(
			SimpleEntry<String, String> category) {
		List<SimpleEntry<String, String>> links = null;
		CategoryCrawler crawler = new CategoryCrawler();
		firePropertyChange(STATE_SYSTEM, null, "        ���ط��ࣺ" + category.getKey() + "  ");
		if (crawler.crawl(category)) {
			links = crawler.links();
		} else {
			firePropertyChange(STATE_SYSTEM, null, " ʧ�ܣ�\n");
		}
		return links;
	}

	@Override
	public boolean crawlBlog(String category, SimpleEntry<String, String> link) {
		String categoryFolder = blogFolder + "\\" + category;
		BlogCrawler crawler = new BlogCrawler(categoryFolder);
		if (crawler.crawl(link)) {
			index.get(category).add(
					new SimpleEntry<String, String>(crawler.title(), crawler
							.blogPath()));
			firePropertyChange(STATE_SYSTEM, null, ">");
			return true;
		}
		firePropertyChange(STATE_SYSTEM, null, "!");
		return false;
	}

	@Override
	public void createIndex() {
		if (isCancelled()) {
			return;
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(root + "\\" + "index.html"));
			firePropertyChange(STATE_SYSTEM, null, "��������...");
			writer.write(Parser.blogInfoToIndex(name, blogInfo));
			for (String category : index.keySet()) {
				List<SimpleEntry<String, String>> list = index.get(category);
				String categoryFolder = "file:\\\\\\" + blogFolder + "\\" + category;
				writer.write("\n"
						+ Parser.categoryToIndex(category, categoryFolder));
				for (SimpleEntry<String, String> link : list) {
					writer.write(Parser.blogToIndex(link.getKey(),
							 "file:\\\\\\" + link.getValue()));
				}
				writer.write(Parser.categoryCloseToIndex());
			}
			writer.close();
			firePropertyChange(STATE_SYSTEM, null, " ��ɣ�\n");
		} catch (IOException e) {
			firePropertyChange(STATE_SYSTEM, null, " ʧ�ܣ�\n");
		}
	}

	@Override
	protected Void doInBackground() {
		if (!profile()) {
			return null;
		}
		crawl();
		createIndex();
		return null;
	}
	
    @Override
    public void done() {
		if (isCancelled()) {
			firePropertyChange(STATE_DONE, null, "\n��ֹ��\n");
		} else {
	        Toolkit.getDefaultToolkit().beep();
	        firePropertyChange(STATE_PROGRESS, null, 100);
	        firePropertyChange(STATE_DONE, null, "����������ɣ�\n");
		}
    }
}
