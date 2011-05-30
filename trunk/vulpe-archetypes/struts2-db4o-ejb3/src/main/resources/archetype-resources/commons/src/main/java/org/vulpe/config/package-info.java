#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
@VulpeProject(
		name = "${rootArtifactId}",
		projectPackage = "${package}",
		view = @VulpeView(
				layout = @VulpeViewLayout(
					showButtonsAsImage = true,
					showIconOfButton = false,
					showTextOfButton = false
				)
		)
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;
