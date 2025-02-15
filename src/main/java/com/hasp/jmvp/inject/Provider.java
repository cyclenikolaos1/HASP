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

package com.hasp.jmvp.inject;

/**
 * Provides instances of {@code T}. Typically implemented by an injector. For
 * any type {@code T} that can be injected, you can also inject
 * {@code Provider<T>}. Compared to injecting {@code T} directly, injecting
 * {@code Provider<T>} enables:
 *
 * <ul>
 * <li>retrieving multiple instances.</li>
 * <li>lazy or optional retrieval of an instance.</li>
 * <li>breaking circular dependencies.</li>
 * <li>abstracting scope so you can look up an instance in a smaller scope
 * from an instance in a containing scope.</li>
 * </ul>
 *
 * <p>
 * For example:
 *
 * <pre>
 *   class Car {
 *     &#064;Inject Car(Provider&lt;Seat> seatProvider) {
 *       Seat driver = seatProvider.get();
 *       Seat passenger = seatProvider.get();
 *       ...
 *     }
 *   }</pre>
 */
public interface Provider<T> {

  /**
   * Provides a fully-constructed and injected instance of {@code T}.
   *
   * @throws RuntimeException if the injector encounters an error while
   * providing an instance. For example, if an injectable member on
   * {@code T} throws an exception, the injector may wrap the exception
   * and throw it to the caller of {@code get()}. Callers should not try
   * to handle such exceptions as the behavior may vary across injector
   * implementations and even different configurations of the same injector.
   */
  T get();
}
