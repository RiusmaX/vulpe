@VulpeProject(
		name = "portal",
		projectPackage = "org.vulpe.portal",
		view = @VulpeView(
				layout = @VulpeViewLayout(
				showButtonsAsImage = true,
				showIconOfButton = false,
				showTextOfButton = false,
				frontendMenuType = org.vulpe.config.annotations.VulpeViewLayout.MenuType.NONE,
				backendMenuType = org.vulpe.config.annotations.VulpeViewLayout.MenuType.DROPPY
				)
		),
		theme = "portal"
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;

