/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.VulpeConstants.Controller.Result;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.VulpeConstants.Upload.File;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.EverParameter;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.util.VulpeUtil;
import org.vulpe.controller.util.VulpeUtil.VulpeControllerUtil;
import org.vulpe.controller.util.VulpeUtil.VulpeViewUtil;
import org.vulpe.controller.util.VulpeUtil.VulpeViewUtil.VulpeViewContentUtil;
import org.vulpe.controller.validator.EntityValidator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.NotDeleteIf;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.services.GenericService;
import org.vulpe.model.services.VulpeService;

/**
 * Base Controller
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		implements VulpeController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseController.class);

	@Autowired
	public I18NService i18nService;

	@Autowired
	public VulpeContext vulpeContext;

	public Collection<String> actionInfoMessages = new ArrayList<String>();

	/**
	 * Global attributes map
	 */
	public EverParameter ever = null;

	/**
	 * Temporal attributes map
	 */
	public VulpeHashMap<String, Object> now = new VulpeHashMap<String, Object>();

	/**
	 * 
	 */
	public VulpeUtil<ENTITY, ID> vulpe;

	/**
	 * Calendar
	 */
	public final Calendar calendar = Calendar.getInstance();

	{
		now.put(Now.SYSTEM_DATE, calendar.getTime());
		now.put(Now.CURRENT_DAY, calendar.get(Calendar.DAY_OF_MONTH));
		now.put(Now.CURRENT_MONTH, calendar.get(Calendar.MONTH));
		now.put(Now.CURRENT_YEAR, calendar.get(Calendar.YEAR));
	}

	/**
	 * Used to set and/or initialize variables in the controller. On this point
	 * the components controlled by Spring are now available.
	 */
	@PostConstruct
	protected void postConstruct() {
		ever = EverParameter.getInstance(getSession());
		vulpe = new VulpeUtil<ENTITY, ID>(this);
		final VulpeControllerUtil controller = vulpe.controller();
		final VulpeBaseControllerConfig<ENTITY, ID> config = controller.config();
		final VulpeViewUtil view = vulpe.view();
		view.maxInactiveInterval(getSession().getMaxInactiveInterval());
		view.formName(config.getFormName());
		if (config.isRequireOneFilter()) {
			view.requireOneFilter();
		}
		final VulpeViewContentUtil content = view.content();
		content.titleKey(config.getTitleKey());
		if (VulpeValidationUtil.isNotEmpty(config.getDetails())) {
			content.masterTitleKey(config.getMasterTitleKey());
		}
		if (controller.type().equals(ControllerType.REPORT)) {
			content.reportTitleKey(config.getReportTitleKey());
		}
		if (!controller.currentName().endsWith(URI.AUTHENTICATOR)) {
			vulpe.securityContext();
		}
	}

	/**
	 * List of entities
	 */
	public List<ENTITY> entities = new ArrayList<ENTITY>();
	/**
	 * Current MAIN Entity
	 */
	public ENTITY entity;
	/**
	 * Current Select entity
	 */
	public ENTITY entitySelect;
	/**
	 * Identifier for selections
	 */
	public ID id;
	/**
	 * Paginated Bean
	 */
	public Paging<ENTITY> paging;

	/**
	 * Extension point to report load.
	 * 
	 * @since 1.0
	 */
	protected abstract DownloadInfo doReportLoad();

	/**
	 * Method to validate quantity of details.
	 * 
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateQuantity(final List<ENTITY> beans,
			final VulpeBaseDetailConfig detailConfig) {
		if (detailConfig.getQuantity() != null) {
			final String tabName = vulpe.view().tabs().containsKey(detailConfig.getTitleKey()) ? vulpe
					.view().tabs().get(detailConfig.getTitleKey()).getTitle()
					: vulpe.controller().text(detailConfig.getTitleKey());
			if (detailConfig.getQuantity().minimum() > 1
					&& detailConfig.getQuantity().maximum() > 1
					&& detailConfig.getQuantity().minimum() < detailConfig.getQuantity().maximum()) {
				if (beans == null || beans.size() < detailConfig.getQuantity().minimum()
						|| beans.size() > detailConfig.getQuantity().maximum()) {
					if (detailConfig.getQuantity().minimum() == detailConfig.getQuantity()
							.maximum()) {
						addActionError("vulpe.error.details.cardinality.custom.equal", vulpe
								.controller().text(detailConfig.getTitleKey()), detailConfig
								.getQuantity().minimum());
					} else {
						addActionError("vulpe.error.details.cardinality.custom", tabName,
								detailConfig.getQuantity().minimum(), detailConfig.getQuantity()
										.maximum());
					}
					return false;
				}
			} else if (detailConfig.getQuantity().minimum() > 1
					&& detailConfig.getQuantity().maximum() == 0
					&& (beans == null || beans.size() < detailConfig.getQuantity().minimum())) {
				addActionError("vulpe.error.details.cardinality.custom.minimum", tabName,
						detailConfig.getQuantity().minimum());
				return false;
			} else if (detailConfig.getQuantity().minimum() == 0
					&& detailConfig.getQuantity().maximum() > 1
					&& (beans == null || beans.size() > detailConfig.getQuantity().maximum())) {
				addActionError("vulpe.error.details.cardinality.custom.maximum", tabName,
						detailConfig.getQuantity().maximum());
				return false;
			} else {
				if (QuantityType.ONE.equals(detailConfig.getQuantity().type())
						|| (detailConfig.getQuantity().minimum() == 1 && detailConfig.getQuantity()
								.maximum() == 1)) {
					boolean valid = true;
					if (beans == null || beans.size() == 0) {
						addActionError("vulpe.error.details.cardinality.one.less", tabName);
						valid = false;
					} else if (beans.size() > 1) {
						addActionError("vulpe.error.details.cardinality.one.only", tabName);
						valid = false;
					}
					return valid;
				} else if (QuantityType.ONE_OR_MORE.equals(detailConfig.getQuantity().type())) {
					if (beans == null || beans.size() == 0) {
						addActionError("vulpe.error.details.cardinality.one.more", tabName);
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method to check duplicated details.
	 * 
	 * @param parent
	 *            Parent
	 * @param baseEntity
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected abstract boolean duplicatedDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Method to validate duplicated details.
	 * 
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean duplicatedDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		final String[] despiseFields = detailConfig.getDespiseFields();
		final Collection<DuplicatedBean> duplicatedBeans = vulpe.controller().duplicatedItens(
				beans, despiseFields);
		if (duplicatedBeans != null && !duplicatedBeans.isEmpty()) {
			if (vulpe.controller().type().equals(ControllerType.TABULAR)
					&& duplicatedBeans.size() == 1) {
				return false;
			}
			final StringBuilder lines = new StringBuilder();
			int count = 1;
			for (DuplicatedBean duplicatedBean : duplicatedBeans) {
				if (duplicatedBeans.size() > 1 && duplicatedBeans.size() == count) {
					lines.append(" " + vulpe.controller().text("label.vulpe.and") + " "
							+ duplicatedBean.getRowNumber());
				} else {
					lines.append(StringUtils.isBlank(lines.toString()) ? String
							.valueOf(duplicatedBean.getRowNumber()) : ", "
							+ duplicatedBean.getRowNumber());
				}
				++count;
			}
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				final String tabName = vulpe.view().tabs().containsKey(detailConfig.getTitleKey()) ? vulpe
						.view().tabs().get(detailConfig.getTitleKey()).getTitle()
						: vulpe.controller().text(detailConfig.getTitleKey());
				if (detailConfig.getParentDetailConfig() != null) {
					final String parentTabName = vulpe.view().tabs().containsKey(
							detailConfig.getParentDetailConfig().getTitleKey()) ? vulpe.view()
							.tabs().get(detailConfig.getParentDetailConfig().getTitleKey())
							.getTitle() : vulpe.controller().text(
							detailConfig.getParentDetailConfig().getTitleKey());
					addActionError("vulpe.error.subdetails.duplicated.tab", tabName, parentTabName,
							lines.toString());
				} else {
					addActionError("vulpe.error.details.duplicated.tab", tabName, lines.toString());
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Method to remove detail despised.
	 * 
	 * @param beans
	 *            details
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected List<VulpeEntity<?>> despiseDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		return vulpe.controller().despiseItens(beans, detailConfig.getDespiseFields(),
				vulpe.controller().type().equals(ControllerType.TABULAR));
	}

	/**
	 * Method to remove detail despised.
	 * 
	 * @param parent
	 *            Parent
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected abstract void despiseDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Validate if entity already exists
	 * 
	 * @return
	 */
	protected boolean validateNotExistEquals() {
		return vulpe.service(GenericService.class).notExistEquals(entity);
	}

	/**
	 *
	 */
	private void manageButtons() {
		manageButtons(vulpe.controller().operation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#manageButtons(org.vulpe.controller
	 * .VulpeSimpleController.Operation)
	 */
	public void manageButtons(final Operation operation) {
		vulpe.view().buttons().clear();
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (vulpe.controller().type().equals(ControllerType.MAIN)) {
			if (config.getDetails() != null) {
				for (final VulpeBaseDetailConfig detail : config.getDetails()) {
					if (Operation.VIEW.equals(operation)) {
						vulpe.view().notRenderDetailButtons(detail.getBaseName(),
								Button.ADD_DETAIL, Button.DELETE);
					} else {
						vulpe.view().renderDetailButtons(detail.getBaseName(), Button.ADD_DETAIL,
								Button.DELETE);
					}
				}
			}
			if ((Operation.CREATE.equals(operation) || Operation.DELETE.equals(operation) || Operation.PREPARE
					.equals(operation))
					|| ((Operation.CREATE.equals(vulpe.controller().operation()) || Operation.CREATE_POST
							.equals(vulpe.controller().operation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				vulpe.view().renderButtons(Button.BACK, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)
					|| ((Operation.UPDATE.equals(vulpe.controller().operation()) || Operation.UPDATE_POST
							.equals(vulpe.controller().operation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				vulpe.view().renderButtons(Button.BACK, Button.CREATE, Button.UPDATE_POST,
						Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().layout().showButtonClone()) {
					vulpe.view().renderButtons(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				vulpe.view().renderButtons();
			}
		} else if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			vulpe.view().renderButtons(Button.READ, Button.CLEAR, Button.CREATE, Button.UPDATE,
					Button.DELETE);
			if (config.getControllerAnnotation().select().showReport()) {
				vulpe.view().renderButtons(Button.REPORT);
			} else {
				vulpe.view().notRenderButtons(Button.REPORT);
			}
			if (vulpe.controller().popup()) {
				vulpe.view().notRenderButtons(Button.CREATE, Button.UPDATE, Button.DELETE);
			}
		} else if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			vulpe.view().renderButtons(Button.READ, Button.CLEAR);
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			vulpe.view().renderButtons(Button.TABULAR_RELOAD, Button.DELETE, Button.TABULAR_POST,
					Button.ADD_DETAIL);
			if (config.isTabularShowFilter()) {
				vulpe.view().renderButtons(Button.TABULAR_FILTER);
			}
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			if (Operation.DELETE.equals(operation) || Operation.CREATE.equals(operation)
					|| Operation.TWICE.equals(operation)) {
				vulpe.view().renderButtons(ControllerType.MAIN, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)) {
				vulpe.view().renderButtons(ControllerType.MAIN, Button.CREATE, Button.UPDATE_POST,
						Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().layout().showButtonClone()) {
					vulpe.view().renderButtons(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				vulpe.view().renderButtons();
			}
			vulpe.view().renderButtons(ControllerType.SELECT, Button.READ, Button.BACK,
					Button.UPDATE, Button.DELETE);
		}
	}

	/**
	 * Method to show error
	 * 
	 * @param message
	 * @return
	 */
	protected void showError(final String message) {
		manageButtons(vulpe.controller().operation());
		addActionError(message);
		controlResultForward();
	}

	protected abstract boolean validateDetails();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#validateEntity()
	 */
	public boolean validateEntity() {
		if ((vulpe.controller().operation().equals(Operation.CREATE_POST) || vulpe.controller()
				.operation().equals(Operation.UPDATE_POST))) {
			if (validateNotExistEquals()) {
				final NotExistEquals notExistEqual = vulpe.controller().config().getEntityClass()
						.getAnnotation(NotExistEquals.class);
				String message = "{vulpe.error.entity.exists}";
				if (StringUtils.isNotEmpty(notExistEqual.message())) {
					message = notExistEqual.message();
				}
				addActionError(message);
				return false;
			}
			managePaging(false);
		}
		return EntityValidator.validate(entity) && validateDetails();
	}

	/**
	 * 
	 * @param persisted
	 */
	private void managePaging(final boolean persisted) {
		for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config().getDetails()) {
			if (vulpe.controller().deleted()) {
				vulpe.controller().removeDetailPaging(detailConfig.getName());
				prepareDetailPaging();
			} else {
				final List<ENTITY> details = VulpeReflectUtil.getFieldValue(entity, detailConfig
						.getParentDetailConfig() != null ? detailConfig.getParentDetailConfig()
						.getName() : detailConfig.getName());
				if (detailConfig.getPageSize() > 0
						&& detailConfig.getPropertyName().startsWith("entity.")) {
					final Paging<ENTITY> paging = vulpe.controller().detailPaging(
							detailConfig.getName());
					if (persisted) {
						paging.setRealList(details);
					} else {
						repairDetailPaging(details, paging);
						details.clear();
						details.addAll(paging.getRealList());
						for (final ENTITY entity : details) {
							if (entity.isFakeId()) {
								entity.setId(null);
							}
						}
					}
					mountDetailPaging(detailConfig, paging);
				}
			}
		}
	}

	protected void tabularPagingMount(final boolean add) {
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (vulpe.controller().type().equals(ControllerType.TABULAR)
				&& config.getTabularPageSize() > 0) {
			if (add) {
				vulpe.controller().tabularSize(
						vulpe.controller().tabularSize()
								+ config.getControllerAnnotation().tabular().newRecords());
			} else {
				vulpe.controller().tabularSize(entities.size());
			}
			Integer page = vulpe.controller().currentPage();
			if (paging != null) {
				page = paging.getPage();
			}
			if (page == null) {
				page = 0;
			}
			paging = new Paging<ENTITY>(vulpe.controller().tabularSize(), vulpe.controller()
					.config().getTabularPageSize(), page);
			paging.setList(entities);
		}
	}

	public void json() {
		final Object object = onJson();
		if (VulpeValidationUtil.isNotEmpty(object)) {
			vulpe.controller().renderJSON(object);
		}
	}

	protected Object onJson() {
		return null;
	}

	protected void autocompleteBefore() {
		// extension point
	}

	protected void autocompleteAfter() {
		// extension point
	}

	public void autocomplete() {
		autocompleteBefore();
		String value = "";
		final ENTITY autocompleteEntity = (ENTITY) prepareEntity(Operation.READ).clone();
		if (StringUtils.isBlank(autocompleteEntity.getQueryConfigurationName())) {
			autocompleteEntity.setQueryConfigurationName("autocomplete");
		}
		List<VulpeHashMap<String, Object>> values = autocompleteValueList();
		if (VulpeValidationUtil.isEmpty(values)) {
			List<ENTITY> autocompleteList = autocompleteList();
			String description = autocompleteEntity.getAutocomplete();
			if (description.contains(",")) {
				autocompleteEntity.setAutocomplete(description
						.substring(description.indexOf(",") + 1));
				description = description.substring(0, description.indexOf(","));
			}

			if (VulpeValidationUtil.isEmpty(autocompleteList)) {
				autocompleteList = (List<ENTITY>) invokeServices(vulpe
						.serviceMethodName(Operation.READ), new Class[] { vulpe.controller()
						.config().getEntityClass() }, new Object[] { autocompleteEntity });
			}
			values = new ArrayList<VulpeHashMap<String, Object>>();
			if (VulpeValidationUtil.isNotEmpty(autocompleteList)) {
				final List<Field> autocompleteFields = VulpeReflectUtil.getFieldsWithAnnotation(
						vulpe.controller().config().getEntityClass(), Autocomplete.class);
				for (final ENTITY entity : autocompleteList) {
					final VulpeHashMap<String, Object> map = new VulpeHashMap<String, Object>();
					try {
						map.put("id", entity.getId());
						map.put("value", PropertyUtils.getProperty(entity, description));
						if (VulpeValidationUtil.isNotEmpty(autocompleteFields)) {
							for (final Field field : autocompleteFields) {
								if (!field.getName().equals(entitySelect.getAutocomplete())) {
									map.put(field.getName(), PropertyUtils.getProperty(entity,
											field.getName()));
								}
							}
						}
						if (entitySelect.getId() != null) {
							value = map.getAuto("value");
							break;
						}
					} catch (Exception e) {
						LOG.error(e);
					}
					values.add(map);
				}
			} else {
				vulpe.controller().renderPlainText("");
			}
		} else if (entitySelect.getId() != null) {
			for (final VulpeHashMap<String, Object> map : values) {
				final Long id = map.getAuto("id");
				if (id.equals(entitySelect.getId())) {
					value = map.getAuto("value");
					break;
				}
			}
		}
		autocompleteAfter();
		if (entitySelect.getId() == null) {
			vulpe.controller().renderJSON(values);
		} else {
			vulpe.controller().renderPlainText(value);
		}
	}

	protected List<ENTITY> autocompleteList() {
		return null;
	}

	protected List<VulpeHashMap<String, Object>> autocompleteValueList() {
		return null;
	}

	/**
	 * Extension point to prepare entity.
	 * 
	 * @since 1.0
	 */
	protected ENTITY prepareEntity(final Operation operation) {
		ENTITY currentEntity = Operation.READ.equals(operation) ? entitySelect : this.entity;
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		try {
			if (operation.equals(Operation.SELECT) || operation.equals(Operation.CREATE)) {
				currentEntity = config.getEntityClass().newInstance();
			} else if (currentEntity == null) {
				currentEntity = config.getEntityClass().newInstance();
			}
			vulpe.updateAuditInformation(currentEntity);
			if (Operation.READ.equals(operation) && entitySelect == null) {
				entitySelect = config.getEntityClass().newInstance();
				currentEntity = entitySelect;
			} else if (entitySelect != null
					&& StringUtils.isNotEmpty(entitySelect.getAutocomplete())) {
				if (id != null && entitySelect.getId() == null) {
					entitySelect.setId(id);
				}
			} else if (Operation.UPDATE.equals(operation)
					|| (Operation.DELETE.equals(operation) && (vulpe.controller().type().equals(
							ControllerType.SELECT)
							|| vulpe.controller().type().equals(ControllerType.TABULAR) || vulpe
							.controller().type().equals(ControllerType.TWICE)))) {
				currentEntity.setId(id);
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		if (StringUtils.isEmpty(currentEntity.getQueryConfigurationName())
				|| "default".equals(currentEntity.getQueryConfigurationName())) {
			currentEntity.setQueryConfigurationName(config.getControllerAnnotation()
					.queryConfigurationName());
		}
		return currentEntity;
	}

	/**
	 * Extension point to prepare detail.
	 * 
	 * @param detail
	 *            Detail.
	 * @since 1.0
	 */
	protected ENTITY prepareDetail(final ENTITY detail) {
		return detail;
	}

	protected void orderDetails() {
		for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config().getDetails()) {
			final List<ENTITY> details = VulpeReflectUtil.getExpressionValue(this, detailConfig
					.getPropertyName());
			if (VulpeValidationUtil.isNotEmpty(details)) {
				Collections.sort(details);
			}
		}
	}


	/**
	 * Configure detail to view.
	 */
	protected void configureDetail() {
		vulpe.view().targetConfig(vulpe.controller().detailConfig());
		vulpe.view().targetConfigPropertyName(vulpe.controller().detail());
	}

	protected void mountDetailPaging(final VulpeBaseDetailConfig detailConfig,
			final Paging<ENTITY> currentPaging) {
		mountPaging(currentPaging, detailConfig.getPageSize());
	}

	protected void mountPaging(final Paging<ENTITY> currentPaging, final int pageSize) {
		final List<ENTITY> list = new ArrayList<ENTITY>();
		if (paging != null && paging.getPage() != null) {
			currentPaging.setPage(paging.getPage());
		}
		if (VulpeValidationUtil.isNotEmpty(currentPaging.getRealList())) {
			int count = 1;
			int total = 0;
			for (final ENTITY entity : currentPaging.getRealList()) {
				if (count > ((currentPaging.getPage() - 1) * currentPaging.getPageSize())) {
					if (total == pageSize) {
						break;
					}
					list.add(entity);
					++total;
				}
				++count;
			}
			currentPaging.processPage();
			currentPaging.setList(list);
		}
	}

	protected void repairDetailPaging(final List<ENTITY> values, final Paging<ENTITY> paging) {
		if (VulpeValidationUtil.isNotEmpty(values)) {
			int index = 0;
			for (final ENTITY real : paging.getRealList()) {
				for (final ENTITY modified : values) {
					if (VulpeValidationUtil.isNotEmpty(modified)) {
						if (real.getId() != null && real.getId().equals(modified.getId())) {
							paging.getRealList().set(index, modified);
							break;
						}
					}
				}
				++index;
			}
		}
	}

	public void paging() {
		pagingBefore();
		if (VulpeValidationUtil.isNotEmpty(vulpe.controller().detail())) {
			final Paging<ENTITY> paging = vulpe.controller().detailPaging();
			final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, vulpe.controller()
					.detail());
			repairDetailPaging(values, paging);
			if (!vulpe.controller().detail().startsWith("entity")) {
				vulpe.controller().detail("entity." + vulpe.controller().detail());
			}
			configureDetail();
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().detailConfig();
			mountDetailPaging(detailConfig, paging);
			manageButtons(Operation.ADD_DETAIL);
			if (vulpe.controller().ajax()) {
				if (detailConfig == null || detailConfig.getViewPath() == null) {
					controlResultForward();
				} else {
					vulpe.controller().resultForward(detailConfig.getViewPath());
				}
			}
		}
		pagingAfter();
	}

	protected void pagingBefore() {
	}

	protected void pagingAfter() {
	}

	protected void prepareDetailPaging() {
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (VulpeValidationUtil.isNotEmpty(config.getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : config.getDetails()) {
				if (detailConfig.getPageSize() > 0
						&& detailConfig.getPropertyName().startsWith("entity.")) {
					final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, detailConfig
							.getName());
					if (VulpeValidationUtil.isNotEmpty(values)) {
						int id = 1;
						for (final ENTITY entity : values) {
							if (entity.getId() == null) {
								entity.setId((ID) new Long(id));
								entity.setFakeId(true);
								++id;
							}
						}
						final List<ENTITY> list = new ArrayList<ENTITY>();
						int count = 0;
						for (final ENTITY entity : values) {
							if (count == detailConfig.getPageSize()) {
								break;
							}
							list.add(entity);
							++count;
						}
						final Paging<ENTITY> paging = new Paging<ENTITY>(values.size(),
								detailConfig.getPageSize(), 0);
						paging.setList(list);
						paging.setRealList(values);
						vulpe.controller().detailPaging(detailConfig.getName(), paging);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#addDetail()
	 */
	public void addDetail() {
		addDetailBefore();
		final VulpeBaseDetailConfig detailConfig = onAddDetail(false);
		configureDetail();
		manageButtons(Operation.ADD_DETAIL);
		if (vulpe.controller().ajax()) {
			if (detailConfig == null || detailConfig.getViewPath() == null) {
				controlResultForward();
			} else {
				vulpe.controller().resultForward(detailConfig.getViewPath());
			}
		} else {
			controlResultForward();
		}
		addDetailAfter();
	}

	/**
	 * Extension point to code before add detail.
	 * 
	 * @since 1.0
	 */
	protected void addDetailBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after add detail.
	 * 
	 * @since 1.0
	 */
	protected void addDetailAfter() {
		// extension point
	}

	protected abstract VulpeBaseDetailConfig onAddDetail(final boolean start);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#clear()
	 */
	public void clear() {
		// vulpe.controller().cleaned(true);
		if (vulpe.controller().type().equals(ControllerType.MAIN)) {
			entitySelect = prepareEntity(Operation.CREATE);
			create();
		} else if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			ever.remove(vulpe.controller().selectFormKey());
			ever.remove(vulpe.controller().selectTableKey());
			ever.remove(vulpe.controller().selectPagingKey());
			entitySelect = prepareEntity(Operation.SELECT);
			select();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	public void create() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.CREATE);
		createBefore();
		onCreate();
		vulpe.controller().selectedTab(null);
		manageButtons(Operation.CREATE);
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		createAfter();
	}

	/**
	 * Extension point to create record.
	 * 
	 * @since 1.0
	 */
	protected void onCreate() {
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
			try {
				entity = config.getEntityClass().newInstance();
				entity = prepareEntity(vulpe.controller().operation());
				if (VulpeValidationUtil.isNotEmpty(config.getDetails())) {
					createDetails(config.getDetails(), false);
					vulpe.controller().detail("");
				}
				prepareDetailPaging();
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			vulpe.controller().executed(false);
		}
	}

	/**
	 * Extension point to code before create.
	 * 
	 * @since 1.0
	 */
	protected void createBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after create.
	 * 
	 * @since 1.0
	 */
	protected void createAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#cloneIt()
	 */
	public void cloneIt() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.CLONE);
		cloneItBefore();
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		if (onCloneIt()) {
			manageButtons(Operation.CREATE);
			addActionMessage(vulpe.controller().defaultMessage());
			vulpe.controller().selectedTab(null);
			cloneItAfter();
		}
	}

	/**
	 * Extension point to clone record.
	 * 
	 * @since 1.0
	 */
	protected boolean onCloneIt() {
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			try {
				entity = (ENTITY) entity.clone();
				entity.setId(null);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			vulpe.controller().executed(false);
		}
		return true;
	}

	/**
	 * Extension point to code before clone.
	 * 
	 * @since 1.0
	 */
	protected void cloneItBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after clone.
	 * 
	 * @since 1.0
	 */
	protected void cloneItAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#createPost()
	 */
	public void createPost() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		vulpe.controller().operation(Operation.CREATE_POST);
		createPostBefore();
		controlResultForward();
		if (validateEntity() && onCreatePost()) {
			manageButtons(Operation.UPDATE);
			orderDetails();
			addActionMessage(vulpe.controller().defaultMessage());
			if (config.getEntityClass().isAnnotationPresent(CachedClass.class)) {
				if (validateCacheClass(entity)) {
					final String entityName = config.getEntityClass().getSimpleName();
					List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
					if (VulpeValidationUtil.isEmpty(list)) {
						list = new ArrayList<ENTITY>();
					}
					list.add(entity);
					Collections.sort(list);
					vulpe.cache().classes().put(entityName, list);
				}
			}
			createPostAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
			manageButtons(Operation.CREATE);
		}
		if (config.isNewOnPost()) {
			create();
		}
	}

	/**
	 * Extension point to code in confirm create.
	 * 
	 * @since 1.0
	 * @return Entity created.
	 */
	protected boolean onCreatePost() {
		entity = (ENTITY) invokeServices(vulpe.serviceMethodName(Operation.CREATE),
				new Class[] { vulpe.controller().config().getEntityClass() },
				new Object[] { prepareEntity(Operation.CREATE_POST) });
		id = entity.getId();
		managePaging(true);
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before confirm create.
	 * 
	 * @since 1.0
	 */
	protected void createPostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after confirm create.
	 * 
	 * @since 1.0
	 */
	protected void createPostAfter() {
		// extension point
	}

	protected abstract void createDetails(final List<VulpeBaseDetailConfig> details,
			final boolean subDetail);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#update()
	 */
	public void update() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.UPDATE);
		updateBefore();
		if (entity == null && id == null) {
			create();
		}
		onUpdate();
		vulpe.controller().selectedTab(null);
		manageButtons();
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		updateAfter();
	}

	/**
	 * Make visualization read only.
	 * 
	 * @since 1.0
	 * @return
	 */
	public void view() {
		vulpe.view().onlyToSee(true);
		update();
		manageButtons(Operation.VIEW);
	}

	/**
	 * Extension point to prepare update.
	 * 
	 * @since 1.0
	 */
	protected void onUpdate() {
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			entity = (ENTITY) invokeServices(vulpe.serviceMethodName(Operation.FIND),
					new Class[] { vulpe.controller().config().getEntityClass() },
					new Object[] { prepareEntity(vulpe.controller().operation()) });
			prepareDetailPaging();
			orderDetails();
			vulpe.controller().executed(false);
		}
	}

	/**
	 * Extension point to code before update.
	 * 
	 * @since 1.0
	 */
	protected void updateBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after update.
	 * 
	 * @since 1.0
	 */
	protected void updateAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#updatePost()
	 */
	public void updatePost() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.UPDATE_POST);
		updatePostBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (validateEntity() && onUpdatePost()) {
			orderDetails();
			addActionMessage(vulpe.controller().defaultMessage());
			if (config.getEntityClass().isAnnotationPresent(CachedClass.class)) {
				boolean valid = validateCacheClass(entity);
				final String entityName = config.getEntityClass().getSimpleName();
				List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
				if (VulpeValidationUtil.isEmpty(list) && valid) {
					list = new ArrayList<ENTITY>();
					list.add(entity);
				} else {
					int count = 0;
					boolean exist = false;
					for (final ENTITY baseEntity : list) {
						if (baseEntity.getId().equals(entity.getId())) {
							exist = true;
							if (valid) {
								list.set(count, entity);
							} else {
								list.remove(count);
							}
							break;
						}
						++count;
					}
					if (!exist) {
						list.add(entity);
					}
				}
				Collections.sort(list);
				vulpe.cache().classes().put(entityName, list);
			}
			if (!config.isOnlyUpdateDetails()) {
				final List<ENTITY> entities = ever.getAuto(vulpe.controller().selectTableKey());
				if (entities != null && !entities.isEmpty()) {
					final List<ENTITY> entitiesOld = new ArrayList<ENTITY>(entities);
					int index = 0;
					for (final ENTITY entity : entitiesOld) {
						if (entity.getId().equals(entity.getId())) {
							entities.remove(index);
							entities.add(index, entity);
						}
						++index;
					}
					ever.put(vulpe.controller().selectTableKey(), entities);
				}
			}
			updatePostAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
		}
	}

	/**
	 * Extension point prepare confirm update.
	 * 
	 * @since 1.0
	 */
	protected boolean onUpdatePost() {
		final ENTITY entity = prepareEntity(Operation.UPDATE_POST);
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (config.isOnlyUpdateDetails()) {
			final List<String> details = new ArrayList<String>();
			for (final VulpeBaseDetailConfig detailConfig : config.getDetails()) {
				details.add(detailConfig.getName());
			}
			entity.getMap().put(Entity.ONLY_UPDATE_DETAILS, details);
		}
		if (VulpeValidationUtil.isNotEmpty(config.getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : config.getDetails()) {
				final List<ENTITY> details = VulpeReflectUtil.getFieldValue(entity, detailConfig
						.getParentDetailConfig() != null ? detailConfig.getParentDetailConfig()
						.getName() : detailConfig.getName());
				if (VulpeValidationUtil.isNotEmpty(details)) {
					if (detailConfig.getParentDetailConfig() == null) {
						for (final ENTITY detail : details) {
							vulpe.updateAuditInformation(detail);
						}
					} else {
						for (final ENTITY detail : details) {
							final List<ENTITY> subDetails = VulpeReflectUtil.getFieldValue(detail,
									detailConfig.getName());
							if (VulpeValidationUtil.isNotEmpty(subDetails)) {
								for (final ENTITY subDetail : subDetails) {
									vulpe.updateAuditInformation(subDetail);
								}
								VulpeReflectUtil.setFieldValue(detail, detailConfig.getName(),
										subDetails);
							}
						}
					}
					VulpeReflectUtil.setFieldValue(entity, detailConfig.getName(), details);
				}
			}
		}
		this.entity = (ENTITY) invokeServices(vulpe.serviceMethodName(Operation.UPDATE),
				new Class[] { config.getEntityClass() }, new Object[] { entity });
		managePaging(true);
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before confirm update.
	 * 
	 * @since 1.0
	 */
	protected void updatePostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after confirm update.
	 * 
	 * @since 1.0
	 */
	protected void updatePostAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#delete()
	 */
	public void delete() {
		vulpe.controller().operation(Operation.DELETE);
		deleteBefore();
		manageButtons();
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (onDelete()) {
			addActionMessage(vulpe.controller().defaultMessage());
			vulpe.controller().selectedTab(null);
			if (config.getEntityClass().isAnnotationPresent(CachedClass.class)) {
				final String entityName = config.getEntityClass().getSimpleName();
				final List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
				if (VulpeValidationUtil.isNotEmpty(list)) {
					for (final Iterator<ENTITY> iterator = list.iterator(); iterator.hasNext();) {
						final ENTITY currentEntity = iterator.next();
						if (vulpe.controller().type().equals(ControllerType.SELECT)) {
							if (VulpeValidationUtil.isNotEmpty(vulpe.controller().selected())) {
								for (final ID id : vulpe.controller().selected()) {
									if (currentEntity.getId().equals(id)) {
										iterator.remove();
										break;
									}
								}
							} else if (currentEntity.getId().equals(id)) {
								iterator.remove();
							}
						} else {
							if (currentEntity.getId().equals(entity.getId())) {
								iterator.remove();
							}
						}
					}
				}
				vulpe.cache().classes().put(entityName, list);
			}
			try {
				entity = config.getEntityClass().newInstance();
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			deleteAfter();
			vulpe.controller().deleted(true);
			if (vulpe.controller().type().equals(ControllerType.MAIN)) {
				managePaging(false);
				create();
			} else if (vulpe.controller().type().equals(ControllerType.TWICE)
					&& entity.getId() != null) {
				onRead();
				controlResultForward();
			} else {
				read();
			}
		} else {
			if (vulpe.controller().type().equals(ControllerType.MAIN)) {
				controlResultForward();
			} else {
				initPaging();
				vulpe.controller().resultForward(config.getViewItemsPath());
			}
		}
	}

	/**
	 * Extension point to delete.
	 * 
	 * @since 1.0
	 */
	protected boolean onDelete() {
		boolean valid = true;
		final ENTITY entity = prepareEntity(Operation.DELETE);
		final List<ENTITY> selectedEntities = new ArrayList<ENTITY>();
		if (VulpeValidationUtil.isNotEmpty(vulpe.controller().selected())) {
			if (!onDeleteMany(selectedEntities)) {
				valid = false;
			}
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			vulpe.controller().tabularSize(vulpe.controller().tabularSize() - 1);
		} else {
			if (!onDeleteOne()) {
				valid = false;
			}
		}
		if (valid) {
			final NotDeleteIf notDeleteIf = entity.getClass().getAnnotation(NotDeleteIf.class);
			if (VulpeValidationUtil.isNotEmpty(selectedEntities)) {
				invokeServices(vulpe.serviceMethodName(Operation.DELETE),
						new Class[] { List.class }, new Object[] { selectedEntities });
				if (notDeleteIf != null) {
					final List<Integer> rows = new ArrayList<Integer>();
					boolean used = false;
					for (final ENTITY entity2 : selectedEntities) {
						if (entity2.isUsed()) {
							for (final ENTITY entity3 : entities) {
								if (entity2.getId().equals(entity3.getId())) {
									rows.add(entity3.getRowNumber());
									used = true;
								}
							}
						}
					}
					if (rows.isEmpty()) {
						for (final ENTITY entity2 : selectedEntities) {
							if (entity2.isConditional()) {
								for (final ENTITY entity3 : entities) {
									if (entity2.getId().equals(entity3.getId())) {
										rows.add(entity3.getRowNumber());
									}
								}
							}
						}
					}
					if (rows.size() > 0) {
						final StringBuilder affectedRows = new StringBuilder();
						int count = 1;
						for (final Integer line : rows) {
							if (affectedRows.length() > 0) {
								affectedRows.append(count == rows.size() ? " "
										+ vulpe.controller().text("label.vulpe.and") + " " : ", ");
							}
							affectedRows.append(line);
							++count;
						}
						if (rows.size() == 1) {
							addActionError(used ? notDeleteIf.usedBy().messageToOneRecordOnSelect()
									: notDeleteIf.conditions().messageToOneRecordOnSelect(),
									affectedRows.toString());
						} else {
							addActionError(used ? notDeleteIf.usedBy()
									.messageToManyRecordsOnSelect() : notDeleteIf.conditions()
									.messageToManyRecordsOnSelect(), affectedRows.toString());
						}
						valid = false;
					}
				}
			} else {
				invokeServices(vulpe.serviceMethodName(Operation.DELETE), new Class[] { vulpe
						.controller().config().getEntityClass() }, new Object[] { entity });
				if (notDeleteIf != null && (entity.isUsed() || entity.isConditional())) {
					addActionError(entity.isUsed() ? notDeleteIf.usedBy().messageToRecordOnMain()
							: notDeleteIf.conditions().messageToRecordOnMain());
					valid = false;
				}
			}
			vulpe.controller().executed(true);
		}
		return valid;
	}

	protected boolean onDeleteOne() {
		return true;
	}

	protected boolean onDeleteMany(final List<ENTITY> entities) {
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		for (final ID id : vulpe.controller().selected()) {
			try {
				final ENTITY newEntity = config.getEntityClass().newInstance();
				newEntity.setId(id);
				entities.add(newEntity);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		if (config.getTabularPageSize() > 0) {
			vulpe.controller().tabularSize(
					vulpe.controller().tabularSize()
							- (entities.size() - vulpe.controller().selected().size()));
		}
		return true;
	}

	/**
	 * Extension point to code before delete.
	 * 
	 * @since
	 */
	protected void deleteBefore() {
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after delete.
	 * 
	 * @since 1.0
	 */
	protected void deleteAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#deleteFile()
	 */
	public void deleteFile() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.DELETE_FILE);
		deleteFileBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		if (validateEntity() && onDeleteFile()) {
			addActionMessage(vulpe.controller().defaultMessage());
			deleteFileAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
		}
	}

	/**
	 * 
	 * @return
	 */
	protected boolean onDeleteFile() {
		boolean valid = true;
		if (StringUtils.isNotBlank(vulpe.controller().propertyName())) {
			if (VulpeReflectUtil.fieldExists(entity.getClass(), vulpe.controller().propertyName())) {
				VulpeReflectUtil.setFieldValue(entity, vulpe.controller().propertyName(), null);
				final String contentType = vulpe.controller().propertyName().concat(
						File.SUFFIX_CONTENT_TYPE);
				if (VulpeReflectUtil.fieldExists(entity.getClass(), contentType)) {
					VulpeReflectUtil.setFieldValue(entity, contentType, null);
				}
				final String fileName = vulpe.controller().propertyName().concat(
						File.SUFFIX_FILE_NAME);
				if (VulpeReflectUtil.fieldExists(entity.getClass(), fileName)) {
					VulpeReflectUtil.setFieldValue(entity, fileName, null);
				}
				valid = onUpdatePost();
			}
		}

		return valid;
	}

	/**
	 * Extension point to code before delete file.
	 * 
	 * @since 1.0
	 */
	protected void deleteFileBefore() {
		// extension point
	}

	/**
	 * Extension point to code after delete file.
	 * 
	 * @since 1.0
	 */
	protected void deleteFileAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#deleteDetail()
	 */
	public void deleteDetail() {
		vulpe.controller().operation(Operation.UPDATE_POST);
		deleteDetailBefore();
		manageButtons(Operation.UPDATE);
		final int size = onDeleteDetail();
		configureDetail();
		if (size > 0) {
			// final String defaultMessage =
			// getDefaultMessage(Operation.DELETE_DETAIL);
			addActionMessage(size > 1 ? "{vulpe.message.delete.details}"
					: "{vulpe.message.delete.detail}");
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				if (!entities.isEmpty()) {
					final ENTITY entityTabular = entities.get(0);
					if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
						final String entityName = entityTabular.getClass().getSimpleName();
						vulpe.cache().classes().put(entityName, entities);
					}
				}
			}
		}
		if (vulpe.controller().ajax()) {
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().config().getDetailConfig(
					vulpe.controller().detail());
			if (detailConfig == null || StringUtils.isBlank(detailConfig.getViewPath())) {
				controlResultForward();
			} else {
				vulpe.controller().resultForward(detailConfig.getViewPath());
			}
		} else {
			controlResultForward();
		}
		deleteDetailAfter();
	}

	/**
	 * Extension point to delete detail items.
	 * 
	 * @since 1.0
	 * @return number of items affected
	 */
	protected abstract int onDeleteDetail();

	/**
	 * Extension point to code before delete detail items.
	 * 
	 * @since 1.0
	 */
	protected void deleteDetailBefore() {
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after delete detail items.
	 * 
	 * @since 1.0
	 */
	protected void deleteDetailAfter() {
		// extension point
	}

	/**
	 * Method to read record.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
	public void read() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().operation(Operation.READ);
		}
		readBefore();
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (ever.containsKey(Controller.VIRTUAL_PAGING) && VulpeValidationUtil.isNotEmpty(paging)) {
			final Paging<ENTITY> currentPaging = ever.getAuto(Controller.VIRTUAL_PAGING);
			mountPaging(currentPaging, config.getPageSize());
			this.paging = currentPaging;
			entities = currentPaging.getList();
			ever.put(vulpe.controller().selectPagingKey(), currentPaging);
		} else {
			onRead();
			manageVirtualPaging();
		}
		manageButtons();
		if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			vulpe.view().targetName("entitySelect");
			if (vulpe.controller().back()) {
				controlResultForward();
				vulpe.controller().back(false);
			} else if (vulpe.controller().ajax() || vulpe.controller().exported()) {
				vulpe.controller().resultForward(config.getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			vulpe.view().targetName("entitySelect");
			vulpe.controller().resultName(Result.REPORT);
			if (vulpe.controller().ajax()) {
				vulpe.controller().resultForward(config.getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.SELECT);
			if (vulpe.controller().ajax()) {
				vulpe.controller().resultForward(config.getViewSelectItemsPath());
			} else {
				vulpe.controller().resultForward(config.getViewSelectPath());
			}
		} else {
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				if (VulpeValidationUtil.isNotEmpty(config.getDetails())
						&& VulpeValidationUtil.isEmpty(entities)
						&& !vulpe.controller().tabularFilter()) {
					createDetails(config.getDetails(), false);
				} else if (VulpeValidationUtil.isEmpty(entities)) {
					addActionInfoMessage(vulpe.controller().defaultMessage(Operation.READ));
				}
			}
			controlResultForward();
		}
		readAfter();
	}

	/**
	 * 
	 */
	private void manageVirtualPaging() {
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (config.getControllerAnnotation().select().virtualPaging()) {
			final int size = VulpeValidationUtil.isNotEmpty(entities) ? entities.size() : 0;
			final Paging<ENTITY> paging = new Paging<ENTITY>(size, config.getPageSize(), 1);
			if (entities.size() > config.getPageSize()) {
				paging.setRealList(new ArrayList<ENTITY>(entities));
				int count = 1;
				for (final Iterator<ENTITY> iterator = entities.iterator(); iterator.hasNext();) {
					iterator.next();
					if (count > config.getPageSize()) {
						iterator.remove();
					}
					++count;
				}
			}
			paging.setList(entities);
			ever.putWeakRef(Controller.VIRTUAL_PAGING, paging);
			this.paging = paging;
		}
	}

	/**
	 * Extension point to read record.
	 * 
	 * @since 1.0
	 */
	protected void onRead() {
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			if (ever.containsKey(vulpe.controller().selectFormKey()) && entitySelect == null) {
				entitySelect = ever.<ENTITY> getAuto(vulpe.controller().selectFormKey());
			}
			if (entitySelect == null) {
				entitySelect = entity;
			}
		}
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (config.requireOneOfFilters().length > 0 && isFiltersEmpty(entitySelect)) {
			final StringBuilder filters = new StringBuilder();
			final String orLabel = vulpe.controller().text("label.vulpe.or");
			int filterCount = 0;
			for (final String attribute : config.requireOneOfFilters()) {
				if (filterCount > 0) {
					filters.append(" ").append(orLabel).append(" ");
				}
				final String text = config.getTitleKey() + "." + attribute;
				filters.append("\"").append(vulpe.controller().text(text)).append("\"");
				++filterCount;
			}
			addActionError("{vulpe.error.validate.require.one.of.filters}", filters.toString());
			return;
		}
		final ENTITY entity = prepareEntity(Operation.READ);
		if (!vulpe.controller().exported()
				&& ((vulpe.controller().type().equals(ControllerType.SELECT) || vulpe.controller()
						.type().equals(ControllerType.TWICE)) && (config.getPageSize() > 0 && !config
						.getControllerAnnotation().select().virtualPaging()))
				|| (vulpe.controller().type().equals(ControllerType.TABULAR) && vulpe.controller()
						.config().getTabularPageSize() > 0)) {
			final Integer page = paging == null || paging.getPage() == null ? 1 : paging.getPage();
			vulpe.controller().currentPage(page);
			final Integer pageSize = vulpe.controller().type().equals(ControllerType.TABULAR) ? vulpe
					.controller().config().getTabularPageSize()
					: config.getPageSize();
			final Paging<ENTITY> currentPaging = (Paging<ENTITY>) invokeServices(vulpe
					.serviceMethodName(Operation.PAGING), new Class[] { config.getEntityClass(),
					Integer.class, Integer.class }, new Object[] { entity.clone(), pageSize, page });
			this.paging = currentPaging;
			entities = currentPaging.getList();
			ever.put(vulpe.controller().selectPagingKey(), currentPaging);
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				vulpe.controller().tabularSize(currentPaging.getSize());
				if (currentPaging.getList() == null || currentPaging.getList().isEmpty()) {
					vulpe.controller().detail(Controller.ENTITIES);
					if (!vulpe.controller().tabularFilter()) {
						onAddDetail(true);
					}
				}
			}
			if (VulpeValidationUtil.isEmpty(entities)) {
				addActionInfoMessage(vulpe.controller().defaultMessage(
						vulpe.controller().deleted() ? Operation.READ_DELETED : Operation.READ));
			}
		} else {
			final List<ENTITY> list = (List<ENTITY>) invokeServices(vulpe
					.serviceMethodName(Operation.READ), new Class[] { config.getEntityClass() },
					new Object[] { entity.clone() });
			entities = list;
			if (vulpe.controller().exported()) {
				initPaging();
			}
			if (config.getControllerAnnotation().select().virtualPaging()) {
				if (VulpeValidationUtil.isEmpty(entities)) {
					addActionInfoMessage(vulpe.controller().defaultMessage(
							vulpe.controller().deleted() ? Operation.READ_DELETED : Operation.READ));
				}
			}
		}
		if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			vulpe.controller().downloadInfo(doReportLoad());
			if (VulpeValidationUtil.isEmpty(entities)) {
				addActionInfoMessage(vulpe.controller().defaultMessage(Operation.REPORT_EMPTY));
			} else {
				addActionMessage(vulpe.controller().defaultMessage(Operation.REPORT_SUCCESS));
				initPaging();
			}
		} else {
			ever.put(vulpe.controller().selectFormKey(), entity.clone());
			if (entities != null && !entities.isEmpty()) {
				ever.put(vulpe.controller().selectTableKey(), entities);
			}
		}
		vulpe.controller().executed(true);
	}

	private void initPaging() {
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		final int size = VulpeValidationUtil.isNotEmpty(entities) ? entities.size() : 0;
		final Paging<ENTITY> currentPaging = new Paging<ENTITY>(size, config.getPageSize(), 1);
		currentPaging.setList(entities);
		paging = currentPaging;
	}

	/**
	 * Extension point to code before read.
	 * 
	 * @since 1.0
	 */
	protected void readBefore() {
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)
				&& !vulpe.controller().type().equals(ControllerType.REPORT)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after read.
	 * 
	 * @since
	 */
	protected void readAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabularFilter()
	 */
	public void tabularFilter() {
		vulpe.controller().tabularFilter(true);
		read();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabularPost()
	 */
	public void tabularPost() {
		if (entities != null) {
			vulpe.controller().operation(Operation.TABULAR_POST);
			tabularPostBefore();
			controlResultForward();
			manageButtons();
			if (validateDetails() && onTabularPost()) {
				addActionMessage(vulpe.controller().defaultMessage());
				if (!entities.isEmpty()) {
					final ENTITY entityTabular = entities.get(0);
					if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
						final String entityName = entityTabular.getClass().getSimpleName();
						final List<ENTITY> list = new ArrayList<ENTITY>();
						for (final ENTITY entity : entities) {
							if (validateCacheClass(entity)) {
								list.add(entity);
							}
						}
						Collections.sort(list);
						vulpe.cache().classes().put(entityName, list);
					}
				}
			}
		}
		tabularPostAfter();
	}

	/**
	 * Extension point to logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected boolean onTabularPost() {
		final int size = entities.size();
		final int sizeDespise = entities.size();
		if (vulpe.controller().config().getTabularPageSize() > 0) {
			vulpe.controller().tabularSize(vulpe.controller().tabularSize() - (size - sizeDespise));
		}
		if (!VulpeValidationUtil.isEmpty(entities)) {
			for (final ENTITY entity : entities) {
				vulpe.updateAuditInformation(entity);
			}
			final List<ENTITY> list = (List<ENTITY>) invokeServices(vulpe
					.serviceMethodName(Operation.PERSIST), new Class[] { List.class },
					new Object[] { entities });
			entities = list;
		}
		tabularPagingMount(false);
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected void tabularPostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected void tabularPostAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#prepare()
	 */
	public void prepare() {
		prepareBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (vulpe.controller().type().equals(ControllerType.SELECT)
				|| vulpe.controller().type().equals(ControllerType.REPORT)) {
			if (vulpe.controller().back()) {
				entitySelect = ever.<ENTITY> getAuto(vulpe.controller().selectFormKey());
				entities = ever.<List<ENTITY>> getAuto(vulpe.controller().selectTableKey());
				read();
			} else {
				ever.remove(vulpe.controller().selectFormKey());
				ever.remove(vulpe.controller().selectTableKey());
			}
			controlResultForward();
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			read();
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.SELECT);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		prepareAfter();
	}

	public void twice() {
		vulpe.controller().type(ControllerType.TWICE);
		prepareBefore();
		onPrepare();
		manageButtons(Operation.TWICE);
		controlResultForward();
		prepareAfter();
	}

	public void export() {
		vulpe.controller().type(ControllerType.SELECT);
		exportBefore();
		vulpe.view().onlyToSee(true);
		vulpe.controller().exported(true);
		onExport();
		// vulpe.controller().resultForward(vulpe.controller().config().getViewItemsPath());
		// vulpe.controller().resultName(Result.EXPORT);
		ever.put(Ever.EXPORT_CONTENT, "PDF");
		exportAfter();
	}

	protected void onExport() {
		read();
	}

	protected void exportBefore() {
		// extension point
	}

	protected void exportAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#select()
	 */
	@Override
	public void select() {
		vulpe.controller().type(ControllerType.SELECT);
		vulpe.view().targetName("entitySelect");
		selectBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (vulpe.controller().back()) {
			entitySelect = ever.<ENTITY> getAuto(vulpe.controller().selectFormKey());
			entities = ever.<List<ENTITY>> getAuto(vulpe.controller().selectTableKey());
			paging = ever.<Paging<ENTITY>> getAuto(vulpe.controller().selectPagingKey());
			if (paging != null) {
				paging.setList(entities);
			}
		}
		controlResultForward();
		if (vulpe.controller().back()) {
			selectAfter();
			read();
			return;
		} else {
			ever.remove(vulpe.controller().selectFormKey());
			ever.remove(vulpe.controller().selectTableKey());
			ever.remove(vulpe.controller().selectPagingKey());
		}
		selectAfter();
		ever.remove(Controller.VIRTUAL_PAGING);
		if ((vulpe.controller().config().getControllerAnnotation().select().readOnShow() || (!vulpe
				.controller().config().getControllerAnnotation().disableApplicationDefaults() && VulpeConfigHelper
				.getProjectConfiguration().view().readOnShow()))
				&& !vulpe.controller().cleaned()) {
			onRead();
			manageVirtualPaging();
		}
	}

	/**
	 * Extension point to code before select.
	 */
	protected void selectBefore() {
		// extension point
	}

	/**
	 * Extension point to code after select.
	 */
	protected void selectAfter() {
		// extension point
	}

	public void report() {
		vulpe.controller().type(ControllerType.REPORT);
		reportBefore();
		manageButtons(Operation.PREPARE);
		read();
		reportAfter();
		vulpe.controller().type(ControllerType.SELECT);
	}

	/**
	 * Extension point to code before report.
	 */
	protected void reportBefore() {
		// extension point
	}

	/**
	 * Extension point to code after report.
	 */
	protected void reportAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabular()
	 */
	public void tabular() {
		vulpe.controller().type(ControllerType.TABULAR);
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		if (config.isTabularShowFilter()) {
			try {
				entitySelect = config.getEntityClass().newInstance();
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		tabularBefore();
		onPrepare();
		manageButtons(Operation.TABULAR);
		tabularAfter();
		read();
	}

	/**
	 * Extension point to code before tabular.
	 */
	protected void tabularBefore() {
		// extension point
	}

	/**
	 * Extension point to code after tabular.
	 */
	protected void tabularAfter() {
		// extension point
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onPrepare() {
		entities = null;
		try {
			final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				if (entity == null) {
					entity = config.getEntityClass().newInstance();
				}
			}
			if (entitySelect == null) {
				entitySelect = config.getEntityClass().newInstance();
			}
		} catch (Exception e) {
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				entity = null;
			}
			entitySelect = null;
		}
		paging = null;
		vulpe.controller().executed(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void prepareBefore() {
		// extension point
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void prepareAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#upload()
	 */
	public void upload() {
		uploadBefore();
		onUpload();
		uploadAfter();
		vulpe.controller().renderBoolean(vulpe.controller().uploaded());
	}

	/**
	 * Extension point to upload.
	 * 
	 * @since 1.0
	 */
	protected void onUpload() {
		vulpe.controller().uploaded(true);
	}

	/**
	 * Extension point to code before upload.
	 * 
	 * @since 1.0
	 */
	protected void uploadAfter() {
		LOG.debug("uploadAfter");
	}

	/**
	 * Extension point to code after upload.
	 * 
	 * @since 1.0
	 */
	protected void uploadBefore() {
		LOG.debug("uploadBefore");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#download()
	 */
	public void download() {
		downloadBefore();
		onDownload();
		downloadAfter();
		vulpe.controller().resultName(Result.DOWNLOAD);
	}

	/**
	 * Extension point to download.
	 * 
	 * @since 1.0
	 */
	protected void onDownload() {
		final DownloadInfo downloadInfo = prepareDownloadInfo();
		vulpe.controller().downloadInfo(downloadInfo);
	}

	/**
	 * Extension point to prepare download.
	 * 
	 * @since 1.0
	 */
	protected abstract DownloadInfo prepareDownloadInfo();

	/**
	 * Extension point to code before download.
	 * 
	 * @since 1.0
	 */
	protected void downloadAfter() {
		LOG.debug("downloadAfter");
	}

	/**
	 * Extension point to code after download.
	 * 
	 * @since 1.0
	 */
	protected void downloadBefore() {
		LOG.debug("downloadBefore");
	}

	/**
	 * 
	 * @param entity
	 */
	public boolean isFiltersEmpty(final ENTITY entity) {
		boolean empty = true;
		for (String attribute : vulpe.controller().config().requireOneOfFilters()) {
			try {
				final Object value = PropertyUtils.getProperty(entity, attribute);
				if (VulpeValidationUtil.isNotEmpty(value)) {
					empty = false;
				}
			} catch (Exception e) {
				LOG.debug(e);
			}
		}
		return empty;
	}

	/**
	 * Method to repair cached classes used by entity.
	 * 
	 * @param entity
	 * @return Entity with cached values reloaded
	 */
	protected ENTITY repairCachedClasses(final ENTITY entity) {
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		for (final Field field : fields) {
			if (VulpeEntity.class.isAssignableFrom(field.getType())) {
				try {
					final VulpeEntity<ID> value = (VulpeEntity<ID>) PropertyUtils.getProperty(
							entity, field.getName());
					if (VulpeValidationUtil.isNotEmpty(value)
							&& !Modifier.isTransient(field.getModifiers())
							&& value.getClass().isAnnotationPresent(CachedClass.class)) {
						final List<ENTITY> cachedList = vulpe.cache().classes().getAuto(
								value.getClass().getSimpleName());
						if (VulpeValidationUtil.isNotEmpty(cachedList)) {
							for (final ENTITY cached : cachedList) {
								if (cached.getId().equals(value.getId())) {
									PropertyUtils.setProperty(entity, field.getName(), cached);
									break;
								}
							}
						}
					}
				} catch (IllegalAccessException e) {
					LOG.error(e);
				} catch (InvocationTargetException e) {
					LOG.error(e);
				} catch (NoSuchMethodException e) {
					LOG.error(e);
				}
			}
		}
		return entity;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	private boolean validateCacheClass(final ENTITY entity) {
		boolean valid = true;
		final CachedClass cachedClass = vulpe.controller().config().getEntityClass().getAnnotation(
				CachedClass.class);
		if (cachedClass != null) {
			if (cachedClass.parameters().length > 0) {
				for (final QueryParameter queryParameter : cachedClass.parameters()) {
					if (StringUtils.isNotBlank(queryParameter.equals().name())
							&& StringUtils.isNotBlank(queryParameter.equals().value())) {
						final Object value = VulpeReflectUtil.getFieldValue(entity, queryParameter
								.equals().name());
						if (VulpeValidationUtil.isNotEmpty(value)
								&& !value.toString().equals(queryParameter.equals().value())) {
							valid = false;
							break;
						}
					}
				}
			}
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#backend()
	 */
	public void backend() {
		vulpe.controller().config().setControllerType(ControllerType.BACKEND);
		vulpe.view().currentLayout("BACKEND");
		backendBefore();
		onBackend();
		controlResultForward();
		backendAfter();
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onBackend() {
		vulpe.controller().executed(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void backendBefore() {
		LOG.debug("backendBefore");
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void backendAfter() {
		LOG.debug("backendAfter");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#frontend()
	 */
	public void frontend() {
		vulpe.controller().config().setControllerType(ControllerType.FRONTEND);
		vulpe.view().currentLayout("FRONTEND");
		frontendBefore();
		onFrontend();
		controlResultForward();
		frontendAfter();
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onFrontend() {
		vulpe.controller().executed(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void frontendBefore() {
		LOG.debug("frontendBefore");
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void frontendAfter() {
		LOG.debug("frontendAfter");
	}

	public void none() {
		vulpe.controller().config().setControllerType(ControllerType.NONE);
		controlResultForward();
		vulpe.controller().executed(false);
	}
	
	/**
	 * Method to invoke services.
	 * 
	 * @param serviceName
	 *            Name of service
	 * @param argsType
	 *            Types of arguments
	 * @param argsValues
	 *            Arguments values
	 * 
	 * @since 1.0
	 * @return Object
	 */
	public Object invokeServices(final String serviceName, final Class<?>[] argsType,
			final Object[] argsValues) {
		final VulpeService service = vulpe.service();
		try {
			final Method method = service.getClass().getMethod(serviceName, argsType);
			return method.invoke(service, argsValues);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/**
	 * Method to add error message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * @param args
	 *            arguments
	 * 
	 * @since 1.0
	 */
	public void addActionError(final String key, final Object... args) {
		addActionError(vulpe.controller().text(key, args));
	}

	/**
	 * Method to add error message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * 
	 * @since 1.0
	 */
	public void addActionErrorKey(final String key) {
		addActionError(vulpe.controller().text(key));
	}

	/**
	 * Method to add warning message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * @param args
	 *            arguments
	 * 
	 * @since 1.0
	 */
	public void addActionMessage(final String key, final Object... args) {
		addActionMessage(vulpe.controller().text(key, args));
	}

	public void addActionInfoMessage(final String key, final Object... args) {
		addActionInfoMessage(vulpe.controller().text(key, args));
	}

	public void addActionInfoMessage(final String aMessage) {
		if (StringUtils.isNotBlank(aMessage)) {
			if (aMessage.startsWith("{") && aMessage.endsWith("}")) {
				final String message = vulpe.controller().text(
						aMessage.substring(1, aMessage.length() - 1));
				actionInfoMessages.add(message);
			} else {
				actionInfoMessages.add(aMessage);
			}
		}
	}

	/**
	 * Method to add warning message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * 
	 * @since 1.0
	 */
	public void addActionMessageKey(final String key) {
		addActionMessage(vulpe.controller().text(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#controlResultForward()
	 */
	public void controlResultForward() {
		vulpe
				.controller()
				.resultForward(
						vulpe.controller().type().equals(ControllerType.TWICE) ? Layout.PROTECTED_JSP_COMMONS
								.concat(Layout.BODY_TWICE_JSP)
								: Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
	}

	public abstract void addActionMessage(final String message);

	public abstract void addActionError(final String message);

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}