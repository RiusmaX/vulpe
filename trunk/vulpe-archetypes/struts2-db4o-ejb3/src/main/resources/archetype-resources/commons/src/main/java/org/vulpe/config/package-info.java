#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
@VulpeApplication(
		name = "${rootArtifactId}",
		applicationPackage = "${package}",
		view = @VulpeView(
				layout = @VulpeViewLayout(
						showIconOfButton = true
				)
		)
)
package org.vulpe.config;

import org.vulpe.config.annotations.VulpeApplication;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.config.annotations.VulpeViewLayout;
