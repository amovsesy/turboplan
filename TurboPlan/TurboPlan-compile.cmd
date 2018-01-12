::=====================================================================::
:: Modify this variable to the path of your gwt installation directory ::
::=====================================================================::
set gwt-dir=C:\Stuff\Classes\cpe402series\gwt-windows-1.5.3

@java -Xmx256M -cp "%~dp0\src;%~dp0\bin;%gwt-dir%/gwt-user.jar;%gwt-dir%/gwt-dev-windows.jar;lib/gwtext.jar;lib/ejb3-persistence.jar;lib/hibernate3.jar;lib/hibernate-validator.jar;lib/hibernate-annotations.jar;lib/gwt-log-2.5.3.jar" com.google.gwt.dev.GWTCompiler -out "%~dp0\www" %* edu.calpoly.csc.luna.turboplan.web.TurboPlan