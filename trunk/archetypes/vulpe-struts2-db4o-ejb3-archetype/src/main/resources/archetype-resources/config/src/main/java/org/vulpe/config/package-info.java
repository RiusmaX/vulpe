#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
@VulpeProject(
		name = "${rootArtifactId}",
		projectPackage = "${package}",
		view = @VulpeView(
				showButtonAsImage = true,
				showButtonIcon = false,
				showButtonText = false,
				frontendCenteredLayout = true
		)
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeProject.MenuType;
import org.vulpe.config.annotations.VulpeView;

