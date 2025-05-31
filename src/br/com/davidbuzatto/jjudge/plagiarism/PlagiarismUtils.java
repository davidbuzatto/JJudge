package br.com.davidbuzatto.jjudge.plagiarism;

import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.utils.Utils;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class PlagiarismUtils {
    
    private static final DiffRowGenerator GENERATOR = DiffRowGenerator
            .create()
            .ignoreWhiteSpaces( true )
            .reportLinesUnchanged( false )
            .build();
    
    public static List<PlagiarismTestResult> runPlagiarismDetector( List<File> jjds ) throws IOException {    
        
        List<File> destDirs = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<PlagiarismTestResult> results = new ArrayList<>();
        
        for ( File f : jjds ) {
            String dirName = f.getName();
            dirName = dirName.substring( 0, dirName.lastIndexOf( ".jjd" ) );
            File destDir = new File( f.getParentFile().getAbsolutePath() + File.separator + dirName + "-jjd-temp" );
            destDirs.add( destDir );
            if ( destDir.exists() ) {
                FileUtils.deleteDirectory( destDir );
            }
            Utils.completeUnzip( f, destDir );
        }
        
        Map<String, List<File>> studentsFileMap = new LinkedHashMap<>();
        
        for ( File destDir : destDirs ) {
            Student s = Utils.loadStudent( destDir.getAbsolutePath() );
            List<File> pkgFiles = new ArrayList<>();
            studentsFileMap.put( s.getName(), pkgFiles );
            students.add( s );
            for ( File f : destDir.listFiles() ) {
                if ( f.isFile() && !f.getName().equals( "student.json" ) ) {
                    pkgFiles.add( f );
                }
            }
        }
        
        for ( int i = 0; i < students.size(); i++ ) {
            List<File> s1Files = studentsFileMap.get( students.get( i ).getName() );
            for ( int j = i + 1; j < students.size(); j++ ) {
                List<File> s2Files = studentsFileMap.get( students.get( j ).getName() );
                //System.out.println( students.get( i ) + " " + students.get( j ) );
                PlagiarismTestResult result = new PlagiarismTestResult( students.get( i ), students.get( j ) );
                results.add( result );
                for ( File f1 : s1Files ) {
                    for ( File f2 : s2Files ) {
                        if ( f1.getName().equals( f2.getName() ) ) {
                            List<String> f1Lines = Files.readAllLines( f1.toPath() );
                            List<String> f2Lines = Files.readAllLines( f2.toPath() );
                            double similarity = similarityCalc( GENERATOR, f1Lines, f2Lines, false );
                            //System.out.printf( "    %s x %s: %.2f%% (similarity)\n", f1.getName(), f2.getName(), similarityCalc( generator, f1Lines, f2Lines, false ) );
                            result.addSimilarityResult( f1, f2, similarity );
                        }
                    }
                }
            }
        }
        
        // cleanup
        for ( File destDir : destDirs ) {
            FileUtils.deleteDirectory( destDir );
        }
        
        return results;
        
    }
    
    private static List<DiffRow> diffRows( DiffRowGenerator generator, List<String> f1Lines, List<String> f2Lines ) throws IOException {
        return generator.generateDiffRows( f1Lines, f2Lines );
    }
    
    private static Patch<String> diffDelta( List<String> f1Lines, List<String> f2Lines ) throws IOException {
        return DiffUtils.diff( f1Lines, f2Lines );
    }
    
    private static double similarityCalc( DiffRowGenerator generator, List<String> f1Lines, List<String> f2Lines, boolean printData ) throws IOException {
        
        List<DiffRow> diffRows = diffRows( generator, f1Lines, f2Lines );
        int totalRows = diffRows.size();
        int equalRows = 0;
        
        for ( DiffRow d : diffRows ) {
            if ( d.getTag() == DiffRow.Tag.EQUAL ) {
                equalRows++;
            }
        }
        
        double similarity = (double) equalRows / totalRows;
        
        if ( printData ) {
            
            Patch<String> patch = diffDelta( f1Lines, f2Lines );
            List<AbstractDelta<String>> deltas = patch.getDeltas();
        
            for ( DiffRow d : diffRows ) {
                System.out.println( d );
            }

            System.out.printf( "Similarity: %.2f%%\n", similarity );

            if ( deltas.isEmpty() ) {
                System.out.println( "files are equal!!!" );
            } else {
                for ( AbstractDelta<String> d : patch.getDeltas() ) {
                    System.out.println( d );
                }
            }
            
        }
        
        return similarity;
        
    }
    
}
