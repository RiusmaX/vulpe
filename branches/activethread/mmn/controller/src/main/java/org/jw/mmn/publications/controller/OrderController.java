package org.jw.mmn.publications.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.publications.model.entity.Classification;
import org.jw.mmn.publications.model.entity.Order;
import org.jw.mmn.publications.model.entity.OrderPublication;
import org.jw.mmn.publications.model.services.PublicationsService;
import org.jw.mmn.report.model.entity.SimpleOrder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publications.OrderController")
@SuppressWarnings({ "serial", "unchecked" })
//@Controller(serviceClass = PublicationsService.class, select = @Select(pageSize = 5, showReport = true), report = @Report(file = "/WEB-INF/reports/publications/Order/Orders.jasper", name = "Orders", subReports = "Publications"), detailsConfig = { @DetailConfig(name = "publications", propertyName = "entity.publications", despiseFields = "publication", newDetails = 1, startNewDetails = 3, quantity = @Quantity(type = QuantityType.ONE_OR_MORE), pageSize = 3) })
@Controller(serviceClass = PublicationsService.class, select = @Select(pageSize = 5, showReport = true), report = @Report(file = "/WEB-INF/reports/publications/Order/Order.jasper", name = "Orders", subReports = "Order-publications"), detailsConfig = { @DetailConfig(name = "publications", propertyName = "entity.publications", despiseFields = "publication", newDetails = 1, startNewDetails = 3, quantity = @Quantity(type = QuantityType.ONE_OR_MORE), pageSize = 3) })
public class OrderController extends ApplicationBaseController<Order, Long> {

	@Override
	protected void selectAfter() {
		entitySelect.setInitialDate(VulpeDateUtil.getFirstDateOfTheMonth());
		entitySelect.setFinalDate(VulpeDateUtil.getLastDateOfTheMonth());
		super.selectAfter();
	}

	@Override
	protected void onCreate() {
		super.onCreate();
		entity.setDate(new Date());
		if (VulpeValidationUtil.isNotEmpty(entity.getPublications())) {
			for (OrderPublication orderPublication : entity.getPublications()) {
				orderPublication.setQuantity(1);
			}
		}
	}

	@Override
	protected boolean onCreatePost() {
		entity.setValidityDate(generateValidityDate());
		entity.setDelivered(validateDelivery());
		if (entity.isDelivered()) {
			entity.setDeliveryDate(new Date());
		}
		return super.onCreatePost();
	}

	private Date generateValidityDate() {
		if (entity.getDate() == null) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(entity.getDate());
		calendar.add(Calendar.MONTH, 3);
		return calendar.getTime();
	}

	@Override
	protected boolean onUpdatePost() {
		entity.setValidityDate(generateValidityDate());
		entity.setDelivered(validateDelivery());
		if (entity.isDelivered()) {
			entity.setDeliveryDate(new Date());
		} else {
			entity.setDeliveryDate(null);
		}

		return super.onUpdatePost();
	}

	private boolean validateDelivery() {
		if (VulpeValidationUtil.isEmpty(entity.getPublications())) {
			return false;
		}
		int count = 0;
		for (OrderPublication orderPublication : entity.getPublications()) {
			if (orderPublication.getQuantityDelivered() == null) {
				continue;
			}
			orderPublication.setDelivered(orderPublication.getQuantity().equals(
					orderPublication.getQuantityDelivered()));
			if (orderPublication.isDelivered()) {
				count++;
			}
		}
		return count == entity.getPublications().size();
	}

	@Override
	protected DownloadInfo doReportLoad() {
		final StringBuilder period = new StringBuilder();
		if (entitySelect.getInitialDate() != null && entitySelect.getFinalDate() != null) {
			final String initialDate = VulpeDateUtil.getDate(entitySelect.getInitialDate(),
					"dd 'de' MMMM 'de' yyyy");
			final String finalDate = VulpeDateUtil.getDate(entitySelect.getFinalDate(),
					"dd 'de' MMMM 'de' yyyy");
			period.append(initialDate).append(" a ").append(finalDate);
			vulpe.controller().reportParameters().put("periodo", period.toString());
		} else if (entitySelect.getInitialDate() != null) {
			final String initialDate = VulpeDateUtil.getDate(entitySelect.getInitialDate(),
					"dd 'de' MMMM 'de' yyyy");
			period.append("A partir de ").append(initialDate);
			vulpe.controller().reportParameters().put("periodo", period.toString());
		} else if (entitySelect.getFinalDate() != null) {
			final String finalDate = VulpeDateUtil.getDate(entitySelect.getFinalDate(),
					"dd 'de' MMMM 'de' yyyy");
			period.append("Antes de ").append(finalDate);
		} else {
			period.append("Total");
		}
		vulpe.controller().reportParameters().put("period", period.toString());
		final List<OrderPublication> publications = new ArrayList<OrderPublication>();
		if (entities != null) {
			for (Order order : entities) {
				publications.addAll(order.getPublications());
			}
			final List<SimpleOrder> stock = new ArrayList<SimpleOrder>();
			for (OrderPublication orderPublication : publications) {
				if (orderPublication.getPublication().getClassification().equals(
						Classification.STOCK)) {
					int count = 0;
					for (OrderPublication orderPublication2 : publications) {
						if (orderPublication.getPublication().getId().equals(
								orderPublication2.getPublication().getId())) {
							count = count + orderPublication2.getQuantity();
						}
					}
					boolean exists = false;
					for (SimpleOrder simpleOrder : stock) {
						if (simpleOrder.getPublication().getId().equals(
								orderPublication.getPublication().getId())) {
							simpleOrder.setQuantity(count);
							exists = true;
						}
					}
					if (!exists) {
						stock.add(new SimpleOrder(orderPublication.getPublication(), count));
					}
				}
			}
			final List<SimpleOrder> soi = new ArrayList<SimpleOrder>();
			for (OrderPublication orderPublication : publications) {
				if (orderPublication.getPublication().getClassification()
						.equals(Classification.SOI)) {
					int count = 0;
					for (OrderPublication orderPublication2 : publications) {
						if (orderPublication.getPublication().getId().equals(
								orderPublication2.getPublication().getId())) {
							count = count + orderPublication2.getQuantity();
						}
					}
					boolean exists = false;
					for (SimpleOrder simpleOrder : soi) {
						if (simpleOrder.getPublication().getId().equals(
								orderPublication.getPublication().getId())) {
							simpleOrder.setQuantity(count);
							exists = true;
						}
					}
					if (!exists) {
						soi.add(new SimpleOrder(orderPublication.getPublication(), count));
					}
				}
			}
			Collections.sort(soi);
			Collections.sort(stock);
			vulpe.controller().reportParameters().put("stockPublications", stock.isEmpty() ? null : stock);
			vulpe.controller().reportParameters().put("SOIPublications", soi.isEmpty() ? null : soi);

		}
		final List<String> collection = new ArrayList<String>();
		collection.add("report");
		vulpe.controller().reportCollection(collection);
		return super.doReportLoad();
	}

	@Override
	protected Order prepareEntity(Operation operation) {
		final Order order = super.prepareEntity(operation);
		order.setCongregation(getCongregation());
		return order;
	}

}
