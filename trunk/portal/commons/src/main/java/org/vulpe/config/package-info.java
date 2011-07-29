@VulpeProject(
		i18n = {"VulpeResources", "VulpeSecurityResources", "PortalResources"},
		name = "portal",
		projectPackage = "org.vulpe.portal",
		view = @VulpeView(
				layout = @VulpeViewLayout(
						showIconOfButton = true,
						frontendMenuType = org.vulpe.config.annotations.VulpeViewLayout.MenuType.NONE,
						showSliderPanel = true
				),
				readOnShow = true
		),
		theme = "portal"
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;

