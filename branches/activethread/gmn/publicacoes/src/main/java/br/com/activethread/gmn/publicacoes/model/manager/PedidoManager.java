package br.com.activethread.gmn.publicacoes.model.manager;

import org.springframework.stereotype.Service;

import br.com.activethread.gmn.publicacoes.model.dao.PedidoDAO;
import br.com.activethread.gmn.publicacoes.model.entity.Pedido;

import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;

@Service
public class PedidoManager extends VulpeBaseCRUDManagerImpl<Pedido, Long, PedidoDAO> {

}
