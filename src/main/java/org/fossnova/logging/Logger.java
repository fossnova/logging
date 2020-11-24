/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
 * and individual contributors as indicated by the @author tags.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.fossnova.logging;

import java.io.Serializable;

/**
 * Adaptive logger that prefers <b>Log4j</b> over <b>j.u.l.</b> logging framework.
 * If <b>log4j</b> is found on classpath the logger will adapt all messages to it.
 * Otherwise <b>java.util.logging</b> will be used.<br/><br/>
 *
 * This logger recognizes the following levels: <b>ERROR</b>, <b>WARN</b>
 * <b>INFO</b>, <b>DEBUG</b> and <b>TRACE</b>. The mapping of logging levels is as follows:<br/><br/>
 * <table border='1'>
 * <tr><th width="200">FOSS Nova Level</th><th width="200">Log4j Level</th><th width="200">j.u.l. Level</th></tr>
 * <tr><td>ERROR</td><td>ERROR</td><td>SEVERE</td></tr>
 * <tr><td>WARN</td><td>WARN</td><td>WARNING</td></tr>
 * <tr><td>INFO</td><td>INFO</td><td>INFO</td></tr>
 * <tr><td>DEBUG</td><td>DEBUG</td><td>FINE</td></tr>
 * <tr><td>TRACE</td><td>TRACE</td><td>FINEST</td></tr>
 * </table>
 *
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public class Logger implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Logger name.
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param name the logger name
     */
    Logger( final String name ) {
        this.name = name;
    }

    /**
     * Return a name of this logger.
     *
     * @return a name of this logger
     */
    public final String getName() {
        return name;
    }

    /**
     * Template method to be implemented by subclasses.
     *
     * @param level the level
     * @param msg the object to log
     * @param t the exception which was thrown, if any
     */
    void logInternal( final Level level, final Object msg, final Throwable t ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Template method to be implemented by subclasses.
     *
     * @param level the level
     * @return {@code true} if specified level is currently being logged 
     */
    boolean isEnabledInternal( final Level level ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Serialization support.
     *
     * @return the canonical logger instance
     */
    final Object writeReplace() {
        return new SerializableLogger( name );
    }

    /**
     * Get a logger by given name.
     *
     * @param name the logger name
     *
     * @return the logger
     * @throws IllegalArgumentException if parameter is null
     */
    public static final Logger getInstance( final String name ) {
        if ( name == null ) {
            throw new IllegalArgumentException();
        }
        return LoggerProvider.getInstance().getLogger( name );
    }

    /**
     * Get a logger by class name.
     *
     * @param clazz to create logger name from
     *
     * @return the logger
     * @throws IllegalArgumentException if parameter is null
     */
    public static final Logger getInstance( final Class< ? > clazz ) {
        if ( clazz == null ) {
            throw new IllegalArgumentException();
        }
        return getInstance( clazz.getName() );
    }

    /**
     * Check if a message of the specified level would actually be logged by this logger.
     *
     * @return {@code true} if the <code>Level</code> is currently being logged, {@code false} otherwise
     * @throws IllegalArgumentException if level is null
     */
    public final boolean isEnabled( final Level level ) {
        if ( level == null ) {
            throw new IllegalArgumentException();
        }
        return isEnabledInternal( level );
    }

    /**
     * Check if a message of the {@code TRACE} level would actually be logged by this logger.
     *
     * @return {@code true} if the {@code TRACE} level is currently being logged
     */
    public final boolean isTraceEnabled() {
        return isEnabledInternal( Level.TRACE );
    }

    /**
     * Log a {@code TRACE} message.
     *
     * @param msg the message
     */
    public final void trace( final Object msg ) {
        logInternal( Level.TRACE, msg, null );
    }

    /**
     * Log a {@code TRACE} message with associated throwable information.
     *
     * @param msg the message
     * @param t the throwable
     */
    public final void trace( final Object msg, final Throwable t ) {
        logInternal( Level.TRACE, msg, t );
    }

    /**
     * Check if a message of the {@code DEBUG} level would actually be logged by this logger.
     *
     * @return {@code true} if the {@code DEBUG} level is currently being logged
     */
    public final boolean isDebugEnabled() {
        return isEnabledInternal( Level.DEBUG );
    }

    /**
     * Log a {@code DEBUG} message.
     *
     * @param msg the message
     */
    public final void debug( final Object msg ) {
        logInternal( Level.DEBUG, msg, null );
    }

    /**
     * Log a {@code DEBUG} message with associated throwable information.
     *
     * @param msg the message
     * @param t the throwable
     */
    public final void debug( final Object msg, final Throwable t ) {
        logInternal( Level.DEBUG, msg, t );
    }

    /**
     * Check if a message of the {@code INFO} level would actually be logged by this logger.
     *
     * @return {@code true} if the {@code INFO} level is currently being logged
     */
    public final boolean isInfoEnabled() {
        return isEnabledInternal( Level.INFO );
    }

    /**
     * Log an {@code INFO} message.
     *
     * @param msg the message
     */
    public final void info( final Object msg ) {
        logInternal( Level.INFO, msg, null );
    }

    /**
     * Log an {@code INFO} message with associated throwable information.
     *
     * @param msg the message
     * @param t the throwable
     */
    public final void info( final Object msg, final Throwable t ) {
        logInternal( Level.INFO, msg, t );
    }

    /**
     * Check if a message of the {@code WARNING} level would actually be logged by this logger.
     *
     * @return {@code true} if the {@code WARNING} level is currently being logged
     */
    public final boolean isWarnEnabled() {
        return isEnabledInternal( Level.WARN );
    }

    /**
     * Log a {@code WARNING} message.
     *
     * @param msg the message
     */
    public final void warn( final Object msg ) {
        logInternal( Level.WARN, msg, null );
    }

    /**
     * Log a {@code WARNING} message with associated throwable information.
     *
     * @param msg the message
     * @param t the throwable
     */
    public final void warn( final Object msg, final Throwable t ) {
        logInternal( Level.WARN, msg, t );
    }

    /**
     * Check if a message of the {@code ERROR} level would actually be logged by this logger.
     *
     * @return {@code true} if the {@code ERROR} level is currently being logged
     */
    public final boolean isErrorEnabled() {
        return isEnabledInternal( Level.ERROR );
    }

    /**
     * Log an {@code ERROR} message.
     *
     * @param msg the message
     */
    public final void error( final Object msg ) {
        logInternal( Level.ERROR, msg, null );
    }

    /**
     * Log an {@code ERROR} message with associated throwable information.
     *
     * @param msg the message
     * @param t the throwable
     */
    public final void error( final Object msg, final Throwable t ) {
        logInternal( Level.ERROR, msg, t );
    }

    /**
     * Log a message at specified <code>Level</code>.
     *
     * @param level debugging level
     * @param msg the message
     * @throws IllegalArgumentExceptoin if level is null.
     */
    public final void log( final Level level, final Object msg ) {
        if ( level == null ) {
            throw new IllegalArgumentException();
        }
        logInternal( level, msg, null );
    }

    /**
     * Log a message with associated throwable information at specified <code>Level</code>.
     *
     * @param msg the message
     * @param t the throwable
     * @throws IllegalArgumentExceptoin if level is null.
     */
    public final void log( final Level level, final Object msg, final Throwable t ) {
        if ( level == null ) {
            throw new IllegalArgumentException();
        }
        logInternal( level, msg, t );
    }
}
