#!/bin/sh
APPDIR=`dirname $0`;
java  -Xmx256M -cp "$APPDIR/src:$APPDIR/bin:/usr/gwt/gwt-linux-1.5.3/gwt-user.jar:/usr/gwt/gwt-linux-1.5.3/gwt-dev-linux.jar:lib/gwtext.jar:lib/ejb3-persistence.jar:lib/hibernate3.jar:lib/hibernate-validator.jar:lib/hibernate-annotations.jar:lib/gwt-log-2.5.3.jar" com.google.gwt.dev.GWTCompiler -out "$APPDIR/www" "$@" edu.calpoly.csc.luna.turboplan.web.TurboPlan;
