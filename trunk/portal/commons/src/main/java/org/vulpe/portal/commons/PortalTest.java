package org.vulpe.portal.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.portal.core.model.entity.Content;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class PortalTest {

	public static void main(String[] args) {
		final Query query = getObjectContainer().query();
		query.constrain(Content.class);
		query.descend("date").orderDescending();
		ObjectSet<Content> contents = query.execute();
		List<Content> contentList = new ArrayList<Content>();
		for (Content content : contents) {
			contentList.add(content);
		}
		Collections.sort(contentList, new Comparator<Content>() {
			@Override
			public int compare(Content o1, Content o2) {
				return o2.getDate().compareTo(o1.getDate());
			}
		});
		for (Content content : contentList) {
			System.out.println(content.getDate());
		}
	}

	public static ObjectContainer getObjectContainer() {
		return VulpeDB4OUtil.getInstance().getObjectContainer();
	}

}
