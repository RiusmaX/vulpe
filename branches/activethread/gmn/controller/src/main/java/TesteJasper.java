import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.util.VulpeDB4OUtil;

import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;
import br.com.activethread.gmn.core.model.entity.PrivilegioAdicional;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.ministerio.model.entity.Mes;
import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import br.com.activethread.gmn.publicacoes.model.entity.ClassificacaoPublicacao;
import br.com.activethread.gmn.publicacoes.model.entity.Pedido;
import br.com.activethread.gmn.publicacoes.model.entity.PedidoPublicacao;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;
import br.com.activethread.gmn.relatorio.model.entity.PedidoSimples;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

@SuppressWarnings( { "unchecked", "unused" })
public class TesteJasper {

	public void gerar(String layout, List dataSource) throws JRException, SQLException,
			ClassNotFoundException {
		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader.load(layout);

		// final InputStream urlRelatorio =
		// Thread.currentThread().getContextClassLoader()
		// .getResourceAsStream("pedidos.jasper");

		// compila o relatório
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// executa o relatório
		Map parametros = new HashMap();
		List<PedidoSimples> lista = new ArrayList<PedidoSimples>();
		lista.add(new PedidoSimples());
		JRBeanCollectionDataSource dsRelatorio = new JRBeanCollectionDataSource(lista);
		parametros.put("periodo", "01/09/2010 - 30/09/2010");
		parametros
				.put(
						"SUBREPORT_0",
						"C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\gmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Pedido\\Publicacoes.jasper");
		for (PedidoSimples pedidoSimples : (List<PedidoSimples>) dataSource) {

		}
		parametros.put("publicacoesNormal", dataSource);
		parametros.put("publicacoesIPE", dataSource);
		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dsRelatorio);

		// exibe o resultado
		JasperViewer viewer = new JasperViewer(impressao, true);
		viewer.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			// List<Date> dates =
			// VulpeDateUtil.getDatesOnMonth(DaysOfWeek.WEDNESDAY,
			// DaysOfWeek.SATURDAY);
			// for (Date date : dates) {
			// System.out.println(date);
			// }
			consulta();
			//relatorio();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void consulta() {
		try {
			ObjectContainer db = VulpeDB4OUtil.getInstance().getObjectContainer();
			Query query = db.query();
			query.constrain(Relatorio.class);
			query.descend("publicador").descend("nome").orderAscending();
			ObjectSet<Relatorio> os = query.execute();
			for (Relatorio relatorio : os) {
				System.out.println(relatorio.getMes());
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void relatorio() {
		try {
			// List<Date> dates =
			// VulpeDateUtil.getDatesOnMonth(DaysOfWeek.WEDNESDAY,
			// DaysOfWeek.SATURDAY);
			// for (Date date : dates) {
			// System.out.println(date);
			// }
			ObjectContainer db = VulpeDB4OUtil.getInstance().getObjectContainer();
			Query query = db.query();
			query.constrain(PedidoPublicacao.class);
			query.descend("publicacao").descend("codigo").orderAscending();
			ObjectSet<PedidoPublicacao> os = query.execute();
			List<PedidoSimples> lista = new ArrayList<PedidoSimples>();
			for (PedidoPublicacao pedidoPublicacao : os) {
				int count = 0;
				for (PedidoPublicacao pedidoPublicacao2 : os) {
					if (pedidoPublicacao.getPublicacao().getId().equals(
							pedidoPublicacao2.getPublicacao().getId())) {
						++count;
					}
				}
				lista.add(new PedidoSimples(pedidoPublicacao.getPublicacao(), count));
			}
			db.close();
			VulpeDB4OUtil.getInstance().close();
			// new TesteJasper()
			// .gerar(
			// "C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\gmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Pedido\\PedidoSimples.jrxml",
			// lista);
			new TesteJasper()
					.gerar(
							"C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\gmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Pedido\\Pedidos.jrxml",
							lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
