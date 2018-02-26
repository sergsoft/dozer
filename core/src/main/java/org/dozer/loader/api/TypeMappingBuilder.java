/*
 * Copyright 2005-2018 Dozer Project
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
package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

/**
 * @author Dmitry Buzdin
 */
public final class TypeMappingBuilder {

  private DozerBuilder.MappingBuilder beanMappingBuilder;

  public TypeMappingBuilder(DozerBuilder.MappingBuilder beanMappingBuilder) {
    this.beanMappingBuilder = beanMappingBuilder;
  }

  public TypeMappingBuilder fields(String a, String b, FieldsMappingOption... options) {
    return fields(new FieldDefinition(a), new FieldDefinition(b), options);
  }

  public TypeMappingBuilder fields(FieldDefinition a, String b, FieldsMappingOption... options) {
    return fields(a, new FieldDefinition(b), options);
  }

  public TypeMappingBuilder fields(String a, FieldDefinition b, FieldsMappingOption... options) {
    return fields(new FieldDefinition(a), b, options);
  }  

  public TypeMappingBuilder fields(FieldDefinition a, FieldDefinition b, FieldsMappingOption... options) {
    Set<String> mainContexts = stream(options)
            .filter(fieldsMappingOption -> fieldsMappingOption instanceof FieldsMappingOptions.FieldsContextMappingOption)
            .map(fieldsMappingOption -> (FieldsMappingOptions.FieldsContextMappingOption) fieldsMappingOption)
            .map(FieldsMappingOptions.FieldsContextMappingOption::getContext)
            .collect(toSet());
    Set<String> contexts = !mainContexts.isEmpty() ? mainContexts : stream(options)
            .flatMap(fieldsMappingOption -> fieldsMappingOption.getContexts().stream())
            .collect(toSet());
    boolean hasDefault = contexts.isEmpty() ||
            stream(options)
                    .anyMatch(fieldsMappingOption -> fieldsMappingOption.getContexts().isEmpty());

    Function<String, DozerBuilder.FieldMappingBuilder> builderSupplier = context -> {
      DozerBuilder.FieldMappingBuilder builder = beanMappingBuilder.field();

      String aText = a.resolve();
      String bText = b.resolve();

      a.build(builder.a(aText));
      b.build(builder.b(bText));

      if (context != null) {
        builder.addContext(context);
      }

      builder.addExcludeContexts(contexts.stream()
              .filter(s -> !s.equals(context))
              .collect(toSet()));
      return builder;
    };

    Map<String, DozerBuilder.FieldMappingBuilder> builderMap = (hasDefault ? concat(contexts.stream(), of((String) null)) : contexts.stream())
            .collect(toMap(identity(), builderSupplier));
    for (FieldsMappingOption option : options) {
      Collection<String> optionContexts = option.getContexts();
      applyOption(option, (optionContexts.isEmpty() ? builderMap.keySet() : optionContexts), builderMap);
    }

    return this;
  }

  private void applyOption(FieldsMappingOption option, Collection<String> contexts, Map<String, DozerBuilder.FieldMappingBuilder> builderMap) {
    contexts.forEach(context -> option.apply(builderMap.get(context)));
  }

  public TypeMappingBuilder exclude(String field) {
    return exclude(new FieldDefinition(field));
  }

  public TypeMappingBuilder exclude(FieldDefinition field) {
    DozerBuilder.FieldExclusionBuilder builder = beanMappingBuilder.fieldExclude();
    builder.a(field.resolve(), null);
    builder.b(field.resolve(), null);
    return this;
  }

}
