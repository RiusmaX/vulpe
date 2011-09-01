<%@include file="/WEB-INF/protected-jsp/commons/common.jsp"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="v"%>
<v:table>
	<jsp:attribute name="tableBody">
		<v:row>
			<v:column>
			<table width="100%" cellpadding="0" cellspacing="0" style="font-size: 10px;">
				<tr>
					<td width="90%">
						<v:label key="label.mmn.publications.Order.main.publications.publication" />
					</td>
					<td>
						<v:label key="label.mmn.publications.Order.main.publications.quantity" />
					</td>
				</tr>
				<tr>
					<td>
						<v:selectPopup property="publication" identifier="id" description="name"
							action="/publications/Publication/select" popupId="publicationSelectPopup"
							popupProperties="publication.id=id,publication.name=name" size="30" popupWidth="420px"
							autocomplete="true" autocompleteMinLength="1"
							autocompleteValueList="${ever['publicationsAutocompleteValueList']}" />
					</td>
					<td>
						<v:text property="quantity" mask="I" size="5" maxlength="5" />
					</td>
				</tr>
				<tr>
					<td>
						<v:label key="label.mmn.publications.Order.main.publications.delivered" />
					</td>
					<td>
						<v:label key="label.mmn.publications.Order.main.publications.quantityDelivered" />
					</td>
				</tr>
				<tr>
					<td>
						<v:checkbox property="delivered" fieldValue="true"
							onclick="return app.publications.fillQuantityDelivered(this)" />
					</td>
					<td>
						<v:text property="quantityDelivered" mask="I" size="5" maxlength="5" />
					</td>
				</tr>
			</table>
			</v:column>
		</v:row>
	</jsp:attribute>
</v:table>