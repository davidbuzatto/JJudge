package br.com.davidbuzatto.jjudge.utils;

import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class NoGuiModeWrapper {
    
    public static void runTest( File fileToTest, TestSet testSet ) {
        
        ResourceBundle bundle = Utils.bundle;
        List<File> javaClasspathFiles = Utils.processStoredFilesPath( Utils.getPref( Utils.PREF_JAVA_CLASSPATH_DEPENDENCIES_PATHS ) );

        new Thread( new Runnable() {

            @Override
            public void run() {

                try {

                    File destDir = new File( fileToTest.getAbsolutePath().replace( ".jjd", "" ).trim() );

                    System.out.println( String.format( bundle.getString( "MainWindow.btnRunTestActionPerformed.processing" ), fileToTest ) );

                    TestSetResult result = Utils.runTest( 
                            fileToTest, 
                            testSet, 
                            true, 
                            5, 
                            null,
                            javaClasspathFiles,
                            true );

                    if ( fileToTest.getName().endsWith( ".jjd" ) ) {
                        System.out.println( String.format( bundle.getString( "MainWindow.btnRunTestActionPerformed.cleaning" ), fileToTest ) );
                        FileUtils.deleteDirectory( destDir );
                    }

                } catch ( IOException exc ) {
                    System.out.println( bundle.getString( "MainWindow.btnRunTestActionPerformed.errorCompileAndRun" ) );
                    exc.printStackTrace();
                } catch ( InterruptedException exc ) {

                }

                System.out.println( bundle.getString( "MainWindow.btnRunTestActionPerformed.finished" ) );

            }

        }).start();
                
    }
    
}
