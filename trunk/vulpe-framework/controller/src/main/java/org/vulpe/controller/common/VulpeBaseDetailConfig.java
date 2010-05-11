package org.vulpe.controller.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.common.Constants.View.Layout;
import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.common.annotations.DetailConfig.CardinalityType;
import org.vulpe.view.tags.Functions;

@SuppressWarnings("serial")
public class VulpeBaseDetailConfig implements Serializable {

	private String name;
	private String propertyName;
	private String simpleName;
	private String titleKey;
	private int detailNews;
	private String[] despiseFields;
	private String viewPath;
	private CardinalityType cardinalityType;
	private VulpeBaseDetailConfig parentDetailConfig;
	private List<VulpeBaseDetailConfig> subDetails = new ArrayList<VulpeBaseDetailConfig>();

	public VulpeBaseDetailConfig() {
		this.detailNews = 1;
	}

	public VulpeBaseDetailConfig(final String name) {
		this();
		this.name = name;
		this.propertyName = name;
		setSimpleName();
	}

	public VulpeBaseDetailConfig(final String name, final String propertyName,
			final int detailNews, final String[] despiseFields) {
		this.name = name;
		this.propertyName = propertyName;
		this.detailNews = detailNews;
		this.despiseFields = despiseFields.clone();
		setSimpleName();
	}

	public VulpeBaseDetailConfig(final String name, final String propertyName,
			final int detailNews, final String[] despiseFields,
			final CardinalityType cardinalityType) {
		this.name = name;
		this.propertyName = propertyName;
		this.detailNews = detailNews;
		this.despiseFields = despiseFields.clone();
		this.cardinalityType = cardinalityType;
		setSimpleName();
	}

	public String getBaseName() {
		return Functions.clearChars(Functions.replaceSequence(name, "[", "]", ""), ".");
	}

	public String getName() {
		return name;
	}

	public VulpeBaseDetailConfig getParentDetailConfig() {
		return parentDetailConfig;
	}

	public int getDetailNews() {
		return detailNews;
	}

	public String[] getDespiseFields() {
		return despiseFields.clone();
	}

	public List<VulpeBaseDetailConfig> getSubDetails() {
		return subDetails;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(final String titleKey) {
		this.titleKey = titleKey;
	}

	public String getSimpleName() {
		return simpleName;
	}

	@SuppressWarnings("unchecked")
	public void setupDetail(final VulpeBaseActionConfig config, final DetailConfig detail) {
		if (detail == null) {
			return;
		}

		if (!detail.name().equals("")) {
			this.name = detail.name();
			this.propertyName = detail.name();
			setSimpleName();
		}

		if (!detail.propertyName().equals("")) {
			this.propertyName = detail.propertyName();
			setSimpleName();
		}

		this.viewPath = config.getViewPath().substring(0,
				StringUtils.lastIndexOf(config.getViewPath(), '/')).concat("/").concat(
				getBaseName()).concat(Layout.SUFFIX_JSP_DETAIL);

		if (StringUtils.isEmpty(getTitleKey()) && StringUtils.isNotEmpty(getPropertyName())) {
			setTitleKey(config.getTitleKey().concat(".").concat(getBaseName()));
		}

		if (detail.despiseFields().length > 0) {
			this.despiseFields = detail.despiseFields();
		}

		if (detail.detailNews() > 1) {
			this.detailNews = detail.detailNews();
		}

		this.cardinalityType = detail.cardinalityType();

		if (!detail.parentDetailName().equals("")) {
			if (config.getDetail(detail.parentDetailName()) == null) {
				config.getDetails().add(new VulpeBaseDetailConfig(detail.parentDetailName()));
			}
			this.parentDetailConfig = (VulpeBaseDetailConfig) config.getDetail(detail
					.parentDetailName());
			this.parentDetailConfig.getSubDetails().add(this);
		}
	}

	private void setSimpleName() {
		if (StringUtils.isNotEmpty(this.propertyName)) {
			if (StringUtils.lastIndexOf(this.propertyName, '.') >= 0) {
				this.simpleName = this.propertyName.substring(StringUtils.lastIndexOf(
						this.propertyName, '.') + 1);
			} else {
				this.simpleName = this.propertyName;
			}
		}
	}

	public void setViewPath(final String viewPath) {
		this.viewPath = viewPath;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setCardinalityType(CardinalityType cardinalityType) {
		this.cardinalityType = cardinalityType;
	}

	public CardinalityType getCardinalityType() {
		return cardinalityType;
	}

}