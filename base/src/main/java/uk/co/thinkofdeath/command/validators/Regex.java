/*
 * Copyright 2014 Matthew Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.thinkofdeath.command.validators;

import uk.co.thinkofdeath.command.CommandError;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@TypeHandler(
        value = RegexHandler.class,
        clazz = String.class
)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Requires the string to match a pattern
 */
public @interface Regex {
    String value();
    String expectedInputType() default "valid input";
}

class RegexHandler implements ArgumentValidator<String> {

    private final Pattern regex;
    private final String fail;
    
    RegexHandler(Regex regex) {
        this.regex = Pattern.compile(regex.value());
        this.fail = regex.expectedInputType();
    }

    @Override
    public CommandError validate(String argStr, String argument) {
        if (regex.matcher(argument).matches()) {
            return null;
        }
        else {
            return new CommandError(3, "validator.regex", argument, regex, fail);
        }
    }
}
