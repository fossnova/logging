/*
 * Copyright (c) 2012, FOSS Nova Software foundation (FNSF),
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
final class Log4jLogger extends Logger {

    private static final long serialVersionUID = 1L;

    private final transient org.apache.log4j.Logger delegate;

    Log4jLogger( final String name ) {
        super( name );
        delegate = org.apache.log4j.Logger.getLogger( name );
    }

    @Override
    boolean isEnabled( final Level level ) {
        return delegate.isEnabledFor( translate( level ) );
    }

    @Override
    void log( final Level level, final Object msg, final Throwable t ) {
        if ( t == null ) {
            delegate.log( translate( level ), msg );
        } else {
            delegate.log( translate( level ), msg, t );
        }
    }

    private static org.apache.log4j.Level translate( final Level level ) {
        switch ( level ) {
            case TRACE:
                return org.apache.log4j.Level.TRACE;
            case DEBUG:
                return org.apache.log4j.Level.DEBUG;
            case INFO:
                return org.apache.log4j.Level.INFO;
            case WARN:
                return org.apache.log4j.Level.WARN;
            case ERROR:
                return org.apache.log4j.Level.ERROR;
            default:
                throw new IllegalStateException();
        }
    }
}
