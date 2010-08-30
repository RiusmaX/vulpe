@VulpeProject(
		name = "gmn",
		projectPackage = "br.com.activethread.gmn",
		mobileEnabled = true,
		mobile = @VulpeMobile(
				viewportWidth = "480",
				viewportHeight = "480",
				viewportUserScalable = "no",
				viewportInitialScale = "1.0",
				viewportMaximumScale = "1.0"
		),
		view = @VulpeView(
				showButtonAsImage = true,
				showButtonIcon = false,
				showButtonText = false,
				messageSlideUp = false
		),
		theme = "gmn",
		security = true
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeMobile;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;

