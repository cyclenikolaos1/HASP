package com.hasp.jmvp.inject;
/*
 *  Copyright (C) 2008-2023 Ioannis Torounoglou <johntor@ionio.gr>
 *        _       _           _
 *       | | ___ | |__  _ __ | |_ ___  _ __
 *    _  | |/ _ \| '_ \| '_ \| __/ _ \| '__|
 *   | |_| | (_) | | | | | | | || (_) | |
 *    \___/ \___/|_| |_|_| |_|\__\___/|_|
 *
 *  Project files can not be copied and/or distributed without the
 *  written permission of Ioannis Torounoglou
 *
 */

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Identifies qualifier annotations. Anyone can define a new qualifier. A
 * qualifier annotation:
 *
 * <ul>
 * <li>is annotated with {@code @Qualifier}, {@code @Retention(RUNTIME)},
 * and typically {@code @Documented}.</li>
 * <li>can have attributes.</li>
 * <li>may be part of the public API, much like the dependency type, but
 * unlike implementation types which needn't be part of the public
 * API.</li>
 * <li>may have restricted usage if annotated with {@code @Target}. While
 * this specification covers applying qualifiers to fields and
 * parameters only, some injector configurations might use qualifier
 * annotations in other places (on methods or classes for example).</li>
 * </ul>
 *
 * <p>
 * For example:
 *
 * <pre>
 *   &#064;java.lang.annotation.Documented
 *   &#064;java.lang.annotation.Retention(RUNTIME)
 *   &#064;javax.inject.Qualifier
 *   public @interface Leather {
 *     Color color() default Color.TAN;
 *     public enum Color { RED, BLACK, TAN }
 *   }</pre>
 *
 * @see javax.inject.Named @Named
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface Qualifier {
}
