@VulpeProject(
		name = "portal",
		projectPackage = "org.vulpe.portal",
		view = @VulpeView(
				showButtonsAsImage = true,
				showIconOfButton = false,
				showTextOfButton = false,
				frontendMenuType = org.vulpe.config.annotations.VulpeView.MenuType.NONE,
				backendMenuType = org.vulpe.config.annotations.VulpeView.MenuType.DROPPY
		),
		theme = "portal"
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;

