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
				layout = @VulpeViewLayout(
						showButtonsAsImage = true,
						showIconOfButton = false,
						showTextOfButton = false,
						frontendMenuType = MenuType.DROPPY,
						backendMenuType = MenuType.DROPPY,
						showSliderPanel = true
						),
				messages = @VulpeViewMessages(
						slideUp = false
				),
				session = @VulpeViewSession(
						idleTime = 29 * 60 * 1000,
						redirectAfter = 60
				)
		),
		theme = "mmn",
		security = true

)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeMobile;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;
import org.vulpe.config.annotations.VulpeViewLayout.MenuType;
import org.vulpe.config.annotations.VulpeViewMessages;
import org.vulpe.config.annotations.VulpeViewSession;

