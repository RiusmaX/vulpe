@VulpeProject(
		name = "mmn",
		projectPackage = "org.jw.mmn",
		mobile = @VulpeMobile(
				viewportWidth = "480",
				viewportHeight = "480",
				viewportUserScalable = "no",
				viewportInitialScale = "1.0",
				viewportMaximumScale = "1.0"
		),
		view = @VulpeView(
				showButtonsAsImage = true,
				showIconOfButton = false,
				showTextOfButton = false,
				messageSlideUp = false,
				frontendMenuType = MenuType.DROPPY,
				backendMenuType = MenuType.DROPPY
		),
		theme = "mmn",
		security = true

)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeMobile;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeView.MenuType;

