package org.vulpe.portal.commons;

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
		for (Content content : contents) {
			System.out.println(content.getDate());
		}
	}
	
	public static ObjectContainer getObjectContainer() {
		return VulpeDB4OUtil.getInstance().getObjectContainer();
	}

}
