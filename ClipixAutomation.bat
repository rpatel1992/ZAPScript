set projectLocation=D:\Rakesh\Selenium\Regi
cd %projectLocation%
set classpath=%projectLocation%\target\test-classes;%projectLocation%\lib\*;
java org.testng.TestNG %projectLocation%\testng.xml
pause