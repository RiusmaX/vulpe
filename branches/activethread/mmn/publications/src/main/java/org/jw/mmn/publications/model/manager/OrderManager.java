package org.jw.mmn.publications.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.publications.model.dao.OrderDAO;
import org.jw.mmn.publications.model.entity.Order;

import org.vulpe.model.services.manager.impl.VulpeBaseManager;

@Service
public class OrderManager extends VulpeBaseManager<Order, Long, OrderDAO> {

}
