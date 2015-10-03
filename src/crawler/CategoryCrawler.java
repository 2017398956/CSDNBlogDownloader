package crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parser.Parser;
import type.Blog;
import type.Category;

/**
 * �������档��ȡһ�������µ������������ӡ� 
 * @author Geurney
 *
 */
public class CategoryCrawler extends Crawler {

	@Override
	public Category crawl(String url, String path) {
		int index = 1;
		Category category = new Category();
		category.setUrl(url);
		while(connect(url + "/" + index)) {
			if (category.getTitle() == null) {
				String[] titles = document.select("title").first().text().split("-");  //[title]��attribute��title��tag
				String title = titles[titles.length - 4].trim();  // maybe better in parser?
				category.setTitle(title);
				category.setPath(path + "\\" + Parser.fileNameValify(title));
			}
			
			Elements pagelinks = document.select(".link_title a[href]");
			for (Element pagelink : pagelinks) {
				Blog blog = new Blog();
				blog.setUrl(pagelink.absUrl("href"));
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				category.getBlogs().add(blog);
			}
			if (document.text().contains("��һҳ")) {
				index++;
			} else {
				return category;
			}
		}
		return null;
	}
	
}
