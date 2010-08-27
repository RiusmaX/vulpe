import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

@SuppressWarnings( { "unchecked", "unused" })
public class TesteJasper {

	public void gerar(String layout) throws JRException, SQLException, ClassNotFoundException {
		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader.load(layout);

		final InputStream urlRelatorio = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("pedidos.jasper");

		// compila o relatório
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// executa o relatório
		Map parametros = new HashMap();
		JRBeanCollectionDataSource dsRelatorio = new JRBeanCollectionDataSource(new ArrayList());
		parametros.put("nota", new Double(10));
		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dsRelatorio);

		// exibe o resultado
		JasperViewer viewer = new JasperViewer(impressao, true);
		viewer.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			new TesteJasper()
					.gerar("C:\\Active Thread\\Vulpe Framework\\1.0\\Workspace\\gmn\\web\\src\\main\\webapp\\WEB-INF\\reports\\publicacoes\\Pedido\\Pedido.jrxml");
			// List<Date> dates =
			// VulpeDateUtil.getDatesOnMonth(DaysOfWeek.WEDNESDAY,
			// DaysOfWeek.SATURDAY);
			// for (Date date : dates) {
			// System.out.println(date);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
