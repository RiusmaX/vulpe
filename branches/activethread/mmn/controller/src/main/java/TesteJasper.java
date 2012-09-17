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

import org.jw.mmn.ministry.model.entity.MemberReport;
import org.jw.mmn.publications.model.entity.OrderPublication;
import org.jw.mmn.report.model.entity.SimpleOrder;
import org.vulpe.commons.util.VulpeDB4OUtil;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class TesteJasper {

	public void gerar(String layout, List dataSource) throws JRException, SQLException,
			ClassNotFoundException {
		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader.load(layout);

		// final InputStream urlRelatorio =
		// Thread.currentThread().getContextClassLoader()
		// .getResourceAsStream("pedidos.jasper");

		// compila o relat�rio
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// executa o relat�rio
		Map parametros = new HashMap();
		List<SimpleOrder> lista = new ArrayList<SimpleOrder>();
		lista.add(new SimpleOrder());
		JRBeanCollectionDataSource dsRelatorio = new JRBeanCollectionDataSource(lista);
		parametros.put("periodo", "01/09/2010 - 30/09/2010");
		parametros
				.put("SUBREPORT_0",
						"C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\mmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Order\\Publicacoes.jasper");
		for (SimpleOrder pedidoSimples : (List<SimpleOrder>) dataSource) {

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
			// relatorio();
			// List<HashMap<String, String>> lista = new
			// ArrayList<HashMap<String,String>>();
			// HashMap<String, String> values = new HashMap<String, String>();
			// values.put("id", "1");
			// values.put("value", "teste");
			// values.put("etste", "ok");
			// lista.add(values);
			// lista.add(values);
			// lista.add(values);
			// System.out.println(new JSONArray(lista));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void consulta() {
		try {
			ObjectContainer db = VulpeDB4OUtil.getInstance().getObjectContainer();
			Query query = db.query();
			query.constrain(MemberReport.class);
			query.descend("year").constrain(2010);
			ObjectSet<MemberReport> os = query.execute();
			for (MemberReport objeto : os) {
				System.out.println(objeto);
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
			query.constrain(OrderPublication.class);
			query.descend("publicacao").descend("codigo").orderAscending();
			ObjectSet<OrderPublication> os = query.execute();
			List<SimpleOrder> lista = new ArrayList<SimpleOrder>();
			for (OrderPublication pedidoPublicacao : os) {
				int count = 0;
				for (OrderPublication pedidoPublicacao2 : os) {
					if (pedidoPublicacao.getPublication().getId()
							.equals(pedidoPublicacao2.getPublication().getId())) {
						++count;
					}
				}
				lista.add(new SimpleOrder(pedidoPublicacao.getPublication(), count));
			}
			db.close();
			VulpeDB4OUtil.getInstance().close();
			// new TesteJasper()
			// .gerar(
			// "C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\mmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Order\\SimpleOrder.jrxml",
			// lista);
			new TesteJasper()
					.gerar("C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\mmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Order\\Pedidos.jrxml",
							lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
