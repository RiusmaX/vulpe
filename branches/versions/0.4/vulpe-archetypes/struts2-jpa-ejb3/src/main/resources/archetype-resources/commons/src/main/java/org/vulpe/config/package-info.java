#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
@VulpeProject(
		name = "${rootArtifactId}",
		projectPackage = "${package}",
		view = @VulpeView(
				layout = @VulpeViewLayout(
						showIconOfButton = true
				)
		)
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;
