import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.vulpe.commons.VulpeDateUtil;
import org.vulpe.commons.VulpeDateUtil.DaysOfWeek;

import br.com.activethread.gmn.core.model.entity.Grupo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings( { "unchecked", "unused", "deprecation", "rawtypes" })
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
		viewer.show();
	}

	public static void main(String[] args) {
		try {
			// new TesteJasper()
			// .gerar("/Users/felipe/Desenvolvimento/Workspace/gerenciador-ministerial/web/src/main/resources/reports/pedidos.jrxml");
			List<Date> dates = VulpeDateUtil.getDatesOnMonth(DaysOfWeek.WEDNESDAY,
					DaysOfWeek.SATURDAY);
			for (Date date : dates) {
				System.out.println(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
