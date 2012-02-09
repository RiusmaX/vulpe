@VulpeProject(
		name = "vraptor_teste",
		projectPackage = "br.teste.vraptor_teste",
		view = @VulpeView(
				layout = @VulpeViewLayout(
						showIconOfButton = true,
						frontendMenuType = org.vulpe.config.annotations.VulpeViewLayout.MenuType.NONE,
						showSliderPanel = true
				),
				paging = @VulpeViewPaging(
						pageSize = 10
				),
				readOnShow = true
		)
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;
import org.vulpe.config.annotations.VulpeViewPaging;
