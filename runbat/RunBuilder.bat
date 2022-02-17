@echo off
@chcp 65001 >nul

echo [36m╔═════════════════════════════════════════════════════════════╗[0m
echo [36m║ Olá aluno, seja bem-vindo^!                                  ║[0m
echo [36m║                                                             ║[0m
echo [36m║ Os comandos deste arquivo em lotes tentarão encontrar as    ║[0m
echo [36m║ dependências binárias do JJudge, com o objetivo de criar um ║[0m
echo [36m║ carregador de aplicação para o mesmo.                       ║[0m
echo [36m║                                                             ║[0m
echo [36m║ Este carregador será chamado de "run.bat" e será criado no  ║[0m
echo [36m║ mesmo diretório em que este arquivo.                        ║[0m
echo [36m║                                                             ║[0m
echo [36m║ Vamos começar?                                              ║[0m
echo [36m║                                                             ║[0m
echo [36m║ Desenvolvido por Prof. Dr. David Buzatto                    ║[0m
echo [36m╚═════════════════════════════════════════════════════════════╝[0m
echo/
echo Pressione qualquer tecla para iniciar o processo.
pause >nul
echo/

echo [94mBuscando pelos compiladores das linguagens C e C++, aguarde...[0m
dir /s /b "\" | findstr "CodeBlocks.*MinGW.*\\bin\\gcc\.exe" > "temp.txt"

for /f "delims=" %%a in (temp.txt) do (
  set caminhoC=%%a
  goto endF1
)
:endF1


echo [94mBuscando pelo compilador da linguagem Java, aguarde...[0m
dir /s /b "\" | findstr "jdk-.*\\bin\\javaw\.exe" > "temp.txt"

for /f "delims=" %%a in (temp.txt) do (
  set caminhoJava=%%a
  goto endF2
)
:endF2


echo [94mBuscando pelo interpretador da linguagem Python, aguarde...[0m
dir /s /b "\" | findstr "\\python\.exe" > "temp.txt"

for /f "delims=" %%a in (temp.txt) do (
  set caminhoPython=%%a
  goto endF3
)
:endF3

del temp.txt

echo/
echo [33mGerando arquivo em lotes "run.bat", aguarde...[0m

echo @ECHO OFF> run.bat
echo/>> run.bat
echo SET GCC_PATH=%caminhoC:~0,-8%>> run.bat
echo SET GPP_PATH=%caminhoC:~0,-8%>> run.bat
echo SET JDK_PATH=%caminhoJava:~0,-10%>> run.bat
echo SET PYTHON_PATH=%caminhoPython:~0,-11%>> run.bat
echo/>> run.bat
echo IF EXIST "%%GCC_PATH%%" SET PATH=%%PATH%%;%%GCC_PATH%%>> run.bat
echo IF EXIST "%%GPP_PATH%%" SET PATH=%%PATH%%;%%GPP_PATH%%>> run.bat
echo IF EXIST "%%JDK_PATH%%" SET PATH=%%PATH%%;%%JDK_PATH%%>> run.bat
echo IF EXIST "%%PYTHON_PATH%%" SET PATH=%%PATH%%;%%PYTHON_PATH%%>> run.bat
echo/>> run.bat
echo START "" "%caminhoJava%" -jar JJudge.jar -stt 5 >> run.bat

echo [32mArquivo gerado^![0m
echo/

echo Pressione qualquer tecla para finalizar.
pause >nul