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

import br.com.activethread.gmn.publicacoes.model.entity.PedidoPublicacao;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

@SuppressWarnings( { "unchecked", "unused" })
public class TesteJasper {

	public void gerar(String layout, List dataSource) throws JRException, SQLException,
			ClassNotFoundException {
		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader.load(layout);

		final InputStream urlRelatorio = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("pedidos.jasper");

		// compila o relatório
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// executa o relatório
		Map parametros = new HashMap();
		JRBeanCollectionDataSource dsRelatorio = new JRBeanCollectionDataSource(dataSource);
		parametros.put("nota", new Double(10));
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
			ObjectContainer db = VulpeDB4OUtil.getInstance().getObjectContainer();
			Query query = db.query();
			query.constrain(PedidoPublicacao.class);
			query.descend("publicacao").descend("codigo").orderAscending();
			ObjectSet<PedidoPublicacao> os = query.execute();
			Map<Publicacao, Integer> map = new HashMap<Publicacao, Integer>();
			for (PedidoPublicacao pedidoPublicacao : os) {
				int count = 0;
				for (PedidoPublicacao pedidoPublicacao2 : os) {
					if (pedidoPublicacao.getPublicacao().getId().equals(
							pedidoPublicacao2.getPublicacao().getId())) {
						++count;
					}
				}
				map.put(pedidoPublicacao.getPublicacao(), count);
			}
			db.close();
			VulpeDB4OUtil.getInstance().close();
			List<ValueBean> lista = new ArrayList<ValueBean>();
			for (Publicacao publicacao : map.keySet()) {
				System.out.println(publicacao.getCodigo() + " - " + publicacao.getNome() + " = "
						+ map.get(publicacao));
				lista.add(new ValueBean(publicacao.getCodigo().toString(), publicacao.getNome()));
			}
			new TesteJasper()
					.gerar("C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\gmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Pedido\\PedidoSimples.jrxml", lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
