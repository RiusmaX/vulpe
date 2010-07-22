@VulpeProject(
		name = "site",
		projectPackage = "org.vulpe.site",
		view = @VulpeView(
				showButtonAsImage = true,
				showButtonIcon = false,
				showButtonText = false,
				frontendCenteredLayout = true
		),
		theme = "site",
		frontendMenuType = MenuType.NONE
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeProject.MenuType;
import org.vulpe.config.annotations.VulpeView;

