@VulpeProject(
		name = "portal",
		projectPackage = "org.vulpe.portal",
		view = @VulpeView(
				layout = @VulpeViewLayout(
						showButtonsAsImage = false,
						showIconOfButton = true,
						showTextOfButton = true,
						frontendMenuType = org.vulpe.config.annotations.VulpeViewLayout.MenuType.NONE
				)
		),
		theme = "portal"
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;

