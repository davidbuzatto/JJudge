@ECHO OFF

SET GCC_PATH="C:\Program Files (x86)\CodeBlocks\MinGW\bin"
SET GPP_PATH="C:\Program Files (x86)\CodeBlocks\MinGW\bin"
SET JDK_PATH="C:\Program Files\Java\jdk1.8.0_181\bin"
SET PYTHON_PATH="C:\WinPython-64bit-2.7.13.1Zero\python-2.7.13.amd64"

IF EXIST %GCC_PATH% SET PATH=%PATH%%C:\Program Files (x86)\CodeBlocks\MinGW\bin%;
IF EXIST %GPP_PATH% SET PATH=%PATH%%C:\Program Files (x86)\CodeBlocks\MinGW\bin%;
IF EXIST %JDK_PATH% SET PATH=%PATH%%C:\Program Files\Java\jdk1.8.0_181\bin%;
IF EXIST %PYTHON_PATH% SET PATH=%PATH%%C:\WinPython-64bit-2.7.13.1Zero\python-2.7.13.amd64%;

START javaw -jar JJudge.jar