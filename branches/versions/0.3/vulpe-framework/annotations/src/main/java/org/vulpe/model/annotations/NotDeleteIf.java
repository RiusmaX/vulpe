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
package org.vulpe.model.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.model.entity.VulpeEntity;

/**
 * Tells Vulpe to not delete entity if conditions is true.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NotDeleteIf {

	Class<? extends VulpeEntity<?>>[] usedBy();

	String messageToOneRecord() default "{vulpe.error.validate.record.cannot.be.deleted}";

	String messageToManyRecords() default "{vulpe.error.validate.records.cannot.be.deleted}";
}