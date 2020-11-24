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

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JdkLogger extends Logger {

    private static final long serialVersionUID = 1L;

    private final transient java.util.logging.Logger delegate;

    JdkLogger( final String name ) {
        super( name );
        delegate = java.util.logging.Logger.getLogger( name );
    }

    @Override
    boolean isEnabledInternal( final Level level ) {
        return delegate.isLoggable( translate( level ) );
    }

    @Override
    void logInternal( final Level level, final Object msg, final Throwable t ) {
        if ( t == null ) {
            delegate.log( translate( level ), String.valueOf( msg ) );
        } else {
            delegate.log( translate( level ), String.valueOf( msg ), t );
        }
    }

    private static java.util.logging.Level translate( final Level level ) {
        switch ( level ) {
            case TRACE:
                return java.util.logging.Level.FINEST;
            case DEBUG:
                return java.util.logging.Level.FINE;
            case INFO:
                return java.util.logging.Level.INFO;
            case WARN:
                return java.util.logging.Level.WARNING;
            case ERROR:
                return java.util.logging.Level.SEVERE;
            default:
                throw new IllegalStateException();
        }
    }
}
