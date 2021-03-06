/*
 * Copyright 2016 Crown Copyright
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
package gaffer.function.simple.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gaffer.function.SimpleFilterFunction;
import gaffer.function.annotation.Inputs;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * An <code>IsMoreThan</code> is a {@link SimpleFilterFunction} that checks that the input
 * {@link java.lang.Comparable} is more than a control value. There is also an orEqualTo flag that can be set to allow
 * the input value to be more than or equal to the control value.
 */
@Inputs(Comparable.class)
public class IsMoreThan extends SimpleFilterFunction<Comparable> {
    private Comparable controlValue;
    private boolean orEqualTo;

    public IsMoreThan() {
        // Required for serialisation
    }

    public IsMoreThan(final Comparable<?> controlValue) {
        this(controlValue, false);
    }

    public IsMoreThan(final Comparable controlValue, final boolean orEqualTo) {
        this.controlValue = controlValue;
        this.orEqualTo = orEqualTo;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    @JsonProperty("value")
    public Comparable getControlValue() {
        return controlValue;
    }

    public void setControlValue(final Comparable controlValue) {
        this.controlValue = controlValue;
    }

    public boolean getOrEqualTo() {
        return orEqualTo;
    }

    public void setOrEqualTo(final boolean orEqualTo) {
        this.orEqualTo = orEqualTo;
    }

    public IsMoreThan statelessClone() {
        final IsMoreThan clone = new IsMoreThan(controlValue);
        clone.setOrEqualTo(orEqualTo);

        return clone;
    }

    @Override
    public boolean isValid(final Comparable input) {
        if (null == input
                || !controlValue.getClass().isAssignableFrom(input.getClass())) {
            return false;
        }

        final int compareVal = controlValue.compareTo(input);
        if (orEqualTo) {
            return compareVal <= 0;
        }

        return compareVal < 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final IsMoreThan that = (IsMoreThan) o;

        return new EqualsBuilder()
                .append(inputs, that.inputs)
                .append(orEqualTo, that.orEqualTo)
                .append(controlValue, that.controlValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(inputs)
                .append(controlValue)
                .append(orEqualTo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("inputs", inputs)
                .append("controlValue", controlValue)
                .append("orEqualTo", orEqualTo)
                .toString();
    }
}
