@VulpeProject(
		name = "portal",
		projectPackage = "org.vulpe.portal",
		view = @VulpeView(
				showButtonsAsImage = true,
				showIconOfButton = false,
				showTextOfButton = false
		),
		theme = "portal",
		frontendMenuType = org.vulpe.config.annotations.VulpeProject.MenuType.NONE,
		backendMenuType = org.vulpe.config.annotations.VulpeProject.MenuType.DROPPY
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;

