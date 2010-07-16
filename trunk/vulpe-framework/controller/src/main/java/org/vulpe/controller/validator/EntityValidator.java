/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.controller.validator;

import java.lang.reflect.Field;
import java.util.List;

import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;

/**
 * 
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings("unchecked")
public class EntityValidator {

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static boolean validate(VulpeBaseEntity entity) {
		final List<Field> fields = VulpeReflectUtil.getInstance().getFields(entity.getClass());
		for (Field field : fields) {
			final VulpeValidate validate = field.getAnnotation(VulpeValidate.class);
			if (validate != null) {
				if (validate.type().equals(VulpeValidateType.STRING)) {
					
				}
			}
		}
		return true;
	}
}
